package com.mingyuchoo.graphql02.repository;

import com.mingyuchoo.graphql02.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {}
