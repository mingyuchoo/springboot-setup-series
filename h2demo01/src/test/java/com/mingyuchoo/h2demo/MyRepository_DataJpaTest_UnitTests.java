package com.mingyuchoo.h2demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class MyRepository_DataJpaTest_UnitTests {

    @Autowired private TestEntityManager testEntityManager;

    @Autowired private MyRepository myRepository;

    @Test
    public void findById() throws Exception {
        // Given
        MyEntity myEntity = MyEntity.builder().firstName("Hello").lastName("World").build();
        // When
        testEntityManager.persist(myEntity);

        // Then
        assertThat(myEntity).isEqualTo(testEntityManager.find(MyEntity.class, myEntity.getId()));
    }
}
