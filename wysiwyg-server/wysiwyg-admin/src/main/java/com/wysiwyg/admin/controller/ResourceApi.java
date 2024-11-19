package com.wysiwyg.admin.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/res")
public class ResourceApi {



    @PostMapping("/test")
    public Map<String,Object> foo(@RequestBody Map<String,Object> body){
        return Collections.singletonMap("hello",body);
    }
}
