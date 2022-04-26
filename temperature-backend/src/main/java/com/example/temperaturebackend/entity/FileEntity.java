package com.example.temperaturebackend.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Temperature")
@Setter
@Getter
public class TextFile {

    private String filename;
    private String fileType;
    private String fileSize;
    private byte[] file;

    public TextFile() {
    }
}
