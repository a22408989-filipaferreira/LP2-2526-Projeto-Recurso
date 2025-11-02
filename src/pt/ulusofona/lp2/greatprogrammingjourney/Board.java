package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.List;

public class Board {
    /* fields */
    private ArrayList<Slot> slots;
    private int size;

    /* constructor */
    public Board(int size) {
        this.size = size;
        this.slots = new ArrayList<>();

        for (int position = 1; position <= size; position++) {
            slots.add(new Slot(position));
        }
    }

    /* getters */
    public int getSize() {
        return size;
    }
}