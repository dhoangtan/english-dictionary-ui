package com.englishdictionary.appui.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListQuestion {
    private List<Question> questions = new ArrayList<>();
    public List<Question> getList(){
        return questions;
    }
    public void setList(List<Question> questions){
        this.questions = questions;
    }
    public void addQuestion(Question question){
        this.questions.add(question);
    }
    public void removeQuestion(Question question){
        this.questions.remove(question);
    }
    public Question getQuestion(int index){
        return this.questions.get(index);
    }
    public Question getQuestionById(String id)
    {
        return this.questions.stream().filter(question -> question.getId().equals(id)).findFirst().orElse(null);
    }
}
