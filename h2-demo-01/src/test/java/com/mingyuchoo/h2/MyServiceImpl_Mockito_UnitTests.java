package com.mingyuchoo.h2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MyServiceImpl_Mockito_UnitTests {

    @Mock private MyRepository myRepository;

    @InjectMocks private MyService myService = new MyServiceImpl();

    @Test
    public void get() throws Exception {
        int id = 1;
        when(myRepository.findById(id)).thenReturn(new MyEntity("Hello", "World"));

        MyEntity myEntity = myService.get();
        assertThat(myEntity.getFirstName()).isEqualTo("Hello");
        assertThat(myEntity.getLastName()).isEqualTo("World");
    }
}
