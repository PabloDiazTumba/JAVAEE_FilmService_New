package com.example.filmservice.controller;

import com.example.filmservice.config.JwtUtil;  // Importera JwtUtil för tokenhantering
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;  // För att skapa JWT-token

    // Endpoint för att logga in och generera en JWT-token
    @RequestMapping("/api/auth/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse response) {

        // Autentisera användaren med AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Om autentisering lyckas, generera en JWT-token
        String token = jwtUtil.generateToken(username);

        // Sätt token som en HTTP-only cookie
        response.addHeader("Authorization", "Bearer " + token);  // Skicka token som header
        // Eller alternativt sätt den som en cookie:
        // Cookie tokenCookie = new Cookie("token", token);
        // tokenCookie.setHttpOnly(true);
        // response.addCookie(tokenCookie);

        return "redirect:/home"; // Efter inloggning, dirigera till hem-sidan
    }

    // Logout-routning (För att ta bort JWT-token eller logout)
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        return "redirect:/login";  // Efter utloggning, dirigera till login-sidan
    }
}