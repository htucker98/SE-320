package edu.drexel.se320;

// Hamcrest
import static org.hamcrest.MatcherAssert.assertThat;


// Core JUnit 5
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Testing {

    @Test
    public void testNullArrayException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            Integer[] arr = {};
            BinarySearch.find(arr, 1);
        });

    }

    @Test
    public void testNullElementException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            Integer[] arr = {1,2,3};
            BinarySearch.find(arr, 1);
        });

    }

}

