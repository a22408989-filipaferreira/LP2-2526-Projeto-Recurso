package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool;

import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class UnitTestsTool extends Tool {
    /* constructor */
    public UnitTestsTool() {
        super(2, "Testes Unitários");
    }

    /* method */
    @Override
    public String react(Player player, int currentTurn) {
        return null;
    }

    @Override
    public boolean cancels(BoardItem item) {
        int id = item.getId();
        return id == 1  /* Logic Error abyss */
                || id == 4 /* Crash abyss */
                || id == 9; /* Segmentation Fault Abyss */
    }
}