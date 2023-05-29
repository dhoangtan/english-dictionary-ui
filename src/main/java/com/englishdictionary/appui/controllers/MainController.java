package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.LoginForm;
import com.englishdictionary.appui.dto.RegisterForm;
import com.englishdictionary.appui.service.UserService;
import com.englishdictionary.appui.service.WordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/")
@Controller
public class MainController {
    @Autowired
    WordService wordService;
    @Autowired
    UserService userService;
    @GetMapping
    public String index(
            HttpSession session
    ) {
        return "home/index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "account/login";
    }
    @PostMapping("/login")
    public String login(
            @ModelAttribute("loginForm") LoginForm loginForm,
            HttpServletRequest request
    ) throws IOException {
        if (userService.getUserId(loginForm) != null) {
            try {
                String userId = userService.getUserId(loginForm);
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(60*60);
                session.setAttribute("userId", userId);
                session.setAttribute("userName", loginForm.getEmail());
                return "redirect:/";
            }catch (Exception e){
                return "redirect:/login";
            }

        }
        else {
            return "redirect:/login";
        }
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "account/register";
    }
    @PostMapping("/register")
    public String register(
            @ModelAttribute("registerForm") RegisterForm registerForm
    )
    {
        return "redirect:/index";
    }

    public String logout(
            HttpSession session
    ) {
        session.removeAttribute("userId");
        return "redirect:/";
    }
    @ModelAttribute("userId")
    public String getUserId(HttpSession session) {
        return (String) session.getAttribute("userId");
    }


}
