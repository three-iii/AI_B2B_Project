package com.three_iii.order.domain.repository;

import static com.three_iii.order.domain.QDelivery.delivery;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.three_iii.order.application.dto.DeliveryResponseDto;
import com.three_iii.order.domain.Delivery;
import com.three_iii.order.domain.QOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<DeliveryResponseDto> searchDelivery(String keyword, String Username,
        Pageable pageable) {

        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);

        QueryResults<Delivery> results = queryFactory
            .selectFrom(delivery)
            .where(
                nameContains(keyword)
            )
            .orderBy(orders.toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        List<DeliveryResponseDto> content = results.getResults().stream()
            .map(DeliveryResponseDto::from)
            .collect(Collectors.toList());
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression nameContains(String keyword) {
        return keyword != null ? QOrder.order.userName.containsIgnoreCase(keyword) : null;
    }

    private BooleanExpression userIdMatches(String userName) {
        return userName != null ? QOrder.order.userName.eq(userName) : null;
    }

    private List<OrderSpecifier<?>> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (pageable.getSort() != null) {
            for (Sort.Order sortOrder : pageable.getSort()) {
                com.querydsl.core.types.Order direction =
                    sortOrder.isAscending() ? com.querydsl.core.types.Order.ASC
                        : com.querydsl.core.types.Order.DESC;
                switch (sortOrder.getProperty()) {
                    case "createdAt":
                        orders.add(new OrderSpecifier<>(direction, QOrder.order.createdAt));
                        break;
                    case "updatedAt":
                        orders.add(new OrderSpecifier<>(direction, QOrder.order.updatedAt));
                        break;
                    default:
                        break;
                }
            }
        }

        return orders;
    }
}
