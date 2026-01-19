package pt.ulusofona.lp2.greatprogrammingjourney.core;

import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public abstract class GameResult {
    /* methods */
    public abstract boolean isTie();
    public abstract Player getWinner();
    public abstract String getEndMessage();
}