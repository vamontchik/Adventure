package data;

import error.MonsterNotFoundException;
import error.NoRoomException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class LayoutTest {
    private static Room testRoom;
    private static Player testPlayer;
    private static Layout testLayout;
    private static Monster testMonster;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUp() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //creation of the test Room object by means of Reflection
        Constructor<Room> roomConstructor = Room.class.getDeclaredConstructor();
        roomConstructor.setAccessible(true);
        testRoom = roomConstructor.newInstance();

        //creation of the test Player object by means of Reflection
        Constructor<Player> playerConstructor = Player.class.getDeclaredConstructor(Room.class);
        playerConstructor.setAccessible(true);
        testPlayer = playerConstructor.newInstance(testRoom);

        //creation of the test Monster object by means of Reflection
        Constructor<Monster> monsterConstructor = Monster.class.getDeclaredConstructor();
        monsterConstructor.setAccessible(true);
        testMonster = monsterConstructor.newInstance();

        //creation of the test Layout object by means of Reflection
        Constructor<Layout> layoutConstructor = Layout.class.getDeclaredConstructor(
                Player.class,
                Monster[].class,
                Room[].class,
                Room.class,
                Room.class
        );
        layoutConstructor.setAccessible(true);
        testLayout = layoutConstructor.newInstance(
                testPlayer,
                new Monster[]{testMonster},
                new Room[]{testRoom},
                testRoom,
                testRoom
        );
    }

    @Test
    public void testLayoutGetters() {
        assertEquals(testPlayer, testLayout.getPlayer());
        assertEquals(testRoom.getName(), testLayout.getStartingRoomName());
        assertEquals(testRoom, testLayout.getStartingRoom());
        assertEquals(testRoom.getName(), testLayout.getEndingRoomName());
        assertEquals(testRoom, testLayout.getEndingRoom());
        assertEquals(testRoom, testLayout.getRooms()[0]);
        assertEquals(testMonster, testLayout.getMonsters()[0]);
    }

    @Test
    public void testLayoutFindRoomSuccess() throws NoRoomException {
        Room found = Layout.findRoomByName(testRoom.getName());
        assertEquals(found.getName(), testRoom.getName());
    }

    @Test
    public void testLayoutFindRoomFail() throws NoRoomException {
        String fakeRoom = "fakeRoom";

        thrown.expect(NoRoomException.class);
        thrown.expectMessage("Room cannot be found for search string: " + fakeRoom);

        Layout.findRoomByName(fakeRoom);
    }

    @Test
    public void testLayoutFindMonsterSuccess() throws MonsterNotFoundException {
        Monster found = Layout.findMonsterByName(testMonster.getName());
        assertEquals(found.getName(), testMonster.getName());
    }

    @Test
    public void testLayoutFindMonsterFail() throws MonsterNotFoundException {
        String fakeMonster = "fakeMunsta";

        thrown.expect(MonsterNotFoundException.class);
        thrown.expectMessage("Monster cannot be found for search string: " + fakeMonster);

        Layout.findMonsterByName(fakeMonster);
    }
}