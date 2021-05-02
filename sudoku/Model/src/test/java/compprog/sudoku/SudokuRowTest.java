package compprog.sudoku;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuRowTest {

    public SudokuRowTest() {
    }

    @Test
    public void testOneRowEqualThisRow() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuRow row = board.getRow(0);
        assertTrue(row.rowEquals(row));
    }

    @Test
    public void testRowNotEqualDifferentObject() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuRow row = board.getRow(0);
        assertFalse(row.equals(board));
    }

	@Test
    public void testNullObjectOnEqualsMethod() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuRow row = board.getRow(0);
        SudokuRow row1 = null;
        assertFalse(row.equals(row1));
    }

    @Test
    public void testTwoSameHashCodesAreEqual() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuRow row = board.getRow(0);
        assertEquals(row.rowHashCode(), row.rowHashCode());
    }

    @Test
    public void testToStringMethodSudokuRow() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuRow row = board.getRow(0);
        assertTrue(row.rowToString().contains("fields"));
    }

    @Test
    public void testRowCloning() throws CloneNotSupportedException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuRow row1 = board.getRow(0);
        SudokuRow row2 = row1.cloneRow();
        assertTrue(row1.equals(row2));
        SudokuRow row3 = board.getRow(1);
        assertFalse(row1.equals(row3));
    }

}