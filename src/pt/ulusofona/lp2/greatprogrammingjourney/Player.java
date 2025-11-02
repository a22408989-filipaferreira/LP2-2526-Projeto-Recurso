package pt.ulusofona.lp2.greatprogrammingjourney;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.Color;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerStatus;

public class Player extends Programmer{
    /* fields */
    private int currentPosition;
    private PlayerStatus status;

    public Player(int id, String name, String languagesStr, Color color){
        super(id, name, languagesStr, color);
    }

    /* getters */
    public int getCurrentPosition(){
        return currentPosition;
    }

    public PlayerStatus getStatus(){
        return status;
    }

    /* setters */
    public void setCurrentPosition(int position){
        this.currentPosition = position;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }
}