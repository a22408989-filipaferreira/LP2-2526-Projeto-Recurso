package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItemFactory;
import pt.ulusofona.lp2.greatprogrammingjourney.core.Board;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestBoardAndFactory {
    @Test
    void boardGetSlot_positionsStartAt1() {
        Board b = new Board(5);
        assertEquals(1, b.getSlot(1).getIndex());
        assertEquals(5, b.getSlot(5).getIndex());
    }

    @Test
    void factory_createsKnownAbyssesAndTools_andNullForUnknown() {
        int[] abyssIds = {0,1,2,3,4,5,6,7,8,9,20};
        for (int id : abyssIds) {
            BoardItem a = BoardItemFactory.create(0, id);
            assertNotNull(a, "Abyss id " + id + " deveria existir");
            assertEquals(0, a.getType());
            assertFalse(a.isCollectable());
        }

        int[] toolIds = {0,1,2,3,4,5};
        for (int id : toolIds) {
            BoardItem t = BoardItemFactory.create(1, id);
            assertNotNull(t, "Tool id " + id + " deveria existir");
            assertEquals(1, t.getType());
            assertTrue(t.isCollectable());
            assertNotNull(t.asTool());
        }

        assertNull(BoardItemFactory.create(0, 999));
        assertNull(BoardItemFactory.create(1, 999));
        assertNull(BoardItemFactory.create(2, 0));
    }
}
