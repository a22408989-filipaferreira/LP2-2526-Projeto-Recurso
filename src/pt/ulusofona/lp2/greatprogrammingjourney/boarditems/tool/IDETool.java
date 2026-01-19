package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool;

import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class IDETool extends Tool {
    /* constructor */
    public IDETool() {
        super(4, "IDE");
    }

    /* method */
    @Override
    public String react(Player player, int currentTurn) {
        player.addTool(this);
        return "Recebeu a ferramenta IDE.";
    }

    @Override
    public boolean cancels(BoardItem item) {
        return item.getId() == 0   /* Syntax Error abyss */
                || item.getId() == 3; /* FileNotFoundException abyss */
    }
}
