package com.mysite.library.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

    @NotBlank(message = "내용은 필수항목입니다.")
    @Size(min = 5, message = "내용은 5자 이상입니다.")
    private String content;
}
