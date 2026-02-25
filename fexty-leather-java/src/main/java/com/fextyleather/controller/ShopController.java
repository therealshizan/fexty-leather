package com.fextyleather.controller;

import com.fextyleather.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ShopController {

    private final ProductService productService;

    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("products", productService.getAllActiveProducts());
        return "shop";
    }
}
