package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.ChangePassword;
import com.englishdictionary.appui.dto.LoginForm;
import com.englishdictionary.appui.models.User;
import com.englishdictionary.appui.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            model.addAttribute("userId",userId);
            /*model.addAttribute("fileAvt", new FileAvt());*/
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
    public String updateProfile(@Valid @ModelAttribute("user") User user,
                                HttpServletRequest request) {
        try {
            logger.info(CONTROLLER_NAME + "/[updateProfile] - [POST] - Called");
            if (request.getSession().getAttribute("userId") == null) {
                logger.info("\tUpdate profile failed - User not logged in");
                logger.info("\tRedirect to [/login]");
                logger.info(CONTROLLER_NAME + "/[updateProfile] - [POST] - Completed");
                return "redirect:/login";
            }else {
                String userId = request.getSession().getAttribute("userId").toString();
                userService.updateUser(user,userId);
                if (userService.updateUser(user,userId).getStatusCode().is2xxSuccessful()) {
                    logger.info("\tUpdate profile - Success");
                    logger.info("\tRedirect to [/user/profile]");
                    logger.info(CONTROLLER_NAME + "/[updateProfile] - [POST] - Completed");
                    return "redirect:/user/profile";
                }
                return "redirect:/user/profile";
            }
        }catch (HttpClientErrorException e)
        {
            logger.error("\tError occurred on Client side with error message: " + e.getMessage());
            logger.info("\tRedirect to [/Profile]");
            return "redirect:/user/profile";
        }
        catch (HttpServerErrorException e)
        {
            logger.error("\tError occurred on Server side with error: " + e.getStatusCode());
            logger.info("\tRedirect to [/Profile]");
            return "redirect:/user/profile";
        }



    }
    @PostMapping("/profile/updateAvt")
    public String updateAvt(@RequestParam("files") MultipartFile files, HttpServletRequest request){
        logger.info(CONTROLLER_NAME + "/[updateAvatar] - [POST] - Called");
        if (request.getSession().getAttribute("userId") == null) {
            logger.info("\tUpdate profile failed - User not logged in");
            logger.info("\tRedirect to [/login]");
            logger.info(CONTROLLER_NAME + "/[updateAvatar] - [POST] - Completed");
            return "redirect:/login";
        }else {
            String userId = request.getSession().getAttribute("userId").toString();

            userService.updateAvatar(files,userId);
            logger.info("\tUpdate profile - Success");
            logger.info("\tRedirect to [/user/profile]");
            logger.info(CONTROLLER_NAME + "/[updateAvatar] - [POST] - Completed");
            return "redirect:/user/profile";
        }
    }
    @GetMapping("/changePassword")
    public String changePassword(Model model) {
        logger.info(CONTROLLER_NAME + "/[changePassword] - [GET] - Called");
        model.addAttribute("changePassword", new ChangePassword());
        logger.info(CONTROLLER_NAME + "/[changePassword] - Completed");
        return "account/changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(
            @ModelAttribute("changePassword") ChangePassword changePassword,
            HttpServletRequest request
    ) throws IOException {
        try {
            logger.info(CONTROLLER_NAME + "/[changePassword] - [PUT] - Called");
            logger.info("\tCall Get user Id");
            LoginForm loginForm= new LoginForm(changePassword.getEmail(),changePassword.getPassword());
            if (userService.getUserId(loginForm).getStatusCode().is2xxSuccessful()) {
                logger.info("\tGet user called - Completed");
                logger.info("\tUser Login - Success");
                String userId = userService.getUserId(loginForm).getBody();
                loginForm.setPassword(changePassword.getNewPassword());
                userService.changePassword(loginForm,userId);
                if (userService.changePassword(loginForm,userId).getStatusCode().is2xxSuccessful()) {
                    logger.info("\tPut change password called - Completed");
                    logger.info("\tChange password - Success");
                    if (userService.getUserId(loginForm).getStatusCode().is2xxSuccessful()) {
                        logger.info("\tGet user called - Completed");
                        logger.info("\tUser Login - Success");
                        String userIdnew = userService.getUserId(loginForm).getBody();
                        logger.info("\tCreate session");
                        HttpSession session = request.getSession();
                        session.setMaxInactiveInterval(60 * 60);
                        session.setAttribute("userId", userIdnew);
                        session.setAttribute("email", loginForm.getEmail());
                        logger.info("\tCreate session - Completed");
                        logger.info("\tRedirect to [/]");
                        return "redirect:/";
                    }else {
                        logger.info("\tGet user called - Completed");
                        logger.info("\tUser Login - Failed");
                        logger.info("\tRedirect to [/login]");
                        return "redirect:/login";
                    }
                }else {
                    logger.info("\tPut change password called - Completed");
                    logger.info("\tChange password - Failed");
                    logger.info("\tRedirect to [/changePassword]");
                    return "redirect:/changePassword";
                }
            } else {
                logger.info("\tGet user called - Completed");
                logger.info("\tCheck User - Failed");
                logger.info("\tRedirect to [/changePassword]");
                return "redirect:/changePassword";
            }
        }
        catch (HttpClientErrorException e)
        {
            logger.error("\tError occurred on Client side with error message: " + e.getMessage());
            logger.info("\tRedirect to [/changePassword]");
            return "redirect:/changePassword";
        }
        catch (HttpServerErrorException e)
        {
            logger.error("\tError occurred on Server side with error: " + e.getStatusCode());
            logger.info("\tRedirect to [/changePassword]");
            return "redirect:/changePassword";
        }
    }
    @GetMapping("/resetPassword")
    public String resetPassword(Model model) {
        logger.info(CONTROLLER_NAME + "/[resetPassword] - [POST] - Called");
        model.addAttribute("loginForm", new LoginForm());
        logger.info("\tRedirect to [account/resetPassword]");
        logger.info(CONTROLLER_NAME + "/[resetPassword] - [POST] - Completed");
        return "account/resetPassword";
    }
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("code") String code,
                           @ModelAttribute("loginForm") LoginForm loginForm
    ) {
        try{
            logger.info(CONTROLLER_NAME + "/[resetPassword] - [POST] - Called");
            if(userService.resetPassword(loginForm,code).getStatusCode().is2xxSuccessful())
            {
                logger.info("\tUser reset password - Success");
                logger.info("\tRedirect to [/login]");
                logger.info(CONTROLLER_NAME + "/[resetPassword] - [POST] - Completed");
                return "redirect:/login";
            }
            else
            {
                logger.info("\tUser login - Failed");
                logger.info("\tRedirect to [/resetPassword]");
                logger.info(CONTROLLER_NAME + "/[resetPassword] - [POST] - Completed");
                return "redirect:/resetPassword";
            }
        }
        catch (HttpClientErrorException e)
        {
            logger.error("\tError occurred on Client side with error message: " + e.getMessage());
            logger.info("\tRedirect to [/resetPassword]");
            return "redirect:/resetPassword";
        }
        catch (HttpServerErrorException e)
        {
            logger.error("\tError occurred on Server side with error: " + e.getStatusCode());
            logger.info("\tRedirect to [/resetPassword]");
            logger.info(CONTROLLER_NAME + "/[resetPassword] - [POST] - Completed");
            return "redirect:/resetPassword";
        }
    }

}
