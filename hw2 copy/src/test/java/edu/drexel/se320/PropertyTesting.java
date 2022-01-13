package edu.drexel.se320;


import net.jqwik.api.*;
import net.jqwik.api.constraints.UniqueElements;
import org.javatuples.Pair;
import org.junit.jupiter.api.Assertions;

import java.util.*;


public class PropertyTesting {


    @Provide
     Arbitrary<Integer[]> arrayArbitrary() {
        return (Arbitraries.integers().between(0, 100)) // integer value range
                .array(Integer[].class).ofMinSize(0).ofMaxSize(30) // array size range
                .uniqueElements()
                .map(i -> {
                    Arrays.sort(i);
                    return i;
                });
    }

    //customer generator that provides a List of an array of sorted integers and a valid element existing in said array
    @Provide
    Arbitrary<Pair<Integer[], Integer>> pairs() {
        return (Arbitraries.integers().between(0, 100)) // integer value range
                .injectDuplicates(.5)
                .array(Integer[].class).ofMinSize(0).ofMaxSize(30) // array size range
                .map(i -> {
                    Arrays.sort(i); //sort array
                    Integer element;
                    if (i.length>0) {
                        element = i[new Random().nextInt(i.length)]; // select index of random element in array
                    }
                    else{
                        element = null;
                    }
                    return new Pair<>(i, element);
                });
    }

    @Provide
    //array that contains duplicates, and an element that has duplicates in the array
    Arbitrary<Pair<Integer[], Integer>> pairsDup() {
        return (Arbitraries.integers().between(0, 100)) // integer value range
                .injectDuplicates(1)
                .array(Integer[].class).ofMinSize(0).ofMaxSize(30) // array size range
                .map(i -> {
                    Arrays.sort(i); //sort array
                    Integer element = null;
                    for(int dup=0; dup<i.length-1;dup++){
                        if(Objects.equals(i[dup], i[dup + 1])){
                            element = i[dup];
                            break;
                        }
                    }
                    return new Pair<>(i, element);
                });
    }

    // check precondition: Array to be searched is not null
    @Property(tries = 250, shrinking = ShrinkingMode.BOUNDED, maxDiscardRatio = 100)
    public boolean nullArrayPreconditionHandling(@ForAll("pairs") Pair<Integer[], Integer> p) {
        Assume.that(p.getValue0().length==0);
        try {
            BinarySearch.find(p.getValue0(), p.getValue1());
            return false;
        }
        catch (IllegalArgumentException e){
            return true;
        }
    }

    // check precondition: element to be searched for is not null
    @Property(tries = 250, shrinking = ShrinkingMode.BOUNDED, maxDiscardRatio = 100)
    public boolean nullElementPreconditionHandling(@ForAll("pairs") Pair<Integer[], Integer> p) {
        Assume.that(p.getValue1() == null);
        try {
            BinarySearch.find(p.getValue0(), p.getValue1());
            return false;
        }
        catch (IllegalArgumentException e){
            return true;
        }
    }

    //check post-condition error handling: The element was not found in the array
    @Property(tries = 600, shrinking = ShrinkingMode.BOUNDED, maxDiscardRatio = 100)
    public boolean noSuchElement(@ForAll("arrayArbitrary") Integer[] a, @ForAll int x) {
        Assume.that(a.length != 0);
        Assume.that(!Arrays.asList(a).contains(x));
        try {
            BinarySearch.find(a, x);
            return false;
        }
        catch (NoSuchElementException e){
            return true;
        }
    }

    //assuming the element exists in the array, an integer for the index will always be returned
    @Property(tries = 250, shrinking = ShrinkingMode.BOUNDED)
    public boolean existingElementAlwaysReturnsIndex(@ForAll("pairs") Pair<Integer[], Integer> p) {
        Assume.that(p.getValue0().length != 0);
        Assume.that(p.getValue1() != null);
        //check if binary function returns and Integer if it exists in array
        return (BinarySearch.find(p.getValue0(), p.getValue1())) == ((Integer) (BinarySearch.find(p.getValue0(), p.getValue1())));
    }
    
    //check that index points to the element searched for
    @Property(tries = 250, shrinking = ShrinkingMode.BOUNDED)
    public boolean indexPointsToCorrectElement(@ForAll("pairs") Pair<Integer[], Integer> p) {
        Assume.that(p.getValue0().length != 0);
        Assume.that(p.getValue1() != null);
        int index = BinarySearch.find(p.getValue0(), p.getValue1());
        return (Objects.equals(p.getValue0()[index], p.getValue1()));
    }

    //check that the same result is returned compared to the built-in binary search function
    @Property(tries = 250, shrinking = ShrinkingMode.BOUNDED)
    public boolean metamorphicCheck(@ForAll("pairs") Pair<Integer[], Integer> p) {
        Assume.that(p.getValue0().length != 0);
        Assume.that(p.getValue1() != null);
        return (Objects.equals(Arrays.binarySearch(p.getValue0(),p.getValue1()), BinarySearch.find(p.getValue0(), p.getValue1())));
    }

    //check that binary search can handle returning an index for an element with duplicates
    @Property(tries = 250, shrinking = ShrinkingMode.BOUNDED, maxDiscardRatio = 50)
    public boolean duplicateElementCheck(@ForAll("pairsDup") Pair<Integer[], Integer> p) {
        Assume.that(p.getValue0().length != 0);
        Assume.that(p.getValue1() != null);
        int foundIndex = BinarySearch.find(p.getValue0(),p.getValue1());

        //check that returned index points to a duplicate of an element when
        return (p.getValue0()[foundIndex] == p.getValue1());
    }

    //check that the first element in the array is found
    @Property(tries = 250, shrinking = ShrinkingMode.BOUNDED, maxDiscardRatio = 100)
    public boolean findFirstElement(@ForAll("arrayArbitrary") Integer[] a  ) {
        Assume.that(a.length != 0);
        return (BinarySearch.find(a,a[0]) == 0);
    }

    //check that the last element in the array is found
    @Property(tries = 250, shrinking = ShrinkingMode.BOUNDED, maxDiscardRatio = 100)
    public boolean findLastElement(@ForAll("arrayArbitrary") Integer[] a  ) {
        Assume.that(a.length != 0);
        return (BinarySearch.find(a,a[a.length-1]) == a.length-1);
    }

    //check that the middle element in the array is found
    @Property(tries = 250, shrinking = ShrinkingMode.BOUNDED, maxDiscardRatio = 100)
    public boolean findMiddleElement(@ForAll("arrayArbitrary") Integer[] a  ) {
        Assume.that(a.length >= 3);
        int mid = (a.length-1)/2;
        return (BinarySearch.find(a,a[mid]) == mid);
    }



}


