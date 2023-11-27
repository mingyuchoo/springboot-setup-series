package com.mingyuchoo.graphql.group.controller;

import com.mingyuchoo.graphql.group.model.Group;
import com.mingyuchoo.graphql.group.repository.GroupRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GroupController {

    private final GroupRepository groupRepository;

    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @SchemaMapping(value = "findAllGroups", typeName = "Query")
    public Iterable<Group> findAllGroups () {
        return groupRepository.findAll();
    }

    @SchemaMapping(value = "countGroups", typeName = "Query")
    public Long countGroups() {
        return groupRepository.count();
    }
}
