package com.mingyuchoo.resourcesettings;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

// 3)
@SpringBootTest
@TestPropertySource(locations = "/foo.properties") // refer foo.properties
class ApplicationPropertyTestCase3 {

    @Value("${foo}")
    private String foo;

    @Test
    void test() {
        assertEquals(2, 1 + 1);
    }

    @Test
    void failingTest() {
        fail(">>>>>>>>>>>>>>>> " + this.foo);
    }
}
