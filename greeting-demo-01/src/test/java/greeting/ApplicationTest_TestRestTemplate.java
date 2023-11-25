package greeting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest_TestRestTemplate {
    @Autowired TestRestTemplate testRestTemplate;

    @MockBean HelloService helloService;

    @Test
    public void testHelloWorld() throws Exception {
        when(helloService.sayHello()).thenReturn("Hello, World!");
        String result = testRestTemplate.getForObject("/", String.class);
        assertThat(result).isEqualTo("Hello, World!");
    }
}
