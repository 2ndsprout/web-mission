package com.example.ms1.note.note.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup (MemberForm memberForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup (@Valid MemberForm memberForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        this.memberService.create(memberForm.getUsername(),memberForm.getPassword(),memberForm.getNickname(),memberForm.getEmail());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login () {
        return "login_form";
    }
}
