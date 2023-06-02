package com.englishdictionary.appui.service;

import com.englishdictionary.appui.dto.*;
import com.englishdictionary.appui.models.Wordlist;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Service
public class WordlistService {
    private final String Port = "4040";

    // lấy tất cả system wordlist
    public List<Wordlist> defaultWordList() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:4040/api/wordlists/default";
        String getWordlist = restTemplate.getForObject(url, String.class);

        Gson gson = new Gson();
        List<Wordlist> wordlist = gson.fromJson(getWordlist, List.class);
        return wordlist;
    }

    // lấy tất cả word của wordlist
    public List<Wordlist> getWordList(String wordlistId, String wordId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + Port + "/api/wordlists/{wordlist-id}/word/{word-id}";
        Map<String, String> params = Map.of("wordlist-id", wordlistId, "word-id", wordId);
        List<Wordlist> wordList = restTemplate.getForObject(url, List.class, params);

        JSONObject jsonObject = new JSONObject(wordList);
        Gson gson = new Gson();
        wordList = gson.fromJson(jsonObject.toString(), List.class);
        return wordList == null ? null : wordList;
    }

    // lấy tất cả wordlist của user
    public List<Wordlist> getAllUserWordList(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + Port + "/api/wordlists/{userId}";

        Map<String, String> params = Map.of("userId", userId);
        String wordList = restTemplate.getForObject(url, String.class, params);

        Gson gson = new Gson();
        List<Wordlist> wordlist = gson.fromJson(wordList, List.class);
        return wordlist == null ? null : wordlist;
    }

    // create new wordlist
    public ResponseEntity<String> createWordList(CreateWordlistForm wordlistForm) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + Port + "/api/wordlists";
        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateWordlistForm> request = new HttpEntity<>(wordlistForm, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response;
    }


    // Delete word from wordlist
    public void deleteWordlist(String id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + Port + "/api/wordlists/{id}";
        Map<String, String> params = Map.of("id", id);
        restTemplate.delete(url, params);
    }

    public ResponseEntity<String> renameWordlist(String id, String name) {
        WebClient webClient = WebClient.create("http://localhost:" + Port + "/api/wordlists/name/" + id + "/" + name);
        ResponseEntity<String> response = webClient.patch().retrieve().toEntity(String.class).block();
        return response;
    }


    // lấy ra wordlist của user theo id wordlist
    public WordlistForm getWordlistById(String userId, String wordlistId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + Port + "/api/wordlists/{userId}/{wordlistId}";
        Map<String, String> params = Map.of("userId", userId, "wordlistId", wordlistId);
        String wordlist = restTemplate.getForObject(url, String.class, params);
        Gson gson = new Gson();
        WordlistForm wordlistForm = gson.fromJson(wordlist, WordlistForm.class);
        return wordlistForm == null ? null : wordlistForm;
    }

    // Remove word from wordlist
    public void removeWordFromWordlist(String wordlistId, String wordId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + Port + "/api/wordlists/{wordlistId}/word/{id}";
        Map<String, String> params = Map.of("wordlistId", wordlistId, "id", wordId);
        restTemplate.delete(url, params);
    }

}
