package com.bu.insu.service;


import com.bu.insu.entity.Member;
import com.bu.insu.exception.DuplicationEmailException;
import com.bu.insu.exception.MemberNotFoundException;
import com.bu.insu.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 상태 변화를 감지해 db 변경
public class MemberService {


    private final MemberRepository memberRepository;


    // 전체 조회
    public List<Member> findAll() {


        return memberRepository.findAll();
    }


    // 개별 조회
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(()-> new MemberNotFoundException(id));
    }


    // 등록
    @Transactional
    public Member save(Member member) {
        memberRepository.findByEmail(member.getEmail())
                // 값이 있으면(이미 존재하는 이메일) -> 예외 던짐
                // 값이 없으면(존재하지 않는 이메일) -> 블록 스킵
                .ifPresent(existingMember -> {// Optional 클래스 메서드로 true/false 반환
                    throw new DuplicationEmailException(member.getEmail());
                });
        return memberRepository.save(member);
    }


    // 회원 수정
    @Transactional
    public Member update(Long id, String name, String email) {
        // 회원 조회, 없으면 예외
        Member member = memberRepository.findById(id)
                .orElseThrow(()-> new MemberNotFoundException(id));


        // 이메일 중복 검사
        memberRepository.findByEmail(email)
                .ifPresent(ex -> {
                    if(!ex.getId().equals(id)) {
                        throw new DuplicationEmailException(email);
                    }
                });


        // 변경
        member.updateInfo(name, email);


        return member;
    }


    // 삭제
    @Transactional
    public void deleteById(Long id) {
       /* == select * from MEMBERS ~
       Member member = memberRepository.findById(id)
               .orElseThrow(()-> new MemberNotFoundException(id));
       memberRepository.delete(member);
       */


        // select 1 from MEMBERS ~
        if(!memberRepository.existsById(id)) {
            throw new MemberNotFoundException(id);
        }
        memberRepository.deleteById(id);
    }
}
