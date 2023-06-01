package com.englishdictionary.appui.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Word {
    private String word;
    private String phonetic;
    private String origin;
    private ArrayList<Phonetic> phonetics;
    private ArrayList<Meaning> meanings;
    private License license;
    private String sourceUrl;


}
