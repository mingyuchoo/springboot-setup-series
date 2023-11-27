package com.mingyuchoo.graphql.biz.resource.repository;

import com.mingyuchoo.graphql.biz.resource.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
}
