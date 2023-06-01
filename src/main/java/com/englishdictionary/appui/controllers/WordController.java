package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.AddWordToWordlistForm;
import com.englishdictionary.appui.dto.Word;
import com.englishdictionary.appui.service.WordService;
import com.englishdictionary.appui.service.WordlistService;
import jakarta.servlet.http.HttpServletRequest;
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
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/{word}";
        Map<String, String> params = Map.of("word","hello");
        String getWord = restTemplate.getForObject(url,String.class, params);

        getWord = String.format(getWord, Word.class);
        return getWord;

    }

    // Add word to wordlist
    @GetMapping("/addToWordList")
    public String addToWordList(
            @RequestParam String word,
            @RequestParam String definition,
            Model model,
            HttpServletRequest request
    )
    {

        if (request.getSession().getAttribute("userId") == null) {
            return "redirect:/login";
        } else {
            String userId = request.getSession().getAttribute("userId").toString();
//            model.addAttribute("word", word);
//            model.addAttribute("definition", definition);
            com.englishdictionary.appui.models.Word wordModel = new com.englishdictionary.appui.models.Word(null,word,definition);
            AddWordToWordlistForm wordForm = new AddWordToWordlistForm();
            wordForm.setWord(wordModel);
            model.addAttribute("word",wordForm);
            model.addAttribute("wordlist", wordlistService.getAllUserWordList(userId));
            return "word/addToWordlist";
        }

    }
    @GetMapping("/addWordToWordlist")
    public String addWordToWordList(
            @RequestParam String wordlistId,
            @RequestParam String word,
            @RequestParam String definition,
            Model model,
            HttpServletRequest request
    )
    {
        if (request.getSession().getAttribute("userId") == null) {
            return "redirect:/login";
        } else {
            try {
                String userId = request.getSession().getAttribute("userId").toString();
                AddWordToWordlistForm wordForm = new AddWordToWordlistForm();
                wordForm.setWordlistId(wordlistId);
                com.englishdictionary.appui.models.Word wordModel = new com.englishdictionary.appui.models.Word(null,word,definition);
                wordForm.setWord(wordModel);
                wordService.addWordToWordlist(wordForm);
                return "redirect:/user/wordlist";
            }
            catch (Exception e) {
                return "redirect:/addToWordList?word=" + word + "&definition=" + definition;
            }

        }
    }

}
