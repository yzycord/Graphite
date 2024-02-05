package com.yzyfiles.api;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

public class GraphiteUtil {

    public static Optional<String> calculateMD5FileHash(MultipartFile multipartFile) {
        try {
            String hash = DigestUtils.md5Hex(multipartFile.getBytes());
            return Optional.of(hash);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty(); // Return an empty Optional if an error occurs
        }
    }

    public static String calculateUploadIdHash(MultipartFile multipartFile) {
        Random random = new Random();

        // i rather not convert to string, so I just have ":".
        String hash = System.currentTimeMillis() + ":" + random.nextInt(1337) + ":" + multipartFile;
        return DigestUtils.md5Hex(hash);
    }
}
