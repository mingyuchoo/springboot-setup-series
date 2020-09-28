package hello;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GreetingTest {

    @Test
    public void testGetId() {
        Greeting hi = new Greeting(1, "Hi");
        assertTrue("testGetId should return 'true'", 1 == hi.getId());
    }
}
