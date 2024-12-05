package com.wysiwyg.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wwcc
 */
@SpringBootApplication(scanBasePackages = {"com.wysiwyg.gateway","com.wysiwyg.common"})
public class WysiwygGatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(WysiwygGatewayApp.class, args);
    }
}