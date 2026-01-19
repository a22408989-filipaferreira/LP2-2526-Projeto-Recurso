//package pt.ulusofona.lp2.greatprogrammingjourney.tests;
//
//import org.junit.jupiter.api.Test;
//import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
//import pt.ulusofona.lp2.greatprogrammingjourney.core.Slot;
//import pt.ulusofona.lp2.greatprogrammingjourney.enums.Color;
//import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
//
//public class TestSlot {
//    @Test
//    void testConstructor() {
//        Slot slot = new Slot(5);
//
//        assertEquals(5, slot.getIndex());
//        assertNull(slot.getItem());
//    }
//
//    @Test
//    void testSetAndGetItem() {
//        Slot slot = new Slot(3);
//        BoardItem abyss = new BoardItem(10, "Abyss") {
//            @Override
//            public String react(Player player) {
//                return "";
//            }
//
//            @Override
//            public int getType() {
//                return 0;
//            }
//
//            @Override
//            public boolean affectsAllPlayersInSlot() {
//                return false;
//            }
//
//            @Override
//            public boolean swapsStuckPlayer() {
//                return false;
//            }
//
//            @Override
//            public boolean isCollectable() {
//                return false;
//            }
//        };
//
//        slot.setItem(abyss);
//
//        assertEquals(abyss, slot.getItem());
//    }
//
//    @Test
//    void testReactWithoutItem() {
//        Slot slot = new Slot(1);
//        Player p = new Player(1, "Ana", "", Color.BLUE);
//
//        String result = slot.react(p);
//
//        assertEquals("", result);
//    }
//
//    @Test
//    void testReactWithItem() {
//        Slot slot = new Slot(1);
//        Player p = new Player(1, "Ana", "", Color.BLUE);
//
//        BoardItem item = new BoardItem(10, "Abyss") {
//            @Override
//            public String react(Player player) {
//                return "Ouch!";
//            }
//
//            @Override
//            public int getType() {
//                return 0;
//            }
//
//            @Override
//            public boolean affectsAllPlayersInSlot() {
//                return false;
//            }
//
//            @Override
//            public boolean swapsStuckPlayer() {
//                return false;
//            }
//
//            @Override
//            public boolean isCollectable() {
//                return false;
//            }
//        };
//
//        slot.setItem(item);
//
//        String result = slot.react(p);
//
//        assertEquals("Ouch!", result);
//    }
//}
