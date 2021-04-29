package compprog.sudoku;

import org.junit.jupiter.api.Test;
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
}