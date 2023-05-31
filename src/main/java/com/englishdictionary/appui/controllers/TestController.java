package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.LoginForm;
import com.englishdictionary.appui.dto.RegisterForm;
import com.englishdictionary.appui.dto.Word;
import com.englishdictionary.appui.models.Wordlist;
import com.englishdictionary.appui.service.UserService;
import com.englishdictionary.appui.service.WordlistService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Controller()
//@RequestMapping("/")
public class TestController {
    @Autowired
    WordlistService wordlistService;
    @Autowired
    UserService userService;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }

    @GetMapping("/test4")
    public List<Wordlist> test4() {
        // List<Wordlist>list = wordlistService.defaultWordList();
        //
        // return list.toString();
        return wordlistService.defaultWordList();
    }

    @GetMapping("/test5")
    public String test5() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:4040/api/wordlists/default";
        String getWordlist = restTemplate.getForObject(url, String.class);

        JSONObject jsonObject = new JSONObject(getWordlist.substring(1, getWordlist.length() - 1));

        Gson gson = new Gson();
        List<Wordlist> wordlist = gson.fromJson(jsonObject.toString(), List.class);
        return getWordlist.toString();
    }

    @GetMapping("/test6")
    public String test6(
            HttpServletRequest request) {
        try {
            return request.getSession().getAttribute("userId").toString();
        } catch (Exception e) {
            return "Session times out";
        }
    }

    @GetMapping("/test7")
    @ResponseBody
    public String test7(
            HttpServletRequest request) {
        String userId = request.getSession().getAttribute("userId").toString();
        List<Wordlist> wordlistForm = wordlistService.getAllUserWordList(userId);
        return wordlistForm.toString();
    }

    @PostMapping("/test10")
    @ResponseBody
    public String getUserId(
            @ModelAttribute("loginForm") @NonNull LoginForm loginForm
    ) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("dòng 89");
        HttpEntity<LoginForm> request = new HttpEntity<>(loginForm, headers);
        System.out.println("dòng 91");
        String url = "http://localhost:4040/api/user/";
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("dòng 95");
            HttpStatusCode statusCode = response.getStatusCode();
            if (ResponseEntity.status(statusCode).build().getStatusCode().is2xxSuccessful())
            {
                String user = response.getBody();
//                return ResponseEntity.ok(user).toString();
                System.out.println("========================");
                System.out.println("status code là: " + statusCode.value());
                System.out.println("response là: " + response.getStatusCode());
                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("response là: " + response.getBody());
                }
                System.out.println("========================");
                return statusCode.value() + " + " + user;
//                return "redirect:/";
            }
            if (ResponseEntity.status(statusCode).build().getStatusCode().is4xxClientError()) {
                System.out.println("=========Errorr===============");
                System.out.println("status code là: " + statusCode.value());
                System.out.println("response là: " + response.getStatusCode());
                System.out.println("========================");
                return statusCode.value() + "---401--eror--- ";
            } else {
//                return ResponseEntity.status(statusCode).build().toString();
                return statusCode.value() + " chỗ này ";
//                return "redirect:/login";

            }
        } catch (HttpClientErrorException ex) {
            HttpStatusCode statusCode = ex.getStatusCode();
            System.out.println("=========Errorr 123 ===============");
            return ResponseEntity.status(statusCode).build().toString();
        }
    }

}
