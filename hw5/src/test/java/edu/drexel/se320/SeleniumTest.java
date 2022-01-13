// Based on the example code at https://www.selenium.dev/documentation/
package edu.drexel.se320;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SeleniumTest {

    protected final String uiPath = "file:///Users/hanatucker/Desktop/SE%20320/hw5/web/index.html";



    @Test
    public void testOneItem() {
        System.setProperty("webdriver.gecko.driver",  "/usr/local/bin/geckodriver");
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            // Find the + to click to display the foerm to add a todo
            // Looking up by the id, not the name attribute
            WebElement elt = driver.findElement(By.id("controls1plus"));

            // Click on the [+]
            elt.click();

            // Find the form field
            WebElement input = driver.findElement(By.id("itemtoadd"));

            // Make up a todo
            input.sendKeys("Something to do");

            // Find and click the "Add to list" button
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            /* The first element added to the list will have id "item1"
             * Subsequent list items will have IDs item2, item3, etc.
             * Arguably this is too brittle, but rather than forcing you
             * all to become experts on the DOM, you may assume this is done
             * correctly, and/or you're testing this functionality implicitly. */
            WebElement li = driver.findElement(By.id("item1"));
            // We use startsWith because getText includes the text of the Delete button
            assertTrue(li.getText().startsWith("Something to do"), "Checking correct text for added element");
        } finally {
            driver.quit();
        }
    }

    @Test
    // Tests that two elements can be added to list
    public void testAddTwoItem() {
        System.setProperty("webdriver.gecko.driver",  "/usr/local/bin/geckodriver");
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            // Find the + to click to display the foerm to add a todo
            // Looking up by the id, not the name attribute
            WebElement elt = driver.findElement(By.id("controls1plus"));

            // Click on the [+]
            elt.click();

            // Find the form field
            WebElement input = driver.findElement(By.id("itemtoadd"));

            // Make up a todo
            input.sendKeys("first item");

            // Find and click the "Add to list" button
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            // Try and find first list item and make sure the text matches
            WebElement firstItem = driver.findElement(By.id("item1"));
            Boolean firstItemWasAdded =  firstItem.getText().startsWith("first item");

            // Make up a todo
            input.clear();
            input.sendKeys("second item");

            // click add button to add second item
            addButton.click();

            WebElement secondItem = driver.findElement(By.id("item2"));
            // We use startsWith because getText includes the text of the Delete button
            String test = secondItem.getText();
            Boolean secondItemWasAddedAfterFirst =  secondItem.getText().startsWith("second item");

            //check that both items exist
            assertTrue(firstItemWasAdded && secondItemWasAddedAfterFirst);
        } finally {
            driver.quit();
        }
    }

    @Test
    // Tests If you remove the first TODO, that TODO is now gone, and that the second TODOs still exist in the same relative order
    public void testRemovefirstItemThenSecond() {
        System.setProperty("webdriver.gecko.driver",  "/usr/local/bin/geckodriver");
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            // Find the + to click to display the foerm to add a todo
            // Looking up by the id, not the name attribute
            WebElement elt = driver.findElement(By.id("controls1plus"));

            // Click on the [+]
            elt.click();

            // Find the form field
            WebElement input = driver.findElement(By.id("itemtoadd"));

            // Make up a todo
            input.sendKeys("first item");

            // Find and click the "Add to list" button
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            // Try and find first list item and make sure the text matches
            WebElement firstItem = driver.findElement(By.id("item1"));
            Boolean firstItemWasAdded =  firstItem.getText().startsWith("first item");

            // Make up a todo
            input.clear();
            input.sendKeys("second item");

            // click add button to add second item
            addButton.click();

            WebElement secondItem = driver.findElement(By.id("item2"));
            // We use startsWith because getText includes the text of the Delete button
            Boolean secondItemWasAddedAfterFirst =  secondItem.getText().startsWith("second item");

            //Find and click "Minus" button
            WebElement minusButton = driver.findElement(By.id("controls1minus"));
            minusButton.click();

            // Find and click the "Delete" button for first item
            WebElement firstDeleteButton = driver.findElement(By.id("button1"));
            firstDeleteButton.click();

            // check that the first item was deleted
            List<WebElement> li = driver.findElements(By.id("item1"));
            Boolean firstItemWasDeleted = li.size() == 0;

            // Check that second item still exists and in same relative order
            String test = secondItem.getText();
            Boolean secondItemStillExists =  driver.findElement(By.id("item2")).getText().startsWith("second item") && driver.findElement(By.id("theList")).getText().startsWith("second item");

            // Find and click the "Delete" button for second item
            WebElement secondDeleteButton = driver.findElement(By.id("button2"));
            secondDeleteButton.click();


            // check that the first item was deleted
            li = driver.findElements(By.id("item2"));
            Boolean secondItemWasDeleted = li.size() == 0;

            assertTrue(firstItemWasAdded && firstItemWasDeleted && secondItemWasAddedAfterFirst && secondItemWasDeleted);
        } finally {
            driver.quit();
        }
    }

    @Test
    // Tests If you remove the first TODO, that TODO is now gone, and that the second TODOs still exist in the same relative order
    public void testRemoveSecondItemThenFirst() {
        System.setProperty("webdriver.gecko.driver",  "/usr/local/bin/geckodriver");
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            // Find the + to click to display the foerm to add a todo
            // Looking up by the id, not the name attribute
            WebElement elt = driver.findElement(By.id("controls1plus"));

            // Click on the [+]
            elt.click();

            // Find the form field
            WebElement input = driver.findElement(By.id("itemtoadd"));

            // Make up a todo
            input.sendKeys("first item");

            // Find and click the "Add to list" button
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            // Try and find first list item and make sure the text matches
            WebElement firstItem = driver.findElement(By.id("item1"));
            Boolean firstItemWasAdded =  firstItem.getText().startsWith("first item");

            // Make up a todo
            input.clear();
            input.sendKeys("second item");

            // click add button to add second item
            addButton.click();

            WebElement secondItem = driver.findElement(By.id("item2"));
            // We use startsWith because getText includes the text of the Delete button
            Boolean secondItemWasAddedAfterFirst =  secondItem.getText().startsWith("second item");

            //Find and click "Minus" button
            WebElement minusButton = driver.findElement(By.id("controls1minus"));
            minusButton.click();

            // Find and click the "Delete" button for first item
            WebElement secondDeleteButton = driver.findElement(By.id("button2"));
            secondDeleteButton.click();

            // check that the first item was deleted
            List<WebElement> li = driver.findElements(By.id("item2"));
            Boolean secondItemWasDeleted = li.size() == 0;

            // Check that second item still exists and in same relative order
            Boolean secondItemStillExists =  driver.findElement(By.id("item1")).getText().startsWith("first item") && driver.findElement(By.id("theList")).getText().startsWith("first item");

            // Find and click the "Delete" button for second item
            WebElement firstDeleteButton = driver.findElement(By.id("button1"));
            firstDeleteButton.click();


            // check that the first item was deleted
            li = driver.findElements(By.id("item1"));
            Boolean firstItemWasDeleted = li.size() == 0;

            assertTrue(firstItemWasAdded && firstItemWasDeleted && secondItemWasAddedAfterFirst && secondItemWasDeleted);
        } finally {
            driver.quit();
        }
    }

    /*@Test
    // Tests that two elements can be added to list
    public void testRemoveBothItems() {
        System.setProperty("webdriver.gecko.driver",  "/usr/local/bin/geckodriver");
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            // Find the + to click to display the foerm to add a todo
            // Looking up by the id, not the name attribute
            WebElement elt = driver.findElement(By.id("controls1plus"));

            // Click on the [+]
            elt.click();

            // Find the form field
            WebElement input = driver.findElement(By.id("itemtoadd"));

            // Make up a todo
            input.sendKeys("first item");

            // Find and click the "Add to list" button
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            // Try and find first list item and make sure the text matches
            WebElement firstItem = driver.findElement(By.id("item1"));
            Boolean firstItemWasAdded =  firstItem.getText().startsWith("first item");

            // Make up a todo
            input.sendKeys("second item");

            // click add button to add second item
            addButton.click();

            WebElement secondItem = driver.findElement(By.id("item2"));
            // We use startsWith because getText includes the text of the Delete button
            Boolean secondItemWasAdded =  secondItem.getText().startsWith("second item");

            //Find and click "Minus" button
            WebElement minusButton = driver.findElement(By.id("controls1minus"));
            minusButton.click();

            // Find and click the "Delete" button for first item
            WebElement firstDeleteButton = driver.findElement(By.id("button1"));
            firstDeleteButton.click();

            // Find and click the "Delete" button for second item
            WebElement secondDeleteButton = driver.findElement(By.id("button2"));
            secondDeleteButton.click();

            // check that the first item was deleted
            Boolean firstItemWasDeleted = false;
            try {
                firstItem = driver.findElement(By.id("item1"));
            } catch (org.openqa.selenium.NoSuchElementException e){
                firstItemWasDeleted = true;
            }

            // check that the first item was deleted
            Boolean secondItemWasDeleted = false;
            try {
                secondItem = driver.findElement(By.id("item2"));
            } catch (org.openqa.selenium.NoSuchElementException e){
                secondItemWasDeleted = true;
            }
            assertTrue(firstItemWasAdded && firstItemWasDeleted && secondItemWasAdded && secondItemWasDeleted);
        } finally {
            driver.quit();
        }
    }

    @Test
    // Tests flow path: Expand Control Menu -> Add First Item -> Collapse Control Menu -> Remove First Item
    public void testAddOneItemThenClickMinusThenRemove() {
        System.setProperty("webdriver.gecko.driver",  "/usr/local/bin/geckodriver");
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get(uiPath);
            // Find the + to click to display the foerm to add a todo
            // Looking up by the id, not the name attribute
            WebElement elt = driver.findElement(By.id("controls1plus"));

            // Click on the [+]
            elt.click();

            // Find the form field
            WebElement input = driver.findElement(By.id("itemtoadd"));

            // Make up a todo
            input.sendKeys("Something to do");

            // Find and click the "Add to list" button
            WebElement addButton = driver.findElement(By.id("addbutton"));
            addButton.click();

            WebElement li = driver.findElement(By.id("item1"));
            // We use startsWith because getText includes the text of the Delete button
            Boolean itemWasAdded =  li.getText().startsWith("Something to do");

            //Find and click "Minus" button
            WebElement minusButton = driver.findElement(By.id("controls1minus"));
            minusButton.click();

            // Find and click the "Delete" button
            WebElement deleteButton = driver.findElement(By.id("button1"));
            deleteButton.click();

            // check that item was deleted
            Boolean itemWasDeleted = false;
            try {
                li = driver.findElement(By.id("item1"));
            } catch (org.openqa.selenium.NoSuchElementException e){
                itemWasDeleted = true;
            }

            assertTrue(itemWasAdded && itemWasDeleted);
        } finally {
            driver.quit();
        }
    }*/


}
