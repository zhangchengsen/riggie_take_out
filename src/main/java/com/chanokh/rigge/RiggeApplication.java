package com.chanokh.rigge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j  // 能使用log了
@SpringBootApplication
public class RiggeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RiggeApplication.class, args);
        log.info("build success");
    }

}
