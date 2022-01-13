package edu.drexel.se320;

// Hamcrest
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.*;

// Core JUnit 5

import org.junit.Before;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class OpenBoxTesting {

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }
    @Test
    // Test Event occurs when null array is given
    public void testNullArrayEvent() {
        Integer[] arr = {};
        try {
            BinarySearch.find(arr, 1);
        } catch (Exception e){
            assertTrue(outContent.toString().contains("null array\n"));
        }


    }

    @Test
    // Test Event occurs when null array is given
    public void testNullElementEvent() {
        Integer[] arr = {1,2,3};
        try {
            BinarySearch.find(arr, null);
        } catch (Exception e) {
            assertTrue(outContent.toString().contains("null element\n"));
        }
    }

    @Test
    // Test Event occurs when element is not found
    public void testElementNotFoundEvent() {
        Integer[] arr = {1,2,3};
        try {
            BinarySearch.find(arr, 4);
        } catch (Exception e) {
            assertTrue(outContent.toString().contains("no such element in array\n"));
        }
    }

    @Test
    // Test low value calculated event
    public void testLowValueCalcuatedEvent() {
        Integer[] arr = {1,2,3};
        BinarySearch.find(arr, 1);
        assertTrue(outContent.toString().contains("low calculated\n"));
    }

    @Test
    // Test high value calculated event
    public void testHighValueCalcuatedEvent() {
        Integer[] arr = {1,2,3};
        BinarySearch.find(arr, 1);
        assertTrue(outContent.toString().contains("high calculated\n"));
    }

    @Test
    public void testLowLessThanOrEqualToHighEvent() {
        Integer[] arr = {1,2,3};
        BinarySearch.find(arr, 1);
        assertTrue(outContent.toString().contains("low <= high\n"));
    }

    @Test
    // Test mid value calculated event
    public void testMidValueCalculatedEvent() {
        Integer[] arr = {1,2,3};
        BinarySearch.find(arr, 1);
        assertTrue(outContent.toString().contains("mid calculated\n"));
    }

    @Test
    public void testLowEqualsElementEvent() {
        Integer[] arr = {1,1,2,3};
        BinarySearch.find(arr, 1);
        assertTrue(outContent.toString().contains("low = element\n"));
    }

    @Test
    public void testMidEqualsElementEvent() {
        Integer[] arr = {1,2,3};
        BinarySearch.find(arr, 2);
        assertTrue(outContent.toString().contains("mid = element\n"));
    }

    @Test
    public void testMidGreaterThanElementEvent() {
        Integer[] arr = {1,2,3};
        BinarySearch.find(arr, 3);
        assertTrue(outContent.toString().contains("mid > element\n"));
    }

    @Test
    public void testMidLessThanElementEvent() {
        Integer[] arr = {1,2,3};
        BinarySearch.find(arr, 1);
        assertTrue(outContent.toString().contains("mid < element\n"));
    }

    @AfterEach
    public void restoreStreams() {
        System.out.flush();
        System.setOut(System.out);
    }


}
