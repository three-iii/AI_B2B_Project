package com.three_iii.hub.domain.repository;

import static com.three_iii.hub.domain.QHubPath.hubPath;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.three_iii.hub.application.dtos.HubPathResponse;
import com.three_iii.hub.domain.HubPath;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class HubPathRepositoryImpl implements HubPathRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<HubPathResponse> searchHubPath(String keyword, Pageable pageable) {
        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);

        QueryResults<HubPath> results = queryFactory
            .selectFrom(hubPath)
            .where(
                nameContains(keyword)
            )
            .orderBy(orders.toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        List<HubPathResponse> content = results.getResults().stream()
            .map(HubPathResponse::fromEntity)
            .collect(Collectors.toList());
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression nameContains(String keyword) {
        return keyword != null ? hubPath.name.containsIgnoreCase(keyword) : null;
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
                        orders.add(new OrderSpecifier<>(direction, hubPath.createdAt));
                        break;
                    case "updatedAt":
                        orders.add(new OrderSpecifier<>(direction, hubPath.updatedAt));
                        break;
                    default:
                        break;
                }
            }
        }

        return orders;
    }

}
