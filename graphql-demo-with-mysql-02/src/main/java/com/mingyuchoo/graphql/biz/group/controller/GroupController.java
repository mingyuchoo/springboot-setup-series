package com.mingyuchoo.graphql.biz.group.controller;

import com.mingyuchoo.graphql.biz.group.model.Group;
import com.mingyuchoo.graphql.biz.group.repository.GroupRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GroupController {

    private final GroupRepository groupRepository;

    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @QueryMapping
    public Iterable<Group> findAllGroups () {
        return groupRepository.findAll();
    }

    @QueryMapping
    public Long countGroups() {
        return groupRepository.count();
    }
}
