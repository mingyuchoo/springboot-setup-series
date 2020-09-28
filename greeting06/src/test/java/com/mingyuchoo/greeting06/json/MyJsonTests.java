package com.mingyuchoo.greeting06.json;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
public class MyJsonTests {

    @Autowired private JacksonTester<String> json;

    @Test
    void testSerialize() throws Exception {
        String hello = new String("Hello");
        // assertThat(json.write(hello)).isEqualToJson("expected.json");
    }

    @Test
    void testDeserialize() throws IOException {
        String hello = new String("Hello");
        String jsonHello = "{\"greeting\": \"Hello\"}";
        // assertThat(json.parse(jsonHello)).isEqualTo(hello);
    }
}
