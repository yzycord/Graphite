package com.yzyfiles.graphite.services;

import com.yzyfiles.graphite.GraphiteUtil;
import com.yzyfiles.graphite.data.FileData;
import com.yzyfiles.graphite.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UploadedService {

    @Value("${graphite.upload-path}")
    private String uploadPath;

    FileRepository fileRepository;

    @Autowired
    public UploadedService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<FileData> getUploads() {
        return fileRepository.findAll();
    }

    public FileData getUpload(String uploadId) {
        Optional<FileData> uploadedFileById = fileRepository
            .findByUploadId(uploadId);

        if (uploadedFileById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "A file with uploadId: " + uploadId + " was not found.");
        }

        return uploadedFileById.get();
    }

    public FileData createUploadedFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Error uploading. No multipartfile data found.");
        }

        Optional <String> fileHash = GraphiteUtil.calculateMD5FileHash(multipartFile);

        if (fileHash.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Error uploading. bad request data. Could not get hash.");
        }

        Path directory = Paths.get(uploadPath + "/" + fileHash.get());

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

        FileData fileData = new FileData();
        fileData.setFileHash(fileHash.get());
        fileData.setUploadId(GraphiteUtil.calculateUploadIdHash());
        fileData.setFileName(multipartFile.getOriginalFilename());
        fileData.setContentType(multipartFile.getContentType());
        fileData.setCreatedAt(LocalDateTime.now());

        fileRepository.save(fileData);

        return fileData;
    }

    public ResponseEntity<byte[]> getUploadedFileBytesByFileHash(FileData fileData) {
        
        // this just feels messy but idk

        String fileHash = fileData.getFileHash();
        Path filePath = Paths.get(uploadPath, fileHash, fileHash + ".file");

        try(InputStream inputStream = Files.newInputStream(filePath)) {
            byte[] fileBytes = inputStream.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(fileData.getContentType()));
            ContentDisposition contentDisposition = ContentDisposition
                    .builder("attachment")
                    .filename(fileData.getFileName())
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

    //public static MultipartFile downloadFile() { return null; }

}

