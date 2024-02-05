package com.yzyfiles.api.services;

import com.yzyfiles.api.files.UploadedFile;
import com.yzyfiles.api.repository.UploadedFileRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class UploadedFileService {

    @Value("${graphite.upload-path}")
    private String uploadPath;

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

    public UploadedFile createUploadedFile(UploadedFile uploadedFile, MultipartFile multipartFile) {
        // this needs auth

        Optional<UploadedFile> uploadedFileById = uploadedFileRepository
            .findByUploadId(uploadedFile.getUploadId());

        if (uploadedFileById.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "A file with uploadId: " + uploadedFile.getId() + " already exists.");
        }

        Optional <String> fileHash = calculateMD5Hash(multipartFile);

        if (fileHash.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Error uploading uploadId: " + uploadedFile.getId() + " bad request data. Could not get hash.");
        }

        Path filePath = Paths.get(uploadPath + "/" + fileHash + "/" + fileHash + ".file");

        // prevent dupes
        if (!Files.exists(filePath)) {
            try {
                Files.write(filePath, multipartFile.getBytes());
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Error uploading uploadId: " + uploadedFile.getId() + " bad request data. Could not write data.");
            }
        }

        uploadedFile.setFileHash(fileHash.get());
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

    public static Optional<String> calculateMD5Hash(MultipartFile file) {
        try {
            String hash = DigestUtils.md5Hex(file.getBytes());
            return Optional.of(hash);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty(); // Return an empty Optional if an error occurs
        }
    }

    //public static MultipartFile downloadFile() { return null; }

}

