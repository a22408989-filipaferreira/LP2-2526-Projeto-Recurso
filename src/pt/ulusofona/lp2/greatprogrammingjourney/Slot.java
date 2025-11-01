package pt.ulusofona.lp2.greatprogrammingjourney;

public class Slot {
    int number;
    Player ocupant;


    public Slot(int number){
        this.number = number;
        this.ocupant = null;
    }

    public boolean isOccupied (){
        return ocupant != null;
    }

    public Player getOcupant(){
        return ocupant;
    }

    public void setOcupant(Player player){
        this.ocupant = player;
    }

    public int getNumber(){
        return number;
    }
}
