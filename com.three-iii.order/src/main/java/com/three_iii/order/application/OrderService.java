package com.three_iii.order.application;

import com.three_iii.order.application.dto.CompanyDto;
import com.three_iii.order.application.dto.OrderItemRequestDto;
import com.three_iii.order.application.dto.OrderRequestDto;
import com.three_iii.order.application.dto.OrderResponseDto;
import com.three_iii.order.application.dto.OrderUpdateRequestDto;
import com.three_iii.order.application.dto.ProductDto;
import com.three_iii.order.application.dto.UserDto;
import com.three_iii.order.domain.Delivery;
import com.three_iii.order.domain.DeliveryStatusEnum;
import com.three_iii.order.domain.Order;
import com.three_iii.order.domain.OrderItem;
import com.three_iii.order.domain.OrderStatusEnum;
import com.three_iii.order.domain.Role;
import com.three_iii.order.domain.UserPrincipal;
import com.three_iii.order.domain.repository.DeliveryPathRepository;
import com.three_iii.order.domain.repository.DeliveryRepository;
import com.three_iii.order.domain.repository.OrderRepository;
import com.three_iii.order.infrastructure.ProductClient;
import com.three_iii.order.infrastructure.UserServiceClient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserServiceClient userServiceClient;

    // 주문 전체 조회
    public Page<OrderResponseDto> findAllOrder(String keyword, Pageable pageable,
        UserPrincipal userPrincipal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 로그인한 사용자 이름을 가져옴

        // 사용자 권한에 따라 처리
        boolean isUser = authentication.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("CUSTOMER"));

        Long userId = getUserIdFromUsername(username); // 사용자 이름으로 사용자 id조회

        if (isUser) {
            // 사용자인 경우 자신의 주문만 조회 가능
            return orderRepository.searchOrder(keyword, userId, pageable);
        } else {
            // 외 관리자 등은 전체 주문 조회 가능
            return orderRepository.searchOrder(keyword, null, pageable);
        }
    }

    private Long getUserIdFromUsername(String username) {
        UserDto userDto = userServiceClient.getUserByUsername(username);
        return userDto.getId();
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

        Delivery delivery = createDelivery(requestDto, savedOrder);
        deliveryRepository.save(delivery);

//        order.setDelivery(delivery);

        return OrderResponseDto.from(savedOrder);
    }


    // 배송 정보 생성 메서드
    private Delivery createDelivery(OrderRequestDto requestDto, Order order) {
        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setAddress(requestDto.getDeliveryAddress());
        delivery.setOriginHubId(UUID.randomUUID());
        delivery.setDestinationHubId(UUID.randomUUID());
        delivery.setRecipientName(requestDto.getRecipientName());
        delivery.setSlackId(requestDto.getSlackId());
        delivery.setStatus(DeliveryStatusEnum.HUB_WAITING);

        delivery.setCreatedAt(LocalDateTime.now());
        delivery.setCreatedBy(String.valueOf(order.getUserId()));

        return delivery;
    }

    // 고정된 경로 생성 메서드 (허브 경로를 고정된 순서로 설정)
//    private void createFixedDeliveryPath(Delivery delivery, UUID originHubId,
//        UUID destinationHubId) {
//        // 모든 허브를 시퀀스 순서로 조회
//        List<Hub> hubs = hubRepository.findAllByOrderBySequenceAsc();
//
//        List<DeliveryPath> paths = new ArrayList<>();
//
//        boolean recordPath = false;
//
//        // 출발 허브부터 목적지 허브까지의 경로를 설정
//        for (Hub hub : hubs) {
//            if (hub.getId().equals(originHubId)) {
//                recordPath = true;  // 출발 허브부터 경로를 기록 시작
//            }
//
//            if (recordPath) {
//                DeliveryPath path = new DeliveryPath();
//                path.setDelivery(delivery);
//                path.setOriginHubId(hub.getId());
//                path.setDestinationHubId(hub.getId());  // 임시로 같은 허브를 설정 (필요시 수정)
//                path.setSequence(hub.getSequence());
//                path.setStatus(DeliveryStatusEnum.HUB_WAITING);  // 초기 상태 설정
//                paths.add(path);
//            }
//
//            if (hub.getId().equals(destinationHubId)) {
//                break;  // 목적지 허브까지 경로 설정 완료 시 루프 종료
//            }
//        }
//
//        delivery.setDeliveryPaths(paths);  // 배송에 경로 리스트 추가
//        deliveryPathRepository.saveAll(paths);  // 모든 경로 저장
//    }

    // 주문 단건 조회
    public OrderResponseDto findOrder(UUID orderId, UserPrincipal userPrincipal) {
        Order order = orderRepository.findOneByOrderItemsOrderItemIdAndDeletedAtIsNull(orderId)
            .orElseThrow(() -> {
                log.error("주문 정보를 찾을 수 없습니다.");
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없습니다.");
            });

        if (!order.getUserId().equals(userPrincipal.getId())) {
            log.error("접근 권한이 없습니다.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
        return OrderResponseDto.from(order);
    }

    // 주문 수정
    @Transactional
    public OrderResponseDto updateOrder(OrderUpdateRequestDto orderUpdateRequestDto,
        UserPrincipal userPrincipal) {

        Order order = orderRepository.findOneByOrderItemsOrderItemIdAndDeletedAtIsNull(
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

            if (productDto.getQuantity() < requestedQuantity) {
                log.error("상품 재고가 부족합니다. 요청 수량: {}, 남은 재고: {}", requestedQuantity,
                    productDto.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 재고가 부족합니다.");
            }

            // Feign Client로 Product 서비스의 재고를 업데이트
            productClient.updateProductStock(productDto.getId(),
                productDto.getQuantity() - requestedQuantity);

            updatedOrderItem.add(OrderItem.builder()
                .order(order)
                .productId(productDto.getId())
                .quantity(itemRequestDto.getProductQuantity())
                .build());
        }

        order.updateOrderItem(updatedOrderItem);
        orderRepository.save(order);

        return OrderResponseDto.from(order);
    }

    // 주문 취소
    @Transactional
    public void cancelOrder(UUID orderId, UserPrincipal userPrincipal) {
        Order order = orderRepository.findOneByOrderItemsOrderItemIdAndDeletedAtIsNull(orderId)
            .orElseThrow(() -> {
                log.error("주문 정보를 찾을 수 없습니다.");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없습니다.");
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
                productClient.restoreStock(orderItem.getOrderItemId(), orderItem.getQuantity());
            } catch (Exception e) {
                log.error("재고 복원 중 오류 발생: {}", e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "재고 복원 중 오류 발생");
            }
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
                productClient.restoreStock(orderItem.getOrderItemId(), orderItem.getQuantity());
            } catch (Exception e) {
                log.error("재고 복원 중 오류 발생: {}", e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "재고 복원 중 오류 발생");
            }
        }
        order.delete("");

//        orderRepository.save(order);
    }
}
