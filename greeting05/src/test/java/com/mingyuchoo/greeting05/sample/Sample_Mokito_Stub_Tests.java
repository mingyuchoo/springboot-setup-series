package com.mingyuchoo.greeting05.sample;

import static org.mockito.Mockito.*;

import java.util.LinkedList;
import org.junit.jupiter.api.Test;

public class Sample_Mokito_Stub_Tests {

    @Test
    void test_LinkedList() {

        // you can mock concrete classes, not only interface
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        when(mockedList.get(0)).thenReturn("first");
    }
}
