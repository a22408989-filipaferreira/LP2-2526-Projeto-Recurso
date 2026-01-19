//package pt.ulusofona.lp2.greatprogrammingjourney.tests;
//
//import org.junit.jupiter.api.Test;
//import pt.ulusofona.lp2.greatprogrammingjourney.core.TurnManager;
//import pt.ulusofona.lp2.greatprogrammingjourney.enums.Color;
//import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.assertEquals;
//
//public class TestTurnManager {
//    private Player createPlayer(int id, String name) {
//        return new Player(id, name, "", Color.BLUE);
//    }
//
//    @Test
//    void testConstructorSetsFirstPlayer() {
//        ArrayList<Player> players = new ArrayList<>();
//        Player p1 = createPlayer(1, "Ana");
//        Player p2 = createPlayer(2, "Bruno");
//
//        players.add(p1);
//        players.add(p2);
//
//        TurnManager tm = new TurnManager(players);
//
//        assertEquals(p1, tm.getCurrentPlayer());
//        assertEquals(1, tm.getCurrentPlayerID());
//    }
//
//    @Test
//    void testNextTurnChangesPlayer() {
//        ArrayList<Player> players = new ArrayList<>();
//        Player p1 = createPlayer(1, "Ana");
//        Player p2 = createPlayer(2, "Bruno");
//
//        players.add(p1);
//        players.add(p2);
//
//        TurnManager tm = new TurnManager(players);
//
//        tm.nextTurn();
//
//        assertEquals(p2, tm.getCurrentPlayer());
//        assertEquals(2, tm.getCurrentPlayerID());
//    }
//
//    @Test
//    void testNextTurnCyclesBackToFirstPlayer() {
//        ArrayList<Player> players = new ArrayList<>();
//        Player p1 = createPlayer(1, "Ana");
//        Player p2 = createPlayer(2, "Bruno");
//        Player p3 = createPlayer(3, "Carla");
//
//        players.add(p1);
//        players.add(p2);
//        players.add(p3);
//
//        TurnManager tm = new TurnManager(players);
//
//        tm.nextTurn();
//        tm.nextTurn();
//        tm.nextTurn();
//
//        assertEquals(p1, tm.getCurrentPlayer());
//    }
//}
