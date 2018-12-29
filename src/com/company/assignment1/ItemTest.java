package com.company.assignment1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

/**
 * A Parameterized test case for testing the proper calculation of the tax for each time.
 */
@RunWith(Parameterized.class)
public class ItemTest {
    private Item testItem;
    private Double expectedValue; // Actual correct answer

    public ItemTest(String name, Double price, Double quantity, Item.Type type, Double result) {
        // Construct the testItem object for each of the test input
        testItem = new Item(name, price, quantity, type);
        expectedValue = result;
    }

    @Parameters
    public static Collection data() {
        // Test inputs for the item
        return Arrays.asList(new Object[][]{
                {
                        "Test1", 100.0, 100.0, Item.Type.RAW, 11250.0
                },
                {
                        "Test2", 100.0, 100.0, Item.Type.MANUFACTURED, 11475.0
                },
                {
                        "Test3", 100.0, 100.0, Item.Type.IMPORTED, 12000.0
                }
        });
    }

    @Test
    public void testItem() {
        System.out.println("Testing for Item " + testItem.getName());
        assertEquals(Double.valueOf(testItem.getTotalCost()), expectedValue);
    }
}