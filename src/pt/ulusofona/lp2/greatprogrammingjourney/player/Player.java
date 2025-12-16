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

    public boolean isStuck() {
        return stuck;
    }

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
    }

    /* methods */
    public String[] getInfoArray() {
        ArrayList<String> langs = new ArrayList<>(favoriteLanguages);
        Collections.sort(langs, String.CASE_INSENSITIVE_ORDER);
        String langsStr = String.join("; ", langs);

        String[] info = new String[5];
        info[0] = String.valueOf(id);
        info[1] = name;
        info[2] = langsStr;
        info[3] = avatarColor.getDisplayName();
        info[4] = String.valueOf(currentPosition);
        return info;
    }

    public String getInfoString() {
        ArrayList<String> langs = new ArrayList<>(favoriteLanguages);
        Collections.sort(langs, String.CASE_INSENSITIVE_ORDER);
        String langsStr = String.join("; ", langs);
        String toolsStr ;

        if(tools.isEmpty()){
            toolsStr = "No tools";
        } else {
            ArrayList<String> toolNames = new ArrayList<>();
            for(Tool tool: tools){
                toolNames.add(tool.getName());
            }
            toolsStr= String.join(", ", toolNames);
        }

        String[] info = new String[5];
        info[0] = String.valueOf(id);
        info[1] = name;
        info[2] = String.valueOf(currentPosition);
        info[3] = langsStr;
        info[4] = status.getDisplayName();

        return String.join(" | ", info);
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
        tools.add(tool);
    }

    public boolean hasToolThatCancels(BoardItem item) {
        for (Tool tool : tools) {
            if (tool.cancels(item)) {
                tools.remove(tool);
                return true;
            }
        }
        return false;
    }
}