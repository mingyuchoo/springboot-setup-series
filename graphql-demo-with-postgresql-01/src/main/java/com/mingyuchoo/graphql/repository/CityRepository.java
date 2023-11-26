package com.mingyuchoo.graphql.repository;

import com.mingyuchoo.graphql.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {}
