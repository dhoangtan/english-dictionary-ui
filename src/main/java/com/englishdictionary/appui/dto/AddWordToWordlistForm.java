package com.englishdictionary.appui.dto;

import com.englishdictionary.appui.models.Word;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddWordToWordlistForm {
    private String wordlistId;
    private Word word;
}
