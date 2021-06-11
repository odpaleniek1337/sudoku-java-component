package compprog.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrototypeTest {

    public PrototypeTest() {
    }

    @Test
    public void testCloningPrototypeClass() throws CloneNotSupportedException {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard.solveGame();
        Prototype testPrototype = new Prototype(testBoard);
        SudokuBoard newClone1 = testPrototype.createClone();
        SudokuBoard newClone2 = testPrototype.createClone();
        assertEquals(newClone1.getCellValue(5), testBoard.getCellValue(5));
        assertEquals(newClone2.getCellValue(5), testBoard.getCellValue(5));
        testBoard.setCellValue(15,10);
        assertNotEquals(newClone1.getCellValue(15), testBoard.getCellValue(15));
        assertNotEquals(newClone2.getCellValue(15), testBoard.getCellValue(15));
        newClone1.setCellValue(15,10);
        assertNotEquals(newClone2.getCellValue(15), newClone1.getCellValue(15));
        assertEquals(newClone1.getCellValue(15) , testBoard.getCellValue(15));
    }
}