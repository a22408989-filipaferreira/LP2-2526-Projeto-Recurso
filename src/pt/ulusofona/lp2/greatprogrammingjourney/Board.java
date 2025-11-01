package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.ArrayList;

public class Board {
    ArrayList<Slot> slots;


    public Board() {
        this.slots = new ArrayList<>();
    }

    public void createSlots(int numberOfSlots) {
        for (int slotNumber = 0; slotNumber < numberOfSlots; slotNumber++) {
            slots.add(new Slot(slotNumber));
        }
    }

    public Slot getSlot(int position) {
        if (position >= 0 && position < slots.size()) {
            return slots.get(position);
        }
        return null;
    }

    public boolean slotisOccupied(int position) {
        Slot slot = getSlot(position);
        return slot != null && slot.isOccupied();
    }


    public void setPlayerInSlot(Player player, int position) {
        Slot slot = getSlot(position);
        if (slot != null) {
            slot.setOcupant(player);
        }
    }
}