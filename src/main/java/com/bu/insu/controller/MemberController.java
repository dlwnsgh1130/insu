package com.bu.insu.controller;

import com.bu.insu.dto.ApiResponse;
import com.bu.insu.dto.MemberRequest;
import com.bu.insu.dto.MemberResponse;
import com.bu.insu.entity.Member;
import com.bu.insu.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


// @Controller + @ResponseBody
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor // final 키워드가 붙은 필드를 생성자로 자동 생성(의존성 주입)
public class MemberController {


    private final MemberService memberService;


    // 회원 전체 조회 GET /api/memebers
    @GetMapping
    public ApiResponse<List<MemberResponse>> findAll() {
        List<MemberResponse> members = memberService.findAll().stream()
                // .map(member -> MemberResponse.from(member))
                .map(MemberResponse::from)
                .toList();
        return ApiResponse.ok(members);
    }


    // 회원 개별 조회
    @GetMapping("/{id}")
    public ApiResponse<MemberResponse> findById(@PathVariable Long id) {
        Member member = memberService.findById(id);
        return ApiResponse.ok(MemberResponse.from(member));
    }




    // 회원 등록 POST
   /* 데이터의 보안을 위해 entity로 바로 접근하지 않음
   public ResponseEntity<Member> create(@PathVariable Member member) {
       Member member = memberService.save();
       return ResponseEntity.ok().body(member);
   }
   */
    @PostMapping // HTTP요청의 본문(body)을 자바 객체로 변환
    public ResponseEntity<ApiResponse<MemberResponse>> create(@Valid @RequestBody MemberRequest memberRequest) {
        // 클라이언트가 보낸 JSON 데이터를 **DTO**로 받음(Entity로 받으면 안됨)
        // @RequestBody를 통해 데이터를 받음
        // 화면용 데이터(DTO) -> DB용 데이터 변환 -> 오라클DB 저장 -> DB에서 ID, regDate 추가
        Member saved = memberService.save(memberRequest.toEntity());
        // DB에서 꺼낸 Entity를 화면에 보여줄 땐 필요한 데이터만 출력
        MemberResponse memberResponse = MemberResponse.from(saved);


        return ResponseEntity.created(URI.create("/api/members/" + saved.getId()))
                .body(ApiResponse.ok(memberResponse));
    }




    // 회원 수정 PUT
    @PutMapping("/{id}")
    public ApiResponse<MemberResponse> update(@PathVariable Long id,
                                              @Valid @RequestBody MemberRequest requestDto) {
        MemberResponse response = MemberResponse.from(
                memberService.update(id, requestDto.name(), requestDto.email())
        );
        return ApiResponse.ok(response);
    }


    // 회원 삭제 DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
/*
ResponseEntity
컨트롤러가 HTTP 상태 코드 + 헤더 + 응답 제어할 수 있게 해 줌


.ok()           200 일반적인 성공
.created()      201 리소스 생성 성공
.noContent()    204 내용없음 성공
클라이언트 쪽
.badRequest()   400 요청 데이터 오류로 인한 실패
서버쪽
.internalServerError()  500 서버 내부 오류


*/



