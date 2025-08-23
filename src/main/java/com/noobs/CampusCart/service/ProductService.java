package com.noobs.CampusCart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noobs.CampusCart.model.Product;
import com.noobs.CampusCart.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAllWithUserAndCategory();
    }

    // Accept categoryId as Long
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryIdWithUserAndCategory(categoryId);
    }

    public List<Product> getProductsBySellOrRent(String type) {
        return productRepository.findByTypeWithUserAndCategory(type);
    }

    public List<Product> getProductsByCategoryAndType(Long categoryId, String type) {
        return productRepository.findByCategoryAndTypeWithUserAndCategory(categoryId, type);
    }

    public List<Product> searchProductsByName(String keyword) {
        return productRepository.findByNameContainingWithUserAndCategory(keyword);
    }

    public void save(Product product) {
        productRepository.save(product);
    }
}
