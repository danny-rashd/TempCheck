package com.example.temperaturebackend.repository;

import com.example.temperaturebackend.entity.FileDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileDataEntity, Long> {
}

