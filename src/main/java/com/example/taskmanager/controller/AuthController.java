package com.example.taskmanager.controller;

import com.example.taskmanager.auth.JwtUtil;
import com.example.taskmanager.dto.RegisterDTO;
import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.RoleRepository;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.request.AuthRequest;
import com.example.taskmanager.request.AuthResponse;
import com.example.taskmanager.service.AuthService;
import com.example.taskmanager.service.CustomUserDetailsService;
import com.example.taskmanager.service.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {



    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Autowired
    private AuthService authService;


//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        final String authHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwt = null;
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            jwt = authHeader.substring(7);
//
//            if (tokenBlacklistService.isTokenBlacklisted(jwt)) {
//                // Token is blacklisted, reject request
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }
//            try {
//                username = jwtUtil.extractUsername(jwt);
//            } catch (Exception e) {
//                // log invalid token
//            }
//        }
//
//        // ... existing auth logic
//
//        filterChain.doFilter(request, response);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) throws Exception {

        return ResponseEntity.ok(authService.login(authRequest));
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            tokenBlacklistService.blacklistToken(jwt);
        }

        return ResponseEntity.ok("Logged out successfully");
    }
    @GetMapping("/blacklist")
    public ResponseEntity<?> getBlackListed() {
        return ResponseEntity.ok(tokenBlacklistService.getBlacklistedTokens());
    }
    @GetMapping("/testRoles")
    public ResponseEntity<?> testRoles(Authentication authentication) {
        return ResponseEntity.ok(authentication.getAuthorities());
    }
}
