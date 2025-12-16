package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool;

import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class InheritanceTool extends Tool {
    /* constructor */
    public InheritanceTool() {
        super(0, "Herança");
    }

    /* method */
    @Override
    public String react(Player player) {
        return null;
    }

    @Override
    public boolean cancels(BoardItem item) {
        return false;
    }
}