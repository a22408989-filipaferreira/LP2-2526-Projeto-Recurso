package pt.ulusofona.lp2.greatprogrammingjourney;

public class Player {
    int id;
    String name;
    String color;
    int currentPosition;
    //String image;

    public Player(int id, String name, String color, int currentPosition, String image){
        this.id = id;
        this.name = name;
        this.color = color;
        this.currentPosition = currentPosition;
        //this.image = image;
    }


    public int getId() {
        return id;
    }


    public String getName(){
        return name;
    }


    public String getColor(){
        return color;
    }


    public int currentPosition(){
        return currentPosition;
    }


    public void setCurrentPosition(int position){
        this.currentPosition = position;
    }


    @Override
    public String toString(){
        return  id + " - " + name + color + currentPosition;
    }

}
