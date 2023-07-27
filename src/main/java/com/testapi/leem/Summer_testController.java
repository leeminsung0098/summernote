package com.testapi.leem;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Controller
public class Summer_testController {
    @Autowired
    DbRepository dbRepository;
    @GetMapping("resources/summerimages")
    public String summernote_test(){
        return "summer_note_lite_2";
    }

//    @PostMapping("resources/summerimages")
//    public JsonObject  sendFile(@RequestParam("file") MultipartFile multipartFile){
//        System.out.println("multipartFile" + multipartFile.getOriginalFilename());
//        String originalFileName = multipartFile.getOriginalFilename();
//        String extension = "";
//
//        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
//        System.out.println("originalFile" + originalFileName);
//        String fileRoot = "C:\\apitest2\\leem\\src\\main\\resources\\static\\summerimages\\";	//저장될 외부 파일 경로
////        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
////        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
//
//        //파일 확장자가 있을경우 실행
//        if (originalFileName != null && originalFileName.contains(".")) {
//            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//        }
//
//        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
//
//        File targetFile = new File(fileRoot + savedFileName);
//
//        try {
//            System.out.println("save image");
//            InputStream fileStream = multipartFile.getInputStream();
//            FileUtils.copyInputStreamToFile(fileStream, targetFile);
//
//            // db 엔티티 생성 및 저장
//            Db entity = new Db();
//            entity.setSubject("Sample Subject");
//            entity.setContent("Sample Content");
//            entity.setRegDate(new Date());
//            entity.setUrl(savedFileName);
//            dbRepository.save(entity);
//
//            // url은 실제 서버의 저장된 장소를 보내야함
//            jsonObjectBuilder.add("url", savedFileName);
//            jsonObjectBuilder.add("responseCode", "success");
//
////            return "redirect:/uploadSummernoteImageFile";
//
//        } catch (IOException e) {
//            System.out.println("트라이캐치문 팔즈 실행");
//            FileUtils.deleteQuietly(targetFile);
//            jsonObjectBuilder.add("responseCode", "error");
//            e.printStackTrace();
//        }
//
//        JsonObject jsonObject = jsonObjectBuilder.build();
//        return jsonObject;
//    }

    @RequestMapping(value="resources/summerimages", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> sendFile(@RequestParam("file") MultipartFile img, HttpServletRequest request) throws IOException {
            System.out.println("=== start ===");

        String originalFileName = img.getOriginalFilename();
        String extension = "";

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        System.out.println("저장시킬 파일 원본 이름 : " + originalFileName);
        String fileRoot = "src\\main\\resource\\summernote_image\\";	//저장될 외부 파일 경로
//        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
//        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자

        //파일 확장자가 있을경우 실행
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        Random random = new Random();
        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);

        try {
            System.out.println("save image");
            InputStream fileStream = img.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);

            // db 엔티티 생성 및 저장
            Db entity = new Db();
            entity.setSubject("Sample Subject");
            entity.setContent("Sample Content");
            entity.setRegDate(new Date());
            entity.setUrl(savedFileName);
            dbRepository.save(entity);
            // url은 실제 서버의 저장된 장소를 보내야함
            jsonObjectBuilder.add("url", savedFileName);
            jsonObjectBuilder.add("responseCode", "success");
            System.out.println("제이손 빌더 :" + jsonObjectBuilder);

//            return "redirect:/uploadSummernoteImageFile";

        } catch (IOException e) {
            System.out.println("트라이캐치문 팔즈 실행");
            FileUtils.deleteQuietly(targetFile);
            jsonObjectBuilder.add("responseCode", "error");
            e.printStackTrace();
        }

        //=====================================================================


            String path ="src\\main\\resources\\static\\summerimages\\";



            long currentTime = System.currentTimeMillis();
            int	randomValue = random.nextInt(100);
            String fileName = Long.toString(currentTime) + "_"+randomValue+"_a_"+img.getOriginalFilename();
        System.out.println("수정된 파일 이름 : " + fileName);



        // 새로운 코드: 임시 폴더에서 실제 저장 폴더로 파일 이동
        File file = new File(path, fileName);
        System.out.println(targetFile);
        if (targetFile.exists()) {
            try {
                FileUtils.moveFile(targetFile, file);
                System.out.println(targetFile);
            } catch (IOException e) {
                System.out.println("파일 이동 중 오류 발생");
                e.printStackTrace();
                }
            }
            img.transferTo(file);
        System.out.println("파일 저장 위치 : " + file);
            return ResponseEntity.ok().body("src\\main\\resources\\static\\images\\"+fileName);

    }
}
