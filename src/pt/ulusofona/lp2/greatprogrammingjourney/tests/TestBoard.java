//package pt.ulusofona.lp2.greatprogrammingjourney.tests;
//
//import org.junit.jupiter.api.Test;
//import pt.ulusofona.lp2.greatprogrammingjourney.core.Board;
//import pt.ulusofona.lp2.greatprogrammingjourney.core.Slot;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//public class TestBoard {
//    @Test
//    void testConstructorAndSize() {
//        Board board = new Board(10);
//
//        assertEquals(10, board.getSize());
//    }
//
//    @Test
//    void testSlotsAreCreatedCorrectly() {
//        Board board = new Board(5);
//
//        for (int i = 1; i <= 5; i++) {
//            Slot slot = board.getSlot(i);
//            assertNotNull(slot);
//            assertEquals(i, slot.getIndex());
//        }
//    }
//
//    @Test
//    void testGetSlotReturnsCorrectSlot() {
//        Board board = new Board(3);
//
//        Slot slot1 = board.getSlot(1);
//        Slot slot2 = board.getSlot(2);
//        Slot slot3 = board.getSlot(3);
//
//        assertEquals(1, slot1.getIndex());
//        assertEquals(2, slot2.getIndex());
//        assertEquals(3, slot3.getIndex());
//    }
//}
