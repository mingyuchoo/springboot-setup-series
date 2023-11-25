package com.mingyuchoo.reactive.controller;

import com.mingyuchoo.reactive.model.Member;
import com.mingyuchoo.reactive.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOneMember(@RequestBody Member member) {
        memberService.addOneMember(member);
    }
    @GetMapping("/members")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Flux<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/members/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Member> getOneMemberById(@PathVariable Long id) {
       return memberService.getOneMemberById(id);
    }

    @PutMapping("/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Member> modifyOneMember(@PathVariable Long id, @RequestBody Member member) {
        return memberService.modifyOneMember(id, member);
    }

    @DeleteMapping("/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> removeOneMember(@PathVariable Long id) {
        return memberService.removeOneMember(id);
    }
}