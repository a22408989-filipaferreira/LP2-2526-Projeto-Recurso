package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.core.TurnManager;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.Color;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerStatus;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestTurnManager {
    @Test
    void nextTurn_rotatesPlayers() {
        Player p1 = new Player(1, "A", "", Color.BLUE);
        p1.setStatus(PlayerStatus.IN_GAME);

        Player p2 = new Player(2, "B", "", Color.GREEN);
        p2.setStatus(PlayerStatus.IN_GAME);

        ArrayList<Player> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);

        TurnManager tm = new TurnManager(list);

        assertEquals(1, tm.getCurrentPlayerID());
        tm.nextTurn();
        assertEquals(2, tm.getCurrentPlayerID());
        tm.nextTurn();
        assertEquals(1, tm.getCurrentPlayerID());
    }

    @Test
    void nextTurn_skipsDefeatedPlayers() {
        Player p1 = new Player(1, "A", "", Color.BLUE);
        p1.setStatus(PlayerStatus.DEFEATED);

        Player p2 = new Player(2, "B", "", Color.GREEN);
        p2.setStatus(PlayerStatus.IN_GAME);

        Player p3 = new Player(3, "C", "", Color.BROWN);
        p3.setStatus(PlayerStatus.IN_GAME);

        ArrayList<Player> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);

        TurnManager tm = new TurnManager(list);

        tm.nextTurn();
        assertEquals(2, tm.getCurrentPlayerID());

        tm.nextTurn();
        assertEquals(3, tm.getCurrentPlayerID());
    }
}
