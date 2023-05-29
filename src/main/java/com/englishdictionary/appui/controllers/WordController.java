package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.Word;
import com.englishdictionary.appui.dto.WordlistForm;
import com.englishdictionary.appui.models.Wordlist;
import com.englishdictionary.appui.service.WordService;
import com.englishdictionary.appui.service.WordlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class WordController {
    @Autowired
    WordService wordService;
    @Autowired
    WordlistService wordlistService;

    @GetMapping("/word")
    public String searchWord(
            @RequestParam String word,
            Model model
    ) {
        if (word == null) {
            return "SearchResult/noWord";
        }
        else {
            try{
                Word getWord = (Word) wordService.searchWord(word);

                model.addAttribute("word", getWord);
                model.addAttribute("wordName", getWord.getWord().toString());

                return "SearchResult/word";
            }catch (Exception e){
                return "SearchResult/noWord";
            }
        }
    }



    // test
    @GetMapping(value = "/test1")
    @ResponseBody
    public String test1() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/hello";
        Map<String, String> params = Map.of("word","hello");
        String getWord = restTemplate.getForObject(url, String.class);
        return getWord.toString();
    }
    @GetMapping(value = "/test2")
    @ResponseBody
    public String test2() {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/{word}";
//        Map<String, String> params = Map.of("word","hello");
//        String getWord = restTemplate.getForObject(url,String.class, params);
//
//        JSONObject jsonObject = new JSONObject(getWord.substring(1,getWord.length()-1));
//
//        Gson gson = new Gson();
//        Word word = gson.fromJson(jsonObject.toString(), Word.class);
//
//        return word.getWord() + word.getPhonetics();



        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/{word}";
        Map<String, String> params = Map.of("word","hello");
        String getWord = restTemplate.getForObject(url,String.class, params);

        getWord = String.format(getWord, Word.class);
        return getWord;

    }


}
