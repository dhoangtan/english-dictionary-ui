package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.LoginForm;
import com.englishdictionary.appui.dto.RegisterForm;
import com.englishdictionary.appui.dto.WordlistForm;
import com.englishdictionary.appui.service.WordlistService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
public class tessttt {
    @Autowired
    WordlistService wordlistService;
    @PostMapping("/test8")
    @ResponseBody
    public LoginForm test8(
            @ModelAttribute("loginForm") LoginForm registerForm
    ) {
        return registerForm;

    }
    @GetMapping("/flipcard")
    @ResponseBody
    public String FlipCard(){

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:4040/api/wordlists/{userId}/{wordlistId}";
        Map<String, String> params = Map.of("userId","2dcJ9WIS3ZRh7UncxXOorrZodiz1","wordlistId","WRRe2zLYrPMF4NpILywj");
        String wordlist = restTemplate.getForObject(url, String.class, params);
        Gson gson = new Gson();
        WordlistForm wordlistForm = gson.fromJson(wordlist, WordlistForm.class);
        return wordlistForm.getName();
    }


}
