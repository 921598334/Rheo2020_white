package com.Rheo.Rheo2020.Service;


import com.Rheo.Rheo2020.model.FileInfo;
import com.Rheo.Rheo2020.repository.FileInfoRepository;
import com.Rheo.Rheo2020.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileInfoServer {


    @Autowired
    FileInfoRepository fileInfoRepository;

    //在数据库中检查这个用户是否存在，不存在就创建，否则就更新(主要更新token)
    public FileInfo createOrUpdate(FileInfo fileInfo){

        fileInfoRepository.save(fileInfo);
        return fileInfo;

    }

}
