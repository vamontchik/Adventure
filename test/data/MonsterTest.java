package data;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class MonsterTest {
    private static Monster testMonster;
    private static double TOLERANCE;

    @BeforeClass
    public static void setUp() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //creation of the test Monster object by means of Reflection
        Constructor<Monster> monsterConstructor = Monster.class.getDeclaredConstructor();
        monsterConstructor.setAccessible(true);
        testMonster = monsterConstructor.newInstance();

        //delta value used to compare doubles
        TOLERANCE = 0.0001;
    }

    @Test
    public void testMonsterGetters() {
        assertEquals("", testMonster.getName());
        assertEquals(0.0, testMonster.getAttack(), TOLERANCE);
        assertEquals(0.0, testMonster.getDefense(), TOLERANCE);
        assertEquals(0.0, testMonster.getHealth(), TOLERANCE);
    }

}