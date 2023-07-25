package com.testapi.leem;

import jakarta.json.JsonObject;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@org.springframework.stereotype.Service
public class Service {

//    @Value("${file.upload.dir}")
//    private String fileRoot;
//
//    public JsonObject summerNoteImageFile(MultipartFile file) {
//        JsonObject jsonObject = new JsonObject();
//        String originalFileName = file.getOriginalFilename();
//        String extension = Objects.requireNonNull(originalFileName).substring(originalFileName.lastIndexOf("."));
//
//        String saveFileName = UUID.randomUUID() + extension;
//
//        File targetFile = new File(fileRoot + saveFileName);
//
//        try {
//            InputStream fileStream = file.getInputStream();
//            FileUtils.copyInputStreamToFile(fileStream, targetFile);
//            jsonObject.addProperty("url", "/summernoteImg/" + saveFileName);
//            jsonObject.addProperty("responseCode", "success");
//        } catch (IOException e) {
//            jsonObject.addProperty("responseCode", "error");
//            e.printStackTrace();
//        }
//        return jsonObject;
//    }
}