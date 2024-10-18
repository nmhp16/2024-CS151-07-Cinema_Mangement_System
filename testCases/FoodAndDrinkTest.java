// Use VS Code Terminal to run
// javac -cp "lib/*;." src/*.java testCases/*.java
// java -cp "lib/*;.;src;testCases" org.junit.runner.JUnitCore testCases.FoodAndDrinkTest
package testCases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import src.FoodAndDrink;

public class FoodAndDrinkTest {
    private FoodAndDrink foodAndDrink1;
    private FoodAndDrink foodAndDrink2;

    @Before
    public void setUp() {
        FoodAndDrink.resetFoodAndDrinkCount();

        // Initialize instances of FoodAndDrink before each test case
        foodAndDrink1 = new FoodAndDrink("Popcorn", 5.50);
        foodAndDrink2 = new FoodAndDrink(1, "Soda", 2.75);
    }

    @Test
    public void testDefaultConstructor() {
        FoodAndDrink defaultFoodAndDrink = new FoodAndDrink();
        Assert.assertNull(defaultFoodAndDrink.getName());
        Assert.assertEquals(0, defaultFoodAndDrink.getId());
    }

    @Test
    public void testConstructorWithNameAndPrice() {
        Assert.assertEquals("Popcorn", foodAndDrink1.getName());
        Assert.assertEquals(5.50, foodAndDrink1.getPrice(), 0.01);
    }

    @Test
    public void testConstructorWithIdNameAndPrice() {
        Assert.assertEquals(1, foodAndDrink2.getId());
        Assert.assertEquals("Soda", foodAndDrink2.getName());
        Assert.assertEquals(2.75, foodAndDrink2.getPrice(), 0.01);
    }

    @Test
    public void testSetName() {
        foodAndDrink1.setName("Nachos");
        Assert.assertEquals("Nachos", foodAndDrink1.getName());
    }

    @Test
    public void testSetPrice() {
        foodAndDrink1.setPrice(6.00);
        Assert.assertEquals(6.00, foodAndDrink1.getPrice(), 0.01);
    }

    @Test
    public void testSetId() {
        foodAndDrink2.setId(2);
        Assert.assertEquals(2, foodAndDrink2.getId());
    }

    @Test
    public void testToString() {
        String expectedString = "FoodAndDrink [name=Popcorn, price=5.5]";
        Assert.assertEquals(expectedString, foodAndDrink1.toString());

        String expectedString2 = "FoodAndDrink [name=Soda, price=2.75]";
        Assert.assertEquals(expectedString2, foodAndDrink2.toString());
    }

    @Test
    public void testFoodAndDrinkCreationLimit() {
        List<FoodAndDrink> items = new ArrayList<>();

        // Create 100 FoodAndDrink instances successfully
        for (int i = 0; i < 97; i++) { // + 2 from set up
            items.add(new FoodAndDrink());
        }

        // Ensure we can still create the 100th transaction
        FoodAndDrink hundredthItem = new FoodAndDrink();
        assertNotNull("100th FoodAndDrink should be created successfully", hundredthItem);
    }

    @Test
    public void testFoodAndDrinkInstanceLimit() {
        List<FoodAndDrink> items = new ArrayList<>();

        // Create 100 FoodAndDrink instances successfully
        for (int i = 0; i < 98; i++) { // + 2 from set up
            items.add(new FoodAndDrink());
        }

        // Try to create the 101st instance and expect an IllegalStateException
        try {
            new FoodAndDrink();
            fail("Expected IllegalStateException for creating more than 100 FoodAndDrink instances.");
        } catch (IllegalStateException e) {
            assertEquals("Maximum number of FoodAndDrink instances (100) reached.", e.getMessage());
        }
    }
}
