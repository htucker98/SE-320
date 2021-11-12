package edu.drexel.se320;

import java.util.Arrays;

public class Main {
    public static void main(String args[]){
        Integer[] arr = {1,2,3,3};
        Arrays.sort(arr);
        System.out.print(arr);
        BinarySearch.find(arr, 1);
    }
}
