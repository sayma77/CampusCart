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
        return productRepository.findAllApproved();
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryNameAndApproved(category);
    }

    public List<Product> getProductsBySellOrRent(String type) {
        return productRepository.findByTypeAndApproved(type);
    }

    public List<Product> getProductsByCategoryAndType(String category, String type) {
        return productRepository.findByCategoryAndTypeAndApproved(category, type);
    }

    public List<Product> searchProductsByName(String keyword) {
        return productRepository.findByNameContainingAndApproved(keyword);
    }
}

