package com.example.temperaturebackend.service;

import com.example.temperaturebackend.entity.TextFile;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations gridFsOperations;

    private GridFSBucket gridFSBucket;

    public String addCSVFile(MultipartFile upload) throws IOException {

        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", upload.getSize());

        Object fileID = gridFsTemplate.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata);

        return fileID.toString();
    }

    public TextFile downloadCSVFile(String id) throws IOException {

        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));

        TextFile csvFile = new TextFile();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            csvFile.setFilename(gridFSFile.getFilename());
            csvFile.setFileType(gridFSFile.getMetadata().get("_contentType").toString());
            csvFile.setFileSize(gridFSFile.getMetadata().get("fileSize").toString());
            csvFile.setFile(IOUtils.toByteArray(gridFsOperations.getResource(gridFSFile).getInputStream()));
        }

        return csvFile;
    }
}