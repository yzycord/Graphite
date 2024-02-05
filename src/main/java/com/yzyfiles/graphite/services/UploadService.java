package com.yzyfiles.graphite.services;

import com.yzyfiles.graphite.config.GraphiteConfig;
import com.yzyfiles.graphite.utils.HashUtil;
import com.yzyfiles.graphite.data.UploadData;
import com.yzyfiles.graphite.repositories.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UploadService {

    private final GraphiteConfig graphiteConfig;
    private final UploadRepository uploadRepository;

    @Autowired
    public UploadService(GraphiteConfig graphiteConfig, UploadRepository uploadRepository) {
        this.graphiteConfig = graphiteConfig;
        this.uploadRepository = uploadRepository;
    }

    public List<UploadData> getUploads() {
        return uploadRepository.findAll();
    }

    public UploadData getUpload(String uploadId) {
        Optional<UploadData> uploadById = uploadRepository.findByUploadId(uploadId);

        if (uploadById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "A file with uploadId: " + uploadId + " was not found.");
        }

        return uploadById.get();
    }

    public UploadData createUpload(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Error uploading. No multipartfile data found.");
        }

        Optional <String> fileHash = HashUtil.calculateMD5FileHash(multipartFile);

        if (fileHash.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Error uploading. bad request data. Could not get hash.");
        }

        Path directory = Paths.get(graphiteConfig.getUploadPath() + "/" + fileHash.get());

        // TODO: Make this the fallback instead and check redis
        //  for dupes

        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
                Path filePath = directory.resolve(fileHash.get() + ".file");

                try (OutputStream outputStream = Files.newOutputStream(filePath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = multipartFile.getInputStream().read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Error uploading. bad request data. Could not write data.", e);
                }
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Error uploading. bad request data. Could not create directories.", e);
            }
        }

        UploadData uploadData = new UploadData();
        uploadData.setFileHash(fileHash.get());
        uploadData.setUploadId(HashUtil.calculateUploadIdHash());
        uploadData.setFileName(multipartFile.getOriginalFilename());
        uploadData.setContentType(multipartFile.getContentType());
        uploadData.setCreatedAt(LocalDateTime.now());

        uploadRepository.save(uploadData);

        return uploadData;
    }
}

