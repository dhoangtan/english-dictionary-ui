package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.Lang;
import com.englishdictionary.appui.dto.LoginForm;
import com.englishdictionary.appui.dto.RegisterForm;
import com.englishdictionary.appui.service.UserService;
import com.englishdictionary.appui.service.WordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.util.Map;

@RequestMapping("/")
@Controller
public class MainController {
    @Autowired
    WordService wordService;
    @Autowired
    UserService userService;
    @Autowired
    private MessageSource messageSource;
    private final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final String CONTROLLER_NAME = "[MainController]";


    @GetMapping
    public String index(
//            HttpSession session
            HttpServletRequest request,
            Model model
    ) {
        logger.info(CONTROLLER_NAME + "/[index] - [GET] - Called");
        logger.info(CONTROLLER_NAME + "/[index] - [GET] - Completed");
        return "home/index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        logger.info(CONTROLLER_NAME + "/[login] - [GET] - Called");
        model.addAttribute("loginForm", new LoginForm());
        logger.info(CONTROLLER_NAME + "/[login] - Completed");
        return "account/login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("loginForm") LoginForm loginForm,
            HttpServletRequest request
    ) throws IOException {
        try {
            logger.info(CONTROLLER_NAME + "/[login] - [POST] - Called");
            logger.info("\tCall Get user Id");
            if (userService.getUserId(loginForm).getStatusCode().is2xxSuccessful()) {
                logger.info("\tGet user called - Completed");
                logger.info("\tUser Login - Success");
                String userId = userService.getUserId(loginForm).getBody();
                logger.info("\tCreate session");
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(60 * 60);
                session.setAttribute("userId", userId);
                session.setAttribute("email", loginForm.getEmail());
                logger.info("\tCreate session - Completed");
                logger.info("\tRedirect to [/]");
                return "redirect:/";
            } else {
                logger.info("\tGet user called - Completed");
                logger.info("\tUser Login - Failed");
                logger.info("\tRedirect to [/login]");
                return "redirect:/login";
            }
        }
        catch (HttpClientErrorException e)
        {
            logger.error("\tError occurred on Client side with error message: " + e.getMessage());
            logger.info("\tRedirect to [/login]");
            return "redirect:/login";
        }
        catch (HttpServerErrorException e)
        {
            logger.error("\tError occurred on Server side with error: " + e.getStatusCode());
            logger.info("\tRedirect to [/login]");
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        logger.info(CONTROLLER_NAME + "/[register] - [GET] - Called");
        model.addAttribute("registerForm", new RegisterForm());
        Map gender= userService.getGender();
        Map level= userService.getLevel();
        Map occupation= userService.getOccupation();
        model.addAttribute("gender",gender);
        model.addAttribute("level",level);
        model.addAttribute("occupation",occupation);
        logger.info("\tRedirect to [account/register]");
        logger.info(CONTROLLER_NAME + "/[register] - [GET] - Completed");
        return "account/register";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute("registerForm") RegisterForm registerForm
    ) {
        try{
            logger.info(CONTROLLER_NAME + "/[register] - [POST] - Called");
            if(userService.Register(registerForm).getStatusCode().is2xxSuccessful())
            {
                logger.info("\tUser login - Success");
                logger.info("\tRedirect to [/login]");
                logger.info(CONTROLLER_NAME + "/[register] - [POST] - Completed");
                return "redirect:/login";
            }
            else
            {
                logger.info("\tUser login - Failed");
                logger.info("\tRedirect to [/register]");
                logger.info(CONTROLLER_NAME + "/[register] - [POST] - Completed");
                return "redirect:/register";
            }
        }
        catch (HttpClientErrorException e)
        {
            logger.error("\tError occurred on Client side with error message: " + e.getMessage());
            logger.info("\tRedirect to [/register]");
            return "redirect:/register";
        }
        catch (HttpServerErrorException e)
        {
            logger.error("\tError occurred on Server side with error: " + e.getStatusCode());
            logger.info("\tRedirect to [/register]");
            logger.info(CONTROLLER_NAME + "/[register] - [POST] - Completed");
            return "redirect:/register";
        }
    }

    @GetMapping("/logout")
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