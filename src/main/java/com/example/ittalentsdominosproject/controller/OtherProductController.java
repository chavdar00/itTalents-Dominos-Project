package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.model.entity.OtherProduct;
import com.example.ittalentsdominosproject.repository.OtherProductRepository;
import com.example.ittalentsdominosproject.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class OtherProductController {
    @Autowired
    private OtherProductRepository otherProductRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private SessionHelper sessionHelper;

    @GetMapping("/products/{id}")
    public OtherProduct getOtherProductById(@PathVariable Long id) {
        return otherProductRepository.findById(id).orElseThrow();
    }

    @PostMapping("/products/image")
    public String uploadOtherProductImage(@RequestParam(name = "file") MultipartFile image,
                                          @RequestParam(name = "product_id") Long id, HttpSession session) {
        sessionHelper.isLogged(session);
        sessionHelper.isAdmin(session);
        return imageService.uploadImage(image, id, false);
    }

    @GetMapping("/products/image/{name}")
    public void downloadImage(@PathVariable String name, HttpServletResponse response) {
        imageService.downloadImage(name, response, false);
    }

    @GetMapping("/products")
    public List<OtherProduct> getAllOtherProducts() {
        return otherProductRepository.findAll();
    }

    @PostMapping("/products")
    public void addOtherProduct(@RequestBody OtherProduct otherProduct, HttpSession session) {
        sessionHelper.isLogged(session);
        sessionHelper.isAdmin(session);
        otherProductRepository.save(otherProduct);
    }

    @DeleteMapping("/products/{id}")
    public void deleteOtherProductById(@PathVariable Long id, HttpSession session) {
        sessionHelper.isLogged(session);
        sessionHelper.isAdmin(session);
        otherProductRepository.deleteById(id);
    }
}
