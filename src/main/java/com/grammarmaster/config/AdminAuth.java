package com.grammarmaster.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// Checks the admin password once at login and hands out a token, which every /api/admin call must send.
// Tokens live in memory, so restarting the backend signs everyone out.
@Component
public class AdminAuth implements HandlerInterceptor {
    private final String adminPassword;
    private final Set<String> activeTokens = ConcurrentHashMap.newKeySet();

    public AdminAuth(@Value("${grammarmaster.admin.password}") String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String login(String password) {
        if (!adminPassword.equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }
        String token = UUID.randomUUID().toString();
        activeTokens.add(token);
        return token;
    }

    public void logout(HttpServletRequest request) {
        activeTokens.remove(readToken(request));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!activeTokens.contains(readToken(request))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin login required");
        }
        return true;
    }

    private String readToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header != null && header.startsWith("Bearer ") ? header.substring(7) : "";
    }
}
