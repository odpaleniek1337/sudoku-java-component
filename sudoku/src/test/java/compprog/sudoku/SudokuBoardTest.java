package compprog.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author 230442 && 230461
 */
public class SudokuBoardTest {

    public SudokuBoardTest() {
    }

    @Test
    public void testCorrectSudokuDigits() {
        SudokuBoard board = new SudokuBoard();
        int notCorrect=0;
        board.makeBoard();
        board.fillBoard();
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
    public void testIsTwoSubsequentCallsAreDifferent() {
        SudokuBoard board = new SudokuBoard();
        SudokuBoard board2 = new SudokuBoard();
        int arrayEquals = 0;
        board.makeBoard();
        board.fillBoard();
        int[] sudoku1 = new int[81];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudoku1[row * 9 + col] = board.getCellValue(row * 9 + col);
            }
        } 
        
        board2.makeBoard();
        board2.fillBoard();
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
}