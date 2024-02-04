package com.yzyfiles.api.services;

import com.yzyfiles.api.files.UploadedFile;
import com.yzyfiles.api.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<UploadedFile> getUploadedFiles() {
        return fileRepository.findAll();
    }

    public UploadedFile getUploadedFile(String uploadedId) {
        return fileRepository.findByUploadId(uploadedId);
    }

    //public UploadedFile createUploadedFile() {
   //     return null;
   // }

    //public UploadedFile deleteUploadedFile() {
    //    return null;
    //}
}

