package com.englishdictionary.appui.service;

import com.englishdictionary.appui.dto.LoginForm;
import com.englishdictionary.appui.dto.RegisterForm;
import com.englishdictionary.appui.models.User;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Service
public class UserService {
    private final String Port = "4040";

    // Lấy ra userId từ email và password
    public ResponseEntity<String> getUserId(
            @PathVariable("loginForm") @NonNull LoginForm loginForm) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginForm> request = new HttpEntity<>(loginForm, headers);
        String url = "http://localhost:" + Port + "/api/user/";
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response;
    }

    public ResponseEntity<String> Register(@PathVariable("registerForm") @NonNull RegisterForm registerForm) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RegisterForm> request = new HttpEntity<>(registerForm, headers);
            String url = "http://localhost:" + Port + "/api/user/new";
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserInfo(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + Port + "/api/user/" + userId + "/profile";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, entity, User.class);
        User user = response.getBody();
        return user;
    }

    public String getAvt(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + Port + "/api/user/profile/avatar/" + userId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody().toString();
    }

    public ResponseEntity<String> updateUser(@PathVariable("user") @NonNull User user, String userId) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<User> request = new HttpEntity<>(user, headers);
            String url = "http://localhost:" + Port + "/api/user/profile/" + userId;
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
