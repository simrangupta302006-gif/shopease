package com.shopease;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    private final Map<String, String> users = new HashMap<>();

    public AuthController() {
        // Demo user
        users.put("demo@shopease.com", "password123");
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        if (users.containsKey(email)
                && users.get(email).equals(password)) {

            session.setAttribute("loggedInUser", email);

            return "redirect:/account";
        }

        model.addAttribute(
                "error",
                "Invalid email or password."
        );

        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        if (users.containsKey(email)) {

            model.addAttribute(
                    "error",
                    "An account with this email already exists."
            );

            return "register";
        }

        users.put(email, password);

        model.addAttribute(
                "success",
                "Registration successful. Please login."
        );

        return "login";
    }

    @GetMapping("/account")
    public String account(
            HttpSession session,
            Model model) {

        Object user =
                session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("email", user);

        return "account";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}