package compprog.sudoku;

public class MainClass {
    public static void main(String[] args) {
        SudokuBoard board = new SudokuBoard(); 
        board.makeBoard();
        board.fillBoard();
        board.display();
    }
}
