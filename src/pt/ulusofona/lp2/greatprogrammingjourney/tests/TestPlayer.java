//package pt.ulusofona.lp2.greatprogrammingjourney.tests;
//
//import org.junit.jupiter.api.Test;
//import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
//import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool.Tool;
//import pt.ulusofona.lp2.greatprogrammingjourney.enums.Color;
//import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerStatus;
//import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;
//
//import static org.junit.Assert.*;
//
//class TestPlayer {
//
//    @Test
//    void testConstructorWithLanguages() {
//        Player p = new Player(1, "Ana", "Java; Python; C", Color.BLUE);
//
//        assertEquals(3, p.getFavoriteLanguages().size());
//        assertTrue(p.getFavoriteLanguages().contains("Java"));
//        assertTrue(p.getFavoriteLanguages().contains("Python"));
//        assertTrue(p.getFavoriteLanguages().contains("C"));
//    }
//
//    @Test
//    void testConstructorWithNullLanguages() {
//        Player p = new Player(2, "Bea", null, Color.GREEN);
//
//        assertTrue(p.getFavoriteLanguages().isEmpty());
//    }
//
//    @Test
//    void testSetAndGetCurrentPosition() {
//        Player p = new Player(1, "Ana", "", Color.BLUE);
//
//        p.setCurrentPosition(5);
//        assertEquals(5, p.getCurrentPosition());
//    }
//
//    @Test
//    void testPositionHistory() {
//        Player p = new Player(1, "Ana", "", Color.BLUE);
//
//        p.setCurrentPosition(3);
//        p.setCurrentPosition(7);
//        p.setCurrentPosition(10);
//
//        assertEquals(7, p.getPreviousPosition());
//        assertEquals(3, p.getPositionTwoMovesAgo());
//    }
//
//    @Test
//    void testPositionHistoryWithNoMoves() {
//        Player p = new Player(1, "Ana", "", Color.BLUE);
//
//        assertEquals(1, p.getPreviousPosition());
//        assertEquals(1, p.getPositionTwoMovesAgo());
//    }
//
//    @Test
//    void testSetStatusAndDiceValue() {
//        Player p = new Player(1, "Ana", "", Color.BLUE);
//
//        p.setStatus(PlayerStatus.IN_GAME);
//        p.setLastDiceValue(6);
//
//        assertEquals(PlayerStatus.IN_GAME, p.getStatus());
//        assertEquals(6, p.getLastDiceValue());
//    }
//
//    @Test
//    void testSetStuckLogic() {
//        Player p = new Player(1, "Ana", "", Color.BLUE);
//
//        p.setStuck(true);
//        assertEquals(PlayerStatus.IN_GAME, p.getStatus());
//
//        p.setStuck(false);
//        assertEquals(PlayerStatus.STUCK, p.getStatus());
//    }
//
//    @Test
//    void testToolsHandling() {
//        Player p = new Player(1, "Ana", "", Color.BLUE);
//
//        Tool tool = new Tool(1, "Debugger") {
//            @Override
//            public boolean cancels(BoardItem item) {
//                return item.getId() == 99;
//            }
//
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
//        p.addTool(tool);
//
//        assertTrue(p.hasTool(tool));
//        assertEquals("Debugger", p.getToolsAsStr());
//    }
//
//    @Test
//    void testHasToolWithNull() {
//        Player p = new Player(1, "Ana", "", Color.BLUE);
//
//        assertFalse(p.hasTool(null));
//    }
//
//    @Test
//    void testConsumeToolThatCancels() {
//        Player p = new Player(1, "Ana", "", Color.BLUE);
//
//        BoardItem abyss = new BoardItem(99, "Abyss") {
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
//        Tool tool = new Tool(1, "Shield") {
//            @Override
//            public boolean cancels(BoardItem item) {
//                return item.getId() == 99;
//            }
//
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
//        p.addTool(tool);
//
//        assertTrue(p.hasToolThatCancels(abyss));
//
//        p.consumeToolThatCancels(abyss);
//
//        assertFalse(p.hasToolThatCancels(abyss));
//        assertEquals("No tools", p.getToolsAsStr());
//    }
//
//    @Test
//    void testInfoStrings() {
//        Player p = new Player(1, "Ana", "Java;Python", Color.BLUE);
//        p.setStatus(PlayerStatus.IN_GAME);
//        p.setCurrentPosition(4);
//
//        String info = p.getInfoString();
//        assertTrue(info.contains("Ana"));
//        assertTrue(info.contains("4"));
//        assertTrue(info.contains(PlayerStatus.IN_GAME.getDisplayName()));
//    }
//}