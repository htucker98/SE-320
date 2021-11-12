
# SE320 Homework 1: Closed-box / Open-box Testing
## Fall 2021

- Late Policy: standard for the course, see the syllabus

### Overview
In this assignment you'll be writing a bit of code, and testing it using whichever techniques we've discussed that seem appropriate.

### Code To Test
For this assignment, please implement a single static method that performs a binary search on a
sorted array.  We will work under the assumption that the array represents some kind of
multiset/bag structure, and that searches are only expected for elements that should be in the
array.

- Valid inputs to the search operation are non-null, non-empty arrays (i.e., length >= 0), and a non-null element to search for.
- Your operation should never modify the array
- If your search operation returns a number that is a valid index into the array, the array element at that index should be ```.equals()``` to the sought-after element.
- Your search operation should always either return a valid index into the array, or throw an exception.
- If the input array is sorted and the element is present, the search operation must find the element and return its index in the array.
    + Arrays with duplicate entries are considered valid. If the element being searched for has duplicates, you may return the index of any copy of that element.
- If the search does not find the element in the array, it should raise an informative
  exception with an informative error message (perhaps a [```NoSuchElementException```](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/NoSuchElementException.html)).
- Your search operation should tolerate invalid input --- i.e., it should produce an exception (perhaps
  an
  [```IllegalArgumentException```](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/IllegalArgumentException.html))
  with an informative error message rather than crashing.
- Notice earch functionality is under-specified for unsorted arrays: the requirements above do not obligate any particular finding or non-finding of elements in unsorted arrays --- you choose how your code should behave in this case, and document it.
    + Your search operation should not check that the array is sorted. Why? This is a trade-off between correctness and performance; checking sortedness is linear in the size of the array, while binary search is logarithmic on sorted arrays; checking sortedness would defeat the point of doing binary search --- sub-linear performance.

For your convenience, you may consider the ```Find``` specification from the
lecture slides, but note that your code is expected to work for more than just integers. 

The ```edu.drexel.se320.BinarySearch``` class in the template code (see below)
has the signature we expect you to implement; do not modify it.  If you are
inclined to change the signature of that method, you are probably on the wrong
track.  Note that this requires you to use Java generics with bounds to ensure
the implementation (should) work with any array that can have elements
compared.  For testing, you don't need to test for every conceivable type that
could be put in the array (this is impossible to do anyways).  You can stick to
one choice of array element type for all of your tests (e.g., Integer or
String).  Make use of the [```Comparable``` interface](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Comparable.html). 

### Testing
Test your code!  Please write appropriate automated tests for your search functionality, by
applying techniques discussed in class.  (Note you'll be
expected to explain the approach you took; see below).

Note that the test cases discussed in class are *far* from adequate.

Tip: While discouraged by some groups' coding standards (for good reason), Java's ```import
static``` support can often make test suites a bit less verbose.  ```import static``` imports a
static method of some class such that it can be used directly as a local method, without having to
repeat the class name.  For example, you'll see that many test suites contain

```import static org.junit.jupiter.api.Assertions.assertEquals```

and similar for other static methods of ```org.junit.jupiter.api.Assertions```.  You're welcome to do this with
your search method as well.

The template code contains several trivial tests already as a reminder for how to write certain tests. 
Remember that the other class examples are on Github: https://github.com/Drexel-se320/examples


### Grading

- 10% Search Routine
    + If your code compiles, genuinely attempts to implement binary search, and passes all of your
      tests, you get 20/20 here.
    + If your code fails to compile, fails some of your tests, or if you implement something other than binary search (i.e.,
      something less error-prone), you'll get 0/20.
- 60% Test suite
- 30% Testing Approach Writeup
    + See below for description of what is expected
    + We *strongly* recommend writing this up in a file within the project directory. That way when you submit the .zip file containing the code, you'll automatically get the write-up too (otherwise people sometimes forget it)

Note that the 10% credit for your code is all-or-nothing, and doesn't depend on the code being bug-free!
Though if you get full credit for the testing portion and your tests all pass, then you've probably
caught most of your bugs.

Note: Yes, Java's standard library includes binary search implementations on arrays.  No, you may *not* simply call that --- you should implement the algorithm yourself; if you just call Java's, you'll get a 0 for the assignment.

### Submitting

Please submit through Learn by the deadline (or, following the late policy).

Please submit an archive file (.zip, .tar, or .tgz/.tar.gz) containing your work for the
assignment, including all scaffolding code (below), and a write-up of your testing approach.
We suggest putting your writeup file in the same directory as the build.gradle file, and
packing that one directory (this makes it hard to forget to submit the write-up).

#### Write-up
For your write-up, you must:

1. Explain the approach you took to testing your search routine.
Tell us if you used equivalence partitions, boundary value analysis, etc.,
including what order you did these things and what process (if any) you used for each.  We are not
looking for a particular combination of techniques; we're interested in your record of how you selected and
applied the chosen techniques.  Please include in your write-up any intermediate results (e.g, your textual breakdown of equivalence partitions, etc.); any closed-box
technique you apply generates intermediate results that are not literally represented as test
methods written in Java.  If you're unsure what information to include here, you could of course err
on the side of more information, but really you should just ask.
2. One part of the requirements above were (identified as) underspecified, yet you still had to make a decision regarding the behavior for those under-specified inputs. Which tests are related to that choice of behavior? Are those closed-box, or open-box tests? Why?

Please *do not submit .rar* files!  Please submit your write-up as a .txt, .md, or .pdf file.

### Scaffolding
To (possibly) simplify things for you, we're providing scaffolding for you to
use the gradle Java build tool for your project.  Gradle is similar to ant, but
requires a bit less work to get going.  We're providing a template project that
automatically downloads JUnit for you, and manages classpaths correctly when
running the test target.  For many projects, simply adding proper dependency
descriptions to the file build.gradle and placing a copy of your original
project's source code in src/main/java/ will be enough for gradle to compile
the project.  Extending or replacing the sample test files under src/test/java/
should make it easy to get started writing unit tests.

Gradle has the additional advantage of auto-generating scripts that relieve you of globally installing the correct gradle version on your computer.  The skeleton code contains scripts in the same directory as the main build file (```build.gradle```) that will download and run the appropriate version of gradle for you, from within your project directory.  These are also the scripts that many IDEs assume are present when importing Gradle projects, so don't delete them.

To run your tests from the command line / shell / command prompt / Powershell, change directory to the folder containing ```build.gradle``` and the ```gradlew``` scripts, and type

    ./gradlew test

on Mac OS or Linux systems (including [Windows Subsystem for Linux](https://docs.microsoft.com/en-us/windows/wsl/install)), or

    .\gradlew.bat test

on Windows.

To make grading easier *we will be running your code via gradle*.  This means your code and test
suite need to work when we run your tests as just described.
This means your program source code must be in files under ```src/main/java/```, and your test code
must be somewhere under ```src/test/java/```.  Beyond that we don't have a preference for how you
organize your code into packages.  The template code already respects this directory layout, so if
you just put code in the same directories as the examples, it should work without issue.

That said, you don't need to use ```gradle``` yourself as you work through the project.  Eclipse,
NetBeans, IntelliJ, and other Java IDEs should be able to work with the directory structure just fine, and
are capable of locating and running JUnit tests, though the details vary across IDEs.  You're
welcome to do development that way, and then make sure it still works via ```gradle``` shortly
before submitting.  Many Java IDEs have support for importing a gradle project and doing all building and testing via gradle.  In the case of Eclipse, you'll need to install the [Eclipse Buildship](https://projects.eclipse.org/projects/tools.buildship) plugin for this.


### Source Control
At this point, you should all know what version control / source control is, and be comfortable
with some form of it.  "My machine crashed and my code is gone" is not an acceptable reason for an extension in this course; you should be keeping all of your code under version control with a version stored somewhere other than your personal computer, from the moment you start work on an assignment.

I strongly recommend getting an account on a site like Github, Gitlab, or Bitbucket (there are others, these are only the most well-known), and using that to host git repositories to store your homework.  (Some also support mercurial if that's your preference.)

While these sites are best known for their role in open source collaboration, most let you keep private repositories as well (the details keep changing, mostly whether or not you need to register for a special academic discount or not; some simply give anyone unlimited private repositories).  I've been using this for pretty much all code I've written since... 2009.

If you're uncomfortable with placing your code on some company's servers, it's also possible to set up git or mercurial repositories on tux, stored in a (private) directory (this is *slightly* more complicated, but I just look it up online whenever I need to create a new such repository).

Keeping your code under version control with a non-local repository (any of the options above) has a number of advantages over normal folders on your hard drive:

- You get version control.
- If you're good about checking in code regularly when things are in good states, if you try out something that really backfires, you can reset to the last version of your code that at least mostly worked, without stressing about it or manually managing backups.
- In the process, you get backups (assuming you remember to not only commit, but push).
- Both git and mercurial can be used locally without any servers, but if you consistently push your latest commits to a server, you'll still have it even if your machine dies (for me, this has saved multiple homeworks and research projects from disaster over the years).
- You get into good habits for when you eventually go off and are expected to make regular checkins.
- After you finish courses, if you remember you've done some particular thing before but can't remember how, it's often easier to look back at the project where you did that thing than it is to search for the solution online.

*Do make sure, whichever route you choose, to ensure your repositories are private,* though, for obvious reasons.  On Github/Gitlab/Mercurial this is a setting you can choose when you create a new repository, and you can change it later if you realize you made a mistake.  On tux, using UNIX permissions to make some parent directory of the directory containing your code unreadable to group or other would suffice.  I have one directory (once "courses" but now called "research") that contains a subfolder for each project, each of which is a git repository.  Fixing permissions on that top directory is enough (chmod go-rwx courses).

If you'd like more information on any of this, I'm happy to send or post some references.  The
course syllabus has a link to a fully open & free book on using git.

