package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItem;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.BoardItemFactory;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestToolCancels {
    @Test
    void exceptionHandlingTool_cancelsExceptionAbysses() {
        Tool tool = new ExceptionHandlingTool();

        BoardItem exception = BoardItemFactory.create(0, 2);
        BoardItem fileNotFound = BoardItemFactory.create(0, 3);
        BoardItem infiniteLoop = BoardItemFactory.create(0, 7);

        assertTrue(tool.cancels(exception));
        assertTrue(tool.cancels(fileNotFound));
        assertFalse(tool.cancels(infiniteLoop));
    }

    @Test
    void ideTool_cancelsSyntaxAndFileNotFound() {
        Tool tool = new IDETool();

        BoardItem syntax = BoardItemFactory.create(0, 0);
        BoardItem fileNotFound = BoardItemFactory.create(0, 3);

        BoardItem logic = BoardItemFactory.create(0, 1);
        BoardItem crash = BoardItemFactory.create(0, 4);

        assertTrue(tool.cancels(syntax));
        assertTrue(tool.cancels(fileNotFound));

        assertFalse(tool.cancels(logic));
        assertFalse(tool.cancels(crash));
    }

    @Test
    void inheritanceTool_cancelsDuplicateCode() {
        Tool tool = new InheritanceTool();

        BoardItem duplicateCode = BoardItemFactory.create(0, 5);
        BoardItem sideEffects = BoardItemFactory.create(0, 4);

        assertTrue(tool.cancels(duplicateCode));
        assertFalse(tool.cancels(sideEffects));
    }

    @Test
    void functionalProgrammingTool_cancelsDuplicateSideEffectsAndInfiniteLoop() {
        Tool tool = new FunctionalProgrammingTool();

        BoardItem duplicateCode = BoardItemFactory.create(0, 5);
        BoardItem sideEffects = BoardItemFactory.create(0, 6);
        BoardItem infiniteLoop = BoardItemFactory.create(0, 8);

        BoardItem logicError = BoardItemFactory.create(0, 1);

        assertTrue(tool.cancels(duplicateCode));
        assertTrue(tool.cancels(sideEffects));
        assertTrue(tool.cancels(infiniteLoop));

        assertFalse(tool.cancels(logicError));
    }

    @Test
    void unitTestsTool_cancelsLogicCrashAndSegFault() {
        Tool tool = new UnitTestsTool();

        BoardItem logicError = BoardItemFactory.create(0, 1);

        BoardItem crash = BoardItemFactory.create(0, 4);

        BoardItem segFault = BoardItemFactory.create(0, 9);

        BoardItem infiniteLoop = BoardItemFactory.create(0, 8);

        assertTrue(tool.cancels(logicError));
        assertTrue(tool.cancels(crash));
        assertTrue(tool.cancels(segFault));

        assertFalse(tool.cancels(infiniteLoop));
    }

    @Test
    void professorHelpTool_cancelsSyntaxLogicAndSegFault() {
        Tool tool = new ProfessorHelpTool();

        BoardItem syntax = BoardItemFactory.create(0, 0);
        BoardItem logic = BoardItemFactory.create(0, 1);
        BoardItem segFault = BoardItemFactory.create(0, 9);

        BoardItem exception = BoardItemFactory.create(0, 2);
        BoardItem infiniteLoop = BoardItemFactory.create(0, 8);

        assertTrue(tool.cancels(syntax));
        assertTrue(tool.cancels(logic));
        assertTrue(tool.cancels(segFault));

        assertFalse(tool.cancels(exception));
        assertFalse(tool.cancels(infiniteLoop));
    }
}
