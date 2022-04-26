package com.example.temperaturebackend.service;


import com.example.temperaturebackend.entity.FileDataEntity;
import com.example.temperaturebackend.entity.FileEntity;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileService {
    FileEntity downloadCSVFile(String id) throws IOException;

    Object uploadCSVFile(MultipartFile file) throws IOException;

    Map<ObjectId, String> getFileById(ObjectId objectId);

    List<FileDataEntity> getAllFiles();
}