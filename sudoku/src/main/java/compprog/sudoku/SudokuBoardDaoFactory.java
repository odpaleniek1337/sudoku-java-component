package compprog.sudoku;

public class SudokuBoardDaoFactory {
    public static Dao getFileDao(String filename) {
        FileSudokuBoardDao dao = new FileSudokuBoardDao(filename);
        return dao;
    }
}
