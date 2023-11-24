package com.mingyuchoo.reactive.repository;

import com.mingyuchoo.reactive.model.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {

}
