package pt.ulusofona.lp2.greatprogrammingjourney;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.Color;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerStatus;

import java.util.ArrayList;
import java.util.List;

public class Player {
    /* fields */
    private int id;
    private String name;
    private ArrayList<String> favoriteLanguages = new ArrayList<>();
    private Color avatarColor;
    private String avatarImageFileName;
    private int currentPosition;
    private PlayerStatus status;

    /* constructor */
    public Player(int id, String name, String languagesStr, Color color){
        this.id = id;
        this.name = name;
        this.avatarColor = color;

        if (languagesStr != null && !languagesStr.isEmpty()) {
            String[] languages = languagesStr.split(";");
            for (String l : languages) {
                this.favoriteLanguages.add(l.trim());
            }
        }

        switch (color) {
            case BLUE:
                this.avatarImageFileName = "playerBlue.png";
                break;
            case BROWN:
                this.avatarImageFileName = "playerBrown.png";
                break;
            case GREEN:
                this.avatarImageFileName = "playerGreen.png";
                break;
            case PURPLE:
                this.avatarImageFileName = "playerPurple.png";
                break;
            default:
                break;
        }
    }

    /* getters */
    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public ArrayList<String> getFavoriteLanguages(){
        return favoriteLanguages;
    }

    public Color getAvatarColor(){
        return avatarColor;
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    public PlayerStatus getStatus(){
        return status;
    }

    public String getAvatarImageFileName() {
        return avatarImageFileName;
    }

    /* setters */
    public void setCurrentPosition(int position){
        this.currentPosition = position;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }
}