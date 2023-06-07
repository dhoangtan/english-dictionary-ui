package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.models.Question;
import com.englishdictionary.appui.service.ExamsService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@Controller
public class ExamsController {
    @Autowired
    ExamsService examsService;

    @GetMapping("/exams")
    public String getExams(
            HttpServletRequest request,
            Model model
    ) {
        try {
            List<Question> question = examsService.getAllQuestions().getBody();
            model.addAttribute("questions", question);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        } catch (HttpServerErrorException e) {
            e.printStackTrace();
        }
        return "exams/exams";
    }

    @GetMapping("/exams/questions")
    @ResponseBody
    public String Exams(
            HttpServletRequest request,
            Model model
    ) {
        List<Question> question = examsService.getAllQuestions().getBody();
        return new Gson().toJson(question);
    }

    @PostMapping("/exams")
    public String postExams(
            HttpServletRequest request,
            Model model,
            @ModelAttribute("questions") List<Question> questions
    ) {
        model.addAttribute("questions", questions);
        return "redirect:/exams";
    }

//    private void setFalseAnswer(List<Question> questions) {
//        for (Question question : questions) {
//            for (Answer answer : question.getAnswers()) {
//                answer.setCorrect(false);
//            }
//        }
//    }

}
