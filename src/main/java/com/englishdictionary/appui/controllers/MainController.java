package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.LoginForm;
import com.englishdictionary.appui.dto.RegisterForm;
import com.englishdictionary.appui.service.UserService;
import com.englishdictionary.appui.service.WordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.util.logging.Logger;

@RequestMapping("/")
@Controller
public class MainController {
    @Autowired
    WordService wordService;
    @Autowired
    UserService userService;
    private final Logger logger = Logger.getLogger("com.englishdictionary.appui.controllers.MainController");
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
        try {
            if (userService.getUserId(loginForm).getStatusCode().is2xxSuccessful() == true) {
                String userId = userService.getUserId(loginForm).getBody();
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(60 * 60);
                session.setAttribute("userId", userId);
                session.setAttribute("email", loginForm.getEmail());
                return "redirect:/";
            } else {
                return "redirect:/login";
            }
        }
        catch (HttpClientErrorException e)
        {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                System.out.println("======================== Dong 62 ================");
                logger.warning("Unauthorized -- email: " + loginForm.getEmail());
                return "redirect:/login";
            }
            return "redirect:/login";
        }
        catch (HttpServerErrorException e)
        {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                logger.warning("Internal server error -- email: " + loginForm.getEmail());
                return "redirect:/login";
            }
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
    ) {
        try{
            if(userService.Register(registerForm).getStatusCode().is2xxSuccessful())
            {
                return "redirect:/login";
            }
            else
            {
                return "redirect:/register";
            }
        }
        catch (HttpClientErrorException e)
        {
            logger.warning("client Unauthorized o ngoai nhe -- email: " + registerForm.getEmail());
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                logger.warning("Unauthorized -- email: " + registerForm.getEmail());
                return "redirect:/register";
            }else {
                logger.warning("Unauthorized else -- email: " + registerForm.getEmail());
                return "redirect:/register";
            }
        }
        catch (HttpServerErrorException e)
        {
            logger.warning("server Unauthorized o ngoai nhe -- email: " + registerForm.getEmail());
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                logger.warning("Internal server error -- email: " + registerForm.getEmail());
                return "redirect:/register";
            }
            else {
                logger.warning("Internal server error else -- email: " + registerForm.getEmail());
                return "redirect:/register";
            }
        }
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