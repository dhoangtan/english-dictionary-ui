package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.Word;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller()
//@RequestMapping("/")
public class TestController {

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }


}
