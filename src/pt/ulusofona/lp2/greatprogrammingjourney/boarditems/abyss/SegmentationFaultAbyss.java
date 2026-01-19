package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class SegmentationFaultAbyss extends Abyss {
    /* constructor */
    public SegmentationFaultAbyss() {
        super(9, "Segmentation Fault");
    }

    /* method */
    @Override
    public String react(Player player, int currentTurn) {
        return "Nada aconteceu.";
    }

    @Override
    public boolean affectsAllPlayersInSlot() {
        return false;
    }
}