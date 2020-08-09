package com.bibe.crm;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@MapperScan(basePackages = {"com.bibe.crm.dao"})
@EnableScheduling
public class BibeCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibeCrmApplication.class, args);
        log.info("start ok！");
    }

}
