
# SE320 Homework 4: Performance Evaluation
## Fall 2021

- This is a *one* week assignment!

### Overview
The goal for this assignment is to expose you to concepts and tools related to performance testing.

Your assignment is to choose reasonable operational profiles, explain the key ideas in provided benchmarking code, measure times for some operational profiles, and explain the consequences in terms of how much load a hypothetical system might handle.

### Experiments
You've been provided with an implementation of a red-black binary search tree.

Attached to this assignment, you'll find ```RedBlackBST.java``` (the red-black tree), and ```Benchmark.java```, which contains a simple benchmarking harness.  In ```Benchmark```, given a hard-coded operational profile for a mix of lookups, insertions, and deletions from the tree, the code performs a warmup run, then 10 times it will run a random mix of 1,000,000 (one million) operations distributed roughly according to the operational profile.  When Benchmark.main() is run, it will print to the console the running time for each iteration (including warmup), and the min, max, and average of the non-warmup runs.

### Submission
Please answer the following. Questions 1-7 and parts of question 8 should be in a text document (e.g., PDF), while some parts of question 8 require writing a few lines of code (for which you should submit the whole directory structure as in prior homeworks). Please remember to submit BOTH the code and written responses (save your written responses in a file in the directory structure for the code!)

1. A short (one paragraph) explanation of why the benchmark harness is structured as it is.  Why is there an initial run that isn't counted in the min/max/average?  Why are we measuring multiple runs?
2. Reasonable operational profiles for use of the RedBlackBST in the following use cases.  Each of these profiles, for our purposes, is simply a percentage of insert/delete/lookup operations (3 numbers that sum to 100 in each case).
    a. A logging data structure, which mostly records new data, with occasional queries and edits (deletions)
    b. A read-heavy database, with occasional updates and deletions.
    c. A read-only database, with no modifications performed.
3. For each of your operational profiles, report the following, which should be produced by Benchmark.main() once you update the hard-coded operational profile.
    a. Warmup time
    b. Minimum iteration time
    c. Maximum iteration time
    d. Average iteration time
5. Which of your operational profiles has higher *throughput* (i.e., performs work work per unit
   time)?  What about the red-black tree might explain this outcome?
   Confirm (or disprove and revisit your hypothesis) by: modifying the benchmark harness to wait for a key press, loading up [VisualVM](https://visualvm.github.io/) (download Standalone), attaching to the program while it is waiting, and doing CPU profiling of the benchmark. Pay attention to what percentage of *time* is spent in each of put, get, and delete (and their helper methods). A method that takes a higher percentage of time than it is given as a percentage of the operational profile is a likely bottleneck. (Note, however, that a random number generator is involved here.)
6. Are your warmup times noticeably different from the "main" iteration times?  Most likely yes, but either way, why might we *expect* a significant difference?
7. The numbers for operational profile (c) aren't actually that useful.  Look a the benchmarking code, and explain why those numbers, as reported, tell us nothing about the performance of using the BST in a real application for a read-only workload.
8. In ```src/jmh/java/se320/Benchmarking.java```, set up microbenchmarking for put, get, and delete:
    a. Set up a test fixture of a substantial red-black tree that can be used as a tree for put/get/delete calls. This is a structure that will be shared across all runs of the benchmark. (Note that this helps avoid the issues hinted at in #7)
    b. Write a microbenchmark for each operation (put, get, delete), where each call to the benchmark invokes the operation on the shared tree with a random integer key (note that the values don't matter for this benchmarking).
    c. Report the average running time for 100 iterations (not including warmup) for each operation.
    d. Looking at the reported times for individual runs (not just the averages), is the average a realistic summary of the performance for all 3 of these operations, or should the benchmark setup ideally be modified for one or more operations? (Hint: Recall that operation performance is sensitive to the tree used for the operation).

### Grading
Above there are 8 questions to respond to. Questions 1-7 are worth 10 points each, question 8 is worth 30 points.

This assignment is itself worth 10% of your term grade.

