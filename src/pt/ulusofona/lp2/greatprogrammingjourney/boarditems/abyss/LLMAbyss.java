package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerStatus;
import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class LLMAbyss extends Abyss {
    /* constructor */
    public LLMAbyss() {
        super(20, "LLM");
    }

    /* method */
    @Override
    public String react(Player player, int currentTurn) {
        int newPosition = player.getCurrentPosition();
        String msgResult;

        if(currentTurn >= 4){
            newPosition = player.getCurrentPosition() + player.getLastDiceValue();
            msgResult = "Caiu no LLM mas já tem experiência! Avança tantas casas quantas as do último movimento.";
        } else { /* player have tools and the game go in 4th or more turn */
            if (player.hasToolThatCancels(this)) {
                msgResult = "LLM anulado por Ajuda Do Professor.";
            } else { /* player haven't tools and the game go in 4th or more turn */
                newPosition = player.getPreviousPosition();
                msgResult = "Caiu no LLM! Recua para a posição onde estava antes.";
            }
        }

        if (newPosition < 1) {
            newPosition = 1;
        }

        player.setCurrentPosition(newPosition);

        return msgResult;
    }
}
