package com.englishdictionary.appui.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterForm {

    @NotNull(message = "Email cannot be null!")
    @NotEmpty(message = "Email cannot be empty!")
    private String email;
    @NotNull(message = "Password cannot be null!")
    @NotEmpty(message = "Password cannot be empty!")
    private String password;

    @NotNull(message = "Name cannot be null!")
    @NotEmpty(message = "Name cannot be empty!")
    private String fullName;
    @NotNull(message = "Gender must not be null!")
    private Integer gender;
    @NotNull(message = "Level must not be null!")
    private Integer level;
    @NotNull(message = "Occupation must not be null!")
    private Integer occupation;
}
