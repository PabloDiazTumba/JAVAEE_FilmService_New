package com.example.filmservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        String cookieUser = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user".equals(cookie.getName())) {
                    cookieUser = cookie.getValue(); // Hämta användarnamnet från cookien
                    break;
                }
            }
        }

        model.addAttribute("cookieUser", cookieUser); // Skicka användarnamnet till Thymeleaf
        return "home"; // Returnera Thymeleaf-vyn
    }
}