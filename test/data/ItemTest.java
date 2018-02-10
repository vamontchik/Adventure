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
        Constructor<Item> itemConstructor = Item.class.getDeclaredConstructor();
        itemConstructor.setAccessible(true);
        testItem = itemConstructor.newInstance();

        TOLERANCE = 0.0001;
    }

    @Test
    public void testItemGetters() {
        assertEquals("", testItem.getName());
        assertEquals(0.0, testItem.getDamage(), TOLERANCE);
    }

}