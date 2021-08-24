package ru.abtank.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.persist.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    //     запросы посредствам Spring Data
    List<Product> findByNameContains(String patternName);

    List<Product> findByPriceLessThanEqual(BigDecimal patternPriceMax);

    List<Product> findByPriceLessThanEqualAndNameContains(BigDecimal patternPriceMax, String patternName);

    List<Product> findByPriceGreaterThanEqual(BigDecimal patternPriceMin);

    List<Product> findByPriceGreaterThanEqualAndNameContains(BigDecimal patternPriceMin, String patternName);

    List<Product> findByPriceBetween(BigDecimal patternPriceMin, BigDecimal patternPriceMax);

    List<Product> findByPriceBetweenAndNameContains(BigDecimal patternPriceMin, BigDecimal patternPriceMax, String patternName);

}