package com.englishdictionary.appui.models;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wordlist {
    private String wordlistId;
    private String name;
    private String userId;
    private ArrayList<Word> words;

    public Map<String, Object> toHashMap() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);
        hashMap.put("user_id", userId);
        hashMap.put("words", words);
        return hashMap;
    }

}