package com.englishdictionary.appui.models;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String id;
    private String name;
    private List<Answer> answers;
}
