package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.CreateWordlistForm;
import com.englishdictionary.appui.models.Wordlist;
import com.englishdictionary.appui.service.WordlistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WordlistController {
    @Autowired private WordlistService service;

    //lấy ra danh sách wordlist mặc định
    // đã có ui
    @GetMapping("/wordlist/default")
    public String defaultWordList(
            Model model
    ) {
        try {
            List<Wordlist> wordList = service.defaultWordList();
            model.addAttribute("wordList", wordList);
            return "WordList/systemWordlist";
        }
        catch (Exception e) {
            return "WordList/noWordlist";
        }
    }

    //lấy ra danh sách wordlist của user
    // đã có ui
    @GetMapping("/user/wordlist")
    public String getWordlist(
            HttpServletRequest request,
            Model model
    ) {
        if (request.getSession().getAttribute("userId") == null) {
            return "redirect:/login";
        }
        else {
            String userId = request.getSession().getAttribute("userId").toString();
            List<Wordlist> wordlistForm = service.getAllUserWordList(userId);
            model.addAttribute("wordList", wordlistForm);
            model.addAttribute("createWordlistForm", new CreateWordlistForm("",userId));
            return "WordList/userWordlist";
        }
    }
    // UI add wordlist
    // đã có ui
    @GetMapping("/wordlist/new")
    public String newWordlist(
            HttpServletRequest request,
            Model model
    ) {
        if (request.getSession().getAttribute("userId") == null) {
            return "redirect:/login";
        }
        else {
            try{
                String userId = request.getSession().getAttribute("userId").toString();
                model.addAttribute("wordlist", new CreateWordlistForm("",userId));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "WordList/add";
        }
    }

    // thêm wordlist mới
    // đã có ui
    @PostMapping("/wordlist/new")
    public String newWordlist(
            HttpServletRequest request,
            @ModelAttribute("createWordlistForm") CreateWordlistForm createWordlistForm
    ) {
        if (request.getSession().getAttribute("userId") == null) {
            return "redirect:/login";
        }
        else {
            try {
                service.createWordList(createWordlistForm);
                return "redirect:/user/wordlist";
            }
            catch (Exception e) {
                return "redirect:/user/wordlist";
            }

        }
    }

    // Delete wordlist
    @GetMapping("/wordlist/delete/{wordlistId}")
    public String deleteWordlist(
            HttpServletRequest request,
            @PathVariable String wordlistId
    ) {
        if (request.getSession().getAttribute("userId") == null) {
            return "redirect:/login";
        }
        else {
            try {
                service.deleteWordlist(wordlistId);
                return "redirect:/user/wordlist";
            }
            catch (Exception e) {
                return "redirect:/user/wordlist";
            }
        }
    }
    // fliping card
    @GetMapping("/wordlist/flip")
    public String card()
    {
        return "card/flipCard";
    }

    // chưa dùng
//    @GetMapping("/wordlist/{userId}/{wordlistId}")
//    public String getUserWordlist(
//            @PathVariable String userId,
//            @PathVariable String wordlistIdId,
//            Model model
//    ) {
//        try {
//            List<Wordlist> wordList = service.getUserWordList(userId, wordlistIdId);
//            model.addAttribute("wordList", wordList);
//            return "WordList/wordlist";
//        }
//        catch (Exception e) {
//            return "WordList/noWordlist";
//        }
//    }
    // chưa dùng
    @GetMapping("/wordlist")
    public String wordList(
            @RequestParam String wordlistId,
            @RequestParam String wordId,
            Model model
    ) {
        try {
            List<Wordlist> wordList = service.getWordList(wordlistId, wordId);
            model.addAttribute("wordList", wordList);
            return "WordList/systemWordlist";
        }
        catch (Exception e) {
            return "WordList/noWordlist";
        }
    }
}
