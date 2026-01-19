package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.abyss.*;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.Color;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerStatus;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

import static org.junit.Assert.*;

public class TestAbyssReact {
    private static class CancelsByIdTool extends Tool {
        private final int cancelId;
        public CancelsByIdTool(int cancelId) {
            super(999, "FakeTool");
            this.cancelId = cancelId;
        }
        @Override
        public String react(Player player, int currentTurn) {
            player.addTool(this);
            return "ok";
        }
        @Override
        public boolean cancels(pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem item) {
            return item != null && item.getId() == cancelId;
        }
    }

    private Player basePlayerAt(int pos) {
        Player p = new Player(1, "Ana", "", Color.BLUE);
        p.setStatus(PlayerStatus.IN_GAME);
        p.setCurrentPosition(pos);
        return p;
    }

    private Player basePlayerAt(int pos, int lastDice) {
        Player p = new Player(1, "Ana", "", Color.BLUE);
        p.setStatus(PlayerStatus.IN_GAME);
        p.setCurrentPosition(pos);
        p.setLastDiceValue(lastDice);
        return p;
    }

    @Test
    void logicErrorAbyss_retrocedesHalfDiceValue_clampedTo1() {
        LogicErrorAbyss abyss = new LogicErrorAbyss();

        Player p = basePlayerAt(2, 6); // retreat = 3 => ficaria -1 -> clamp para 1
        String msg = abyss.react(p, 1);

        assertEquals(1, p.getCurrentPosition());
        assertTrue(msg.contains("Recuou 3") || msg.contains("Recuou 3 casas"));
    }

    @Test
    void logicErrorAbyss_whenDiceTooSmall_noRetreat() {
        LogicErrorAbyss abyss = new LogicErrorAbyss();

        Player p = basePlayerAt(10, 1); // retreat = 0
        String msg = abyss.react(p, 1);

        assertEquals(10, p.getCurrentPosition());
        assertTrue(msg.toLowerCase().contains("nenhum recuo"));
    }

    @Test
    void exceptionAbyss_retrocedes2_clampedTo1() {
        ExceptionAbyss abyss = new ExceptionAbyss();

        Player p = basePlayerAt(1, 4);
        String msg = abyss.react(p, 1);

        assertEquals(1, p.getCurrentPosition());
        assertTrue(msg.contains("Recuou 2"));
    }

    @Test
    void fileNotFoundExceptionAbyss_retrocedes3() {
        FileNotFoundExceptionAbyss abyss = new FileNotFoundExceptionAbyss();

        Player p = basePlayerAt(10, 4);
        String msg = abyss.react(p, 1);

        assertEquals(7, p.getCurrentPosition());
        assertTrue(msg.contains("Recuou 3"));
    }

    @Test
    void duplicateCodeAbyss_goesBackToPreviousPosition() {
        DuplicateCodeAbyss abyss = new DuplicateCodeAbyss();

        Player p = basePlayerAt(1, 3);
        p.setCurrentPosition(4);
        p.setCurrentPosition(6);

        String msg = abyss.react(p, 1);

        assertEquals(4, p.getCurrentPosition());
        assertTrue(msg.contains("casa anterior") || msg.contains("anterior"));
    }

    @Test
    void crashAbyss_withoutTool_sendsPlayerToStart() {
        CrashAbyss abyss = new CrashAbyss();

        Player p = basePlayerAt(12, 3);
        String msg = abyss.react(p, 1);

        assertEquals(1, p.getCurrentPosition());
        assertTrue(msg.toLowerCase().contains("crash"));
    }

    @Test
    void crashAbyss_withCancelTool_doesNotMovePlayer() {
        CrashAbyss abyss = new CrashAbyss();

        Player p = basePlayerAt(12, 3);
        p.addTool(new CancelsByIdTool(4));

        String msg = abyss.react(p, 1);

        assertEquals(12, p.getCurrentPosition());
        assertTrue(msg.toLowerCase().contains("anulado"));
    }

    @Test
    void infiniteLoopAbyss_makesPlayerStuck_andStatusStuck() {
        InfiniteLoopAbyss abyss = new InfiniteLoopAbyss();

        Player p = basePlayerAt(5, 2);
        String msg = abyss.react(p, 1);

        assertTrue(p.isStuck());
        assertEquals(PlayerStatus.STUCK, p.getStatus());
        assertTrue(msg.toLowerCase().contains("ciclo"));
    }

    @Test
    void blueScreenOfDeathAbyss_defeatsPlayer() {
        BlueScreenOfDeathAbyss abyss = new BlueScreenOfDeathAbyss();

        Player p = basePlayerAt(5, 2);
        String msg = abyss.react(p, 1);

        assertEquals(PlayerStatus.DEFEATED, p.getStatus());
        assertTrue(msg.toLowerCase().contains("perdeu"));
    }

    @Test
    void segmentationFaultAbyss_doesNothing_specialFlags() {
        SegmentationFaultAbyss abyss = new SegmentationFaultAbyss();

        Player p = basePlayerAt(7, 3);
        String msg = abyss.react(p, 1);

        assertEquals(7, p.getCurrentPosition());
        assertEquals("Nada aconteceu.", msg);
        assertFalse(abyss.affectsAllPlayersInSlot());
    }

    @Test
    void llmAbyss_turnsUpTo3_movesBackByLastDice_ifNotCanceled() {
        LLMAbyss abyss = new LLMAbyss();

        Player p = basePlayerAt(10, 4);
        String msg = abyss.react(p, 2); // <=3

        assertEquals(6, p.getCurrentPosition());
        assertTrue(msg.toLowerCase().contains("recua"));
    }

    @Test
    void llmAbyss_turnsUpTo3_ifCanceled_consumesTool_andDoesNotMove() {
        LLMAbyss abyss = new LLMAbyss();

        Player p = basePlayerAt(10, 4);
        Tool canceller = new CancelsByIdTool(20);
        p.addTool(canceller);

        String msg = abyss.react(p, 2);

        assertEquals(10, p.getCurrentPosition());
        assertTrue(msg.toLowerCase().contains("anulado"));
        assertEquals("No tools", p.getToolsAsStr());
    }

    @Test
    void llmAbyss_afterTurn3_movesForwardByLastDice() {
        LLMAbyss abyss = new LLMAbyss();

        Player p = basePlayerAt(10, 4);
        String msg = abyss.react(p, 4);

        assertEquals(14, p.getCurrentPosition());
        assertTrue(msg.toLowerCase().contains("avança"));
    }

    @Test
    void syntaxErrorAbyss_movesBackOneSquare() {
        SyntaxErrorAbyss abyss = new SyntaxErrorAbyss();
        Player p = basePlayerAt(10);

        abyss.react(p, 1);

        assertEquals(9, p.getCurrentPosition());
    }

    @Test
    void syntaxErrorAbyss_clampsToOne() {
        SyntaxErrorAbyss abyss = new SyntaxErrorAbyss();
        Player p = basePlayerAt(1);

        abyss.react(p, 1);

        assertEquals(1, p.getCurrentPosition());
    }

    @Test
    void sideEffectsAbyss_returnsToPositionTwoMovesAgo() {
        SideEffectsAbyss abyss = new SideEffectsAbyss();
        Player p = basePlayerAt(1);

        p.setCurrentPosition(4);
        p.setCurrentPosition(7);

        abyss.react(p, 1);

        assertEquals(1, p.getCurrentPosition());
    }
}
