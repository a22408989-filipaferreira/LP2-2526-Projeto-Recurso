package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerStatus;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class BlueScreenOfDeathAbyss extends Abyss {
    /* constructor */
    public BlueScreenOfDeathAbyss() {
        super(7, "Blue Screen of Death");
    }

    /* method */
    @Override
    public String react(Player player, int currentTurn) {
        player.setStatus(PlayerStatus.DEFEATED);
        return "Caiu em Blue Screen of Death! Perdeu o jogo.";
    }
}