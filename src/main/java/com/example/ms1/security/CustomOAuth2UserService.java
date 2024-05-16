package com.example.ms1.security;

import com.example.ms1.note.note.member.Member;
import com.example.ms1.note.note.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String username = (String) attributes.get("sub");
        String nickname = registrationId + "_" + username;

        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        Member member;
        if (memberOptional.isPresent()) {
            member = memberOptional.get();
        } else {
            member = new Member();
            member.setEmail(email);
            member.setUsername(username);
            member.setPassword("");  // OAuth2 사용자는 패스워드가 필요하지 않음
            member.setCreateDate(LocalDateTime.now());
            member.setNickname(nickname);
            memberRepository.save(member);
        }

        return new DefaultOAuth2User(List.of(new SimpleGrantedAuthority("ROLE_MEMBER")),
                attributes, "sub");
    }
}
