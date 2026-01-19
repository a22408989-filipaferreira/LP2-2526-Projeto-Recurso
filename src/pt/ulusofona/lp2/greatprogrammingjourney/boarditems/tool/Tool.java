package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool;

import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public abstract class Tool extends BoardItem {
    /* constructor */
    public Tool(int id, String name) {
        super(id, name); /* using the constructor from its parent class (BoardItem) */
    }

    /* methods */

    /* signature of a method that reacts - according to the abyss type */
    @Override
    public abstract String react(Player player, int currentTurn);

    public abstract boolean cancels(BoardItem item);

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public boolean isCollectable() {
        return true;
    }

    @Override
    public Tool asTool() {
        return this;
    }
}