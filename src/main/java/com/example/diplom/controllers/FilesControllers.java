package com.example.diplom.controllers;

import com.example.diplom.models.File;
import com.example.diplom.services.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class FilesControllers {
    private final FileService fileService;

    @GetMapping("/list")
    List<File> getAllFiles(@RequestParam("limit") Integer limit) {
        //return new ArrayList<>();
        return fileService.getAllFiles(limit).stream()
                .map(f -> new File(f.getFilename(), f.getSize()))
                .collect(Collectors.toList());
    }

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam("filename") String filename, MultipartFile file) throws IOException {
        fileService.uploadFile(filename, file);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestParam("filename") String filename) {
        fileService.deleteFile(filename);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(path = "/file", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> downloadFile(@RequestParam("filename") String filename) {
        File file = fileService.downloadFile(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getContent());
    }

    @PutMapping("/file")
    public ResponseEntity<?> editFileName(@RequestParam("filename") String filename,
                                          @RequestBody Map<String, String> fileNameRequest) {
        fileService.editFileName(filename, fileNameRequest.get("filename"));
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
