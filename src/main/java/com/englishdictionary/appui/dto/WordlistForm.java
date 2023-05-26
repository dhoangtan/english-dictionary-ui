package com.englishdictionary.appui.dto;

import com.englishdictionary.appui.models.Word;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordlistForm {
    private String wordlistId;
    private String name;
    private String userId;
    private List<Word> words;

    public Map<String, Object> toHashMap() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("user_id", userId);
        hashMap.put("words", words);
        return hashMap;
    }
}
