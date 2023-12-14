package com.example.diplom.services;

import com.example.diplom.models.User;
import com.example.diplom.repositories.AuthorizationRepository;
import com.example.diplom.repositories.FileRepository;
import com.example.diplom.repositories.UserRepository;
import com.example.diplom.services.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FileServiceTest {
    private FileService fileService;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private AuthorizationRepository authorizationRepository;

    @Mock
    private UserRepository userRepository;

    public static final String LOGIN_1 = "user";
    public static final String PASSWORD_1 = "password";
    public static final User USER_1 = new User(1, LOGIN_1, PASSWORD_1, null);
    public static final String TOKEN = "token";
    public static final String FILENAME_1 = "fileName1";
    public static final String FILENAME_2 = "fileName2";

    @BeforeEach
    void setUp() {
        Mockito.when(authorizationRepository.getUserNameByToken(TOKEN)).thenReturn(LOGIN_1);
        Mockito.when(userRepository.findByLogin(LOGIN_1)).thenReturn(Optional.of(USER_1));
    }

    @Test
    void deleteFile() {
        fileService.deleteFile(FILENAME_1);
        Mockito.verify(fileRepository, Mockito.times(1)).removeByUserAndFilename(USER_1, FILENAME_1);
    }

    @Test
    void editFileName() {
        fileService.editFileName(FILENAME_1, FILENAME_2);
        Mockito.verify(fileRepository, Mockito.times(1)).editFileNameByUser(USER_1, FILENAME_1, FILENAME_2);
    }
}
