package com.swapnil.bookstore.app.controller;

import com.swapnil.bookstore.app.dto.AuthRequest;
import com.swapnil.bookstore.app.dto.AuthResponse;
import com.swapnil.bookstore.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        return new AuthResponse(service.register(request));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return new AuthResponse(service.login(request));
    }
}
