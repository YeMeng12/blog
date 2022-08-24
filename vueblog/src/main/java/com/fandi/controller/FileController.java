package com.fandi.controller;

import com.fandi.common.lang.Result;
import com.fandi.strategy.context.UploadStrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController {

    @Autowired
    private UploadStrategyContext uploadStrategyContext;


    //上传头像
    @PostMapping("/userAvatar")
    @ApiOperation(value = "用户上传头像")
    public Result userAvatar(@RequestParam(value = "file", required = false) MultipartFile file) {
        String url = uploadStrategyContext.executeUploadStrategy(file,"userAvatar/");
        HashMap<String, String> map = new HashMap<>();
        map.put("url", url);
        return Result.succ(map);
    }
}
