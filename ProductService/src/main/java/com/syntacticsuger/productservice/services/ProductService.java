package com.syntacticsuger.productservice.services;

import com.syntacticsuger.productservice.ProductServiceApplication;
import com.syntacticsuger.productservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {

    Product getProductById(Long id);

    List<Product> getAllProducts();

    Product replaceProduct(Long id, Product product);
    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    Product createProduct(Product product);
}
