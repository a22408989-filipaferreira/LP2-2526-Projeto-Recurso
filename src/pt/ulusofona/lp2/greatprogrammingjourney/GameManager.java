package pt.ulusofona.lp2.greatprogrammingjourney;

import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItemFactory;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.core.*;
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
    private int currentTurn;
    private GameResult gameResult;  /* null until game ends */

    /* constructor */
    public GameManager() {
        this.board = null;
        this.turnManager = null;
        this.gameIsOver = false;
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
        this.gameResult = null;

        return true;
    }

    public boolean createInitialBoard(String[][] playerInfo, int boardSize, String[][] abyssesAndTools) {
        if (!createInitialBoard(playerInfo, boardSize)) {
            return false;
        }

        if (abyssesAndTools == null) {
            return true;
        }

        for (String[] slotInfo : abyssesAndTools) {
            if (slotInfo == null || slotInfo.length < 3) {
                return false;
            }

            int position;
            int id;
            int type;

            try {
                position = Integer.parseInt(slotInfo[2]);
                type = Integer.parseInt(slotInfo[0]);
                id = Integer.parseInt(slotInfo[1]);
            } catch (NumberFormatException e) {
                return false;
            }

            if (position < 1 || position > board.getSize()) {
                return false;
            }

            if (type != 0 && type != 1) {
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

            result.append(player.getShortInfoString());
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

        String id = "";
        String name = "";

        if (item != null) {
            name = item.getName();

            if (item.getType() == 0) {
                id = "A:" + item.getId();
            } else if (item.getType() == 1) {
                id = "T:" + item.getId();
            }
        }

        return new String[]{result, name, id};
    }

    public int getCurrentPlayerID(){
        return turnManager.getCurrentPlayerID();
    }

    public boolean moveCurrentPlayer(int nrSpaces) {
        if (nrSpaces < 1 || nrSpaces > 6) {
            return false;
        }

        if (board == null || turnManager == null) {
            return false;
        }

        Player currentPlayer = turnManager.getCurrentPlayer();

        if (currentPlayer.isStuck()) {
            currentPlayer.setStuck(false);
            endTurn(currentPlayer);
            return false;
        }

        if (currentPlayer.getStatus() != PlayerStatus.IN_GAME) {
            return false;
        }

        if (!currentPlayer.getFavoriteLanguages().isEmpty()) {
            String lang = currentPlayer.getFavoriteLanguages().get(0);

            if (lang.equals("Assembly") && nrSpaces > 2) {
                return false;
            }

            if (lang.equals("C") && nrSpaces > 3) {
                return false;
            }
        }

        currentPlayer.setLastDiceValue(nrSpaces);

        int currentPosition = currentPlayer.getCurrentPosition();
        int boardSize = board.getSize();
        int newPosition = currentPosition + nrSpaces;

        if (newPosition > boardSize) {
            newPosition = boardSize;
        }

        currentPlayer.setCurrentPosition(newPosition);

        return true;
    }

    public String reactToAbyssOrTool() {
        Player currentPlayer = turnManager.getCurrentPlayer();
        int position = currentPlayer.getCurrentPosition();
        List<Player> playersHere = getPlayersInPosition(position);

        Slot slot = board.getSlot(position);
        BoardItem item = slot.getItem();

        if (item == null) {
            endTurn(currentPlayer);
            return null;
        }

        if (item.isCollectable()) {
            Tool tool = item.asTool();

            if (!currentPlayer.hasTool(tool)) {
                currentPlayer.addTool(tool);
                endTurn(currentPlayer);
                return "Apanhou a ferramenta " + tool.getName();
            }

            return "Já tens esta ferramenta";
        }

        if (item.getId() != 20 && currentPlayer.hasToolThatCancels(item)) {
            currentPlayer.consumeToolThatCancels(item);
            endTurn(currentPlayer);
            return "A ferramenta " + item.getName() + " anulou o abismo";
        }

        int playerTurn = currentPlayer.getTurnsPlayed() + 1;
        String message = item.react(currentPlayer, playerTurn);

        if (currentPlayer.isStuck()) {
            endTurn(currentPlayer);
            return null; // tests expect null when stuck
        }

        if (item.swapsStuckPlayer()) {
            for (Player p : playersHere) {
                p.setStuck(false);
            }
        }

        if(playersHere.size() >= 2){
            if (item.affectsAllPlayersInSlot()) {
                for (Player p : playersHere) {
                    if (p != currentPlayer && p.getStatus() == PlayerStatus.IN_GAME) {
                        int pTurn = p.getTurnsPlayed() + 1;
                        item.react(p, pTurn);
                    }
                }
            }
        }

        endTurn(currentPlayer);
        return message;
    }

    public boolean gameIsOver() {
        if (board == null) {
            return false;
        }

        int lastPosition = board.getSize();

        List<Player> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort(Comparator.comparing(Player::getName, String.CASE_INSENSITIVE_ORDER));

        for (Player player : sortedPlayers) {
            if (player.getCurrentPosition() == lastPosition) {
                gameResult = new WinResult(player);
                gameIsOver = true;

                int winnerId = player.getId();
                while (turnManager.getCurrentPlayerID() != winnerId) {
                    turnManager.nextTurn();
                }

                return true;
            }
        }

        if (noOneCanMove()) {
            gameResult = new TieResult();
            gameIsOver = true;
            return true;
        }

        return false;
    }

    public ArrayList<String> getGameResults(){
        ArrayList<String> results = new ArrayList<>();

        if (!gameIsOver || gameResult == null){ return results; }

        results.add("THE GREAT PROGRAMMING JOURNEY");
        results.add("");
        results.add("NR. DE TURNOS");
        results.add(String.valueOf(currentTurn));
        results.add("");

        if (gameResult.isTie()) {
            results.add(gameResult.getEndMessage());
            results.add("");
            results.add("PARTICIPANTES");
            for (Player p : players) {
                results.add(p.getName() + " : " + p.getCurrentPosition() + " : " + getAbyssNameAtPlayer(p));
            }
            return results;
        }

        Player winner = gameResult.getWinner();
        results.add("VENCEDOR");
        results.add(winner.getName());
        results.add("");
        results.add("RESTANTES");

        ArrayList<Player> remainingPlayers = new ArrayList<>(players);
        remainingPlayers.remove(winner);

        remainingPlayers.sort((p1, p2) -> {
            int cmp = Integer.compare(p2.getCurrentPosition(), p1.getCurrentPosition()); // desc by position
            if (cmp != 0){ return cmp; }
            return p1.getName().compareToIgnoreCase(p2.getName()); // asc by name
        });

        for (Player player : remainingPlayers) {
            results.add(player.getName() + " " + player.getCurrentPosition());
        }

        return results;
    }

    public void loadGame(File file) throws InvalidFileException, FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }

        try (Scanner sc = new Scanner(file)) {
            String worldLine = nextNonEmptyLine(sc);
            String[] worldParts = worldLine.split(";");

            if (worldParts.length != 3) {
                throw new InvalidFileException();
            }

            int boardSize = Integer.parseInt(worldParts[0]);
            int loadedTurn = Integer.parseInt(worldParts[1]);
            int currentPlayerId = Integer.parseInt(worldParts[2]);

            Board newBoard = new Board(boardSize);

            int nrPlayers = Integer.parseInt(nextNonEmptyLine(sc));
            ArrayList<Player> newPlayers = new ArrayList<>();

            for (int i = 0; i < nrPlayers; i++) {
                String[] p = nextNonEmptyLine(sc).split(";", 6);
                if (p.length != 6) {
                    throw new InvalidFileException();
                }

                int id = Integer.parseInt(p[0]);
                String name = p[1];
                String color = p[2];
                int position = Integer.parseInt(p[3]);
                int status = Integer.parseInt(p[4]);
                String langs = p[5];

                Player player = new Player(id, name, langs, Color.fromString(color));
                player.setCurrentPosition(position);
                player.setStatus(status == 1 ? PlayerStatus.IN_GAME : PlayerStatus.DEFEATED);

                newPlayers.add(player);
            }

            int abyssCount = Integer.parseInt(nextNonEmptyLine(sc));

            for (int i = 0; i < abyssCount; i++) {
                String[] a = nextNonEmptyLine(sc).split(";");
                if (a.length != 2) {
                    throw new InvalidFileException();
                }

                int abyssId = Integer.parseInt(a[0]);
                int position = Integer.parseInt(a[1]);

                BoardItem abyss = BoardItemFactory.create(0, abyssId);
                newBoard.getSlot(position).setItem(abyss);
            }

            int toolCount = Integer.parseInt(nextNonEmptyLine(sc));

            for (int i = 0; i < toolCount; i++) {
                String[] t = nextNonEmptyLine(sc).split(";");
                if (t.length != 2) {
                    throw new InvalidFileException();
                }

                int toolId = Integer.parseInt(t[0]);
                int position = Integer.parseInt(t[1]);

                BoardItem tool = BoardItemFactory.create(1, toolId);
                newBoard.getSlot(position).setItem(tool);
            }

            this.board = newBoard;
            this.players = newPlayers;
            this.turnManager = new TurnManager(newPlayers);
            this.currentTurn = loadedTurn;

            while (turnManager.getCurrentPlayerID() != currentPlayerId) {
                turnManager.nextTurn();
            }

            this.gameIsOver = false;
            this.gameResult = null;

        } catch (IllegalArgumentException e) {
            throw new InvalidFileException();
        }
    }

    public boolean saveGame(File file) {
        if (file == null || board == null || turnManager == null) {
            return false;
        }

        try (java.io.PrintWriter writer = new java.io.PrintWriter(file)) {
            writer.println(
                    board.getSize() + ";" +
                            currentTurn + ";" +
                            turnManager.getCurrentPlayerID()
            );

            writer.println(players.size());
            for (Player p : players) {
                int status = (p.getStatus() == PlayerStatus.IN_GAME) ? 1 : 0;

                String langs = "";
                if (p.getInfoArray()[2] != null) {
                    langs = p.getInfoArray()[2];
                }

                writer.println(
                        p.getId() + ";" +
                                p.getName() + ";" +
                                p.getInfoArray()[3] + ";" +
                                p.getCurrentPosition() + ";" +
                                status + ";" +
                                langs
                );
            }

            List<String> abyssLines = new ArrayList<>();

            for (int i = 1; i <= board.getSize(); i++) {
                Slot slot = board.getSlot(i);
                BoardItem item = slot.getItem();

                if (item != null && !item.isCollectable()) {
                    abyssLines.add(item.getId() + ";" + i);
                }
            }

            writer.println(abyssLines.size());
            for (String line : abyssLines) {
                writer.println(line);
            }

            List<String> toolLines = new ArrayList<>();

            for (int i = 1; i <= board.getSize(); i++) {
                Slot slot = board.getSlot(i);
                BoardItem item = slot.getItem();

                if (item != null && item.isCollectable()) {
                    toolLines.add(item.getId() + ";" + i);
                }
            }

            writer.println(toolLines.size());
            for (String line : toolLines) {
                writer.println(line);
            }

            return true;

        } catch (FileNotFoundException e) {
            return false;
        }
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
            if (isActive(p) && p.getCurrentPosition() == position) {
                result.add(p);
            }
        }
        return result;
    }

    private String nextNonEmptyLine(Scanner sc) throws InvalidFileException {
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
        }
        throw new InvalidFileException();
    }

    private boolean canMove(Player p, int dice) {
        if (p.getStatus() != PlayerStatus.IN_GAME){ return false; }
        if (p.isStuck()){ return false; }
        if (dice < 1 || dice > 6){ return false; }

        if (!p.getFavoriteLanguages().isEmpty()) {
            String lang = p.getFavoriteLanguages().get(0);
            if (lang.equals("Assembly") && dice > 2){ return false; }
            if (lang.equals("C") && dice > 3){ return false; }
        }
        return true;
    }

    private boolean noOneCanMove() {
        for (Player p : players) {
            if (p.getStatus() != PlayerStatus.IN_GAME){ continue; }
            for (int dice = 1; dice <= 6; dice++) {
                if (canMove(p, dice)){ return false; }
            }
        }
        return true;
    }

    private String getAbyssNameAtPlayer(Player p) {
        Slot slot = board.getSlot(p.getCurrentPosition());
        BoardItem item = slot.getItem();
        if (item != null && !item.isCollectable()) {
            return item.getName();
        }
        return "";
    }

    private void endTurn(Player currentPlayer) {
        currentPlayer.incrementTurnsPlayed();
        currentTurn++;
        turnManager.nextTurn();
    }

    private boolean isActive(Player p) {
        return p != null && p.getStatus() != PlayerStatus.DEFEATED;
    }
}