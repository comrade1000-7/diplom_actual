package com.example.diplom.services;

import com.example.diplom.exceptions.InputDataException;
import com.example.diplom.exceptions.UnauthorizedException;
import com.example.diplom.models.File;
import com.example.diplom.models.User;
import com.example.diplom.repositories.FileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class FileService {
    FileRepository fileRepository;
    AuthService authService;


    public List<File> getAllFiles(Integer limit) {
        final User user = authService.getCurrentUser();
        log.info("User {} get all files", user.getLogin());
        return fileRepository.findAllByUser(user, Sort.by("filename"));
    }

    public void uploadFile(String filename, MultipartFile file) throws IOException {
        final User user = authService.getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException("Unauthorized error");
        }
        fileRepository.save(new File(filename, file.getSize(), file.getContentType(), file.getBytes(), user));
        log.info("User {} upload file {}", user.getLogin(), filename);
    }

    public void deleteFile(String filename) {
        final User user = authService.getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException("Unauthorized error");
        }
        log.info("User {} delete file {}", user.getLogin(), filename);
        fileRepository.removeByUserAndFilename(user, filename);
    }

    public File downloadFile(String filename) {
        final User user = authService.getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException("Unauthorized error");
        }
        final File file = fileRepository.findByUserAndFilename(user, filename);
        if (file == null) {
            log.error("Download file error");
            throw new InputDataException("Error input data");
        }
        log.info("User {} download file {}", user.getLogin(), filename);
        return file;
    }

    public void editFileName(String filename, String newFileName) {
        final User user = authService.getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException("Unauthorized error");
        }

        if (newFileName == null) throw new InputDataException("Error input data");

        fileRepository.editFileNameByUser(user, filename, newFileName);
        log.info("User {} edit file {}", user.getLogin(), filename);
    }
}
