package pt.ulusofona.lp2.greatprogrammingjourney.boarditems.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.player.Player;

public class LogicErrorAbyss extends Abyss {
    /* constructor */
    public LogicErrorAbyss() {
        super(1, "Erro de Lógica");
    }

    /* method */
    @Override
    public String react(Player player, int currentTurn) {
        int diceValue = player.getLastDiceValue();
        int retreat = diceValue / 2;

        if (retreat <= 0) {
            return "Erro de lógica: nenhum recuo aplicado";
        }

        int newPosition = player.getCurrentPosition() - retreat;

        if (newPosition < 1) {
            newPosition = 1;
        }

        player.setCurrentPosition(newPosition);

        return "Caiu num erro de lógica! Recuou " + retreat + " casas.";
    }
}