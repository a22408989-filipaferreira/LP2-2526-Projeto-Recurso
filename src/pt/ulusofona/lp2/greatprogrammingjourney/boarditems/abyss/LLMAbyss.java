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
        if (currentTurn <= 3) {

            if (player.hasToolThatCancels(this)) {
                player.consumeToolThatCancels(this);
                return "LLM anulado por Ajuda Do Professor.";
            }

            int newPos = player.getCurrentPosition() - player.getLastDiceValue();
            if (newPos < 1) newPos = 1;

            player.setCurrentPosition(newPos);
            return "Caiu no LLM! Recua para a posição onde estava antes.";
        }

        int newPos = player.getCurrentPosition() + player.getLastDiceValue();
        player.setCurrentPosition(newPos);

        return "Caiu no LLM mas já tem experiência! Avança tantas casas quantas as do último movimento.";
    }
}
