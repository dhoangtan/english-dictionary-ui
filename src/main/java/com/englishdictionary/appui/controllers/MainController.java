package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.LoginForm;
import com.englishdictionary.appui.dto.RegisterForm;
import com.englishdictionary.appui.dto.Word;
import com.englishdictionary.appui.service.UserService;
import com.englishdictionary.appui.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@Controller
public class MainController {
    @Autowired
    WordService wordService;
    @Autowired
    UserService userService;
    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }
    @PostMapping("/login")
    public String login(
            @ModelAttribute("loginForm") LoginForm loginForm
    )
    {
        if (userService.user(loginForm) != null) {
            return "redirect:/";
        }
        else {
            return "redirect:/login";
        }
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }
    @PostMapping("/register")
    public String register(
            @ModelAttribute("registerForm") RegisterForm registerForm
    )
    {
        return "redirect:/index";
    }



}
