package com.testapi.leem;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;
import java.util.UUID;

@Controller
public class SummerController {

    //섬머노트 api 페이지 불러오기및 저장
    @Autowired
    private DbRepository dbRepository;
    @Autowired
    private ResourceLoader resourceLoader;
    @GetMapping("uploadSummernoteImageFile")
    public String ac(){
        return "summer_note_lite";
    }

    @PostMapping(value="/uploadSummernoteImageFile", produces = "application/json")
    @ResponseBody
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
        String originalFileName = multipartFile.getOriginalFilename();
        String extension = "";

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        System.out.println("조인");
        String fileRoot = "C:\\summernote_image\\";	//저장될 외부 파일 경로
//        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
//        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자

        //파일 확장자가 있을경우 실행
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
        
        File targetFile = new File(fileRoot + savedFileName);

        try {
            System.out.println("트라이캐치문 트루 실행");
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);

            // db 엔티티 생성 및 저장
            Db entity = new Db();
            entity.setSubject("Sample Subject");
            entity.setContent("Sample Content");
            entity.setRegDate(new Date());
            entity.setUrl(savedFileName);
            dbRepository.save(entity);

            jsonObjectBuilder.add("url", "/summernoteImage/"+savedFileName);
            jsonObjectBuilder.add("responseCode", "success");

//            return "redirect:/uploadSummernoteImageFile";

        } catch (IOException e) {
            System.out.println("트라이캐치문 팔즈 실행");
            FileUtils.deleteQuietly(targetFile);
            jsonObjectBuilder.add("responseCode", "error");
            e.printStackTrace();
        }

        JsonObject jsonObject = jsonObjectBuilder.build();
        return jsonObject;
    }
    // 끝 ---------------------


    // 저장된 url 을 불러오기
    @GetMapping("/getSummernoteImage/{url}")
    public String getSummernoteImageFile(@PathVariable("url") String url, Model model) {
        String fileRoot = "file:C:/summernote_image/"; // 파일이 저장된 외부 디렉토리 경로
        Resource resource = resourceLoader.getResource(fileRoot + url);
        System.out.println("저장 위치 정보 :" + resource);

        if (resource.exists()) {
            model.addAttribute("imageUrl", "/img?url=" + url);
        } else {
            model.addAttribute("imageUrl", ""); // 파일이 존재하지 않는 경우 빈 문자열로 처리
        }

        return "img"; // 템플릿 이름 반환
    }



}
