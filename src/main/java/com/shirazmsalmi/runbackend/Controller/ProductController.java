package com.shirazmsalmi.runbackend.Controller;



import com.shirazmsalmi.runbackend.Entity.ImageModel;
import com.shirazmsalmi.runbackend.Entity.Product;
import com.shirazmsalmi.runbackend.ServiceImp.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @PostMapping(value = {"/addd"},consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addProduit(@RequestPart("product") Product product, @RequestPart("imageFile") MultipartFile[] file){
        try {
            Set<ImageModel> imageModelSet =uploadImage(file);
            product.setImageModels(imageModelSet);
            return productService.addProduit(product);
    }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {

        Set<ImageModel> imageModels=new HashSet<>();
        for(MultipartFile file:multipartFiles){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            ImageModel imageModel = new ImageModel();
            imageModel.setFilePath("C:\\xampp\\htdocs\\Product\\" + fileName); // Utilisation du chemin souhaité
            imageModel.setBytes(file.getBytes());

            // Sauvegarder physiquement le fichier sur le système de fichiers
            saveImageToFileSystem(file, fileName);
           imageModels.add( imageModel);
        }
        return imageModels;
    }
    public void saveImageToFileSystem(MultipartFile file, String fileName) throws IOException {
        String uploadDir = "C:\\xampp\\htdocs\\Product\\"; // Chemin vers le dossier de destination

        // Créer le dossier s'il n'existe pas déjà
        Path uploadPath = Paths.get(uploadDir);
        Files.createDirectories(uploadPath);

        // Écrire le fichier sur le système de fichiers
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());
    }


    @GetMapping("/{pid}")
    public Product getProductById(@PathVariable int pid ){
        return productService.getProductById(pid);
    }
    @GetMapping("/getall")
    public List<Product> getAllProduct(){
        return productService.getAllProduct();
    }



}
