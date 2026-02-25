package com.fextyleather.controller;

import com.fextyleather.dto.CartItem;
import com.fextyleather.entity.Product;
import com.fextyleather.repository.ProductRepository;
import com.fextyleather.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductRepository productRepository;

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam(defaultValue = "1") int qty,
                            HttpSession session) {
        cartService.addToCart(productId, qty, session);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        Map<Long, CartItem> items = cartService.getCart(session);
        List<Product> products = new ArrayList<>();
        double total = 0.0;

        for (CartItem item : items.values()) {
            productRepository.findById(item.getProductId()).ifPresent(product -> {
                products.add(product);
            });
        }

        for (Product product : products) {
            CartItem item = items.get(product.getId());
            total += product.getPrice() * item.getQty();
        }

        model.addAttribute("cartItems", items);
        model.addAttribute("products", products);
        model.addAttribute("total", total);
        return "cart";
    }

    @GetMapping("/cart/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId, HttpSession session) {
        cartService.removeFromCart(productId, session);
        return "redirect:/cart";
    }
}
