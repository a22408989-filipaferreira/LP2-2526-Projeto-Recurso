package pt.ulusofona.lp2.greatprogrammingjourney.core;

import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class WinResult extends GameResult {
    /* fields */
    private final Player winner;

    /* constructor */
    public WinResult(Player winner) {
        this.winner = winner;
    }

    /* methods */

    @Override
    public boolean isTie() {
        return false;
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    @Override
    public String getEndMessage() {
        return null;
    }
}