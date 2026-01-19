//package pt.ulusofona.lp2.greatprogrammingjourney.tests;
//
//import org.junit.jupiter.api.Test;
//import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;
//
//import static org.junit.Assert.*;
//
//public class TestGameManager {
//    private String[][] validPlayers() {
//        return new String[][]{
//                {"1", "Ana", "Java", "Blue"},
//                {"2", "Bruno", "C", "Purple"}
//        };
//    }
//
//    @Test
//    void testCreateInitialBoardValid() {
//        GameManager gm = new GameManager();
//
//        boolean result = gm.createInitialBoard(validPlayers(), 10);
//
//        assertTrue(result);
//        assertNotEquals(0, gm.getCurrentPlayerID());
//    }
//
//    @Test
//    void testCreateInitialBoardInvalidPlayers() {
//        GameManager gm = new GameManager();
//
//        assertFalse(gm.createInitialBoard(null, 10));
//        assertFalse(gm.createInitialBoard(new String[0][0], 10));
//    }
//
//    @Test
//    void testGetProgrammerInfo() {
//        GameManager gm = new GameManager();
//        gm.createInitialBoard(validPlayers(), 10);
//
//        String[] info = gm.getProgrammerInfo(1);
//
//        assertNotNull(info);
//        assertEquals("1", info[0]);
//        assertEquals("Ana", info[1]);
//    }
//
//    @Test
//    void testGetSlotInfoInitialPosition() {
//        GameManager gm = new GameManager();
//        gm.createInitialBoard(validPlayers(), 10);
//
//        String[] slotInfo = gm.getSlotInfo(1);
//
//        assertEquals("1,2", slotInfo[0]);
//        assertEquals("", slotInfo[1]);
//        assertEquals("", slotInfo[2]);
//    }
//
//    @Test
//    void testMoveCurrentPlayerValid() {
//        GameManager gm = new GameManager();
//        gm.createInitialBoard(validPlayers(), 10);
//
//        boolean moved = gm.moveCurrentPlayer(3);
//
//        assertTrue(moved);
//        assertEquals(1, gm.getCurrentPlayerID());
//    }
//
//    @Test
//    void testMoveCurrentPlayerInvalidDice() {
//        GameManager gm = new GameManager();
//        gm.createInitialBoard(validPlayers(), 10);
//
//        assertFalse(gm.moveCurrentPlayer(0));
//        assertFalse(gm.moveCurrentPlayer(7));
//    }
//}
