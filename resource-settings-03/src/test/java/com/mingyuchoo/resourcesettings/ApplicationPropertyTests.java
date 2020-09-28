package com.mingyuchoo.resourcesettings;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test-dev") // refer application-test-dev.yml
class ApplicationPropertyTests {

    @Value("${book.consumer.name}")
    private String consumer_name;

    @Value("${book.provider.name}")
    private String provider_name;

    @Value("${book.provider.prefix}")
    private String provider_prefix;

    @Value("${book.provider.host}")
    private String provider_host;

    @Value("${book.provider.port}")
    private String provider_port;

    @Autowired private JsonProperties jsonProperties;

    @Test
    void test() {
        assertEquals(2, 1 + 1);
    }

    @Test
    void failingTest() {
        fail(
                ">>>>>>>>>>>>>>>> "
                        + this.consumer_name
                        + " "
                        + this.provider_name
                        + " "
                        + this.provider_prefix
                        + " "
                        + this.provider_host
                        + " "
                        + this.provider_port);
    }

    @Test
    public void whenPropertiesLoadedViaJsonPropertySource_thenLoadFlatValues() {
        assertEquals("mailer@mail.com", jsonProperties.getHost());
        assertEquals(9090, jsonProperties.getPort());
        assertTrue(jsonProperties.isResend());
    }
}
