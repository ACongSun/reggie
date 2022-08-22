package com.ssc.controller;

import com.ssc.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName Upload
 * @Authoc 孙少聪
 * @Date 2022/8/22 16:08:17
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        // file时临时文件，需要转存到指定位置，否则本次的请求完成之后临时文件删除
        log.info(file.toString());
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 使用UUID来重新生成一个文件名防止重复
        String s = UUID.randomUUID().toString()+suffix;

        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        try {
            // 将临时文件转存到指定位置
            file.transferTo(new File(basePath+s));
        }catch (IOException e){
            e.printStackTrace();
        }
        return R.success(s);
    }

}
