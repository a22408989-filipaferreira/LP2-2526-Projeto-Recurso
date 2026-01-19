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
    public String react(Player player, int currentTurn) {
        player.addTool(this);
        return "Recebeu a ferramenta Herança";
    }

    @Override
    public boolean cancels(BoardItem item) {
        return item.getName().equals("Herança");
    }
}