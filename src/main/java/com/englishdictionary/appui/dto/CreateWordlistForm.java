package com.englishdictionary.appui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateWordlistForm {
    private String name;
    private String userId;
}
