package pt.ulusofona.lp2.greatprogrammingjourney.core;

import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class TieResult extends GameResult {
    /* constructor */
    public TieResult() {
    }

    /* methods */

    @Override
    public boolean isTie() {
        return true;
    }

    @Override
    public Player getWinner() {
        return null;
    }

    @Override
    public String getEndMessage() {
        return "O jogo terminou empatado.";
    }
}