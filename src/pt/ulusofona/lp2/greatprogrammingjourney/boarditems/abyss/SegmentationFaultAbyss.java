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
        int newPosition = player.getCurrentPosition() - 3;

        if (newPosition < 1) {
            newPosition = 1;
        }

        player.setCurrentPosition(newPosition);

        return "Todos os programadores na casa sofreram um Segmentation Fault e recuaram 3 casas.";
    }

    @Override
    public boolean affectsAllPlayersInSlot() {
        return true;
    }
}
