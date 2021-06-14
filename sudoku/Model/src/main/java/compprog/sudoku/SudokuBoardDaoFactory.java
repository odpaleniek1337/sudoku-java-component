package compprog.sudoku;

public class SudokuBoardDaoFactory {
    public static Dao getFileDao(String filename) {
        FileSudokuBoardDao dao = new FileSudokuBoardDao(filename);
        return dao;
    }

    public static Dao<SudokuBoard> getJdbcSudokuBoardDao(String sudokuBoardName) {
        return new JdbcSudokuBoardDao(sudokuBoardName);
    }
}
