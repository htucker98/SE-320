
# SE320 Homework 5: GUI Testing
## Fall 2021

- Late Policy: Due to end of the term, only the free 3-day extension or extensions by the professor apply: this assignment may not otherwise be turned in late for reduced credit.

### Overview
The goal for this assignment is to give you just a taste of GUI testing.

You have been given a gradle project.  Inside the ```web/``` subdirectory, there is a single file ```index.html```.  If you open it in your web browser, you'll see that it is a very (very) simple TODO list.

Your job is to test the GUI functionality, including:

- Can you add TODOs?
- Can you remove TODOs?
- If you remove one TODO, are the others still there?
- etc.

There are several parts to this assignment:

1. Write an event flow graph that assumes there are up to two TODO items in the list.
    + You can model the fact that new elements are created and removed dynamically by having the events for adding or removing TODOs move to different "clusters" of events --- e.g., after one TODO is created, certain things are possible, after a second is made a different set of events is possible.
    + This means you need not consider event sequences where more than two TODOs would be present at
      the same time --- e.g., after creating a second list item addition is no longer an enabled action.  Note that it's still possible to have event *sequences* (paths) that include more than two item additions,
      such as adding two items, removing one, and adding another. Consider actions
      relating to the first item (e.g., removing the first item of two) to be distinct actions
      (nodes) from those relating to the second (e.g., removing the second item).
2. Write enough tests to achieve *event coverage* on your event flow graph
    + Most of the tests will indeed be very similar, and will need to start by setting up two-element TODO lists.
    + There are many things you could consider checking in your tests.  To keep things simple, your tests should only check the following:
        - If you add a TODO, that TODO should now be present
        - If you remove a TODO, that TODO is now gone, and any other TODOs still exist in the same relative order (regardless of absolute location)
3. Briefly explain the trade-offs involved in stopping at event coverage for this program, rather than pursuing event interaction coverage or length-n event sequence coverage.  What kinds of tests is this simpler coverage criteria *not* forcing you to write?  What are the advantages of not writing them (think about how many tests you'd need for the stronger criteria...).
4. Part 1 requests an event flow graph, which has a fixed number of nodes.  As you likely noticed when designing the event flow graph, this page's UI does *not* restrict you to 2 TODO items (to keep things manageable, it limits you to 3).  So you have derived tests from an abstraction that mimcs some aspects of the program and hides others.  Please consider the consequences of this mismatch.  If this event flow graph is the basis for all of your tests, what sorts of problems are unlikely to be discovered? Can you describe a test whose behavior is not captured by your event flow graph? Conversely, are there any ways in which the fixed size assumption helps simplify testing in a good way?  Does it encourage you to write tests for impossible scenarios?  Would any of these issues be affected by working with a larger event flow graph (say, up to 5-element TODO lists)?

The javadocs for the ```WebDriver``` will likely be useful: [http://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/WebDriver.html](http://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/WebDriver.html).  An important subtlety that people have lost time on in the past is that ```findElement``` (singular) should not be used to look for elements that are not there (like deleted elements); this is in the documentation, but not a natural assumption.  The documentation says to use ```findElements``` (plural) for this.  ```findElements``` returns a ```List<WebElement>```, and if a certain element shouldn't exist, looking up with ```findElements``` should return an empty (size 0) list.

### Skeleton Code and Dependencies
You've been given a test class with a single working test.  In order to make it work on your system, you'll need to do the following:

- Modify the uiPath string in that class to give the file URL to ```web/index.html``` on your system.  This is "file://" followed by the full system path to that file wherever you've unpacked the zip file.  The easiest way to get this is to open ```web/index.html``` in your favorite browser, and copy what the browser puts in your address bar over as the new value of uiPath.
    + Yes, there are ways to do this without hard-coding the path, but they're sensitive to how you run the tests. Since many students prefer to use (many various) IDEs in addition to Gradle, we've opted for the simplest thing that works for everyone.
- Install GeckoDriver ([https://github.com/mozilla/geckodriver](https://github.com/mozilla/geckodriver)), the program Selenium uses to control an instance of the Firefox web browser.  
    + For all platforms, you can download and install from [the release page](https://github.com/mozilla/geckodriver/releases)
        - On Mac, you may need to manually run the ```geckodriver``` binary by right-clicking, choosing Open, and then choosing Open (yes, again) in the resulting pop-up, or MacOS will disable it since you downloaded it from the internet. If you forget to do this, you'll get a pop-up complaining about geckodriver being from the internet.
    + On Mac, you can use homebrew to install geckodriver, but it will take a long time (it compiles the Rust compiler if you do this)
- Make sure geckodriver is in your system path.  There are directions for this in the [Selenium section of the GeckoDriver instructions](https://firefox-source-docs.mozilla.org/testing/geckodriver/geckodriver/Usage.html?highlight=selenium).  (As you read those instructions, don't worry about the Selenium version business; the gradle project uses an appropriate version.)
    + If you don't do this, your tests will fail with complaints about no geckodriver being installed, because Selenium won't know where to find it.

Once all three of the steps above are done, the existing test in the project should pass (```gradlew
test```).

We strongly encourage you to try this much on the early side, even if you don't intend to do the bulk of the work until later.  This will give us more time to help with any installation issues that may come up.

Note: You *must* use ```geckodriver```, and may not modify the assignment to use the WebDriver
support in Chrome, Edge, or other browsers.


### Submission
Please submit 

1. a zip or tarball including the whole gradle project
2. an appropriate picture format (PDF, BMP, JPG, PNG) for your event flow graph (scans of a
   hand-written diagram are fine, as long as they are *legible*)
3. an appropriate text format (TXT, RTF, PDF) for your written explanation.

### Grading

- 30% Event flow graph
- 30% Tests that achieve event coverage
- 20% Discussion of trade-offs
- 20% Discussion of fixed-size graph for a variable-sized UI

This assignment is itself worth 10% of your term grade.
