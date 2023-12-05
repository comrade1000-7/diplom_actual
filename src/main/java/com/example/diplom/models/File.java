package com.example.diplom.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@Table(name = "files")
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class File {

    @Id
    @Column(nullable = false, unique = true)
    private int id;

    @Column(nullable = false)
    private String fileName;


    @Column(nullable = false)
    private Long size;

    @ManyToOne
    private User user;


    public File(String fileName, Long size) {
        this.fileName = fileName;
        this.size = size;
    }
}
