package ru.abtank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.abtank.persist.entity.Product;
import ru.abtank.persist.repo.ProductRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product") // localhost:8080/mvc/product
public class ProductController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;


    @GetMapping
    public String allProducts(Model model,
                              @RequestParam(value = "name_filter", required = false) String name_filter,
                              @RequestParam(value = "check_name_filter", required = false) String check_name_filter,
                              @RequestParam(value = "price_min_filter", required = false) BigDecimal price_min_filter,
                              @RequestParam(value = "check_price_min_filter", required = false) Boolean check_price_min_filter,
                              @RequestParam(value = "price_max_filter", required = false) BigDecimal price_max_filter,
                              @RequestParam(value = "check_price_max_filter", required = false) Boolean check_price_max_filter) {
        List<Product> allProduct = new ArrayList<>();
        if (check_price_min_filter == null && check_price_max_filter == null && check_name_filter == null) {
            allProduct = productRepository.findAll();
        } else {
            // 1 1 1
            if ((check_price_min_filter != null && price_min_filter != null) && (check_price_max_filter != null && price_max_filter != null) && (check_name_filter != null && !name_filter.isEmpty())) {
                allProduct = productRepository.findByPriceBetweenAndNameContains(price_min_filter, price_max_filter, name_filter);
                // 1 0 0
            } else if ((check_price_min_filter != null && price_min_filter != null) && (check_price_max_filter == null && price_max_filter != null) && (check_name_filter == null && !name_filter.isEmpty())) {
                allProduct = productRepository.findByPriceGreaterThanEqual(price_min_filter);
                // 0 1 0
            } else if ((check_price_min_filter == null && price_min_filter != null) && (check_price_max_filter != null && price_max_filter != null) && (check_name_filter == null && !name_filter.isEmpty())) {
                allProduct = productRepository.findByPriceLessThanEqual(price_max_filter);
                // 0 0 1
            } else if ((check_price_min_filter == null && price_min_filter != null) && (check_price_max_filter == null && price_max_filter != null) && (check_name_filter != null && !name_filter.isEmpty())) {
                allProduct = productRepository.findByNameContains(name_filter);
                // 1 1 0
            } else if ((check_price_min_filter != null && price_min_filter != null) && (check_price_max_filter != null && price_max_filter != null) && (check_name_filter == null && !name_filter.isEmpty())) {
                allProduct = productRepository.findByPriceBetween(price_min_filter, price_max_filter);
                // 0 1 1
            } else if ((check_price_min_filter == null && price_min_filter != null) && (check_price_max_filter != null && price_max_filter != null) && (check_name_filter != null && !name_filter.isEmpty())) {
                allProduct = productRepository.findByPriceLessThanEqualAndNameContains(price_max_filter, name_filter);
                // 1 0 1
            } else if ((check_price_min_filter != null && price_min_filter != null) && (check_price_max_filter == null && price_max_filter != null) && (check_name_filter != null && !name_filter.isEmpty())) {
                allProduct = productRepository.findByPriceGreaterThanEqualAndNameContains(price_min_filter, name_filter);
            }

        }


        model.addAttribute("products", allProduct);
        model.addAttribute("nav_selected", "nav_products");
        LOGGER.info("GET ALL PRODUCTS: " + allProduct.stream()
                .map(Product::getName)
                .collect(Collectors.joining(", ")));
        return "products"; // возврат названия html файла из view (представлений)
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model) throws SQLException {
        Product product = productRepository.findById(id).get();
        LOGGER.info("EDIT PRODUCT: " + product.toString());
        model.addAttribute("nav_selected", "nav_products");
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/update")
    public String updateProduct(Product product) {
        LOGGER.info("UPDATE OR INSERT PRODUCT: " + product.toString());
        productRepository.save(product);
        return "redirect:/product";
    }

    @GetMapping("/create")
    public String createProduct(Model model) {
        Product product = new Product();
        LOGGER.info("CREATE PRODUCT: " + product.toString());
        model.addAttribute("nav_selected", "ADD_NEW");
        model.addAttribute("product", product);
        return "product";
    }

    @DeleteMapping("{id}/delete")
    public String deleteProduct(@PathVariable("id") Integer id) {
        LOGGER.info("DELETE PRODUCT id=" + id);
        productRepository.deleteById(id);
        return "redirect:/product";
    }

}
