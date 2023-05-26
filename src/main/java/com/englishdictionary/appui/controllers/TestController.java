package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.Word;
import com.englishdictionary.appui.models.Wordlist;
import com.englishdictionary.appui.service.WordlistService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/")
public class TestController {
    @Autowired
    WordlistService wordlistService;
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }

    @GetMapping("/test4")
    public List<Wordlist> test4() {
//        List<Wordlist>list = wordlistService.defaultWordList();
//
//        return list.toString();
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:4040/api/wordlists/default";
        String getWordlist = restTemplate.getForObject(url,String.class);

        Gson gson = new Gson();
        List<Wordlist> wordlist = gson.fromJson(getWordlist, List.class);
        return wordlist;
    }
}
