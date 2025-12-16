package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class TestGameManager {
    private GameManager gameManager;

    /* this function is executed BEFORE each test */
    @BeforeEach
    void setUp() {
        /* create a new instance of GameManager for each test */
        gameManager = new GameManager();
    }

    /* auxiliary method to create a valid game scenario that can be used in multiple tests */
    private void setupTestGameValid() {
        String[][] players = {
                {"1", "Beatriz", "Java;Python", "Purple"},
                {"2", "Filipa", "C#;Ruby", "Green"}
        };
        int boardSize = 10;
        gameManager.createInitialBoard(players, boardSize);
    }

    /* --- Test Case 1: Valid Game Creation --- */
    @Test
    void testCreateInitialBoardValid() {
        String[][] players = {
                {"1", "Beatriz", "Java;Python", "Purple"}, //
                {"2", "Filipa", "C#", "Green"} //
        };
        int boardSize = 10;

        /* call project code */
        boolean result = gameManager.createInitialBoard(players, boardSize);

        /* asserts */
        assertTrue(result, "A criação do tabuleiro deveria ser válida com 2 jogadores e tamanho 10.");

        /* check if the first player is the correct one (ID 1) */
        assertEquals(1, gameManager.getCurrentPlayerID(), "O primeiro jogador a jogar deveria ser o ID 1.");
    }

    /* --- Test Case 2: Creation Failure (Duplicate IDs) --- */
    @Test
    void testCreateInitialBoardIDsDuplicates() {
        String[][] players = {
                {"1", "Beatriz", "Java", "Purple"},
                {"1", "Filipa", "C#", "Green"}
        };

        /* call project code */
        boolean result = gameManager.createInitialBoard(players, 10); //

        /* assert */
        assertFalse(result, "A criação do tabuleiro deveria falhar devido a IDs duplicados.");
    }

    /* --- Test Case 3: Creation Failure (Duplicate Colors) --- */
    @Test
    void testCreateInitialBoardDuplicatedColors() {
        String[][] players = {
                {"1", "Beatriz", "Java", "Purple"},
                {"2", "Filipa", "C#", "Purple"}
        };

        /* call project code */
        boolean result = gameManager.createInitialBoard(players, 10); //

        /* assert */
        assertFalse(result, "A criação do tabuleiro deveria falhar devido a cores duplicadas.");
    }

    /* --- Test Case 4: Valid Player Movement --- */
    @Test
    void testMoveCurrentPlayerValid() {
        /* setup (create a valid game first) */
        setupTestGameValid();

        /* call project code */
        boolean moveResult = gameManager.moveCurrentPlayer(4); //

        /* asserts */
        assertTrue(moveResult, "O movimento de 4 espaços deveria ser válido.");

        /* check if the location has been updated. */
        String[] info = gameManager.getProgrammerInfo(1); //
        assertEquals("5", info[4], "O Jogador 1 deveria estar agora na posição 5 (1 + 4).");

        /* check if the turn has advanced to the next player (ID 2) */
        assertEquals(2, gameManager.getCurrentPlayerID(), "O turno deveria ter avançado para o Jogador 2."); //
    }

    /* --- Test Case 5: Invalid Player Movement --- */
    @Test
    void testMoveCurrentPlayerInvalid() {
        /* setup (create a valid game first) */
        setupTestGameValid();

        /* call project code */
        boolean moveResult = gameManager.moveCurrentPlayer(7); //

        /* asserts */
        assertFalse(moveResult, "O movimento de 7 espaços deveria ser inválido.");

        /* check if the position hasn't been updated */
        String[] info = gameManager.getProgrammerInfo(1); //
        assertEquals("1", info[4], "O Jogador 1 deveria continuar na posição 1."); //

        /* check if the shift hasn't progressed. */
        assertEquals(1, gameManager.getCurrentPlayerID(), "O turno não deveria ter avançado."); //
    }

    /* --- Test Case 6: Testing Endgame and Results --- */
    @Test
    void testGameIsOverAndResults() {
        /* setup (create a valid game first) */
        setupTestGameValid();

        /* first, check that the game is not over. */
        assertFalse(gameManager.gameIsOver(), "O jogo não deveria ter terminado no início."); //

        /* move Player 1 to space 4 (Player 2's turn) */
        gameManager.moveCurrentPlayer(3); //

        /* move Player 2 to space 3 (Player 1's turn) */
        gameManager.moveCurrentPlayer(2); //

        /* move Player 1 to space 10 (4 + 6), ending the game */
        gameManager.moveCurrentPlayer(6); //

        /* asserts */
        assertTrue(gameManager.gameIsOver(), "O jogo deveria ter terminado (Jogador 1 chegou à casa 10)."); //

        /* check the results */
        ArrayList<String> results = gameManager.getGameResults(); //

        assertNotNull(results, "A lista de resultados não pode ser nula.");

        /* check if the winner is correct. */
        assertTrue(results.contains("VENCEDOR"), "Resultados devem ter a secção 'VENCEDOR'.");
        assertTrue(results.contains("Beatriz"), "O vencedor 'Beatriz' devia estar nos resultados.");

        /* check if the other player is listed correctly */
        assertTrue(results.stream().anyMatch(s -> s.contains("Filipa 3")), "O resultado da 'Filipa' na casa 3 devia estar listado.");
    }
}