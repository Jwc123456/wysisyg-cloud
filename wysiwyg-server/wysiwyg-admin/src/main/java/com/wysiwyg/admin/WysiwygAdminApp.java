package com.wysiwyg.admin;


import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author wwcc
 */
@SpringBootApplication(scanBasePackages = {"com.wysiwyg.admin","com.wysiwyg.common"})
@MapperScan("com.wysiwyg.admin.mapper")
public class WysiwygAdminApp {


    public static void main(String[] args) {
        SpringApplication.run(WysiwygAdminApp.class, args);
    }


}