package testCases;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.FoodAndDrink;

public class FoodAndDrinkTest {
    private FoodAndDrink foodAndDrink1;
    private FoodAndDrink foodAndDrink2;

    @Before
    public void setUp() {
        // Initialize instances of FoodAndDrink before each test case
        foodAndDrink1 = new FoodAndDrink("Popcorn", 5.50);
        foodAndDrink2 = new FoodAndDrink(1, "Soda", 2.75);
    }

    @Test
    public void testDefaultConstructor() {
        FoodAndDrink defaultFoodAndDrink = new FoodAndDrink();
        Assert.assertNull(defaultFoodAndDrink.getName());
        Assert.assertEquals(0.0, defaultFoodAndDrink.getPrice());
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
}
