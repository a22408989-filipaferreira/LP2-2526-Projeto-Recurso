package pt.ulusofona.lp2.greatprogrammingjourney;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.Color;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerStatus;

import javax.swing.*;
import java.util.*;

public class GameManager {
    /* fields */
    private Board board;
    private List<Player> players = new ArrayList<>();
    private TurnManager turnManager;
    private boolean gameIsOver;
    private Player winner;
    private int currentTurn;

    /* getters */
    public String getWinnerName() {
        if (winner == null) {
            return null;
        }
        return winner.getName();
    }


    /* constructor */
    public GameManager() {
        this.board = null;
        this.turnManager = null;
        this.gameIsOver = false;
        this.winner = null;
        this.currentTurn = 0;
    }

    /* methods */
    public boolean createInitialBoard(String[][] playerInfo, int boardSize) {
        if (playerInfo == null || playerInfo.length == 0 || boardSize <= 0) {
            return false;
        }

        int numPlayers = playerInfo.length;
        if (numPlayers < 2 || numPlayers > 4) {
            return false;
        }
        if (boardSize < numPlayers * 2) {
            return false;
        }

        this.board = new Board(boardSize);

        List<Player> listOfPlayers = new ArrayList<>();
        Set<Integer> playerIds = new HashSet<>();
        Set<Color> colorsUsed = new HashSet<>();

        for (int i = 0; i < numPlayers; i++) {
            String[] data = playerInfo[i];
            if (data == null || data.length < 4) {
                return false;
            }

            int id;
            try {
                id = Integer.parseInt(data[0]);
            } catch (NumberFormatException e) {
                return false;
            }

            if (id <= 0 || playerIds.contains(id)) {
                return false;
            }
            playerIds.add(id);

            String name = data[1];
            if (name == null || name.isBlank()) {
                return false;
            }

            String languagesStr = data[2];
            String colorStr = data[3];

            Color avatarColor = Color.fromString(colorStr);
            if (avatarColor == null || colorsUsed.contains(avatarColor)) {
                return false;
            }
            colorsUsed.add(avatarColor);

            Player player = new Player(id, name, languagesStr, avatarColor);
            player.setCurrentPosition(1);
            player.setStatus(PlayerStatus.IN_GAME);

            listOfPlayers.add(player);
        }

        this.players = listOfPlayers;
        this.turnManager = new TurnManager(players);

        this.currentTurn = 1;
        this.gameIsOver = false;
        this.winner = null;

        return true;
    }

    //public String getImagePng(int nrSquare){}

    public String[] getProgrammerInfo(int id) {
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

    public String getProgrammerInfoAsStr(int id) {
        String[] info = getProgrammerInfo(id);

        if (info == null) {
            return null;
        }

        return String.join(" | ", info);
    }


    //public String[] getSlotInfo(int position){}
    //public int getCurrentPlayerID(){}
    //public boolean moveCurrentPlayer(int nrSpaces){}
    //public boolean gameIsOver(){}
    //public ArrayList<String> getGameResults(){}
    //public JPanel getAuthorsPanel(){}
    //public HashMap<String, String> customizeBoard(){}
}