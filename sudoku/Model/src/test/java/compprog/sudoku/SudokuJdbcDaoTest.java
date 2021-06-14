package compprog.sudoku;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SudokuJdbcDaoTest {
    public SudokuJdbcDaoTest() {
    }

    @Test
    public void testReadingAndWritingSudokuBoard() {
        try (Dao<SudokuBoard> dao =
                     SudokuBoardDaoFactory.getJdbcSudokuBoardDao("test")) {

            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

            board.solveGame();

            int[] boardState = new int[81];
            for (int x = 0; x < 81; x++) {
                boardState[x] = board.getCellValue(x);
            }

            dao.write(board);
            SudokuBoard readBoard = dao.read();

            for (int x = 0; x < 81; x++) {
                assertEquals(boardState[x], readBoard.getCellValue(x));
            }
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testSudokuBoardDaoFactory() {
        try (JdbcSudokuBoardDao factoryDao =
                     (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getJdbcSudokuBoardDao("test2")) {

            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
            board.solveGame();

            int[] boardState = new int[81];
            for (int x = 0; x < 81; x++) {
                    boardState[x] = board.getCellValue(x);
            }

            factoryDao.write(board);
            SudokuBoard readBoard = factoryDao.read();

            for (int x = 0; x < 81; x++) {
                    assertEquals(boardState[x], readBoard.getCellValue(x));
            }
        }
    }
}
