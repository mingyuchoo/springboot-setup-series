package com.mingyuchoo.pgsql.biz.person.repository;

import com.mingyuchoo.pgsql.biz.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends JpaRepository<Person, Long> {
    // Person findById(long id);
    // List<Person> findByFirstName(String firstName);
    // List<Person> findByLastName(String lastName);
    // List<Person> findAll();
}
