package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.CreateWordlistForm;
import com.englishdictionary.appui.dto.WordlistForm;
import com.englishdictionary.appui.models.Wordlist;
import com.englishdictionary.appui.service.WordlistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class WordlistController {
    @Autowired
    private WordlistService service;
    private final Logger logger = Logger.getLogger("com.englishdictionary.appui.controllers.WordlistController");

    // lấy ra danh sách wordlist mặc định
    // đã có ui
    @GetMapping("/wordlist/default")
    public String defaultWordList(
            Model model) {
        try {
            List<Wordlist> wordList = service.defaultWordList();
            model.addAttribute("wordList", wordList);
            return "WordList/systemWordlist";
        } catch (Exception e) {
            return "WordList/noWordlist";
        }
    }

    // lấy ra danh sách wordlist của user
    // đã có ui
    @GetMapping("/user/wordlist")
    public String getWordlist(
            HttpServletRequest request,
            Model model) {
        if (request.getSession().getAttribute("userId") == null) {
            return "redirect:/login";
        } else {
            String userId = request.getSession().getAttribute("userId").toString();
            List<Wordlist> wordlistForm = service.getAllUserWordList(userId);
            model.addAttribute("wordList", wordlistForm);
            model.addAttribute("createWordlistForm", new CreateWordlistForm("", userId));
            model.addAttribute("wordlistForm", new WordlistForm());
            model.addAttribute("message", "hello");
            return "WordList/userWordlist";
        }
    }

    // UI add wordlist
    // đã có ui
    @GetMapping("/wordlist/new")
    public String newWordlist(
            HttpServletRequest request,
            Model model) {
        if (request.getSession().getAttribute("userId") == null) {
            return "redirect:/login";
        } else {
            try {
                String userId = request.getSession().getAttribute("userId").toString();
                model.addAttribute("wordlist", new CreateWordlistForm("", userId));
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
            @ModelAttribute("createWordlistForm") CreateWordlistForm createWordlistForm,
            Model model
    ) {
        if (request.getSession().getAttribute("userId") == null) {
            return "redirect:/login";
        }
        else {
            try {
                if(service.createWordList(createWordlistForm).getStatusCode().is2xxSuccessful())
                {
                    model.addAttribute("message", "Create wordlist successfully");
                    return "redirect:/user/wordlist";
                }
                else
                {
                    model.addAttribute("message", null);
                    return "redirect:/user/wordlist";
                }
            }
            catch (HttpClientErrorException e)
            {
                model.addAttribute("message", null);
                if (e.getRawStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
                    logger.warning("Create wordlist is Unauthorized --> email: " + request.getSession().getAttribute("email"));
                    return "redirect:/user/wordlist";
                }
                return "redirect:/user/wordlist";
            }
            catch (HttpServerErrorException e)
            {
                model.addAttribute("message", null);
                if (e.getRawStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                    logger.warning("Create wordlist is Internal Server Error --> email: " + request.getSession().getAttribute("email"));
                    return "redirect:/user/wordlist";
                }
                return "redirect:/user/wordlist";
            }
        }
    }

    // Delete wordlist
    @GetMapping("/wordlist/delete/{wordlistId}")
    public String deleteWordlist(
            HttpServletRequest request,
            @PathVariable String wordlistId) {
        if (request.getSession().getAttribute("userId") == null) {
            return "redirect:/login";
        } else {
            try {
                service.deleteWordlist(wordlistId);
                return "redirect:/user/wordlist";
            }
            catch (HttpClientErrorException e)
            {
                if (e.getRawStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
                    logger.warning("Delete wordlist is Unauthorized --> email: " + request.getSession().getAttribute("email"));
                    return "redirect:/user/wordlist";
                }
                return "redirect:/user/wordlist";
            }
            catch (HttpServerErrorException e)
            {
                if (e.getRawStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                    logger.warning("Delete wordlist is Internal Server Error --> email: " + request.getSession().getAttribute("email"));
                    return "redirect:/user/wordlist";
                }
                return "redirect:/user/wordlist";
            }
        }
    }

    // fliping card
    @GetMapping("/wordlist/flip/{wordlistId}")
    public String card(
            HttpServletRequest request,
            Model model,
            @PathVariable String wordlistId)
    {
        try{
            String userId = request.getSession().getAttribute("userId").toString();
            if(userId == null) {
                return "redirect:/login";
            }
            else {
                WordlistForm wordlist = service.getWordlistById(userId, wordlistId);
                model.addAttribute("wordlist", wordlist);

                return "card/flipCard";
            }
        }catch (Exception e) {
//            return "redirect:/user/wordlist";
        }
        return "card/flipCard";
    }

    // chưa dùng
    // @GetMapping("/wordlist/{userId}/{wordlistId}")
    // public String getUserWordlist(
    // @PathVariable String userId,
    // @PathVariable String wordlistIdId,
    // Model model
    // ) {
    // try {
    // List<Wordlist> wordList = service.getUserWordList(userId, wordlistIdId);
    // model.addAttribute("wordList", wordList);
    // return "WordList/wordlist";
    // }
    // catch (Exception e) {
    // return "WordList/noWordlist";
    // }
    // }
    // chưa dùng
    @GetMapping("/wordlist")
    public String wordList(
            @RequestParam String wordlistId,
            @RequestParam String wordId,
            Model model) {
        try {
            List<Wordlist> wordList = service.getWordList(wordlistId, wordId);
            model.addAttribute("wordList", wordList);
            return "WordList/systemWordlist";
        } catch (Exception e) {
            return "WordList/noWordlist";
        }
    }
}
