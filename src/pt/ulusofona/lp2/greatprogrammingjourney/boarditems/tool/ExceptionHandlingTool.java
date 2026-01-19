package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool;

import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class ExceptionHandlingTool extends Tool {
    /* constructor */
    public ExceptionHandlingTool() {
        super(3, "Tratamento de Excepções");
    }

    /* method */
    @Override
    public String react(Player player, int currentTurn) {
        player.addTool(this);
        return "Recebeu a ferramenta Tratamento de Exceções.";
    }

    @Override
    public boolean cancels(BoardItem item) {
        return item.getId() == 2   /* Exception abyss */
                || item.getId() == 3;  /* FileNotFoundException abyss */
    }
}
