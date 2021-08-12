package ru.abtank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.abtank.persistance.Product;
import ru.abtank.persistance.ProductRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product") // localhost:8080/mvc/product
public class ProductController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;


    @GetMapping
    public String allProducts(Model model) throws SQLException {
        List<Product> allProduct = productRepository.getAllProducts();
        model.addAttribute("products", allProduct);
        LOGGER.info("GET ALL PRODUCTS: "+allProduct.stream()
                .map(Product::getName)
                .collect(Collectors.joining(", ")));
    return "products"; // возврат названия html файла из view (представлений)
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) throws SQLException {
            Product product = productRepository.findById(id);
        LOGGER.info("EDIT PRODUCT: "+product.toString());
            model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/update")
    public String updateProduct(Product product) throws SQLException {
        if(product.getId() > 0) {
            LOGGER.info("UPDATE PRODUCT: "+product.toString());
            productRepository.update(product);
        } else{
            LOGGER.info("INSERT PRODUCT: "+product.toString());
            productRepository.insert(product);
        }
        return "redirect:/product";
    }

    @GetMapping("/create")
    public  String createProduct (Model model){
        Product product = new Product(0,"","","");
        LOGGER.info("CREATE PRODUCT: "+product.toString());
        model.addAttribute("product", product);
        return "product";
    }

    @DeleteMapping ("{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id){
        LOGGER.info("DELETE PRODUCT id="+id +" "+ productRepository.deleteById(id));
        return "redirect:/product";
    }

}
