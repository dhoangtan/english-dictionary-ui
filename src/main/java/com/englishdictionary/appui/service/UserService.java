package com.englishdictionary.appui.service;

import com.englishdictionary.appui.dto.LoginForm;
import com.englishdictionary.appui.dto.RegisterForm;
import com.englishdictionary.appui.models.User;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

@Service
public class UserService {
    private final String Port = "4040";

    public User getUser(
            @PathVariable("loginForm") @NonNull LoginForm loginForm) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost/:" + Port + "/api/user/" + loginForm.getEmail() + "/" + loginForm.getPassword();
        User user = restTemplate.getForObject(url, User.class);
        return user;
    }

    public String getUserId(
            @PathVariable("loginForm") @NonNull LoginForm loginForm) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginForm> request = new HttpEntity<>(loginForm, headers);
        String url = "http://localhost/:" + Port + "/api/user/";
        String user = restTemplate.postForObject(url, request, String.class);
        return user == null ? null : user;
    }

    public String Register(@PathVariable("registerForm") @NonNull RegisterForm registerForm) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RegisterForm> request = new HttpEntity<>(registerForm, headers);
            String url = "http://localhost/:" + Port + "/api/user/new";
            String user = restTemplate.postForObject(url, request, String.class);
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}