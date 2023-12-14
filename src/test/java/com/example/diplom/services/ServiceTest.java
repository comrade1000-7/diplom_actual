package com.example.diplom.services;

import com.example.diplom.exceptions.UnauthorizedException;
import com.example.diplom.models.User;
import com.example.diplom.repositories.UserRepository;
import com.example.diplom.services.AuthService;
import com.example.diplom.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    public static final String LOGIN_1 = "user";
    public static final String PASSWORD_1 = "password";
    public static final User USER_1 = new User(1, LOGIN_1, PASSWORD_1, null);
    public static final UserDetails USER_DETAILS_1 = org.springframework.security.core.userdetails.User.builder()
            .username(LOGIN_1)
            .password(PASSWORD_1)
            .authorities(new ArrayList<>())
            .build();

    @BeforeEach
    void setUp() {
        Mockito.when(userRepository.findByLogin(LOGIN_1)).thenReturn(Optional.of(USER_1));
    }

    @Test
    void loadUserByUsername() {
        Assertions.assertEquals(USER_DETAILS_1, userService.loadUserByUsername(LOGIN_1));
    }

}
