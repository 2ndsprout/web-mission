package com.example.ms1.note.note.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "사용자 ID를 입력해주세요")
    private String username;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;

    @NotEmpty(message = "닉네임을 입력해주세요")
    private String nickname;

    @NotEmpty(message = "이메일을 입력해주세요")
    @Email
    private String email;


}
