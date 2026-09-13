package com.shopease;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CartController {

    private static final String CART_KEY = "cart";

    private final Map<Integer, Product> products = new HashMap<>();

    public CartController() {

        products.put(1, new Product(
                1,
                "Premium Laptop",
                "Electronics",
                59999,
                "💻"
        ));

        products.put(2, new Product(
                2,
                "Wireless Headphones",
                "Electronics",
                2499,
                "🎧"
        ));

        products.put(3, new Product(
                3,
                "Smart Watch",
                "Electronics",
                3999,
                "⌚"
        ));

        products.put(4, new Product(
                4,
                "Running Shoes",
                "Fashion",
                2999,
                "👟"
        ));

        products.put(5, new Product(
                5,
                "Modern Chair",
                "Home",
                4499,
                "🪑"
        ));

        products.put(6, new Product(
                6,
                "Beauty Essentials",
                "Beauty",
                1499,
                "💄"
        ));
    }


    @SuppressWarnings("unchecked")
    private Map<Integer, Integer> getCart(HttpSession session) {

        Map<Integer, Integer> cart =
                (Map<Integer, Integer>) session.getAttribute(CART_KEY);

        if (cart == null) {

            cart = new LinkedHashMap<>();

            session.setAttribute(CART_KEY, cart);
        }

        return cart;
    }


    @PostMapping("/cart/add")
    @ResponseBody
    public String addToCart(
            @RequestParam int productId,
            HttpSession session) {

        Map<Integer, Integer> cart = getCart(session);

        cart.put(
                productId,
                cart.getOrDefault(productId, 0) + 1
        );

        return "success";
    }


    @GetMapping("/cart")
    public String cart(
            HttpSession session,
            Model model) {

        Map<Integer, Integer> cart =
                getCart(session);

        List<CartItem> items =
                new ArrayList<>();

        double subtotal = 0;

        for (Map.Entry<Integer, Integer> entry :
                cart.entrySet()) {

            Product product =
                    products.get(entry.getKey());

            if (product != null) {

                int quantity = entry.getValue();

                double itemTotal =
                        product.price() * quantity;

                subtotal += itemTotal;

                items.add(
                        new CartItem(
                                product,
                                quantity,
                                itemTotal
                        )
                );
            }
        }

        double delivery =
                subtotal > 0 ? 99 : 0;

        double total =
                subtotal + delivery;

        model.addAttribute("items", items);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("delivery", delivery);
        model.addAttribute("total", total);

        return "cart";
    }


    @PostMapping("/cart/update")
    public String updateCart(
            @RequestParam int productId,
            @RequestParam int quantity,
            HttpSession session) {

        Map<Integer, Integer> cart =
                getCart(session);

        if (quantity <= 0) {

            cart.remove(productId);

        } else {

            cart.put(productId, quantity);
        }

        return "redirect:/cart";
    }


    @PostMapping("/cart/remove")
    public String removeFromCart(
            @RequestParam int productId,
            HttpSession session) {

        Map<Integer, Integer> cart =
                getCart(session);

        cart.remove(productId);

        return "redirect:/cart";
    }


    public record Product(
            int id,
            String name,
            String category,
            double price,
            String icon
    ) {}


    public record CartItem(
            Product product,
            int quantity,
            double itemTotal
    ) {}
}