package com.budcoded.productservice.Service;

import com.budcoded.productservice.DTO.ProductRequest;
import com.budcoded.productservice.DTO.ProductResponse;
import com.budcoded.productservice.Model.Product;
import com.budcoded.productservice.Repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void createProduct (ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        repository.save(product);
        log.info("Product {} saved successfully.", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = repository.findAll();
//        List<ProductResponse> productResponses = new ArrayList<>();
//        for (Product product : products) {
//            ProductResponse productResponse = ProductResponse.builder()
//                    .id(product.getId())
//                    .name(product.getName())
//                    .description(product.getDescription())
//                    .price(product.getPrice())
//                    .build();
//        }
        return products.stream().map(this::mapToProductResponse).toList();
//        return productResponses;
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
