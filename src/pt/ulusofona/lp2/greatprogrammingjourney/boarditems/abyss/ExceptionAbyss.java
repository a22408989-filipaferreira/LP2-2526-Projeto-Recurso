package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class ExceptionAbyss extends Abyss {
    /* constructor */
    public ExceptionAbyss() {
        super(2, "Exception");
    }

    /* method */
    @Override
    public String react(Player player, int currentTurn) {
        int newPosition = player.getCurrentPosition() - 2;

        if (newPosition < 1) {
            newPosition = 1;
        }

        player.setCurrentPosition(newPosition);

        return "Caiu numa Exception! Recuou 2 casas.";
    }
}