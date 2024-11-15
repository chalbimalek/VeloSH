package com.shirazmsalmi.runbackend.ServiceImp;




import com.shirazmsalmi.runbackend.Entity.Product;
import com.shirazmsalmi.runbackend.Repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepo productRepo;
@Transactional
    public Product addProduit(Product product) {
        product.setCreationDate(new Date());

        Product  product1=productRepo.save(product);


        return product1;
    }

    public Product getProductById(int id) {

        return productRepo.findById(id).orElse(null);
    }

    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }









}