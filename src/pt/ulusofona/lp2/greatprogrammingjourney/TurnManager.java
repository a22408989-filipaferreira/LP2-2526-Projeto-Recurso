package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TurnManager {
    /* fields */
    private Queue<Player> playerOrder;
    private Player currentPlayer;

    /* getters */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /* constructor */
    public TurnManager(List<Player> players) {
        this.playerOrder = new LinkedList<>(players);
        this.currentPlayer = playerOrder.peek();
    }

    /* methods */
    public int getCurrentPlayerID() {
        return currentPlayer.getId();
    }

    public void nextTurn() {
        if (!playerOrder.isEmpty()) {
            playerOrder.add(playerOrder.poll());
            currentPlayer = playerOrder.peek();
        }
    }
}
