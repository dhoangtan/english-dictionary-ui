package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.CreateWordlistForm;
import com.englishdictionary.appui.dto.RegisterForm;
import com.englishdictionary.appui.dto.WordlistForm;
import com.englishdictionary.appui.models.User;
import com.englishdictionary.appui.models.Wordlist;
import com.englishdictionary.appui.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
public class ProfileController {

    @Autowired
    private UserService userService;
    private final Logger logger = Logger.getLogger("com.englishdictionary.appui.controllers.ProfileController");

    @GetMapping("/profile")
    public String getInfoProfile(HttpServletRequest request, Model model) {
        if (request.getSession().getAttribute("userId") == null) {
            return "redirect:/login";
        } else {
            String userId = request.getSession().getAttribute("userId").toString();
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
            return "profile/index";
        }
    }
    @PostMapping("/profile/update")
    public String updateProfile(
            @ModelAttribute("user") User user,HttpServletRequest request
    ) {
        if (request.getSession().getAttribute("userId") == null) {
            return "redirect:/login";
        }else {
            String userId = request.getSession().getAttribute("userId").toString();
            userService.updateUser(user,userId);
            return "redirect:/user/profile";
        }
    }

}
