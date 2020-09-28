package com.mingyuchoo.greeting04.sample;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.mockito.Mockito.*;

public class Sample_Mokito_Stub_Test {

    @Test
    void test_LinkedList() {

        // you can mock concrete classes, not only interface
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        when(mockedList.get(0)).thenReturn("first");
    }
}
