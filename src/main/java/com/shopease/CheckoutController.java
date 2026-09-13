package com.shopease;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutController {

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {

        Object cart = session.getAttribute("cart");

        if (cart == null) {
            return "redirect:/cart";
        }

        model.addAttribute("customerName", "");

        return "checkout";
    }

    @PostMapping("/checkout/place-order")
    public String placeOrder(
            String customerName,
            String email,
            String phone,
            String address,
            String city,
            String pincode,
            String paymentMethod,
            HttpSession session,
            Model model) {

        model.addAttribute("customerName", customerName);
        model.addAttribute("email", email);
        model.addAttribute("paymentMethod", paymentMethod);

        // Clear cart after successful order
        session.removeAttribute("cart");

        return "order-success";
    }
}