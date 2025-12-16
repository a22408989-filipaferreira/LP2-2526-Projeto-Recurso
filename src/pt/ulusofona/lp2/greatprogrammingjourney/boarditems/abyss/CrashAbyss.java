package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class CrashAbyss extends Abyss {
    /* constructor */
    public CrashAbyss() {
        super(4, "Crash");
    }

    /* method */
    @Override
    public String react(Player player) {
        player.setCurrentPosition(1);
        return "Caiu em Crash! Voltou à primeira casa.";
    }
}