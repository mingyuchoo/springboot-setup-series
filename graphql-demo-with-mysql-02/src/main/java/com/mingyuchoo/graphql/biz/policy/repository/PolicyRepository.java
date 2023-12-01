package com.mingyuchoo.graphql.biz.policy.repository;

import com.mingyuchoo.graphql.biz.policy.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {
}
