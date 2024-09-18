package com.three_iii.service.application;

import com.three_iii.service.application.dto.order.OrderItemRequestDto;
import com.three_iii.service.application.dto.order.OrderRequestDto;
import com.three_iii.service.application.dto.order.OrderResponseDto;
import com.three_iii.service.application.dto.order.OrderUpdateRequestDto;
import com.three_iii.service.domain.Company;
import com.three_iii.service.domain.Order;
import com.three_iii.service.domain.OrderItem;
import com.three_iii.service.domain.OrderStatusEnum;
import com.three_iii.service.domain.Product;
import com.three_iii.service.domain.Role;
import com.three_iii.service.domain.User;
import com.three_iii.service.domain.repository.CompanyRepository;
import com.three_iii.service.domain.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CompanyRepository companyRepository;

    // 주문 추가
    public OrderResponseDto createOrder(OrderRequestDto requestDto, User user) {
        Company company = companyRepository.findById(
            requestDto.getRequestCompanyId()).orElseThrow(() -> {
            log.error("생산 업체 정보를 찾을 수 없습니다.");
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "생산 업체 정보를 찾을 수 없습니다.");
        });

        Order order = requestDto.toOrder(user, company);

        for (OrderItemRequestDto itemRequestDto : requestDto.getOrderItemRequestDto()) {
            Product product = productRepository.findByProductIdAndDeletedAtIsNull(
                productRequest.getProductId()).orElseThrow(() -> {
                    log.error("상품 정보를 찾을 수 없습니다.");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "상품 정보를 찾을 수 없습니다.");
                }
            );

            if (productRequest.getProductQuantity() <= 0) {
                log.error("상품 수량이 잘못 되었습니다.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 수량이 잘못 되었습니다.");
            }

            int requestedQuantity = itemRequestDto.getProductQuantity();

            if (product.getStockQuantity() < requestedQuantity) {
                log.error("상품 재고가 부족합니다. 요청 수량: {}, 남은 재고: {}", requestedQuantity,
                    product.getStockQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 재고가 부족합니다.");
            }

            product.setStockQuantity(product.getStockQuantity() - requestedQuantity);
            productRepository.save(product);

            order.addOrderItem(OrderItem.builder()
                .order(order)
                .product(product)
                .productQuantity(productRequest.getProductQuantity())
                .build());
        }
        return OrderResponseDto.from(orderRepository.save(order));
    }

    // 주문 단건 조회
    public OrderResponseDto findOrder(UUID orderId, User user) {
        Order order = orderRepository.findOneByOrderIdAndDeletedAtIsNull(orderId)
            .orElseThrow(() -> {
                log.error("주문 정보를 찾을 수 없습니다.");
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없습니다.");
            });

        if (!order.getUserId().equals(user.getId())) {
            log.error("접근 권한이 없습니다.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
        return OrderResponseDto.from(order);
    }

    // 주문 수정
    @Transactional
    public OrderResponseDto updateOrder(OrderUpdateRequestDto orderUpdateRequestDto,
        User user) {
        Order order = orderRepository.findOneByOrderIdAndDeletedAtIsNull(
            orderUpdateRequestDto.getOrderId()).orElseThrow(() -> {
            log.error("주문 정보를 찾을 수 없습니다.");
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없습니다.");
        });

        // 사용자 권한 체크
        Role userRole = user.getRole();
        boolean isAuthorizedRole = userRole == Role.MASTER_MANAGER || userRole == Role.HUB_MANAGER;

        if (!isAuthorizedRole && !order.getUserId().equals(user.getId())) {
            log.error("접근 권한이 없습니다.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }

        List<OrderItem> updatedOrderItem = new ArrayList<>();
        for (OrderItemRequestDto orderItemRequestDto : orderUpdateRequestDto.getOrderItemRequestDto()) {
            Product product = productRepository.findByProductIdAndDeletedAtIsNull(
                productRequest.getProductId()).orElseThrow(() -> {
                log.error("상품 정보를 찾을 수 없습니다.");
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "상품 정보를 찾을 수 없습니다.");
            });

            if (orderItemRequestDto.getProductQuantity() <= 0) {
                log.error("상품 수량이 잘못 되었습니다.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 수량이 잘못 되었습니다.");
            }

            int requestedQuantity = itemRequestDto.getProductQuantity();

            if (product.getStockQuantity() < requestedQuantity) {
                log.error("상품 재고가 부족합니다. 요청 수량: {}, 남은 재고: {}", requestedQuantity,
                    product.getStockQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 재고가 부족합니다.");
            }

            product.setStockQuantity(product.getStockQuantity() - requestedQuantity);
            productRepository.save(product);

            updatedOrderItem.add(OrderItem.builder()
                .order(order)
                .product(product)
                .productQuantity(orderItemRequestDto.getProductQuantity())
                .build());
        }

        order.updateOrderItem(updatedOrderItem);
        orderRepository.save(order);

        return OrderResponseDto.from(order);
    }

    // 주문 취소
    @Transactional
    public void cancelOrder(UUID orderId, User user) {
        Order order = orderRepository.findOneByOrderIdAndDeletedAtIsNull(orderId)
            .orElseThrow(() -> {
                log.error("주문 정보를 찾을 수 없습니다.");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없습니다.");
            });

        // 사용자 권한 체크
        Role userRole = user.getRole();
        boolean isAuthorizedRole = userRole == Role.MASTER_MANAGER || userRole == Role.HUB_MANAGER;

        if (!isAuthorizedRole && !order.getUserId().equals(user.getId())) {
            log.error("접근 권한이 없습니다.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }

        //  주문 항목을 순회하면서 재고 복원
        for (OrderItem orderItem : order.getOrderItems()) {
            productService.restoreStock(orderItem.getProductId(), orderItem.getQuantity());
        }

        order.setStatus(OrderStatusEnum.CANCELED);
        order.delete(LocalDateTime.now(), userName.getUsername());
        order.setIsDelete(true);

        orderRepository.save(order);
    }

    // 주문 삭제
    @Transactional
    public void deleteOrder(UUID orderId, User user) {
        Order order = orderRepository.findOneByOrderIdAndDeletedAtIsNull(orderId)
            .orElseThrow(() -> {
                log.error("주문 정보를 찾을 수 없습니다.");
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없습니다.");
            });

        if (!order.getUserId().equals(user.getId())) {
            log.error("접근 권한이 없습니다.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }

        for (OrderItem orderItem : order.getOrderItems()) {
            productService.restoreStock(orderItem.getProductId(), orderItem.getQuantity());
        }

        order.delete(LocalDateTime.now(), user.getUsername());
        order.setIsDelete(true);

        orderRepository.save(order);
    }
}
