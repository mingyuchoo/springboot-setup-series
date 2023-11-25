package com.mingyuchoo.greeting.sample;

import static org.mockito.Mockito.*;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Sample_Mokito_Verify_Tests {

    // mock creation
    List mockList = mock(List.class);

    @Test
    void test_List() {
        // using mock object - it does not throw any "unexpected interaction" exception
        mockList.add("one");
        mockList.clear();

        // selective, explicit, highly readable verification
        verify(mockList).add("one");
        verify(mockList).clear();
    }
}
