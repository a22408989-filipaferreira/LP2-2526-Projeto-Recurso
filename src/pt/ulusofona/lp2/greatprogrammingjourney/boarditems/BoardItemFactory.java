package pt.ulusofona.lp2.greatprogrammingjourney.boarditems;

import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.abyss.*;
import pt.ulusofona.lp2.greatprogrammingjourney.boarditems.tool.*;

public class BoardItemFactory {
    /* methods */
    public static BoardItem create(String type, int id) {
        if (type.equals("A")) {
            return createAbyss(id);
        } else if (type.equals("T")) {
            return createTool(id);
        }
        return null;
    }

    private static Abyss createAbyss(int id) {
        switch (id) {
            case 0: return new SyntaxErrorAbyss();
            case 1: return new LogicErrorAbyss();
            case 2: return new ExceptionAbyss();
            case 3: return new FileNotFoundExceptionAbyss();
            case 4: return new CrashAbyss();
            case 5: return new DuplicateCodeAbyss();
            case 6: return new SideEffectsAbyss();
            case 7: return new BlueScreenOfDeathAbyss();
            case 8: return new InfiniteLoopAbyss();
            case 9: return new SegmentationFaultAbyss();
            default: return null;
        }
    }

    private static Tool createTool(int id) {
        switch (id) {
            case 0: return new InheritanceTool();
            case 1: return new FunctionalProgrammingTool();
            case 2: return new UnitTestsTool();
            case 3: return new ExceptionHandlingTool();
            case 4: return new IDETool();
            case 5: return new ProfessorHelpTool();
            default: return null;
        }
    }
}
