package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class SideEffectsAbyss extends Abyss {
    /* constructor */
    public SideEffectsAbyss() {
        super(6, "Efeitos Secundários");
    }

    /* method */
    @Override
    public String react(Player player, int currentTurn) {
        int targetPosition = player.getPositionTwoMovesAgo();
        player.setCurrentPosition(targetPosition);
        return "Caiu nos efeitos secundários! Recua para a posição onde estava há 2 movimentos atrás";
    }
}
