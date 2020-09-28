package com.mingyuchoo.greeting04.sample;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;


public class Sample_Mokito_Verify_Test {

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
