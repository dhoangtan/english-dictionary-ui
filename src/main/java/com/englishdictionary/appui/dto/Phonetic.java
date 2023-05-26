package com.englishdictionary.appui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Phonetic {
    private String text;
    private String audio;
    private String sourceUrl;
    private License license;
}
