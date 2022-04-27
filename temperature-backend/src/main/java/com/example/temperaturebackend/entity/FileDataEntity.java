package com.example.temperaturebackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//store data in MySQL
@Entity
@Table(name = "file_data")
@Data
@NoArgsConstructor
public class FileDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String objectId;
    private String filename;
}
