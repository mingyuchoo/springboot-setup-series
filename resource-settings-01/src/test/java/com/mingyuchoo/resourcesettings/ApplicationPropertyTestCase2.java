package com.mingyuchoo.resourcesettings;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

// 2)
@SpringBootTest
@ActiveProfiles("test") // refer application-test.yml
class ApplicationPropertyTestCase2 {

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
