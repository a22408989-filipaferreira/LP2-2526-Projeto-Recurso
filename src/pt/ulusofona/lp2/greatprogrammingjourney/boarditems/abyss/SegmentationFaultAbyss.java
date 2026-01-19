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
        int prev = player.getPreviousPosition();
        player.setCurrentPosition(prev);
        return "Todos os programadores na casa sofreram um Segmentation Fault e recuaram para a posição anterior.";
    }

    @Override
    public boolean affectsAllPlayersInSlot() {
        return true;
    }
}