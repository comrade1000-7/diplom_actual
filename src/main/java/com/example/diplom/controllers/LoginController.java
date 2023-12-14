package com.example.diplom.controllers;

import com.example.diplom.models.File;
import com.example.diplom.requests.JwtRequest;
import com.example.diplom.responses.JwtResponse;
import com.example.diplom.services.AuthService;
import com.example.diplom.services.FileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    private final AuthService authService;
    private final FileService service;



    public LoginController(AuthService authService, FileService service) {
        this.authService = authService;
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody JwtRequest authRequest) throws AuthException, JsonProcessingException {
        final JwtResponse token = authService.login(authRequest);
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("auth-token", token.getAuthToken());
        return ResponseEntity.ok((new ObjectMapper()).writeValueAsString(responseMap));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String authToken) {
        authService.logout(authToken);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
