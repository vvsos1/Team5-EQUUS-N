package com.feedhanjum.back_end.member.controller;

import com.feedhanjum.back_end.auth.infra.Login;
import com.feedhanjum.back_end.member.controller.dto.MemberDto;
import com.feedhanjum.back_end.member.domain.ProfileImage;
import com.feedhanjum.back_end.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/{id}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable Long id) {
        MemberDto memberDto = new MemberDto(memberService.getMemberById(id));
        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }

    @PostMapping("/member/name")
    public ResponseEntity<MemberDto> changeName(@Login Long memberId, @RequestParam String name) {
        MemberDto memberDto = new MemberDto(memberService.changeName(memberId, name));
        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }

    @PostMapping("/member/image")
    public ResponseEntity<MemberDto> changeProfileImage(@Login Long memberId, @RequestBody ProfileImage profileImage) {
        MemberDto memberDto = new MemberDto(memberService.changeProfileImage(memberId, profileImage));
        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }
}
