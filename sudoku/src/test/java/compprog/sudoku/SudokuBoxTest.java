package compprog.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoxTest {

    public SudokuBoxTest() {
    }

    @Test
    public void testOneBoxEqualThisBox() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBox box = board.getBox(0);
        assertTrue(box.boxEquals(box));
    }

    @Test
    public void testBoxNotEqualDifferentObject() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBox box = board.getBox(0);
        assertFalse(box.equals(board));
    }

    @Test
    public void testTwoSameHashCodesAreEqual() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBox box = board.getBox(0);
        assertEquals(box.boxHashCode(), box.boxHashCode());
    }

    @Test
    public void testToStringMethodSudokuBox() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBox box = board.getBox(0);
        assertTrue(box.boxToString().contains("fields"));
    }
}