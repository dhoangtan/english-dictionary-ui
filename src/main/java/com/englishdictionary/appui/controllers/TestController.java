package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.models.Wordlist;
import com.englishdictionary.appui.service.UserService;
import com.englishdictionary.appui.service.WordlistService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller()
//@RequestMapping("/")
public class TestController {
    @Autowired
    WordlistService wordlistService;
    @Autowired
    UserService userService;;

    @GetMapping("/test")
//    @ResponseBody
    public String test(
            HttpServletRequest request,
            Model model
    ) {
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
    @GetMapping("/test8")
    @ResponseBody
    public String test8(
            HttpServletRequest request) {
   Map test= userService.getGender();
        return test.toString();
    }


}
