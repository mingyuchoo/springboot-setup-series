package com.mingyuchoo.graphql.biz.policy.controller;

import com.mingyuchoo.graphql.biz.policy.model.Policy;
import com.mingyuchoo.graphql.biz.policy.repository.PolicyRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PolicyController {

    private final PolicyRepository policyRepository;

    public PolicyController(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @QueryMapping
    public Iterable<Policy> findAllPolicys () {
        return policyRepository.findAll();
    }

    @QueryMapping
    public Long countPolicys() {
        return policyRepository.count();
    }
}
