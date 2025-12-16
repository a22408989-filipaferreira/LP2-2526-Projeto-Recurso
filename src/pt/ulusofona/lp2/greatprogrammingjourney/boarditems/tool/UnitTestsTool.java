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
    public String react(Player player) {
        return null;
    }

    @Override
    public boolean cancels(BoardItem item) {
        return false;
    }
}