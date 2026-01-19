package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool;

import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class ProfessorHelpTool extends Tool {
    /* constructor */
    public ProfessorHelpTool() {
        super(5, "Ajuda do Professor");
    }

    /* method */
    @Override
    public String react(Player player, int currentTurn) {
        return "Recebeu a ferramenta Ajuda do Professor";
    }

    @Override
    public boolean cancels(BoardItem item) {
        return item.getId() == 1   /* Logic Error abyss */
                || item.getId() == 0   /* Syntax Error abyss */
                || item.getId() == 9 /* Segmentation Fault abyss */
                || item.getId() == 20; /* LLM abyss */
    }
}