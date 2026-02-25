package com.fextyleather.controller;

import com.fextyleather.entity.Product;
import com.fextyleather.repository.CategoryRepository;
import com.fextyleather.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/addpro")
    public String addProductPage(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/add-product";
    }

    @PostMapping("/addpro")
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam(required = false) Long categoryId,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        try {
            if (categoryId != null) {
                categoryRepository.findById(categoryId).ifPresent(product::setCategory);
            }
            productService.saveProduct(product, imageFile);
            redirectAttributes.addFlashAttribute("success", "Product added successfully");
            return "redirect:/admin/addpro";
        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload image");
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/add-product";
        }
    }
}
