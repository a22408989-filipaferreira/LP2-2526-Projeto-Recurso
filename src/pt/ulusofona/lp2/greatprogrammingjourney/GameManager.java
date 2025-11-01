package pt.ulusofona.lp2.greatprogrammingjourney;

import javax.swing.*;
import java.util.ArrayList;
//import java.util.HashMap;

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


    //public boolean createInitialBoard(String[][] playerInfo, int worldSize){}
    //public String getImagePng(int nrSquare){}
    //public String[] getProgrammerInfo(int id){}
    //public String getProgrammerInfoAsStr(int id){}
    //public String[] getSlotInfo(int position){}
    //public int getCurrentPlayerID(){}
    //public boolean moveCurrentPlayer(int nrSpaces){}
    //public boolean gameIsOver(){}
    //public ArrayList<String> getGameResults(){}
    //public JPanel getAuthorsPanel(){}
    //public HashMap<String, String> customizeBoard(){}
}