package pt.ulusofona.lp2.greatprogrammingjourney;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.Color;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerStatus;

import javax.swing.*;
import java.util.*;

public class GameManager {
    /* fields */
    private Board board;
    private ArrayList<Player> players = new ArrayList<>();
    private TurnManager turnManager;
    private boolean gameIsOver;
    private Player winner;
    private int currentTurn;

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

        ArrayList<Player> listOfPlayers = new ArrayList<>();
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

    public String getImagePng(int nrSquare){
        if (board == null || nrSquare < 1 || nrSquare > board.getSize()) {
            return null;
        }

        if (nrSquare == board.getSize()) {
            return "glory.png";
        }

        return null;
    }

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

        ArrayList<String> langs = new ArrayList<>(player.getFavoriteLanguages());
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

    public String[] getSlotInfo(int position){
        if (board == null || position < 1 || position > board.getSize()) {
            return null;
        }

        StringBuilder listOfPlayerIds = new StringBuilder();

        for (Player player : players) {
            if (player.getCurrentPosition() == position) {
                if (listOfPlayerIds.length() > 0) {
                    listOfPlayerIds.append(",");
                }
                listOfPlayerIds.append(player.getId());
            }
        }

        String result = listOfPlayerIds.length() > 0 ? listOfPlayerIds.toString() : "";

        return new String[]{result};
    }

    public int getCurrentPlayerID(){
        return turnManager.getCurrentPlayerID();
    }

    public boolean moveCurrentPlayer(int nrSpaces){
        if (nrSpaces < 1 || nrSpaces > 6) {
            return false;
        }
        if (board == null || turnManager == null) {
            return false;
        }

        Player currentPlayer = turnManager.getCurrentPlayer();
        int currentPosition = currentPlayer.getCurrentPosition();

        int newPosition = currentPosition + nrSpaces;
        if (newPosition > board.getSize()) {
            newPosition = newPosition - board.getSize();
        }

        currentPlayer.setCurrentPosition(newPosition);

        currentTurn++;

        turnManager.nextTurn();

        return true;
    }

    public boolean gameIsOver(){
        if (board == null) {
            return false;
        }

        int lastPosition = board.getSize();

        for (Player player : players) {
            if (player.getCurrentPosition() == lastPosition) {
                winner = player;
                gameIsOver = true;
                return true;
            }
        }

        return false;
    }

    public ArrayList<String> getGameResults(){
        ArrayList<String> results = new ArrayList<>();

        if (!gameIsOver || winner == null) {
            return results;
        }

        results.add("THE GREAT PROGRAMMING JOURNEY");
        results.add("");
        results.add("NR. DE TURNOS");
        results.add(String.valueOf(currentTurn));
        results.add("");
        results.add("VENCEDOR");
        results.add(winner.getName());
        results.add("");
        results.add("RESTANTES");

        int lastPosition = board.getSize();

        ArrayList<Player> remainingPlayers = new ArrayList<>(players);
        remainingPlayers.remove(winner);

        /* bubble sort - sort by closest to the finish */
        for (int i = 0; i < remainingPlayers.size() - 1; i++) {
            for (int j = 0; j < remainingPlayers.size() - 1 - i; j++) {
                Player p1 = remainingPlayers.get(j);
                Player p2 = remainingPlayers.get(j + 1);

                if (p1.getCurrentPosition() < p2.getCurrentPosition()) {
                    remainingPlayers.set(j, p2);
                    remainingPlayers.set(j + 1, p1);
                }
            }
        }

        for (Player player : remainingPlayers) {
            results.add(player.getName() + " " + player.getCurrentPosition());
        }

        return results;
    }

    public JPanel getAuthorsPanel(){
        return null;
    }

    public HashMap<String, String> customizeBoard(){
        return new HashMap<>();
    }
}