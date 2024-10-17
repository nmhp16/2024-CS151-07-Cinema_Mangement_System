import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.FoodAndDrink;

public class FoodAndDrinkTest {
    private FoodAndDrink foodAndDrink1;
    private FoodAndDrink foodAndDrink2;

    @BeforeEach
    public void setUp() {
        // Initialize instances of FoodAndDrink before each test case
        foodAndDrink1 = new FoodAndDrink("Popcorn", 5.50);
        foodAndDrink2 = new FoodAndDrink(1, "Soda", 2.75);
    }

    @Test
    public void testDefaultConstructor() {
        FoodAndDrink defaultFoodAndDrink = new FoodAndDrink();
        assertNull(defaultFoodAndDrink.getName());
        assertEquals(0.0, defaultFoodAndDrink.getPrice());
        assertEquals(0, defaultFoodAndDrink.getId());
    }

    @Test
    public void testConstructorWithNameAndPrice() {
        assertEquals("Popcorn", foodAndDrink1.getName());
        assertEquals(5.50, foodAndDrink1.getPrice(), 0.01);
    }

    @Test
    public void testConstructorWithIdNameAndPrice() {
        assertEquals(1, foodAndDrink2.getId());
        assertEquals("Soda", foodAndDrink2.getName());
        assertEquals(2.75, foodAndDrink2.getPrice(), 0.01);
    }

    @Test
    public void testSetName() {
        foodAndDrink1.setName("Nachos");
        assertEquals("Nachos", foodAndDrink1.getName());
    }

    @Test
    public void testSetPrice() {
        foodAndDrink1.setPrice(6.00);
        assertEquals(6.00, foodAndDrink1.getPrice(), 0.01);
    }

    @Test
    public void testSetId() {
        foodAndDrink2.setId(2);
        assertEquals(2, foodAndDrink2.getId());
    }

    @Test
    public void testToString() {
        String expectedString = "FoodAndDrink [name=Popcorn, price=5.5]";
        assertEquals(expectedString, foodAndDrink1.toString());

        String expectedString2 = "FoodAndDrink [name=Soda, price=2.75]";
        assertEquals(expectedString2, foodAndDrink2.toString());
    }
}
