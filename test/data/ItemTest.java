package data;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class ItemTest {
    private static Item testItem;
    private static double TOLERANCE;

    @BeforeClass
    public static void setUp() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //creation of the test Item object by means of Reflection
        Constructor<Item> itemConstructor = Item.class.getDeclaredConstructor();
        itemConstructor.setAccessible(true);
        testItem = itemConstructor.newInstance();

        //delta value used to compare doubles
        TOLERANCE = 0.0001;
    }

    @Test
    public void testItemGetters() {
        assertEquals("testItem", testItem.getName());
        assertEquals(0.0, testItem.getDamage(), TOLERANCE);
    }

}