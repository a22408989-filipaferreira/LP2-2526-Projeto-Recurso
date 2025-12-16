package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool;

import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class FunctionalProgrammingTool extends Tool {
    /* constructor */
    public FunctionalProgrammingTool() {
        super(1, "Programação Funcional");
    }

    /* method */
    @Override
    public String react(Player player) {
        player.addTool(this);
        return "Recebeu a ferramenta Programação Funcional.";
    }

    @Override
    public boolean cancels(BoardItem item) {
        return item.getId() == 5   /* Duplicate Code abyss */
                || item.getId() == 6   /* Side Effects abyss */
                || item.getId() == 8;  /* Infinite Loop abyss */
    }
}
