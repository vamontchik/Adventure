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
        Constructor<Direction> directionConstructor = Direction.class.getDeclaredConstructor(String.class);
        directionConstructor.setAccessible(true);
        testDirection = directionConstructor.newInstance("testRoom");
    }

    @Test
    public void testDirectionGetters() {
        assertEquals("testRoom", testDirection.getRoomName());
        assertEquals("testDirection", testDirection.getDirection());
    }
}