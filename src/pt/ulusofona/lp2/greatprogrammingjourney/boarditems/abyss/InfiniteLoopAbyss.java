package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class InfiniteLoopAbyss extends Abyss {
    /* constructor */
    public InfiniteLoopAbyss() {
        super(8, "Ciclo Infinito");
    }

    /* method */
    @Override
    public String react(Player player, int currentTurn) {
        player.setStuck(true);
        return "Caiu num ciclo infinito! Ficou preso na casa.";
    }

    @Override
    public boolean swapsStuckPlayer() {
        return true;
    }
}