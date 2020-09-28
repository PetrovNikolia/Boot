package com.petrov.Boot.persist.repo;

import com.petrov.Boot.persist.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public final class ProductSpecification {


    public static Specification<Product> trueLiteral(){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    public static Specification<Product> priceGreaterOrEqualsThan(int min) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), min);
    }

    public static Specification<Product> priceLesserOrEqualsThan(int max) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("cost"), max);
    }
}
