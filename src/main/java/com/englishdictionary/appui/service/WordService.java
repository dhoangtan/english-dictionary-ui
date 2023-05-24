package com.englishdictionary.appui.service;

import com.englishdictionary.appui.dto.LoginForm;
import com.englishdictionary.appui.dto.Word;
import com.englishdictionary.appui.models.User;
import com.englishdictionary.appui.models.Wordlist;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class WordService {
    private final String Port = "4040";
    private final String urlAPI = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    public Word searchWord(String param) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/{word}";
        Map<String, String> params = Map.of("word",param);
        String getWord = restTemplate.getForObject(url,String.class, params);
        JSONObject jsonObject = new JSONObject(getWord.substring(1,getWord.length()-1));

        Gson gson = new Gson();
        Word word = gson.fromJson(jsonObject.toString(), Word.class);

        return word==null ? null:word;

    }


}
