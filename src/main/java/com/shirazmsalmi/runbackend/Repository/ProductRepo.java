package com.shirazmsalmi.runbackend.Repository;

import com.shirazmsalmi.runbackend.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo  extends JpaRepository<Product,Integer> {
}
