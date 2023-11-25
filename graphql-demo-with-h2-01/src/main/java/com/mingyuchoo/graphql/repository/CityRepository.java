package com.mingyuchoo.graphql.repository;

import com.mingyuchoo.graphql.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {}
