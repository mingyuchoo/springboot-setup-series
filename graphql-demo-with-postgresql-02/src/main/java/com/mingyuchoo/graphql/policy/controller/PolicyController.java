package com.mingyuchoo.graphql.policy.controller;

import com.mingyuchoo.graphql.policy.model.Policy;
import com.mingyuchoo.graphql.policy.repository.PolicyRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PolicyController {

    private final PolicyRepository policyRepository;

    public PolicyController(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @SchemaMapping(value = "findAllPolicys", typeName = "Query")
    public Iterable<Policy> findAllPolicys () {
        return policyRepository.findAll();
    }

    @SchemaMapping(value = "countPolicys", typeName = "Query")
    public Long countPolicys() {
        return policyRepository.count();
    }
}
