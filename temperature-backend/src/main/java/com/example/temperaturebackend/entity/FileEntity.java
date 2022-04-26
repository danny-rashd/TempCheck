package com.example.temperaturebackend.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


// Store in MongoDB
@Document(collection = "Temperature")
@Setter
@Getter
public class FileEntity {

    private String filename;
    private String fileType;
    private String fileSize;
    private byte[] file;

    public FileEntity() {
    }

}