package com.testapi.leem;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class SummerController {
    @GetMapping("/getSummernoteImage")
    public String getSummernoteImageFile() {
        return "summer_note_lite";
    }

    @Value("${upload.path}") //저장위치 설정. properties에서 가져온다.
    private String fileUploadPath;

    @PostMapping("/uploadSummernoteImageFile")
    @ResponseBody
    public ResponseEntity<?> uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return ResponseEntity.badRequest().body("업로드할 파일을 선택해주세요.");
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String savedFileName = sdf.format(new Date()) + extension;

        File targetFile = new File(fileUploadPath + savedFileName);
        System.out.println("저장위치 : " + targetFile);

        try {
            System.out.println("성공 ");
            multipartFile.transferTo(targetFile);
            String imageUrl = "/summerimages/" + savedFileName;

            JsonObjectBuilder responseJson = Json.createObjectBuilder();
            responseJson.add("imageUrl", imageUrl);

            return ResponseEntity.ok(responseJson.build());
        } catch (IOException e) {
            System.out.println("실패 ");
            e.printStackTrace();
            return ResponseEntity.status(500).body("이미지 업로드에 실패하였습니다.");
        }
    }

    @GetMapping("/getImage")
    @ResponseBody
    public ResponseEntity<Resource> uploadAndInsertImage(@RequestParam("imageName") String imageName) {
        System.out.println("이미지 불러오기 실행");
        try {
            System.out.println("이미지 파일 읽기 시작");
            // 이미지가 저장된 경로에서 이미지 파일을 읽어옵니다.
            Resource resource = new UrlResource("file:" + fileUploadPath + imageName);

            if (resource.exists()) {
                System.out.println("이미지 리소스 반환 시작");
                // 이미지가 존재하면 해당 리소스를 반환합니다.
                return ResponseEntity.ok(resource);
            } else {
                System.out.println("이미지 존재 하지않아 404 반환");
                // 이미지가 존재하지 않으면 404 Not Found 상태를 반환합니다.
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            System.out.println("이미지 파일 읽기 실패,");
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @GetMapping("img/{20230728153334.jpg}")
    public String imgcheck(@RequestParam(name = "imageUrl", required = false)String imageUrl){

        return "img";
    }

}
        // 끝 ---------------------


