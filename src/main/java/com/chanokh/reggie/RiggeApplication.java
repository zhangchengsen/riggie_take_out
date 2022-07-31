package com.chanokh.reggie;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j  // 能使用log了
@SpringBootApplication
@MapperScan("com.chanokh.reggie.mapper")
@ServletComponentScan
@EnableTransactionManagement
public class RiggeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RiggeApplication.class, args);
        log.info("build success");
    }

}
