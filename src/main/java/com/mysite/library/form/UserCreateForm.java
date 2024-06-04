package com.mysite.library.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "유저네임은 필수항목입니다.")
    private String name;    //유저네임
    @NotEmpty(message = "패스워드는 필수항목입니다.")
    private String password1;   //패스워드
    @NotEmpty(message = "패스워드 확인은 필수항목입니다.")
    private String password2;   //패스워드 확인
    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email(message = "이메일 양식에 맞게 적어주세요")
    private String email;       //이메일

    @NotEmpty(message = "필수항목입니다")
    private String phone;

    @NotEmpty(message = "필수항목입니다")
    private String address;

}
