package com.mingyuchoo.graphql.resouce.controller;

import com.mingyuchoo.graphql.resource.model.Resource;
import com.mingyuchoo.graphql.resource.repository.ResourceRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ResourceController {

    private final ResourceRepository resourceRepository;

    public ResourceController(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @SchemaMapping(value = "findAllResources", typeName = "Query")
    public Iterable<Resource> findAllResources () {
        return resourceRepository.findAll();
    }

    @SchemaMapping(value = "countResources", typeName = "Query")
    public Long countResources() {
        return resourceRepository.count();
    }
}
