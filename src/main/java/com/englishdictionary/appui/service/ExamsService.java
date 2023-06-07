package com.englishdictionary.appui.service;

import com.englishdictionary.appui.models.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ExamsService {
    private final String Port = "4040";
    public ResponseEntity<List<Question>> getAllQuestions() {
        WebClient webClient = WebClient.create("http://localhost:" + Port + "/api/tests/questions");
        return webClient.get().retrieve().toEntityList(Question.class).block();
    }
}
