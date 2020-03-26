package com.Rheo.Rheo2020.repository;


import com.Rheo.Rheo2020.model.FileInfo;
import com.Rheo.Rheo2020.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Integer> {





}
