package pt.ulusofona.lp2.greatprogrammingjourney;

import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItemFactory;
import pt.ulusofona.lp2.greatprogrammingjourney.core.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.core.Slot;
import pt.ulusofona.lp2.greatprogrammingjourney.core.TurnManager;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.Color;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerStatus;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
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

    public boolean createInitialBoard(String[][] playerInfo, int boardSize, String[][] abyssesAndTools) {
        if (!createInitialBoard(playerInfo, boardSize)) {
            return false;
        }

        if (abyssesAndTools == null) {
            return true;
        }

        for (String[] slotInfo  : abyssesAndTools) {
            if (slotInfo == null || slotInfo.length < 3) {
                return false;
            }

            int position;
            int id;
            String type;

            try {
                position = Integer.parseInt(slotInfo[0]);
                type = slotInfo[1];
                id = Integer.parseInt(slotInfo[2]);
            } catch (NumberFormatException e) {
                return false;
            }

            if (position < 1 || position > board.getSize()) {
                return false;
            }

            if (!type.equals("A") && !type.equals("T")) {
                return false;
            }

            BoardItem item = BoardItemFactory.create(type, id);
            if (item == null) {
                return false;
            }

            Slot slot = board.getSlot(position);
            slot.setItem(item);
        }

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

        return player.getInfoArray();
    }

    public String getProgrammerInfoAsStr(int id) {
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

        return player.getInfoString();
    }

    public String getProgrammersInfo() {
        if (players == null || players.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Player player : players) {
            if (player.getStatus() != PlayerStatus.IN_GAME) {
                continue;
            }

            if (!first) {
                result.append(" | ");
            }

            result.append(getProgrammerInfoAsStr(player.getId()));
            first = false;
        }

        return result.toString();
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

        Slot slot = board.getSlot(position);
        BoardItem item = slot.getItem();

        String type = "";
        String id = "";

        if (item != null) {
            type = item.getType();
            id = String.valueOf(item.getId());
        }

        return new String[]{result, type, id};
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

        if (currentPlayer.isStuck()) {
            turnManager.nextTurn(); /* player loses the play */
            currentTurn++;
            return true;
        }

        currentPlayer.setLastDiceValue(nrSpaces);

        int currentPosition = currentPlayer.getCurrentPosition();
        int boardSize = board.getSize();
        int newPosition = currentPosition + nrSpaces;

        if (newPosition > boardSize) {
            int excess = newPosition - boardSize;
            newPosition = boardSize - excess;
        }

        currentPlayer.setCurrentPosition(newPosition);

        currentTurn++;

        turnManager.nextTurn();

        return true;
    }

    public String reactToAbyssOrTool() {
        Player currentPlayer = turnManager.getCurrentPlayer();
        int position = currentPlayer.getCurrentPosition();

        Slot slot = board.getSlot(position);
        BoardItem item = slot.getItem();

        if (item == null) {
            return null;
        }

        if (currentPlayer.hasToolThatCancels(item)) {
            return item.getName() + " anulado por ferramenta.";
        }

        String message = null;

        if (item.swapsStuckPlayer()) {
            List<Player> playersHere = getPlayersInPosition(position);

            for (Player p : playersHere) {
                if (p.isStuck()) {
                    p.setStuck(false);
                }
            }

            message = item.react(currentPlayer);
        }
        else if (item.affectsAllPlayersInSlot()) {
            List<Player> playersHere = getPlayersInPosition(position);

            if (playersHere.size() >= 2) {
                for (Player p : playersHere) {
                    message = slot.react(p);
                }
            }
        } else {
            message = slot.react(currentPlayer);
        }

        return message;
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

    public void loadGame(File file) throws InvalidFileException, FileNotFoundException {
    }

    public boolean saveGame(File file) {
        return false;
    }

    public JPanel getAuthorsPanel(){
        return null;
    }

    public HashMap<String, String> customizeBoard(){
        return new HashMap<>();
    }

    /* helper methods */
    private List<Player> getPlayersInPosition(int position) {
        List<Player> result = new ArrayList<>();

        for (Player p : players) {
            if (p.getStatus() == PlayerStatus.IN_GAME &&
                    p.getCurrentPosition() == position) {
                result.add(p);
            }
        }

        return result;
    }
}