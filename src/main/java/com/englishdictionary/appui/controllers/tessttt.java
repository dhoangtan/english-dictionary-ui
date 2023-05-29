package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class tessttt {
    @PostMapping("/test8")
    @ResponseBody
    public RegisterForm test8(
            @ModelAttribute("registerForm") RegisterForm registerForm
    ) {
        return registerForm;

    }
    @GetMapping("/flipcard")
    public String FlipCard(){
        return "word/flipcard";
    }
}
