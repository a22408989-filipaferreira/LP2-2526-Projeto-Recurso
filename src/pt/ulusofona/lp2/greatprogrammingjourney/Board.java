package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;
import java.util.List;

public class Board {
    /* fields */
    private List<Slot> slots;
    private int size;

    /* getters */
    public int getSize() {
        return size;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    /* constructor */
    public Board(int size) {
        this.size = size;
        this.slots = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            this.slots.add(new Slot(i));
        }
    }

    /* methods */
    public Slot getSlot(int index) {
        if (index < 1 || index > size){
            return null;
        }
        return slots.get(index - 1);
    }
}