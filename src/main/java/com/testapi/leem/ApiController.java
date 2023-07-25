package com.testapi.leem;

import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ApiController {

//    @RequestMapping(value="SummerNoteImageFile" , method = RequestMethod.POST)
//    public @ResponseBody JsonObject SummerNoteImageFile(@RequestParam("file") MultipartFile file) {
//        JsonObject jsonObject = pls.SummerNoteImageFile(file);
//        System.out.println(jsonObject);
//        return jsonObject;
//    }


}
