package com.mingyuchoo.reactive.service;

import com.mingyuchoo.reactive.model.Member;
import com.mingyuchoo.reactive.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberRepository memberRepository;

    // Mono 0 ~ 1 // single
    // Flux 0 ~ N // multi

    public void addOneMember(Member member) {
        memberRepository.save(member).subscribe();
    }

    public Flux<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Mono<Member> getOneMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public Mono<Member> modifyOneMember(Long id, Member member) {
        return memberRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("User Not Exists")))
                .map(olderMember -> {
                    if(member.getName() != null) olderMember.setName(member.getName());
                    if(member.getSurname() != null) olderMember.setSurname(member.getSurname());
                    if(member.getUsername() != null) olderMember.setUsername(member.getUsername());
                    if(member.getEmail() != null) olderMember.setEmail(member.getEmail());
                    return olderMember;
                })
                .flatMap(memberRepository::save);
    }

    public Mono<Void> removeOneMember(Long id) {
        return memberRepository.deleteById(id)
                // .switchIfEmpty(Mono.error(new Exception("User Not Found")))
                .then();
    }
}
