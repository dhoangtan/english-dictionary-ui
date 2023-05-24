package com.englishdictionary.appui.service;

import com.englishdictionary.appui.dto.AddWordToWordlistForm;
import com.englishdictionary.appui.dto.WordlistForm;
import com.englishdictionary.appui.models.Wordlist;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class WordlistService {
    private final String Port = "4040";
    // lấy tất cả system wordlist
    public List<Wordlist> defaultWordList() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:"+Port+"/api/wordlist/default";
        List<Wordlist> wordList = restTemplate.getForObject(url, List.class);

        JSONObject jsonObject = new JSONObject(wordList);
        Gson gson = new Gson();
        wordList = gson.fromJson(jsonObject.toString(), List.class);
        return wordList==null ? null:wordList;
    }

    // lấy tất cả word của wordlist
    public List<Wordlist> getWordList(String wordlistId, String wordId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:"+Port+"/api/wordlists/{wordlist-id}/word/{word-id}";
        Map<String, String> params = Map.of("wordlist-id",wordlistId,"word-id",wordId);
        List<Wordlist> wordList = restTemplate.getForObject(url, List.class, params);

        JSONObject jsonObject = new JSONObject(wordList);
        Gson gson = new Gson();
        wordList = gson.fromJson(jsonObject.toString(), List.class);
        return wordList==null ? null:wordList;
    }

    // lấy tất cả wordlist của user
    public List<Wordlist> getUserWordList(String userId, String wordlistIdId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:"+Port+"/api/wordlist/{userId}/{wordlistId}";

        Map<String, String> params = Map.of("userId",userId,"wordlistId",wordlistIdId);

        List<Wordlist> wordList = restTemplate.getForObject(url, List.class, params);

        JSONObject jsonObject = new JSONObject(wordList);
        Gson gson = new Gson();
        wordList = gson.fromJson(jsonObject.toString(), List.class);

        return wordList==null ? null:wordList;
    }

    public void createWordList(WordlistForm wordlistForm) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:"+Port+"/api/wordlists?name={name}&userId={user_id}";
        Map<String, String> params = Map.of("name",wordlistForm.getName(),"user_id",wordlistForm.getUserId());
        restTemplate.postForObject(url, wordlistForm, WordlistForm.class, params);
    }

    //test create wordlist form json object
    public void createWordListJson(WordlistForm wordlistForm) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:"+Port+"/api/wordlists";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", wordlistForm.getName());
        jsonObject.put("userId", wordlistForm.getUserId());

        restTemplate.postForObject(url, jsonObject, JSONObject.class);
    }

    // Add word to wordlist
    public void addWordToWordlist(AddWordToWordlistForm addWordToWordlistForm) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:"+Port+"/api/wordlists/word";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("wordlistId", addWordToWordlistForm.getWordlistId());
        jsonObject.put("word", addWordToWordlistForm.getWord());

        restTemplate.postForObject(url, jsonObject, JSONObject.class);
    }
    // Delete word from wordlist
    public void deleteWordlist(String id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:"+Port+"/api/wordlists/{id}";
        Map<String, String> params = Map.of("id",id);
        restTemplate.delete(url, params);
    }
    // Remove word from wordlist
    public void removeWordFromWordlist(String wordlistId, String wordId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:"+Port+"/api/wordlists/{wordlistId}/word/{id}";
        Map<String, String> params = Map.of("wordlistId",wordlistId,"id",wordId);
        restTemplate.delete(url, params);
    }
    // Rename wordlist
    public void renameWordlist(String id, String name) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:"+Port+"/api/wordlists/";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        restTemplate.put(url, jsonObject, JSONObject.class);
    }
}
