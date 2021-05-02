package compprog.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {

    public SudokuBoardTest() {
    }

    @Test
    public void testCorrectSudokuDigits() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        int notCorrect=0;
        board.solveGame();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int number = board.getCellValue(row * 9 + col);
                if (!board.isInRow(number, row) && !board.isInColumn(number,col) && !board.isInSquare(number,row,col)) {
                    notCorrect = 1;
                }
            }
        }
        assertEquals(notCorrect, 0);
    }

    @Test
    public void testIfTwoSubsequentCallsAreDifferent() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());
        int arrayEquals = 0;
        board.solveGame();
        
        int[] sudoku1 = new int[81];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudoku1[row * 9 + col] = board.getCellValue(row * 9 + col);
            }
        } 
        
        board2.solveGame();
        int[] sudoku2 = new int[81];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudoku2[row * 9 + col] = board2.getCellValue(row * 9 + col);
            }
        } 
        
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudoku1[row * 9 + col] == sudoku2[row * 9 + col]) {
                    arrayEquals++;
                }
            }
        }
        assertNotEquals(arrayEquals,81);
    }
    
    @Test
    public void testIfRandomizeHintsGiveExactNumberOfHints() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        int counterHidden = 0;
        board.solveGame();
        board.setBoardForGame(64);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertNotNull(board.getCellValue(row * 9 + col));
                if(board.getCellValue(row * 9 + col) == 0) {
                    counterHidden++;
                }
            }
        }
        assertEquals(counterHidden, 64);
    }
    
    @Test
    public void testCorrectnessOfVerifyMethodSudokuBoard() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        assertTrue(board.checkBoard());
    }
    
    @Test
    public void testFalseOfVerifyMethodSudokuBoard() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        for(int i = 0; i < 81; i++) {
            board.setCellValue(i, 0);
        }

        assertFalse(board.checkBoard());
    }

    @Test
    public void testTwoBoardNotEqual() {
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());
        assertFalse(board1.equals(board2));
    }

    @Test
    public void testBoardNotEqualDifferentObject() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBox box = board.getBox(0);
        assertFalse(board.equals(box));
    }

    @Test
    public void testTwoHashCodesNotEqual() {
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());
        assertNotEquals(board1.hashCode(), board2.hashCode());
    }

    @Test
    public void testTwoHashCodesAreEqual() {
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board2 = board1;
        assertEquals(board1.hashCode(), board2.hashCode());
    }

	@Test
    public void testNullObjectOnEqualsMethod() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board1 = null;
        assertFalse(board.equals(board1));
    }

    @Test
    public void testToStringMethodSudokuBoard() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        String toString = board.toString();
        assertTrue(toString.contains("board"));
        assertTrue(toString.contains("sudokuSolver"));
    }

    @Test
    public void testCloneBoard() throws CloneNotSupportedException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuBoard newBoard = board.clone();
        assertEquals(board.getCellValue(5),newBoard.getCellValue(5));
        board.setCellValue(5, 9);
        board.setCellValue(6, 9);
        assertNotEquals(board.getCellValue(5) + board.getCellValue(6), newBoard.getCellValue(5) + newBoard.getCellValue(6));
    }
}