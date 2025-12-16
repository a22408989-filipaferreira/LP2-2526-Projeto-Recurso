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
    public String react(Player player) {
        return null;
    }

    @Override
    public boolean cancels(BoardItem item) {
        return false;
    }
}