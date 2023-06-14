package com.englishdictionary.appui.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Verify {
    @NotNull
    @Size(max = 6, min = 6, message = "Code should be of 6 digits")
    @Pattern(regexp = "[7-9][0-9]{5}", message = "Code is invalid!!")
    private String code;

}
