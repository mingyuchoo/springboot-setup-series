package com.mingyuchoo.reactive.biz.member.service;

import com.mingyuchoo.reactive.biz.member.model.Member;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MemberService {
    public void addOneMember(Member member);

    public Flux<Member> getAllMembers();

    public Mono<Member> getOneMemberById(Long id);

    public Mono<Member> modifyOneMember(Long id, Member member);

    public Mono<Void> removeOneMember(Long id);
}
