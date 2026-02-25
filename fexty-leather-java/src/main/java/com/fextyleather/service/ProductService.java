package com.fextyleather.service;

import com.fextyleather.entity.Product;
import com.fextyleather.entity.Status;
import com.fextyleather.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getFeaturedProducts() {
        return productRepository.findByStatusOrderByIdDesc(Status.Active);
    }

    public List<Product> getAllActiveProducts() {
        return productRepository.findByStatusOrderByIdDesc(Status.Active);
    }

    public Product saveProduct(Product product, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/uploads/" + fileName);
            Files.createDirectories(path.getParent());
            image.transferTo(path);
            product.setImage("/uploads/" + fileName);
        }
        return productRepository.save(product);
    }
}
