package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class DuplicateCodeAbyss extends Abyss {
    /* constructor */
    public DuplicateCodeAbyss() {
        super(5, "Código Duplicado");
    }

    /* method */
    @Override
    public String react(Player player, int currentTurn) {
        int previousPosition = player.getPreviousPosition();
        player.setCurrentPosition(previousPosition);

        return "Caiu em Código Duplicado! Recuou para a casa anterior.";
    }
}
