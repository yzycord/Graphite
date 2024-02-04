package com.yzyfiles.api.services;

import com.yzyfiles.api.files.UploadedFile;
import com.yzyfiles.api.repository.UploadedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public UploadedFile getUploadedFile(String uploadId) {
        Optional<UploadedFile> uploadedFileById = uploadedFileRepository
            .findByUploadId(uploadId);

        if (uploadedFileById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "A file with uploadId: " + uploadId + " was not found.");
        }

        return uploadedFileById.get();
    }

    public UploadedFile createUploadedFile(UploadedFile uploadedFile) {
        // this needs auth

        Optional<UploadedFile> uploadedFileById = uploadedFileRepository
            .findByUploadId(uploadedFile.getUploadId());

        if (uploadedFileById.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "A file with uploadId: " + uploadedFile.getId() + " already exists.");
        }

        uploadedFileRepository.save(uploadedFile);

        return uploadedFile;
    }

    public UploadedFile deleteUploadedFile(String uploadId) {
        // TODO: [URGENT] THIS NEEDS AUTH

        Optional<UploadedFile> uploadedFileById = uploadedFileRepository
            .findByUploadId(uploadId);

        if (uploadedFileById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "A file with uploadId: " + uploadId + " was not found.");
        }

        UploadedFile uploadedFile = uploadedFileById.get();
        uploadedFileRepository.deleteById(uploadedFile.getId());

        return uploadedFile;
    }
}

