package edu.drexel.se320;

// Hamcrest
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.lessThan;

// Core JUnit 5
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

public class ClosedBoxTesting {

    @Test
    // Test that exception is thrown is array is null instead of completing binary search
    public void testNullArrayException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Integer[] arr = {};
            BinarySearch.find(arr, 1);
        });
        assertEquals("Null array", exception.getMessage());
    }

    @Test
    // Test that exception is thrown if element is null instead of completing binary search
    public void testNullElementException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Integer[] arr = {1,2,3};
            BinarySearch.find(arr, null);
        });
        assertEquals("Null element", exception.getMessage());

    }


    @Test
    public void testFirstElementFoundInteger() {
        Integer[] arr = {-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9};
        assertEquals(BinarySearch.find(arr, -9), 0);
    }

    @Test
    public void testMiddleElementFoundInteger() {
        Integer[] arr = {-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9};
        assertEquals(BinarySearch.find(arr, 0), 9);
    }

    @Test
    public void testLastElementFoundInteger() {
        Integer[] arr = {-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9};
        assertEquals(BinarySearch.find(arr, 9), 18);
    }

    @Test
    public void testFirstInstanceOfRepeatIntegerAtFront() {
        Integer[] arr = {-9,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9};
        assertEquals(BinarySearch.find(arr, -9), 0);
    }

    @Test
    public void testFirstInstanceOfRepeatIntegerInMiddle() {
        Integer[] arr = {-9,-8,-7,-6,-5,-4,-3,-2,-1,0,0,1,2,3,4,5,6,7,8,9};
        assertEquals(BinarySearch.find(arr, 0), 9);
    }

    @Test
    public void testFirstInstanceOfRepeatIntegerAtEnd() {
        Integer[] arr = {-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9,9};
        assertEquals(BinarySearch.find(arr, 9), 18);
    }

    @Test
    public void testInternalBoundaryFrontInteger() {
        Integer[] arr = {Integer.MIN_VALUE,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9};
        assertEquals(BinarySearch.find(arr, Integer.MIN_VALUE), 0);
    }

    @Test
    public void testInternalBoundaryMinusOneFrontInteger() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            Integer[] arr = {Integer.MIN_VALUE-1,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9};
            BinarySearch.find(arr, Integer.MIN_VALUE-1);
        });
        assertEquals("Element was not found in array", exception.getMessage());
    }

    @Test
    public void testInternalBoundaryEndInteger() {
        Integer[] arr = {-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9, Integer.MAX_VALUE};
        assertEquals(BinarySearch.find(arr, Integer.MAX_VALUE), 19);
    }

    @Test
    public void testInternalBoundaryPlusOneEndInteger() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            Integer[] arr = {-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9, Integer.MAX_VALUE+1};
            BinarySearch.find(arr, Integer.MAX_VALUE+1);
        });
        assertEquals("Element was not found in array", exception.getMessage());
    }


    @Test
    public void testFirstElementFoundDouble() {
        Double[] arr = {-.9,-.8,-.7,-.6,-.5,-.4,-.3,-.2,-.1,0.0,.1,.2,.3,.4,.5,.6,.7,.8,.9};
        assertEquals(BinarySearch.find(arr, -.9), 0);
    }

    @Test
    public void testMiddleElementFoundDouble() {
        Double[] arr = {-.9,-.8,-.7,-.6,-.5,-.4,-.3,-.2,-.1,0.0,.1,.2,.3,.4,.5,.6,.7,.8,.9};
        assertEquals(BinarySearch.find(arr, 0.0), 9);
    }

    @Test
    public void testLastElementFoundDouble() {
        Double[] arr = {-.9,-.8,-.7,-.6,-.5,-.4,-.3,-.2,-.1,0.0,.1,.2,.3,.4,.5,.6,.7,.8,.9};
        assertEquals(BinarySearch.find(arr, .9), 18);
    }

    @Test
    public void testFirstInstanceOfRepeatDoubleAtFront() {
        Double[] arr = {-.9,-.9,-.8,-.7,-.6,-.5,-.4,-.3,-.2,-.1,0.0,.1,.2,.3,.4,.5,.6,.7,.8,.9};
        assertEquals(BinarySearch.find(arr, -.9), 0);
    }

    @Test
    public void testFirstInstanceOfRepeatDoubleAtMiddle() {
        Double[] arr = {-.9,-.8,-.7,-.6,-.5,-.4,-.3,-.2,-.1,0.0,0.0,.1,.2,.3,.4,.5,.6,.7,.8,.9};
        assertEquals(BinarySearch.find(arr, 0.0), 9);
    }

    @Test
    public void testFirstInstanceOfRepeatDoubleAtEnd() {
        Double[] arr = {-.9,-.8,-.7,-.6,-.5,-.4,-.3,-.2,-.1,0.0,.1,.2,.3,.4,.5,.6,.7,.8,.9,.9};
        assertEquals(BinarySearch.find(arr, .9), 18);
    }


    @Test
    public void testInternalBoundaryFrontDouble() {
        Double[] arr = {Double.MIN_VALUE,0.0};
        assertEquals(BinarySearch.find(arr, Double.MIN_VALUE),0);
    }

    @Test
    public void testInternalBoundaryMinusOneFrontDouble() {
        Double[] arr = {Double.MIN_VALUE-1,0.0};
        assertEquals(BinarySearch.find(arr, Double.MIN_VALUE-1),0);
    }

    @Test
    public void testInternalBoundaryEndDouble() {
        Double[] arr = {0.0,Double.MAX_VALUE};
        int test = BinarySearch.find(arr, Double.MAX_VALUE);
        assertEquals(BinarySearch.find(arr, Double.MAX_VALUE), 1);
    }

    @Test
    public void testInternalBoundaryPlusOneEndDouble() {
        Double[] arr = {0.0,Double.MAX_VALUE+1};
        int test = BinarySearch.find(arr, Double.MAX_VALUE+1);
        assertEquals(BinarySearch.find(arr, Double.MAX_VALUE), 1);
    }


    @Test
    public void testDifferentTypedDoubleOfSameValueIsFound() {
        Double[] arr = {-.9,-.8,-.7,-.6,-.5,-.4,-.3,-.2,-.1,0.0,.1,.2,.3,.4,.5,.6,.7,.8,.9};
        assertEquals(BinarySearch.find(arr, -0.9), 0);
    }

    @Test
    //Make sure that positive and negative zero are not confused
    public void testSignsForZero() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            Double[] arr = {-.9,-.8,-.7,-.6,-.5,-.4,-.3,-.2,-.1,0.0,.1,.2,.3,.4,.5,.6,.7,.8,.9};
            BinarySearch.find(arr, -0.0);
        });
        assertEquals("Element was not found in array", exception.getMessage());
    }

    @Test
    public void testFirstElementFoundString() {
        String[] arr = {"AA","B","C","D","a","b","c"};
        assertEquals(BinarySearch.find(arr, "AA"), 0);
    }

    @Test
    public void testMiddleElementFoundString() {
        String[] arr = {"AA","B","C","D","a","b","c"};
        assertEquals(BinarySearch.find(arr, "D"), 3);
    }

    @Test
    public void testLastElementFoundString() {
        String[] arr = {"AA","B","C","D","a","b","c"};
        assertEquals(BinarySearch.find(arr, "c"), 6);
    }

    @Test
    public void testFirstInstanceOfRepeatStringAtFront() {
        String[] arr = {"AA","AA","B","C","D","a","b","c"};
        assertEquals(BinarySearch.find(arr, "AA"), 0);
    }

    @Test
    public void testFirstInstanceOfRepeatStringAtMiddle() {
        String[] arr = {"AA","B","C","D","D","a","b","c"};
        assertEquals(BinarySearch.find(arr, "D"), 3);
    }

    @Test
    public void testFirstInstanceOfRepeatStringAtEnd() {
        String[] arr = {"AA","B","C","D","a","b","c","c"};
        assertEquals(BinarySearch.find(arr, "c"), 6);
    }

    @Test
    public void testEmptyStringFound() {
        String[] arr = {"","AA","B","C","D","a","b","c"};
        assertEquals(BinarySearch.find(arr, ""), 0);
    }


    @Test
    public void testSlightlyDifferentString() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            String[] arr = {"AA","B","C","D","a","b","c"};
            BinarySearch.find(arr, "c ");
        });
        assertEquals("Element was not found in array", exception.getMessage());
    }


    @Test
    public void testFindLargeSizeStringInFront() {

        String[] arr = {"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA","B","C","D","a","b","c"};
        assertEquals(BinarySearch.find(arr, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"), 0);
    }

    @Test
    public void testFindLargeSizeStringInEnd(){
        String[] arr = {"AA","B","C","D","a","b","c","ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"};
        assertEquals(BinarySearch.find(arr,"ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"),7);
    }

    @Test
    //This test should fail as the Binary Search is only specifed to work with unsorted arrays
    public void testBinarySearchOnUnSortedArray(){
        Integer[] arr = {3,4,1,2};
        assertEquals(BinarySearch.find(arr,3),0);
    }


}

