package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class SyntaxErrorAbyss extends Abyss {
    /* constructor */
    public SyntaxErrorAbyss() {
        super(0, "Erro de sintaxe");
    }

    /* method */
    @Override
    public String react(Player player) {
        int currentPosition = player.getCurrentPosition();
        int newPosition = currentPosition - 1;

        if (newPosition < 1) {
            newPosition = 1;
        }

        player.setCurrentPosition(newPosition);

        return "Caiu num erro de sintaxe! Recua 1 casa";
    }
}
