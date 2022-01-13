package se320; 

import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.junit.jupiter.api.Order;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;


public class Benchmarking {

    /**
     * The BenchmarkState class, by virtue of the State annotation,
     * will be shared across all runs of 
     */
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        public static Random rand = new Random();
        //code for question 7.1
        public RedBlackBST<Integer, Integer> rb = new RedBlackBST<Integer,Integer>();
    }
    
    // Write your benchmarks here. An example test is included as a quick reminder of how to use the annotations, but this test is <i>not good</i> because it's only benchmarking insertion into an empty tree!

    @Benchmark
    @Order(1)
    @Fork(1)
    @Warmup(iterations=5, time=1)
    @Measurement(iterations=100, time=1)
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void putBenchmark(BenchmarkState state) {
        state.rb.put(state.rand.nextInt(100),state.rand.nextInt());
    }

    @Benchmark
    @Order(2)
    @Fork(2)
    @Warmup(iterations=5, time=1)
    @Measurement(iterations=100, time=1)
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void zgetBenchmark(BenchmarkState state) {
        state.rb.get(state.rand.nextInt(100));
    }


    @Benchmark
    @Order(3)
    @Fork(2)
    @Warmup(iterations=5, time=1)
    @Measurement(iterations=100, time=1)
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void zzdeleteBenchmark(BenchmarkState state) {
        state.rb.delete(state.rand.nextInt(100));
    }
}
