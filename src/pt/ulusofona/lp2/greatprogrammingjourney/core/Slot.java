package pt.ulusofona.lp2.greatprogrammingjourney.core;

import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class Slot {
    /* fields */
    private int index;
    private String imageFileName;
    private BoardItem item;

    /* constructor */
    public Slot(int index) {
        this.index = index;
        this.imageFileName = null;
        this.item = null;
    }

    /* getters */
    public int getIndex() {
        return index;
    }

    public BoardItem getItem() {
        return item;
    }

    /* setters */
    public void setItem(BoardItem item) {
        this.item = item;
    }

//    public String react(Player player, int currentTurn) {
//        if (item == null) return "";
//        return item.react(player, currentTurn);
//    }
}
