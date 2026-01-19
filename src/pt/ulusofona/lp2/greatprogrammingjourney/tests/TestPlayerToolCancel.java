package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.Color;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerStatus;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

import static org.junit.Assert.*;

public class TestPlayerToolCancel {
    private static class CancelsByIdTool extends Tool {
        private final int cancelId;
        public CancelsByIdTool(int cancelId) {
            super(999, "FakeTool");
            this.cancelId = cancelId;
        }
        @Override
        public String react(Player player, int currentTurn) { player.addTool(this); return "ok"; }
        @Override
        public boolean cancels(BoardItem item) { return item != null && item.getId() == cancelId; }
    }

    @Test
    void hasToolThatCancels_returnsTrue_whenToolExists() {
        Player p = new Player(1, "Ana", "", Color.BLUE);
        p.setStatus(PlayerStatus.IN_GAME);

        p.addTool(new CancelsByIdTool(9)); // cancela SegFault

        BoardItem segFault = new BoardItem(9, "Segmentation Fault") {
            @Override public String react(Player player, int currentTurn) { return null; }
            @Override public int getType() { return 0; }
        };

        assertTrue(p.hasToolThatCancels(segFault));
    }

    @Test
    void consumeToolThatCancels_removesFirstMatchingTool() {
        Player p = new Player(1, "Ana", "", Color.BLUE);
        p.setStatus(PlayerStatus.IN_GAME);

        p.addTool(new CancelsByIdTool(1));
        p.addTool(new CancelsByIdTool(9)); // queremos consumir esta

        BoardItem segFault = new BoardItem(9, "Segmentation Fault") {
            @Override public String react(Player player, int currentTurn) { return null; }
            @Override public int getType() { return 0; }
        };

        assertTrue(p.hasToolThatCancels(segFault));

        p.consumeToolThatCancels(segFault);

        assertFalse(p.hasToolThatCancels(segFault));
        assertNotEquals("No tools", p.getToolsAsStr());
    }

    @Test
    void consumeToolThatCancels_whenLastToolRemoved_toolsAsStrIsNoTools() {
        Player p = new Player(1, "Ana", "", Color.BLUE);
        p.setStatus(PlayerStatus.IN_GAME);

        p.addTool(new CancelsByIdTool(9));

        BoardItem segFault = new BoardItem(9, "Segmentation Fault") {
            @Override public String react(Player player, int currentTurn) { return null; }
            @Override public int getType() { return 0; }
        };

        p.consumeToolThatCancels(segFault);

        assertEquals("No tools", p.getToolsAsStr());
    }
}
