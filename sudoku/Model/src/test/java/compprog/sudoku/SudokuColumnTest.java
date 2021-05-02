package compprog.sudoku;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuColumnTest {

    public SudokuColumnTest() {
    }

    @Test
    public void testOneColumnEqualThisColumn() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuColumn column = board.getColumn(0);
        assertTrue(column.colummnEquals(column));
    }

    @Test
    public void testColumnNotEqualDifferentObject() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuColumn column = board.getColumn(0);
        assertFalse(column.equals(board));
    }

    @Test
    public void testTwoSameHashCodesAreEqual() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuColumn column = board.getColumn(0);
        assertEquals(column.columnHashCode(), column.columnHashCode());
    }

    @Test
    public void testToStringMethodSudokuColumn() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuColumn column = board.getColumn(0);
        assertTrue(column.columnToString().contains("fields"));
    }

    @Test
    public void testColumnCloning() throws CloneNotSupportedException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuColumn column1 = board.getColumn(0);
        SudokuColumn column2 = column1.cloneColumn();
        assertTrue(column1.equals(column2));
        SudokuColumn column3 = board.getColumn(1);
        assertFalse(column1.equals(column3));
    }
}