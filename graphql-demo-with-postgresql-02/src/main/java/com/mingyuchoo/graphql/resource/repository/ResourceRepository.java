package com.mingyuchoo.graphql.resource.repository;

import com.mingyuchoo.graphql.resource.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
}
