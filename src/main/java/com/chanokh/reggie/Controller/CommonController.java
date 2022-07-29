package com.chanokh.reggie.Controller;

import com.chanokh.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String path;
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {   //参数名要与name一致!
        //当前file为临时文件 转存 否则请求完成则消除
        log.info(file.toString());
        try {
            //将临时文件转存到指定位置
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String filename = UUID.randomUUID().toString() + suffix;
            //如果没目录
            //File dir = new File(path);
            //if(!dir.exists()) {
            //    dir.mkdirs();
            //}

            file.transferTo(new File(path + filename    ));
            return R.success(filename);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("download")
    public void download(String name, HttpServletResponse response) {
        try {
            FileInputStream fi = new FileInputStream(path + name);
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            response.setContentType("image/jpeg");
            int len = 0;
            while((len = fi.read(bytes) ) != -1 ) {
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
