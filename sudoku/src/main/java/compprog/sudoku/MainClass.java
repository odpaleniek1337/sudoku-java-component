package compprog.sudoku;

public class MainClass {
    public static void main(String[] args) {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(sudokuSolver); 
        board.solveGame();
    }
}
