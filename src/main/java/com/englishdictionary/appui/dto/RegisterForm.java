package com.englishdictionary.appui.dto;

import lombok.Data;

@Data
public class RegisterForm {
    private String email;
    private String password;
    private String fullName;
    private Integer gender;
    private Integer level;
    private Integer occupation;
}
