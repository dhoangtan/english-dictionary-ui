package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.CreateWordlistForm;
import com.englishdictionary.appui.dto.RegisterForm;
import com.englishdictionary.appui.dto.WordlistForm;
import com.englishdictionary.appui.models.User;
import com.englishdictionary.appui.models.Wordlist;
import com.englishdictionary.appui.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class ProfileController {

    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final String CONTROLLER_NAME = "[ProfileController]";

    @GetMapping("/profile")
    public String getInfoProfile(HttpServletRequest request, Model model) {
        logger.info(CONTROLLER_NAME + "/[getInfoProfile] - [GET] - Called");
        if (request.getSession().getAttribute("userId") == null) {
            logger.info("\tGet user profile - Failed - User not logged in");
            logger.info("\tRedirect to [/login]");
            logger.info(CONTROLLER_NAME + "/[getInfoProfile] - [GET] - Completed");
            return "redirect:/login";
        } else {
            String userId = request.getSession().getAttribute("userId").toString();
            logger.info("\tGet user profile - Success");
            User user = userService.getUserInfo(userId);
            String urlAvt= userService.getAvt(userId);
            Map gender= userService.getGender();
            Map level= userService.getLevel();
            Map occupation= userService.getOccupation();
            model.addAttribute("gender",gender);
            model.addAttribute("level",level);
            model.addAttribute("occupation",occupation);
            String userGender=gender.get(user.getGender().toString()).toString();
            model.addAttribute("userGender",userGender);
            String userLevel=level.get(user.getLevel().toString()).toString();
            model.addAttribute("userLevel",userLevel);
            String userOccupation=occupation.get(user.getOccupation().toString()).toString();
            model.addAttribute("userOccupation",userOccupation);
            model.addAttribute("user",user);
            model.addAttribute("urlAvt",urlAvt );
            logger.info("\tRedirect to [profile/index]");
            logger.info(CONTROLLER_NAME + "/[getInfoProfile] - [GET] - Completed");
            return "profile/index";
        }
    }
    @PostMapping("/profile/update")
    public String updateProfile(
            @ModelAttribute("user") User user,HttpServletRequest request
    ) {
        logger.info(CONTROLLER_NAME + "/[updateProfile] - [POST] - Called");
        if (request.getSession().getAttribute("userId") == null) {
            logger.info("\tUpdate profile failed - User not logged in");
            logger.info("\tRedirect to [/login]");
            logger.info(CONTROLLER_NAME + "/[updateProfile] - [POST] - Completed");
            return "redirect:/login";
        }else {
            String userId = request.getSession().getAttribute("userId").toString();
            userService.updateUser(user,userId);
            logger.info("\tUpdate profile - Success");
            logger.info("\tRedirect to [/user/profile]");
            logger.info(CONTROLLER_NAME + "/[updateProfile] - [POST] - Completed");
            return "redirect:/user/profile";
        }
    }

}
