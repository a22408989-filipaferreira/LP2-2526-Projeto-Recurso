package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public abstract class Abyss extends BoardItem {
    /* constructor */
    public Abyss(int id, String name) {
        super(id, name); /* using the constructor from its parent class (BoardItem) */
    }

    /* methods */

    /* signature of a method that reacts - according to the abyss type */
    @Override
    public abstract String react(Player player, int currentTurn);

    @Override
    public int getType() {
        return 0;
    }
}