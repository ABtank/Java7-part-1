package ru.abtank.persist.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.persist.entity.Product;

import java.math.BigDecimal;

public final class ProductSpecification {
    //    аналог where 1=1
    public static Specification<Product> trueLiteral() {
        return (root, quary, builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<Product> nameLike(String name) {
        return (root, quary, builder) -> builder.like(root.get("name"), name);
    }

    public static Specification<Product> nameContains(String name) {
        return (root, quary, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> descriptionContains(String description) {
        return (root, quary, builder) -> builder.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<Product> priceGreater(BigDecimal price) {
        return (root, quary, builder) -> builder.ge(root.get("price"), price);
    }

    public static Specification<Product> priceLess(BigDecimal price) {
        return (root, quary, builder) -> builder.le(root.get("price"), price);
    }
}
