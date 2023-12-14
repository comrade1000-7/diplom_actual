package com.example.diplom.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "files")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    @Column(nullable = false, unique = true)
    private int id;

    @Column(nullable = false, unique = true)
    private String filename;

    @Column(nullable = false)
    private long size;

    @Column(nullable = false)
    private String type;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;

    @ManyToOne
    private User user;


    public File(String filename, Long size) {
        this.filename = filename;
        this.size = size;
    }

    public File(String filename, long size, String contentType, byte[] bytes, User user) {
        this.filename = filename;
        this.size = size;
        this.type = contentType;
        this.content = bytes;
        this.user = user;
    }
}
