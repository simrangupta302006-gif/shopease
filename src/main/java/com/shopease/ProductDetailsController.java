package com.shopease;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductDetailsController {

    @GetMapping("/product/{id}")
    public String productDetails(@PathVariable int id, Model model) {

        String name;
        String category;
        String price;
        String description;
        String icon;
        String rating;

        switch (id) {

            case 1:
                name = "Premium Laptop";
                category = "Electronics";
                price = "₹59,999";
                description = "Powerful laptop designed for work, study, entertainment and everyday productivity.";
                icon = "💻";
                rating = "⭐⭐⭐⭐⭐";
                break;

            case 2:
                name = "Wireless Headphones";
                category = "Electronics";
                price = "₹2,499";
                description = "Enjoy immersive sound with a comfortable wireless design and long-lasting battery.";
                icon = "🎧";
                rating = "⭐⭐⭐⭐☆";
                break;

            case 3:
                name = "Smart Watch";
                category = "Electronics";
                price = "₹3,999";
                description = "Track your fitness, monitor your daily activities and stay connected throughout the day.";
                icon = "⌚";
                rating = "⭐⭐⭐⭐⭐";
                break;

            case 4:
                name = "Running Shoes";
                category = "Fashion";
                price = "₹2,999";
                description = "Comfortable and lightweight running shoes designed for everyday use and active lifestyles.";
                icon = "👟";
                rating = "⭐⭐⭐⭐☆";
                break;

            case 5:
                name = "Modern Chair";
                category = "Home";
                price = "₹4,499";
                description = "A stylish and comfortable chair that adds a modern touch to your home.";
                icon = "🪑";
                rating = "⭐⭐⭐⭐☆";
                break;

            case 6:
                name = "Beauty Essentials";
                category = "Beauty";
                price = "₹1,499";
                description = "Premium beauty essentials designed to complement your everyday personal-care routine.";
                icon = "💄";
                rating = "⭐⭐⭐⭐⭐";
                break;

            default:
                return "redirect:/products";
        }

        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("category", category);
        model.addAttribute("price", price);
        model.addAttribute("description", description);
        model.addAttribute("icon", icon);
        model.addAttribute("rating", rating);

        return "product-details";
    }
}
