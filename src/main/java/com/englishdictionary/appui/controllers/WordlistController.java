package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.dto.CreateWordlistForm;
import com.englishdictionary.appui.dto.WordlistForm;
import com.englishdictionary.appui.models.Wordlist;
import com.englishdictionary.appui.service.WordlistService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@Controller
public class WordlistController {
    @Autowired
    private WordlistService service;

    private Logger logger = LoggerFactory.getLogger(WordlistController.class);
    private final String CONTROLLER_NAME = "[WordlistController]";

    // lấy ra danh sách wordlist mặc định
    // đã có ui
    @GetMapping("/wordlist/default")
    public String defaultWordList(
            Model model) {
        try {
            logger.info(CONTROLLER_NAME + "/[defaultWordList] - [GET] - Called");
            List<Wordlist> wordList = service.defaultWordList();
            model.addAttribute("wordList", wordList);
            logger.info("\tRedirect to [Wordlist/systemWordlist]");
            logger.info(CONTROLLER_NAME + "/[defaultWordList] - [GET] - Completed");
            return "WordList/systemWordlist";
        } catch (Exception e) {
            logger.error("\tError occurred with message: " + e.getMessage());
            logger.info("\tRedirect to [Wordlist/noWordList]");
            logger.info(CONTROLLER_NAME + "/[defaultWordList] - [GET] - Completed");
            return "WordList/noWordlist";
        }
    }

    // lấy ra danh sách wordlist của user
    // đã có ui
    @GetMapping("/user/wordlist")
    public String getWordlist(
            HttpServletRequest request,
            Model model) {
        try {
            logger.info(CONTROLLER_NAME + "/[getWordList] - [GET] - Called");
            if (request.getSession().getAttribute("userId") == null) {
                logger.info("\tGet wordlist - [Failed] - User is not logged in");
                logger.info("\tRedirect to [/login]");
                logger.info(CONTROLLER_NAME + "/[getWordList] - [GET] - Completed");
                return "redirect:/login";
            } else {
                logger.info("\tGet wordlist - [Success]");
                String userId = request.getSession().getAttribute("userId").toString();
                List<Wordlist> wordlistForm = service.getAllUserWordList(userId);
                model.addAttribute("wordList", wordlistForm);
                model.addAttribute("createWordlistForm", new CreateWordlistForm("", userId));
                model.addAttribute("wordlistForm", new WordlistForm());
                model.addAttribute("message", "hello");
                logger.info("\tRedirect to [WordList/userWordlist]");
                logger.info(CONTROLLER_NAME + "/[getWordList] - [GET] - Completed");
                return "WordList/userWordlist";
            }
        } catch (HttpClientErrorException e) {
            logger.error("\tError on Client side occurred - Message: " + e.getMessage());
            String userId = request.getSession().getAttribute("userId").toString();
            List<Wordlist> wordlistForm = null;
            model.addAttribute("wordList", wordlistForm);
            model.addAttribute("createWordlistForm", new CreateWordlistForm("", userId));
            model.addAttribute("wordlistForm", new WordlistForm());
            model.addAttribute("message", "hello");
            logger.info("\tRedirect to [WordList/userWordlist]");
            logger.info(CONTROLLER_NAME + "/[getWordList] - [GET] - Completed");
            return "WordList/userWordlist";
        } catch (Exception e) {
            logger.error("\tError on Server side occurred - Message: " + e.getMessage());
            logger.info("\tRedirect to [WordList/userWordlist]");
            logger.info(CONTROLLER_NAME + "/[getWordList] - [GET] - Completed");
            return "WordList/noWordlist";
        }

    }

    // UI add wordlist
    // đã có ui
    @GetMapping("/wordlist/new")
    public String newWordlist(
            HttpServletRequest request,
            Model model) {
        logger.info(CONTROLLER_NAME + "/[newWordlist] - [GET] - Called");
        if (request.getSession().getAttribute("userId") == null) {
            logger.info("\tCould not create new word list - [Failed] - User is not logged in");
            logger.info("\tRedirect to [/login]");
            logger.info(CONTROLLER_NAME + "/[newWordlist] - [GET] - Completed");
            return "redirect:/login";
        } else {
            try {
                logger.info("\tCould not create new word list - [Success]");
                String userId = request.getSession().getAttribute("userId").toString();
                model.addAttribute("wordlist", new CreateWordlistForm("", userId));
            } catch (Exception e) {
                logger.error("\tError occurred with message: " + e.getMessage());
                e.printStackTrace();
            }
            logger.info("\tRedirect to [Wordlist/add]");
            logger.info(CONTROLLER_NAME + "/[newWordlist] - [GET] - Completed");
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
        logger.info(CONTROLLER_NAME + "/[newWordlist] - [POST] - Called");
        if (request.getSession().getAttribute("userId") == null) {
            logger.info("\tCould not create new wordlist - User is not logged in");
            logger.info("\tRedirect to [/login]");
            logger.info(CONTROLLER_NAME + "/[newWordlist] - [POST] - Completed");
            return "redirect:/login";
        } else {
            try {
                String userId = request.getSession().getAttribute("userId").toString();
                if (service.createWordList(createWordlistForm).getStatusCode().is2xxSuccessful()) {
                    model.addAttribute("message", "Create wordlist successfully");
                    logger.info("\tRedirect to [/user/wordlist]");
                    logger.info(CONTROLLER_NAME + "/[newWordlist] - [POST] - Completed");
                    return "redirect:/user/wordlist";
                } else {
                    model.addAttribute("message", null);
                    logger.info("\tRedirect to [/user/wordlist]");
                    logger.info(CONTROLLER_NAME + "/[newWordlist] - [POST] - Completed");
                    return "redirect:/user/wordlist";
                }
            } catch (HttpClientErrorException e) {
                logger.error("\tError occurred with message: " + e.getMessage());
                model.addAttribute("message", null);
                logger.info("\tRedirect to [/user/wordlist]");
                logger.info(CONTROLLER_NAME + "/[newWordlist] - [POST] - Completed");
                return "redirect:/user/wordlist";
            } catch (HttpServerErrorException e) {
                logger.error("\tError occurred with message: " + e.getMessage());
                model.addAttribute("message", null);
                logger.info("\tRedirect to [/user/wordlist]");
                logger.info(CONTROLLER_NAME + "/[newWordlist] - [POST] - Completed");
                return "redirect:/user/wordlist";
            }
        }
    }

    // Delete wordlist
    @GetMapping("/wordlist/delete/{wordlistId}")
    public String deleteWordlist(
            HttpServletRequest request,
            @PathVariable String wordlistId) {
        logger.info(CONTROLLER_NAME + "/[deleteWordlist] - [GET] - Called");
        if (request.getSession().getAttribute("userId") == null) {
            logger.info("\tCould not delete wordlist - User is not logged in");
            logger.info("\tRedirect to [/login]");
            logger.info(CONTROLLER_NAME + "/[deleteWordlist] - [POST] - Completed");
            return "redirect:/login";
        } else {
            try {
                service.deleteWordlist(wordlistId);
                logger.info("\tRedirect to [/user/wordlist]");
                logger.info(CONTROLLER_NAME + "/[createWordlist] - [GET] - Completed");
                return "redirect:/user/wordlist";
            } catch (HttpClientErrorException e) {
                logger.error("\tError occurred on Client side with message: " + e.getMessage());
                logger.info("\tRedirect to [/user/wordlist]");
                logger.info(CONTROLLER_NAME + "/[newWordlist] - [POST] - Completed");
                return "redirect:/user/wordlist";
            } catch (HttpServerErrorException e) {
                logger.error("\tError occurred on Server side with message: " + e.getMessage());
                logger.info("\tRedirect to [/user/wordlist]");
                logger.info(CONTROLLER_NAME + "/[newWordlist] - [POST] - Completed");
                return "redirect:/user/wordlist";
            }
        }
    }

    // fliping card
    @GetMapping("/wordlist/flip/{wordlistId}")
    public String card(
            HttpServletRequest request,
            Model model,
            @PathVariable String wordlistId) {
        try {
            logger.info(CONTROLLER_NAME + "/[card] - [GET] - Called");
            String userId = request.getSession().getAttribute("userId").toString();
            if (userId == null) {
                logger.info("\tCould not [hàm card dùng để làm gì tôi cũng đéo biết luôn mấy ông?] - User is not logged in");
                logger.info("\tRedirect to [/login]");
                logger.info(CONTROLLER_NAME + "/[card] - [GET] - Completed");
                return "redirect:/login";
            } else {
                logger.info("\tRedirect to [/card/flipCard]");
                WordlistForm wordlist = service.getWordlistById(userId, wordlistId);
                logger.info(CONTROLLER_NAME + "/[card] - [GET] - Completed");
                model.addAttribute("wordlist", wordlist);

                return "card/flipCard";
            }
        } catch (Exception e) {
//            return "redirect:/user/wordlist";
            logger.error("\tError occurred on Server side with message: " + e.getMessage());
            logger.info("\tRedirect to [/user/wordlist]");
            logger.info(CONTROLLER_NAME + "/[newWordlist] - [POST] - Completed");
        }
        logger.info("\tRedirect to [/card/flipCard]");
        logger.info(CONTROLLER_NAME + "/[card] - [GET] - Completed");
        return "card/flipCard";
    }

    // rename wordlist
    @PostMapping("/wordlist/rename")
    public String renameWordlist(
            HttpServletRequest request,
            @RequestParam("wordlistId") String wordlistId,
            @RequestParam("name") String name,
            Model model
    ) {
        logger.info(CONTROLLER_NAME + "/[renameWordlist] - [POST] - Called");
        if (request.getSession().getAttribute("userId") == null) {
            logger.info("\tCould not rename wordlist - Failed - User is not logged in");
            logger.info("\tRedirect to [/login]");
            logger.info(CONTROLLER_NAME + "/[renameWordlist] - [Post] - Completed");
            return "redirect:/login";
        } else {
            try {
                if (service.renameWordlist(wordlistId, name).getStatusCode() == HttpStatus.OK) {
                    logger.info("Rename wordlist is OK --> email: " + request.getSession().getAttribute("email"));
                    return "redirect:/wordlist/flip/" + wordlistId;
                } else {
                    return "redirect:/user/wordlist";
                }
            } catch (HttpClientErrorException e) {
                if (e.getRawStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
                    return "redirect:/user/wordlist";
                } else {
                    return "redirect:/user/wordlist";
                }
            } catch (HttpServerErrorException e) {

                if (e.getRawStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                    return "redirect:/user/wordlist";
                }
                return "redirect:/user/wordlist";
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/user/wordlist";
            }
        }
    }

    @GetMapping("/wordlist/delete/{wordlistId}/{wordId}")
    public String deleteWordFromWordlist(
            HttpServletRequest request,
            @PathVariable String wordlistId,
            @PathVariable String wordId
    ) {
        if (request.getSession().getAttribute("userId") == null) {
            return "redirect:/login";
        } else {
            try {
                service.removeWordFromWordlist(wordlistId, wordId);
                return "redirect:/wordlist/flip/" + wordlistId.toString();
            } catch (HttpClientErrorException e) {
                if (e.getRawStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
                    return "redirect:/wordlist/flip/" + wordlistId;
                }
                return "redirect:/wordlist/flip/" + wordlistId;
            } catch (HttpServerErrorException e) {

                if (e.getRawStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                    return "redirect:/wordlist/flip/" + wordlistId;
                }
                return "redirect:/wordlist/flip/" + wordlistId;
            }
        }

    }

}
