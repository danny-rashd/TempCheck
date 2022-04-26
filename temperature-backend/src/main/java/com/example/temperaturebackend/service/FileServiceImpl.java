package com.example.temperaturebackend.service;

import com.example.temperaturebackend.entity.FileEntity;
import com.example.temperaturebackend.entity.FileDataEntity;
import com.example.temperaturebackend.repository.FileRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations gridFsOperations;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public String uploadCSVFile(MultipartFile upload) throws IOException {

        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", upload.getSize());

        Object fileID = gridFsTemplate.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata);

        return fileID.toString();
    }

    @Override
    public FileEntity downloadCSVFile(String id) throws IOException {

        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));

        FileEntity fileEntity = new FileEntity();
        FileDataEntity fileDataEntity = new FileDataEntity();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            //store in mongodb
            fileEntity.setFilename(gridFSFile.getFilename());
            fileEntity.setFileType(gridFSFile.getMetadata().get("_contentType").toString());
            fileEntity.setFileSize(gridFSFile.getMetadata().get("fileSize").toString());
            fileEntity.setFile(IOUtils.toByteArray(gridFsOperations.getResource(gridFSFile).getInputStream()));

            // store data in mysql
        }
        fileDataEntity.setObjectId(String.valueOf(gridFSFile.getObjectId()));
        fileDataEntity.setFilename(gridFSFile.getFilename());
        fileRepository.save(fileDataEntity);
        return fileEntity;
    }

    @Override
    public Map<ObjectId, String> getFileById(ObjectId id) {
        List<GridFSFile> gridFSFile = Collections.singletonList(gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id))));
        return gridFSFile.stream().collect(Collectors.toMap(d -> new ObjectId(String.valueOf(d.getObjectId())), d -> d.getFilename()));
    }

    @Override
    public List<FileDataEntity> getAllFiles() {
        return fileRepository.findAll();
    }

}













































































