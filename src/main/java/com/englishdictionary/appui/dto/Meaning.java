package com.englishdictionary.appui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meaning {
    private String partOfSpeech;
    private ArrayList<Definition> definitions;
    private ArrayList<String> synonyms;
    private ArrayList<String> antonyms;
}
