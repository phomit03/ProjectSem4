package com.example.eproject4.Utils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class Helper {
    private final String uploadImageDir = "./src/main/resources/static/img";

    public Object[] uploadImage(MultipartFile file) throws IOException {
        Object[] result = new Object[2];
        result[0] = false;
        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path uploadPath = Path.of(uploadImageDir);
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            result[0] = true;
            result[1] = "/img/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
