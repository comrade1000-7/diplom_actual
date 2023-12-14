package com.example.diplom.services;

import com.example.diplom.exceptions.UnauthorizedException;
import com.example.diplom.models.User;
import com.example.diplom.providers.JwtProvider;
import com.example.diplom.repositories.AuthorizationRepository;
import com.example.diplom.requests.JwtRequest;
import com.example.diplom.responses.JwtResponse;

import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j

public class AuthService {

    private final UserService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;


    @Autowired
    private AuthorizationRepository authorizationRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        final User user = userService.loadUserModelByUsername(currentUserName);
        if (user == null) {
            log.error("Get all files error");
            throw new UnauthorizedException("Unauthorized error");
        }
        return user;
    }

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {

        final String username = authRequest.getLogin();
        final String password = authRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userService.loadUserByUsername(username);

        String token = jwtProvider.generateToken(userDetails);

        authorizationRepository.putTokenAndUsername(token, username);

        log.info("User {} is authorized", username);

        return new JwtResponse(token);
    }

    public void logout(String authToken) {
        if (authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
        }
        final String username = authorizationRepository.getUserNameByToken(authToken);
        log.info("User {} logout", username);
        authorizationRepository.removeTokenAndUsernameByToken(authToken);
    }

}