package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItemFactory;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool.ExceptionHandlingTool;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.Color;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerStatus;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

import static org.junit.Assert.*;

public class TestPlayer {
    @Test
    void languagesParsedAndSortedInInfoArray() {
        Player p = new Player(1, "Ana", "Python; java; C", Color.BLUE);
        p.setStatus(PlayerStatus.IN_GAME);
        p.setCurrentPosition(1);

        String[] info = p.getInfoArray();
        assertEquals("1", info[0]);
        assertEquals("Ana", info[1]);

        assertEquals("C; java; Python", info[2]);
    }

    @Test
    void positionHistory_previousAndTwoMovesAgo_work() {
        Player p = new Player(1, "Ana", "", Color.BLUE);
        p.setStatus(PlayerStatus.IN_GAME);

        assertEquals(1, p.getPreviousPosition());
        assertEquals(1, p.getPositionTwoMovesAgo());

        p.setCurrentPosition(1);
        p.setCurrentPosition(3);
        p.setCurrentPosition(6);

        assertEquals(3, p.getPreviousPosition());
        assertEquals(1, p.getPositionTwoMovesAgo());
    }

    @Test
    void stuckChangesStatus() {
        Player p = new Player(1, "Ana", "", Color.BLUE);
        p.setStatus(PlayerStatus.IN_GAME);

        p.setStuck(true);
        assertTrue(p.isStuck());
        assertEquals(PlayerStatus.STUCK, p.getStatus());

        p.setStuck(false);
        assertFalse(p.isStuck());
        assertEquals(PlayerStatus.IN_GAME, p.getStatus());
    }

    @Test
    void toolsStringSortedAndHasToolWorks() {
        Player p = new Player(1, "Ana", "", Color.BLUE);
        p.setStatus(PlayerStatus.IN_GAME);

        Tool t1 = (Tool) BoardItemFactory.create(1, 0);
        Tool t2 = (Tool) BoardItemFactory.create(1, 2);

        assertNotNull(t1);
        assertNotNull(t2);

        p.addTool(t2);
        p.addTool(t1);

        BoardItem t1asItem = BoardItemFactory.create(1, 0);
        assertTrue(p.hasTool(t1asItem));

        String tools = p.getToolsAsStr();
        assertTrue(tools.contains(t1.getName()));
        assertTrue(tools.contains(t2.getName()));
    }

    @Test
    void incrementTurnsPlayed_increasesCounter() {
        Player p = new Player(1, "Ana", "", Color.BLUE);
        assertEquals(0, p.getTurnsPlayed());
        p.incrementTurnsPlayed();
        assertEquals(1, p.getTurnsPlayed());
    }
}
