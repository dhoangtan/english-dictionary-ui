package com.englishdictionary.appui.service;

import com.englishdictionary.appui.dto.LoginForm;
import com.englishdictionary.appui.models.User;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;


@Service
public class UserService {
    private final String Port = "4040";

    public User user(
            @PathVariable("loginForm") @NonNull LoginForm loginForm
    ) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + Port + "/api/user/" + loginForm.getEmail() + "/" + loginForm.getPassword();
        User user = restTemplate.getForObject(url, User.class);
        return user;
    }
    public String getUser(
            @PathVariable("loginForm") @NonNull LoginForm loginForm
    ) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // request body parameters
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("email", loginForm.getEmail());
//        jsonObject.put("password", loginForm.getPassword());

        HttpEntity<LoginForm> request = new HttpEntity<>(loginForm, headers);
        String url = "http://localhost:" + Port + "/api/user/";
        String user = restTemplate.postForObject(url, request,String.class);
        return user;
    }

    public void Register(
            @PathVariable("registerForm") @NonNull LoginForm registerForm
    ) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + Port + "/api/user/register";
        restTemplate.postForObject(url, registerForm, LoginForm.class);
    }
}
