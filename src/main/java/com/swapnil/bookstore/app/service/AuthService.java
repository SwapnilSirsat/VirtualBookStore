package com.swapnil.bookstore.app.service;


import com.swapnil.bookstore.app.dto.AuthRequest;
import com.swapnil.bookstore.app.entity.User;
import com.swapnil.bookstore.app.repository.UserRepository;
import com.swapnil.bookstore.app.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String register(AuthRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);
        return jwtUtil.generate(user.getEmail());
    }

    public String login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        return jwtUtil.generate(user.getEmail());
    }
}
