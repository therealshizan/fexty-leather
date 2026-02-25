package com.fextyleather.service;

import com.fextyleather.dto.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    private static final String CART_SESSION_KEY = "cart";

    public void addToCart(Long productId, int qty, HttpSession session) {
        Map<Long, CartItem> cart = getCart(session);

        cart.merge(productId, new CartItem(productId, qty), (old, neu) -> {
            old.setQty(old.getQty() + neu.getQty());
            return old;
        });
    }

    @SuppressWarnings("unchecked")
    public Map<Long, CartItem> getCart(HttpSession session) {
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute(CART_SESSION_KEY);

        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }

        return cart;
    }

    public void removeFromCart(Long productId, HttpSession session) {
        getCart(session).remove(productId);
    }
}
