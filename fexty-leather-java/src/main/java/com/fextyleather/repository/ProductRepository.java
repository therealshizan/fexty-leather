package com.fextyleather.repository;

import com.fextyleather.entity.Product;
import com.fextyleather.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusOrderByIdDesc(Status status);
}
