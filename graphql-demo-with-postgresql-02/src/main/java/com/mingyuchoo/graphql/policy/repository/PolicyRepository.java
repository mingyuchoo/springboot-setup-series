package com.mingyuchoo.graphql.policy.repository;

import com.mingyuchoo.graphql.policy.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {
}
