package com.bu.insu.service;

import com.bu.insu.entity.Member;
import com.bu.insu.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class MemberService {


    private final MemberRepository memberRepository;


    // 전체 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    //개별 조회
    public Member findById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(id));
    }


    // 등록
    @Transactional
    public Member save(Member member) {
        memberRepository.findByEmail(member.getEmail())
                // 값이 있으면(이미 존재하는 이메일) -> 예외 던짐
                // 값이 없으면(존재하지 않는 이메일) -> 블록 스킵
                .ifPresent(existingMember -> {// Optional 클래스 메서드로 true/false 반환
                    throw new IllegalStateException("Email already exists!");
                });
        return memberRepository.save(member);
    }
}

