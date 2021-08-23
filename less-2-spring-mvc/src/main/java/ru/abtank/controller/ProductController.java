package ru.abtank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.abtank.persist.entity.Product;
import ru.abtank.persist.repo.ProductRepository;
import ru.abtank.persist.repo.ProductSpecification;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    @Autowired
    private EntityManagerFactory factory;


    @GetMapping
    public String allProducts(Model model,
                              @RequestParam(value = "name_filter", required = false) String name_filter,
                              @RequestParam(value = "check_name_filter", required = false) String check_name_filter,
                              @RequestParam(value = "price_min_filter", required = false) BigDecimal price_min_filter,
                              @RequestParam(value = "check_price_min_filter", required = false) Boolean check_price_min_filter,
                              @RequestParam(value = "price_max_filter", required = false) BigDecimal price_max_filter,
                              @RequestParam(value = "check_price_max_filter", required = false) Boolean check_price_max_filter) {

//        1) вариант
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

//        2) вариант Criteria API
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb =  em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> from = query.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();
        if (check_price_min_filter != null && price_min_filter != null){
            predicates.add(cb.ge(from.get("price"),price_min_filter));
        }
        if (check_price_max_filter != null && price_max_filter != null){
            predicates.add(cb.le(from.get("price"),price_max_filter));
        }
        if (check_name_filter != null && !name_filter.isEmpty()){
            predicates.add(cb.like(from.get("name"), "%"+name_filter+"%"));
        }
        CriteriaQuery<Product> cq = query.select(from).where(predicates.toArray(new Predicate[0]));
        allProduct = em.createQuery(cq).getResultList();

//        3) вариант Specification (добавляем в интерфейс репозитория  JpaSpecificationExecutor<Product> чтоб findAll принимал spec)
        Specification<Product> spec = ProductSpecification.trueLiteral();
        if (check_price_min_filter != null && price_min_filter != null){
            spec = spec.and(ProductSpecification.priceGreater(price_min_filter));
        }
        if (check_price_max_filter != null && price_max_filter != null){
            spec = spec.and(ProductSpecification.priceLess(price_max_filter));
        }
        if (check_name_filter != null && !name_filter.isEmpty()){
            spec = spec.and(ProductSpecification.nameContains(name_filter));
        }
        allProduct = productRepository.findAll(spec);


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
    public String updateProduct(Product product, RedirectAttributes redirectAttributes) {
        LOGGER.info("UPDATE OR INSERT PRODUCT: " + product.toString());
        String msg = (product.getId() != null) ? "Susses update Product " : "Susses create Product ";
        productRepository.save(product);
        msg += product.getName();
        redirectAttributes.addFlashAttribute("msg", msg);
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