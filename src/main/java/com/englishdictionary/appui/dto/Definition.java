package com.englishdictionary.appui.dto;

import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Definition {
    private String definition;
    private String example;
    private ArrayList<String> synonyms;
    private ArrayList<String> antonyms;

}
