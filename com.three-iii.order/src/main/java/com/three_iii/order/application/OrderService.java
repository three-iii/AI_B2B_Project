package com.three_iii.order.application;

import com.three_iii.order.application.dto.CompanyDto;
import com.three_iii.order.application.dto.HubDto;
import com.three_iii.order.application.dto.OrderItemRequestDto;
import com.three_iii.order.application.dto.OrderRequestDto;
import com.three_iii.order.application.dto.OrderResponseDto;
import com.three_iii.order.application.dto.OrderUpdateRequestDto;
import com.three_iii.order.application.dto.ProductDto;
import com.three_iii.order.domain.Delivery;
import com.three_iii.order.domain.DeliveryPath;
import com.three_iii.order.domain.DeliveryStatusEnum;
import com.three_iii.order.domain.Order;
import com.three_iii.order.domain.OrderItem;
import com.three_iii.order.domain.OrderStatusEnum;
import com.three_iii.order.domain.Role;
import com.three_iii.order.domain.UserPrincipal;
import com.three_iii.order.domain.repository.DeliveryPathRepository;
import com.three_iii.order.domain.repository.DeliveryRepository;
import com.three_iii.order.domain.repository.OrderRepository;
import com.three_iii.order.exception.Response;
import com.three_iii.order.infrastructure.HubClient;
import com.three_iii.order.infrastructure.ProductClient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryPathRepository deliveryPathRepository;
    private final HubClient hubClient;

    // 주문 전체 조회
    public Page<OrderResponseDto> findAllOrder(String keyword, Pageable pageable,
        UserPrincipal userPrincipal) {
        String role = userPrincipal.getRole();

        if (role.equals("CUSTOMER")) {
            // 사용자인 경우 자신의 주문만 조회 가능
            return orderRepository.searchOrder(keyword, userPrincipal.getUsername(), pageable);
        } else {
            // 외 관리자 등은 전체 주문 조회 가능
            return orderRepository.searchOrder(keyword, null, pageable);
        }
    }

    // 주문 추가
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, UserPrincipal userPrincipal) {
        // 입력 검증
        requestDto.validate();

        String authToken = "Bearer " + userPrincipal.getToken();
        log.info("Auth Token: {}", authToken);
        if (!StringUtils.hasText(authToken)) {
            throw new IllegalArgumentException("Authorization token cannot be null or empty");
        }

        // 모든 생산 업체를 조회
        Page<CompanyDto> productionCompanyPage = productClient.getCompanyById().getResult();
        if (productionCompanyPage == null) {
            log.error("생산 업체 정보를 찾을 수 없습니다.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "생산 업체 정보를 찾을 수 없습니다.");
        }

        // 특정 조건에 맞는 생산 업체를 필터링
        Optional<CompanyDto> productionCompanyOptional = productionCompanyPage.getContent().stream()
            .filter(company -> company.getId()
                .equals(requestDto.getProductionCompanyId()))
            .findFirst();

        if (!productionCompanyOptional.isPresent()) {
            log.error("해당 생산 업체를 찾을 수 없습니다.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 생산 업체를 찾을 수 없습니다.");
        }

        CompanyDto productionCompany = productionCompanyOptional.get();

        // 모든 수령 업체를 조회
        Page<CompanyDto> receiptCompanyPage = productClient.getCompanyById().getResult();
        if (receiptCompanyPage == null) {
            log.error("수령 업체 정보를 찾을 수 없습니다.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수령 업체 정보를 찾을 수 없습니다.");
        }
//
//        // 특정 조건에 맞는 수령 업체를 필터링
        Optional<CompanyDto> receiptCompanyOptional = receiptCompanyPage.getContent().stream()
            .filter(
                company -> company.getId().equals(requestDto.getReceiptCompanyId()))
            .findFirst();

        if (!receiptCompanyOptional.isPresent()) {
            log.error("해당 수령 업체를 찾을 수 없습니다.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 수령 업체를 찾을 수 없습니다.");
        }

        CompanyDto receiptCompany = receiptCompanyOptional.get();

        Order order = requestDto.toOrder(userPrincipal, productionCompany.getId(),
            receiptCompany.getId());

        for (OrderItemRequestDto itemRequestDto : requestDto.getOrderItemRequestDto()) {
            Page<ProductDto> productPage = productClient.findAllProduct().getResult();
            if (productPage == null) {
                log.error("상품 정보를 찾을 수 없습니다.");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "상품 정보를 찾을 수 없습니다.");
            }

            ProductDto productDto = productPage.getContent().stream()
                .filter(product -> product.getId().equals(itemRequestDto.getProductId()))
                .findFirst()
                .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다."));

            int requestedQuantity = itemRequestDto.getProductQuantity();

            if (requestedQuantity <= 0) {
                log.error("상품 수량이 잘못 되었습니다.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 수량이 잘못 되었습니다.");
            }

            if (productDto.getQuantity() < requestedQuantity) {
                log.error("상품 재고가 부족합니다. 요청 수량: {}, 남은 재고: {}", requestedQuantity,
                    productDto.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 재고가 부족합니다.");
            }

            // Feign Client로 Product 서비스의 재고를 업데이트
            productClient.updateProductStock(productDto.getId(),
                productDto.getQuantity() - requestedQuantity);

            order.addOrderItem(OrderItem.builder()
                .order(order)
                .productId(productDto.getId())
                .quantity(itemRequestDto.getProductQuantity())
                .build());
        }

        Order savedOrder = orderRepository.save(order);

        Delivery delivery = createDelivery(requestDto, savedOrder, productionCompany,
            receiptCompany);
        deliveryRepository.save(delivery);

        // 배송 경로 생성
        createFixedDeliveryPath(delivery, delivery.getOriginHubId(),
            delivery.getDestinationHubId());

        savedOrder.setDelivery(delivery);

        return OrderResponseDto.from(savedOrder);
    }


    // 배송 정보 생성 메서드
    private Delivery createDelivery(OrderRequestDto requestDto, Order order,
        CompanyDto productionCompany, CompanyDto receiptCompany) {
        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setAddress(requestDto.getDeliveryAddress());
        delivery.setProductionCompany(productionCompany.getId());  // 생산 업체 ID 설정
        delivery.setReceiptCompany(receiptCompany.getId());  // 수령 업체 ID 설정
        delivery.setOriginHubId(productionCompany.getHubId());  // 생산 업체의 허브 ID를 출발 허브로 설정
        delivery.setDestinationHubId(receiptCompany.getHubId());  // 수령 업체의 허브 ID를 도착 허브로 설정
        delivery.setRecipientName(requestDto.getRecipientName());
        delivery.setSlackId(requestDto.getSlackId());
        delivery.setStatus(DeliveryStatusEnum.HUB_WAITING);

        delivery.setCreatedAt(LocalDateTime.now());
        delivery.setCreatedBy(String.valueOf(order.getUserId()));

        return delivery;
    }

    // 고정된 경로 생성 메서드 (허브 경로를 고정된 순서로 설정)
    private void createFixedDeliveryPath(Delivery delivery, UUID originHubId,
        UUID destinationHubId) {
        // 모든 허브를 시퀀스 순서로 조회
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);  // 모든 페이지를 가져오기 위해
        Response<Page<HubDto>> response = hubClient.getAllHubs();

        // 허브 리스트 추출
        List<HubDto> hubResponses = response.getResult().getContent();
        List<DeliveryPath> paths = new ArrayList<>();
        boolean recordPath = false;

        HubDto previousHub = null;  // 이전 허브를 추적하기 위한 변수
        int sequence = 0;

        // 출발 허브부터 목적지 허브까지의 경로를 설정
        for (HubDto hubResponse : hubResponses) {
            if (hubResponse.getId().equals(originHubId)) {
                recordPath = true;  // 출발 허브부터 경로 기록 시작
            }

            if (recordPath) {
                if (previousHub != null) {
                    // 현재 허브와 이전 허브 사이의 경로를 설정
                    DeliveryPath path = new DeliveryPath();
                    path.setDelivery(delivery);
                    path.setOriginHubId(previousHub.getId());
                    path.setDestinationHubId(hubResponse.getId());
                    path.setSequence(sequence++);
                    path.setStatus(DeliveryStatusEnum.HUB_WAITING);  // 초기 상태 설정
                    path.setEstimatedDistance("0.0"); // 적절한 값으로 설정
                    path.setActualDistance("0.0"); // 적절한 값으로 설정
                    path.setEstimatedDuration("0.0"); // 적절한 값으로 설정
                    path.setActualDuration("0.0"); // 적절한 값으로 설정
                    path.setShipperId(UUID.randomUUID()); // 적절한 값을 설정하거나 외부에서 값을 가져옵니다.

                    paths.add(path);
                }

                previousHub = hubResponse;  // 현재 허브를 이전 허브로 설정
            }

            if (hubResponse.getId().equals(destinationHubId)) {
                // 목적지 허브까지의 경로 설정
                if (previousHub != null) {
                    DeliveryPath finalPath = new DeliveryPath();
                    finalPath.setDelivery(delivery);
                    finalPath.setOriginHubId(previousHub.getId());
                    finalPath.setDestinationHubId(destinationHubId);  // 최종 목적지 허브를 설정
                    finalPath.setSequence(sequence++);
                    finalPath.setStatus(DeliveryStatusEnum.HUB_WAITING);  // 초기 상태 설정
                    finalPath.setEstimatedDistance("0.0"); // 적절한 값으로 설정
                    finalPath.setActualDistance("0.0"); // 적절한 값으로 설정
                    finalPath.setEstimatedDuration("0.0"); // 적절한 값으로 설정
                    finalPath.setActualDuration("0.0"); // 적절한 값으로 설정
                    finalPath.setShipperId(UUID.randomUUID()); // 적절한 값을 설정하거나 외부에서 값을 가져옵니다.

                    paths.add(finalPath);
                }
                break;  // 목적지 허브까지 경로 설정 완료 시 루프 종료
            }
        }

        delivery.setDeliveryPaths(paths);  // 배송에 경로 리스트 추가

        // 경로를 저장합니다. 이 시점에서 모든 엔티티가 영속성 컨텍스트에 존재하게 됩니다.
        for (DeliveryPath path : paths) {
            deliveryPathRepository.save(path);  // 새 엔티티를 저장
        }
    }

    // 주문 단건 조회
    public OrderResponseDto findOrder(UUID orderId, UserPrincipal userPrincipal) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> {
                log.error("주문 정보를 찾을 수 없습니다.");
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없습니다.");
            });

        if (!order.getUserName().equals(userPrincipal.getUsername())) {
            log.error("접근 권한이 없습니다.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
        return OrderResponseDto.from(order);
    }

    // 주문 수정
    @Transactional
    public OrderResponseDto updateOrder(OrderUpdateRequestDto orderUpdateRequestDto,
        UserPrincipal userPrincipal) {

        Order order = orderRepository.findById(
            orderUpdateRequestDto.getOrderId()).orElseThrow(() -> {
            log.error("주문 정보를 찾을 수 없습니다.");
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없습니다.");
        });

        // 사용자 권한 체크
        Role userRole = Role.valueOf(userPrincipal.getRole());
        boolean isAuthorizedRole = userRole == Role.MASTER_MANAGER || userRole == Role.HUB_MANAGER;

        if (!isAuthorizedRole && !order.getUserId().equals(userPrincipal.getId())) {
            log.error("접근 권한이 없습니다.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }

        List<OrderItem> updatedOrderItem = new ArrayList<>();

        for (OrderItemRequestDto itemRequestDto : orderUpdateRequestDto.getOrderItemRequestDto()) {
            Page<ProductDto> productPage = productClient.findAllProduct().getResult();
            if (productPage == null) {
                log.error("상품 정보를 찾을 수 없습니다.");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "상품 정보를 찾을 수 없습니다.");
            }

            ProductDto productDto = productPage.getContent().stream()
                .filter(product -> product.getId().equals(itemRequestDto.getProductId()))
                .findFirst()
                .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다."));

            int requestedQuantity = itemRequestDto.getProductQuantity();

            if (requestedQuantity <= 0) {
                log.error("상품 수량이 잘못 되었습니다.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 수량이 잘못 되었습니다.");
            }

            // 기존 주문 항목에서 해당 상품의 수량 찾기
            OrderItem existingOrderItem = order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getProductId().equals(productDto.getId()))
                .findFirst()
                .orElse(null);

            int previousQuantity =
                (existingOrderItem != null) ? existingOrderItem.getQuantity() : 0;
            int quantityDifference = requestedQuantity - previousQuantity;

            // 재고 부족 확인 (추가로 주문한 경우에만 확인)
            if (quantityDifference > 0 && productDto.getQuantity() < quantityDifference) {
                log.error("상품 재고가 부족합니다. 요청 추가 수량: {}, 남은 재고: {}", quantityDifference,
                    productDto.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 재고가 부족합니다.");
            }

            // 재고 업데이트 (차이만큼만 반영)
            if (quantityDifference != 0) {
                // Feign Client로 Product 서비스의 재고를 업데이트 (수량 차이만큼 재고를 차감 또는 복원)
                productClient.updateProductStock(productDto.getId(),
                    productDto.getQuantity() - quantityDifference);  // 차이만큼만 업데이트
            }

            if (existingOrderItem != null) {
                existingOrderItem.setQuantity(requestedQuantity);
                updatedOrderItem.add(existingOrderItem);
            } else {
                updatedOrderItem.add(OrderItem.builder()
                    .order(order)
                    .productId(productDto.getId())
                    .quantity(itemRequestDto.getProductQuantity())
                    .build());
            }
        }

        order.updateOrderItem(updatedOrderItem);
        orderRepository.save(order);

        return OrderResponseDto.from(order);
    }

    // 주문 취소
    @Transactional
    public void cancelOrder(UUID orderId, UserPrincipal userPrincipal) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> {
                log.error("주문 정보를 찾을 수 없습니다.");
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없습니다.");
            });

        // 사용자 권한 체크
        Role userRole = Role.valueOf(userPrincipal.getRole());
        boolean isAuthorizedRole = userRole == Role.MASTER_MANAGER || userRole == Role.HUB_MANAGER;

        if (!isAuthorizedRole && !order.getUserId().equals(userPrincipal.getId())) {
            log.error("접근 권한이 없습니다.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }

        //  주문 항목을 순회하면서 재고 복원
        for (OrderItem orderItem : order.getOrderItems()) {
            try {
                productClient.restoreStock(orderItem.getProductId(), orderItem.getQuantity());
            } catch (Exception e) {
                log.error("재고 복원 중 오류 발생: {}", e.getMessage(), e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "재고 복원 중 오류 발생");
            }
        }

        Delivery delivery = order.getDelivery();
        if (delivery != null) {
            delivery.setDelete(LocalDateTime.now(), userPrincipal.getUsername());
        }

        order.setStatus(OrderStatusEnum.CANCELED);
        order.setDelete(LocalDateTime.now(), userPrincipal.getUsername());
        order.delete("");

        orderRepository.save(order);
    }

    // 주문 삭제
    @Transactional
    public void deleteOrder(UUID orderId, UserPrincipal userPrincipal) {
        Order order = orderRepository.findOneByOrderItemsOrderItemIdAndDeletedAtIsNull(orderId)
            .orElseThrow(() -> {
                log.error("주문 정보를 찾을 수 없습니다.");
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없습니다.");
            });

        if (!order.getUserId().equals(userPrincipal.getId())) {
            log.error("접근 권한이 없습니다.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }

        //  주문 항목을 순회하면서 재고 복원
        for (OrderItem orderItem : order.getOrderItems()) {
            try {
                productClient.restoreStock(orderItem.getProductId(), orderItem.getQuantity());
            } catch (Exception e) {
                log.error("재고 복원 중 오류 발생: {}", e.getMessage(), e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "재고 복원 중 오류 발생");
            }
        }
        Delivery delivery = order.getDelivery();
        if (delivery != null) {
            delivery.setDelete(LocalDateTime.now(), userPrincipal.getUsername());
        }

        order.setStatus(OrderStatusEnum.CANCELED);
        order.setDelete(LocalDateTime.now(), userPrincipal.getUsername());
        order.delete("");

        orderRepository.save(order);
    }

    public List<OrderResponseDto> findAllOrderBetweenTime() {
        // 24시간 범위로 주문 조회
        LocalDateTime startTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endTime = LocalDateTime.now();
        List<Order> deliveries = orderRepository.findAllByCreatedAtBetween(startTime,
            endTime);

        return deliveries.stream()
            .map(OrderResponseDto::from)
            .collect(Collectors.toList());
    }
}
