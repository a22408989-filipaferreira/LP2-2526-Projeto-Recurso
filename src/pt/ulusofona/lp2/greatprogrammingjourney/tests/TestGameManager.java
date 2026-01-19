package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;
import pt.ulusofona.lp2.greatprogrammingjourney.InvalidFileException;

import java.io.File;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class TestGameManager {
    private static String[][] players2(String id1, String name1, String langs1, String color1,
                                       String id2, String name2, String langs2, String color2) {
        return new String[][]{
                new String[]{id1, name1, langs1, color1},
                new String[]{id2, name2, langs2, color2}
        };
    }

    @Test
    void createInitialBoard_rejectsInvalidArgs() {
        GameManager gm = new GameManager();

        assertFalse(gm.createInitialBoard(null, 10));
        assertFalse(gm.createInitialBoard(new String[][]{}, 10));
        assertFalse(gm.createInitialBoard(players2("1","A","","Blue","2","B","","Green"), 0));

        assertFalse(gm.createInitialBoard(new String[][]{
                new String[]{"1", "A", "", "Blue"}
        }, 10));

        assertFalse(gm.createInitialBoard(new String[][]{
                new String[]{"1", "A", "", "Blue"},
                new String[]{"2", "B", "", "Green"},
                new String[]{"3", "C", "", "Brown"},
                new String[]{"4", "D", "", "Purple"},
                new String[]{"5", "E", "", "Blue"}
        }, 20));

        assertFalse(gm.createInitialBoard(players2("1","A","","Blue","2","B","","Green"), 3));
    }

    @Test
    void createInitialBoard_rejectsDuplicateIdsAndColors() {
        GameManager gm = new GameManager();

        assertFalse(gm.createInitialBoard(players2("1","A","","Blue","1","B","","Green"), 10));

        assertFalse(gm.createInitialBoard(players2("1","A","","Blue","2","B","","Blue"), 10));
    }

    @Test
    void getImagePng_returnsGloryOnlyOnLastSquare() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players2("1","Ana","","Blue","2","Bob","","Green"), 6));

        assertNull(gm.getImagePng(1));
        assertEquals("glory.png", gm.getImagePng(6));
        assertNull(gm.getImagePng(7));
        assertNull(gm.getImagePng(0));
    }

    @Test
    void getSlotInfo_emptySlot_hasEmptyNameAndId() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players2("1","Ana","","Blue","2","Bob","","Green"), 6));

        String[] info = gm.getSlotInfo(1);
        assertNotNull(info);
        assertEquals("1,2", info[0]);
        assertEquals("", info[1]);
        assertEquals("", info[2]);
    }

    @Test
    void reactToAbyssOrTool_collectTool_thenDuplicateToolMessage() {
        GameManager gm = new GameManager();

        String[][] players = players2("1","Ana","","Blue","2","Bob","","Green");
        String[][] items = new String[][]{
                new String[]{"1", "0", "1"} // tool id 0 na posição 1
        };

        assertTrue(gm.createInitialBoard(players, 6, items));

        String msg1 = gm.reactToAbyssOrTool();
        assertNotNull(msg1);
        assertTrue(msg1.startsWith("Apanhou a ferramenta "));

        String msg2 = gm.reactToAbyssOrTool();
        assertNotNull(msg2);
        assertTrue(msg2.startsWith("Apanhou a ferramenta "));

        String msg3 = gm.reactToAbyssOrTool();
        assertEquals("Já tens esta ferramenta", msg3);
    }

    @Test
    void reactToAbyssOrTool_segmentationFaultWith2PlayersHere_triggersGlobalMoveBackMessage() {
        GameManager gm = new GameManager();

        String[][] players = players2("1","Ana","","Blue","2","Bob","","Green");

        String[][] items = new String[][]{
                new String[]{"0", "9", "1"}
        };

        assertTrue(gm.createInitialBoard(players, 6, items));

        String msg = gm.reactToAbyssOrTool();
        assertEquals("Falha de segmentação! Todos os jogadores recuaram 3 casas.", msg);

        assertEquals("1,2", gm.getSlotInfo(1)[0]);
    }

    @Test
    void gameIsOver_whenMultiplePlayersAtEnd_winnerIsAlphabeticallyFirstAndTurnManagerPointsToWinner() {
        GameManager gm = new GameManager();

        String[][] players = players2("2","bob","","Green","1","Ana","","Blue");
        assertTrue(gm.createInitialBoard(players, 4));

        assertTrue(gm.moveCurrentPlayer(6));
        gm.reactToAbyssOrTool();

        assertTrue(gm.moveCurrentPlayer(6));
        gm.reactToAbyssOrTool();

        assertTrue(gm.gameIsOver());

        assertEquals(1, gm.getCurrentPlayerID());

        var results = gm.getGameResults();
        assertTrue(results.contains("VENCEDOR"));
        assertTrue(results.contains("Ana"));
    }

    @Test
    void saveAndLoadGame_roundTrip_preservesCurrentPlayerAndSlots(@TempDir Path tempDir) throws Exception {
        GameManager gm = new GameManager();

        String[][] players = players2("1","Ana","","Blue","2","Bob","","Green");

        String[][] items = new String[][]{
                new String[]{"1","0","2"},
                new String[]{"0","1","3"}
        };

        assertTrue(gm.createInitialBoard(players, 6, items));

        assertTrue(gm.moveCurrentPlayer(1));
        gm.reactToAbyssOrTool();

        int expectedCurrentPlayer = gm.getCurrentPlayerID();

        File saveFile = tempDir.resolve("save.txt").toFile();
        assertTrue(gm.saveGame(saveFile));

        GameManager loaded = new GameManager();
        loaded.loadGame(saveFile);

        assertEquals(expectedCurrentPlayer, loaded.getCurrentPlayerID());

        String[] slot2 = loaded.getSlotInfo(2);
        assertNotNull(slot2);
        assertTrue(slot2[2].isEmpty() || slot2[2].startsWith("T:"));

        String[] slot3 = loaded.getSlotInfo(3);
        assertNotNull(slot3);
        assertTrue(slot3[2].isEmpty() || slot3[2].startsWith("A:"));
    }

    @Test
    void loadGame_invalidFile_throwsInvalidFileException(@TempDir Path tempDir) throws Exception {
        File f = tempDir.resolve("bad.txt").toFile();

        java.nio.file.Files.writeString(f.toPath(), "invalid\n");

        GameManager gm = new GameManager();
        assertThrows(InvalidFileException.class, () -> gm.loadGame(f));
    }
}
