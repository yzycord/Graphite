package com.yzyfiles.api.services;

import com.yzyfiles.api.files.UploadedFile;
import com.yzyfiles.api.repository.UploadedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UploadedFileService {

    UploadedFileRepository uploadedFileRepository;

    @Autowired
    public UploadedFileService(UploadedFileRepository uploadedFileRepository) {
        this.uploadedFileRepository = uploadedFileRepository;
    }

    public List<UploadedFile> getUploadedFiles() {
        return uploadedFileRepository.findAll();
    }

    public UploadedFile getUploadedFile(String uploadedId) {
        Optional<UploadedFile> uploadedFileById = uploadedFileRepository
            .findByUploadId(uploadedId);

        if (uploadedFileById.isEmpty()) {
            throw new IllegalStateException("Upload does not exist");
        }

        return uploadedFileById.get();
    }

    public UploadedFile createUploadedFile(UploadedFile uploadedFile) {
        Optional<UploadedFile> uploadedFileById = uploadedFileRepository
            .findByUploadId(uploadedFile.getUploadId());

        if (uploadedFileById.isPresent()) {
            throw new IllegalStateException("Upload with this id already exists");
        }

        uploadedFileRepository.save(uploadedFile);

        return uploadedFile;
    }

    //public UploadedFile deleteUploadedFile() {
    //    return null;
    //}
}

