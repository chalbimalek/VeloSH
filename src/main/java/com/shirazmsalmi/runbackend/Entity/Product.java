package com.shirazmsalmi.runbackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table( name = "Product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduct;
    private String adresse;
    private int numero;
    private String brand;
    private String name;
    private Date creationDate;
    private Double price;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER ,cascade =  CascadeType.ALL)
    @JoinTable(name = "products_image" ,joinColumns = {@JoinColumn(name = "product_id")}
    ,inverseJoinColumns = {@JoinColumn (name = "image_id")})
    private Set<ImageModel> imageModels;


}
