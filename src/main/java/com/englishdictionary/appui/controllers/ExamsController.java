package com.englishdictionary.appui.controllers;

import com.englishdictionary.appui.models.Answer;
import com.englishdictionary.appui.models.ListQuestion;
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
            ListQuestion question = examsService.getAllQuestions().getBody();
            setFalseAnswer(question);
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
        ListQuestion question = examsService.getAllQuestions().getBody();
        return new Gson().toJson(question);
    }

    @PostMapping("/exams")
    public String postExams(
            HttpServletRequest request,
            Model model
            , @ModelAttribute("questions") ListQuestion questions
    ) {
        int grade = 0;
        ListQuestion serverQuestions = examsService.getAllQuestions().getBody();

        for (int i = 0; i < questions.getList().size(); i++) {
            Question userQuestion = questions.getList().get(i);
            List<Answer> userAnswers = userQuestion.getAnswers();

            Question serverQuestion = serverQuestions.getList().get(i);
            List<Answer> serverAnswers = serverQuestion.getAnswers();

            for (int j = 0; j < userAnswers.size(); j++) {
                Answer userAnswer = userAnswers.get(j);
                Answer serverAnswer = serverAnswers.get(j);

                boolean isUserAnswerCorrect = request.getParameter("questions[" + i + "].answers[" + j + "].isCorrect") != null;
                userAnswer.setIsCorrect(isUserAnswerCorrect);

                if (isUserAnswerCorrect && serverAnswer.getIsCorrect()) {
                    grade++;
                }
            }
        }

        System.out.println("Grade: " + grade);
        System.out.println("Questions: " + questions.getList().get(0).getAnswers().get(0).getIsCorrect());
        model.addAttribute("questions", serverQuestions);
        model.addAttribute("grade", grade);
        model.addAttribute("total", serverQuestions.getList().size());
        return "exams/resultExams";
    }

    private void setFalseAnswer(ListQuestion questions) {
        for (Question question : questions.getQuestions()) {
            for (Answer answer : question.getAnswers()) {
                answer.setIsCorrect(false);
            }
        }
    }

}
