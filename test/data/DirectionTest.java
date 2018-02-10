package data;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class DirectionTest {
    private static Direction testDirection;

    @BeforeClass
    public static void setUp() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //creation of the test Direction object by means of Reflection
        Constructor<Direction> directionConstructor = Direction.class.getDeclaredConstructor();
        directionConstructor.setAccessible(true);
        testDirection = directionConstructor.newInstance();
    }

    @Test
    public void testDirectionGetters() {
        assertEquals("", testDirection.getRoomName());
        assertEquals("", testDirection.getDirection());
    }
}