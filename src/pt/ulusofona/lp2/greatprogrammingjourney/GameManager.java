package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GameManager {
    Board board;
    ArrayList<Player>players;
    int currentPlayerIndex;
    int turnNumber;

    public GameManager(){
        this.board = new Board();
        this.players = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.turnNumber = 1;
    }


    public void startGame(){}


    public void nextTurn(){
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        turnNumber ++;
    }

    public void movePlayer(int numberOfSlots){}


    public boolean checkIfGameIsOver(){
        return false;
    }


    public String[] getProgrammerInfo(int id){
        Player player = null;
        for (Player p : players) {
            if (p.getId() == id) {
                player = p;
                break;
            }
        }
        if (player == null) {
            return null;
        }

        List<String> langs = new ArrayList<>(player.getFavoriteLanguages());
        Collections.sort(langs, String.CASE_INSENSITIVE_ORDER);

        String langsStr = String.join("; ", langs);

        String[] info = new String[5];
        info[0] = String.valueOf(player.getId());
        info[1] = player.getName();
        info[2] = String.valueOf(player.getCurrentPosition());
        info[3] = langsStr;
        info[4] = player.getStatus().getDisplayName();

        return info;
    }


    public String getProgrammerInfoAsStr(int id){
        String[] info = getProgrammerInfo(id);

        if (info == null) {
            return null;
        }

        return String.join(" | ", info);
    }
    //public boolean createInitialBoard(String[][] playerInfo, int worldSize){}
    //public String getImagePng(int nrSquare){}
    //public String[] getSlotInfo(int position){}
    //public int getCurrentPlayerID(){}
    //public boolean moveCurrentPlayer(int nrSpaces){}
    //public boolean gameIsOver(){}
    //public ArrayList<String> getGameResults(){}
    //public JPanel getAuthorsPanel(){}
    //public HashMap<String, String> customizeBoard(){}
}