package com.yzyfiles.api.services;

import com.yzyfiles.api.files.UploadedFile;
import com.yzyfiles.api.repository.UploadedFileRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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

    public UploadedFile createUploadedFile(MultipartFile multipartFile) {
        // this needs auth

        String uploadId = System.currentTimeMillis() / 1000 + "-temp";

        Optional <String> fileHash = calculateMD5Hash(multipartFile);

        if (fileHash.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Error uploading uploadId: " + uploadId + " bad request data. Could not get hash.");
        }

        Path directory = Paths.get(uploadPath + "/" + fileHash.get());

        // prevent dupes
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
                Path filePath = directory.resolve(fileHash.get() + ".file");
                Files.write(filePath, multipartFile.getBytes());
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Error uploading uploadId: " + uploadId + " bad request data. Could not write data.", e);
            }
        }

        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setFileHash(fileHash.get());
        uploadedFile.setUploadId(uploadId);
        uploadedFile.setFileName(multipartFile.getOriginalFilename());
        uploadedFile.setContentType(multipartFile.getContentType());
        uploadedFile.setCreatedAt(LocalDateTime.now());

        uploadedFileRepository.save(uploadedFile);

        return uploadedFile;
    }

    public ResponseEntity<byte[]> getUploadedFileBytesByFileHash(UploadedFile uploadedFile) {
        
        // this just feels messy but idk

        String fileHash = uploadedFile.getFileHash();
        Path filePath = Paths.get(uploadPath, fileHash, fileHash + ".file");

        try(InputStream inputStream = Files.newInputStream(filePath)) {
            byte[] fileBytes = inputStream.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(uploadedFile.getContentType()));
            ContentDisposition contentDisposition = ContentDisposition
                    .builder("attachment")
                    .filename(uploadedFile.getFileName())
                    .build();
            headers.setContentDisposition(contentDisposition);

            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File bytes were not found", e);
        }
    }

//    public UploadedFile deleteUploadedFile(String uploadId) {
//        // TODO: [URGENT] THIS NEEDS AUTH
//
//        Optional<UploadedFile> uploadedFileById = uploadedFileRepository
//            .findByUploadId(uploadId);
//
//        if (uploadedFileById.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
//                "A file with uploadId: " + uploadId + " was not found.");
//        }
//
//        UploadedFile uploadedFile = uploadedFileById.get();
//        uploadedFileRepository.deleteById(uploadedFile.getId());
//
//        return uploadedFile;
//    }

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

