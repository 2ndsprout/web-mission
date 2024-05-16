package com.example.ms1.note.note.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername (String username);
    Optional<Member> findByEmail (String email);
}
