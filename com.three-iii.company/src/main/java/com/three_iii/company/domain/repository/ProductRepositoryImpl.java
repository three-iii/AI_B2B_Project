package com.three_iii.company.domain.repository;


import static com.three_iii.company.domain.QCompany.company;
import static com.three_iii.company.domain.QProduct.product;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.three_iii.company.application.dto.ProductResponse;
import com.three_iii.company.domain.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductResponse> searchProduct(String keyword, UUID hubId, UUID companyId,
        Pageable pageable) {
        List<OrderSpecifier<?>> orders = getAllOrderSpecifiers(pageable);

        QueryResults<Product> results = queryFactory
            .selectFrom(product)
            .where(
                nameContains(keyword),
                isMatchByHub(hubId),
                isMatchByCompany(companyId),
                company.is_delete.eq(false)
            )
            .orderBy(orders.toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        List<ProductResponse> content = results.getResults().stream()
            .map(ProductResponse::fromEntity)
            .collect(Collectors.toList());
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression isMatchByHub(UUID hubId) {
        return hubId != null ? product.hubId.eq(hubId) : null;
    }

    private BooleanExpression isMatchByCompany(UUID companyId) {
        return companyId != null ? product.company.id.eq(companyId) : null;
    }

    private BooleanExpression nameContains(String keyword) {
        return keyword != null ? product.name.containsIgnoreCase(keyword) : null;
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
                        orders.add(new OrderSpecifier<>(direction, product.createdAt));
                        break;
                    case "updatedAt":
                        orders.add(new OrderSpecifier<>(direction, product.updatedAt));
                        break;
                    default:
                        break;
                }
            }
        }

        return orders;
    }
}
