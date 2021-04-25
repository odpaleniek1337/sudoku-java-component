package compprog.sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SudokuDaoTest {

    public SudokuDaoTest() {
    }

    @Test
    public void testReadWriteCloseMethods() throws IOException, ClassNotFoundException {
        String stringOriginal = "Some text";
        FileSudokuBoardDao<String> dao = new FileSudokuBoardDao<>("file.txt");
        dao.write(stringOriginal);
        String stringRead = dao.read();
        assertEquals(stringOriginal, stringRead);
        dao.close();
    }

    @Test
    public void testSudokuBoardDaoFactory() throws IOException, ClassNotFoundException {
        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> factoryDao = sudokuBoardDaoFactory.getFileDao("anotherFile.txt");

        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        int[] boardCopy = new int[81];
        for (int i = 0; i < 81; i++) {
            boardCopy[i] = board.getCellValue(i);
        }

        factoryDao.write(board);
        SudokuBoard boardRead = factoryDao.read();

        for (int i = 0; i < 81; i++) {
            Assertions.assertEquals(boardCopy[i], boardRead.getCellValue(i));
        }
    }
}