package pt.ulusofona.lp2.greatprogrammingjourney.boarditems;

import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public abstract class BoardItem {
    /* fields */
    protected int id;
    protected String name;

    /* constructor */
    public BoardItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /* getters */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /* methods */

    /* signature of a method that reacts - according to the boardItem type */
    public abstract String react(Player player);

    public boolean affectsAllPlayersInSlot() {
        return false;
    }
}