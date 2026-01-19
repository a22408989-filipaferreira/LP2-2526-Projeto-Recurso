package pt.ulusofona.lp2.greatprogrammingjourney.player;

import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.Color;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerStatus;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    /* fields */
    private int id;
    private String name;
    private ArrayList<String> favoriteLanguages = new ArrayList<>();
    private Color avatarColor;
    private int currentPosition;
    private PlayerStatus status;
    private ArrayList<Integer> positionHistory = new ArrayList<>();
    private int lastDiceValue;
    private boolean stuck = false;
    private ArrayList<Tool> tools = new ArrayList<>();
    private int turnsPlayed = 0;

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
    }

    /* getters */
    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    public PlayerStatus getStatus(){ return status; }

    public int getLastDiceValue() {
        return lastDiceValue;
    }

    public int getTurnsPlayed() {
        return turnsPlayed;
    }

    public boolean isStuck() {
        return stuck;
    }

    public ArrayList<String> getFavoriteLanguages() { return favoriteLanguages; }

    /* setters */
    public void setCurrentPosition(int position){
        positionHistory.add(this.currentPosition);
        this.currentPosition = position;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public void setLastDiceValue(int lastDiceValue) {
        this.lastDiceValue = lastDiceValue;
    }

    public void setStuck(boolean stuck) {
        this.stuck = stuck;
        this.status = stuck ? PlayerStatus.STUCK : PlayerStatus.IN_GAME;
    }

    /* methods */
    public String[] getInfoArray() {
        ArrayList<String> langs = new ArrayList<>(favoriteLanguages);
        Collections.sort(langs, String.CASE_INSENSITIVE_ORDER);
        String langsStr = String.join("; ", langs);

        String[] info = new String[7];
        info[0] = String.valueOf(id);
        info[1] = name;
        info[2] = langsStr;
        info[3] = avatarColor.getDisplayName();
        info[4] = String.valueOf(currentPosition);
        info[5] = getToolsAsStr();
        info[6] = status.getDisplayName();

        return info;
    }

    public String getInfoString() {
        ArrayList<String> langs = new ArrayList<>(favoriteLanguages);
        Collections.sort(langs, String.CASE_INSENSITIVE_ORDER);
        String langsStr = String.join("; ", langs);

        String[] info = new String[6];
        info[0] = String.valueOf(id);
        info[1] = name;
        info[2] = String.valueOf(currentPosition);
        info[3] = getToolsAsStr();
        info[4] = langsStr;
        info[5] = status.getDisplayName();

        return String.join(" | ", info);
    }

    public String getShortInfoString() {
        String[] info = new String[2];
        info[0] = name;
        info[1] = getToolsAsStr();

        return String.join(" : ", info);
    }

    public int getPositionTwoMovesAgo() {
        int size = positionHistory.size();

        if (size < 2) {
            return 1;
        }

        return positionHistory.get(size - 2);
    }

    public int getPreviousPosition() {
        int size = positionHistory.size();

        if (size < 1) {
            return 1;
        }

        return positionHistory.get(size - 1);
    }

    public void addTool(Tool tool) {
        if (tool != null) {
            tools.add(tool);
        }
    }

    public boolean hasTool(BoardItem tool) {
        if (tool == null) {
            return false;
        }

        for (Tool t : tools) {
            if (t.getId() == tool.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasToolThatCancels(BoardItem item) {
        for (Tool tool : tools) {
            if (tool.cancels(item)) {
                return true;
            }
        }
        return false;
    }

    public void consumeToolThatCancels(BoardItem item) {
        for (int i = 0; i < tools.size(); i++) {
            if (tools.get(i).cancels(item)) {
                tools.remove(i);
                return;
            }
        }
    }

    public String getToolsAsStr() {
        if (tools.isEmpty()) {
            return "No tools";
        }

        ArrayList<String> toolNames = new ArrayList<>();
        for (Tool tool : tools) {
            toolNames.add(tool.getName());
        }

        /* sort tools to alphabetic order */
        toolNames.sort(String.CASE_INSENSITIVE_ORDER);

        return String.join(";", toolNames);
    }

    public void incrementTurnsPlayed() {
        turnsPlayed++;
    }
}