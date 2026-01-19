package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class FileNotFoundExceptionAbyss extends Abyss {
    /* constructor */
    public FileNotFoundExceptionAbyss() {
        super(3, "FileNotFoundException");
    }

    /* method */
    @Override
    public String react(Player player, int currentTurn) {
        int newPosition = player.getCurrentPosition() - 3;

        if (newPosition < 1) {
            newPosition = 1;
        }

        player.setCurrentPosition(newPosition);

        return "Caiu num FileNotFoundException! Recuou 3 casas.";
    }

}