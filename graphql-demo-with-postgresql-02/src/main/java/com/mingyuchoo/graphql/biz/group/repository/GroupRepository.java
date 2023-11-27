package com.mingyuchoo.graphql.biz.group.repository;

import com.mingyuchoo.graphql.biz.group.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
