package com.mingyuchoo.reactive.biz.member.repository;

import com.mingyuchoo.reactive.biz.member.model.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {}
