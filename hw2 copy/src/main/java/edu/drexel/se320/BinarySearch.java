package edu.drexel.se320;
import java.lang.Class;
import java.util.NoSuchElementException;

public class BinarySearch {

    public static <T extends Comparable<T>> int find(T[] array, T elem){

        //check if array is null
        if (array.length == 0) {
            throw new IllegalArgumentException("Null array");
        }
        // checks that element is of the same type as array
        else if (elem == null){
            throw new IllegalArgumentException("Null element");
        }

        else {
            int low = 0;
            int high = array.length - 1;
            int mid = low + ((high - low) / 2);

            while (low <= high) {
                mid = low + ((high - low) / 2);

                // case: element is found
                if (elem.compareTo(array[mid]) == 0) {
                    return mid;
                } else {
                    //element is right branch
                    if (elem.compareTo(array[mid]) > 0) {
                        low = mid + 1;
                    }
                    //element in left branch
                    else if (elem.compareTo(array[mid]) < 0) {
                        high = mid - 1;
                    }
                }
            }
            // if while loop is exited and nothing returned the element wasn't found
            throw new NoSuchElementException("Element was not found in array");
        }
        //while(elem.compareTo(array[mid]) != 0){
        //if (low > high){
        //throw new NoSuchElementException("Element was not found in array");
        //}
        //else{
        //element in left branch
        //if (elem.compareTo(array[mid]) > 0){
        //high = mid-1;
        //}
        //element is right branch
        //else if (elem.compareTo(array[mid]) < 0){
        //low = mid+1;
        //}
        //update midpoint
        //mid = low + ((high-low)/2);
        //}
        //}
        //return mid;
        //}
    }



    public void exampleCalls() {
        // Java generics do not treat primitives the same as object types.
        // To pass a primitive type (int, double, etc.) to the find method, you need to actually use the corresponding "boxed" version (Integer, Double, etc.) which is a class-based version of each primitive.
        // For types which are already/always objects, like String, everything will just work.
        // This method is not part of a test, but just a refresher/crash-course for those who may not have tried passing primitives to generic Java methods previously.

        Integer[] arr = { 0, 1, 2 };
        find(arr, 1);

        String[] arr2 = { "a", "b", "c", "foo" };
        find(arr2, "c");

    }
}
