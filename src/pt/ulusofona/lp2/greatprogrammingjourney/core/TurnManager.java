package pt.ulusofona.lp2.greatprogrammingjourney.core;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerStatus;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TurnManager {
    private Queue<Player> playerOrder;
    private Player currentPlayer;

    public TurnManager(ArrayList<Player> players) {
        this.playerOrder = new LinkedList<>(players);
        this.currentPlayer = playerOrder.peek();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getCurrentPlayerID() {
        return currentPlayer == null ? -1 : currentPlayer.getId();
    }

    public void nextTurn() {
        if (playerOrder.isEmpty()) {
            currentPlayer = null;
            return;
        }

        int tries = playerOrder.size();

        while (tries > 0) {
            playerOrder.add(playerOrder.poll());
            currentPlayer = playerOrder.peek();

            if (currentPlayer != null && currentPlayer.getStatus() != PlayerStatus.DEFEATED) {
                return;
            }

            tries--;
        }

        currentPlayer = null;
    }
}