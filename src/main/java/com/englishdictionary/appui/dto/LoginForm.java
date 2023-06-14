package com.englishdictionary.appui.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {
    @NotNull(message = "Email cannot be null!")
    @NotEmpty(message = "Email cannot be empty!")
    private String email;
    @NotNull(message = "Password cannot be null!")
    @NotEmpty(message = "Password cannot be empty!")
    private String password;

}
