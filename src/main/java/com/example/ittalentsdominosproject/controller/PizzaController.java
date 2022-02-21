package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.model.dto.PizzaDTO;
import com.example.ittalentsdominosproject.model.entity.Pizza;
import com.example.ittalentsdominosproject.repository.PizzaRepository;
import com.example.ittalentsdominosproject.service.ImageService;
import com.example.ittalentsdominosproject.service.PizzaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/pizza/{id}")
    public Pizza getByPizzaId(@PathVariable Long id) {
        return pizzaRepository.findById(id).orElseThrow();
    }

    @GetMapping("/pizza")
    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }
    @PostMapping("/pizza")
    public Pizza addPizza(@RequestBody PizzaDTO pizza) {
        Pizza p = new Pizza();
        p.setName(pizza.getName());
        p.setIngredients(pizzaService.add(pizza.getIngredientIds()));
        p.setPrice(pizzaService.calcPrice(p));
        return pizzaRepository.save(p);
    }
    @PostMapping("/pizza/image")
    public String uploadPizzaImage(@RequestParam(name = "file")MultipartFile image,
                                   @RequestParam(name = "pizza_id") Long id){
        boolean isPizzaImage = true;
        return imageService.uploadImage(image,id,isPizzaImage);
    }
    @GetMapping("/pizza/image/{name}")
    public void downloadImage(@PathVariable String name, HttpServletResponse response){
        boolean isPizzaImage=true;
        imageService.downloadImage(name,response,isPizzaImage);
    }

    @DeleteMapping("/pizza/{id}")
    public void deletePizzaById(@PathVariable Long id) {
        pizzaRepository.deleteById(id);
    }
}
