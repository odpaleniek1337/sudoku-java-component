package compprog.sudoku;

public class Prototype {
    private SudokuBoard templateBoard;

    public Prototype(SudokuBoard board) {
        templateBoard = board;
    }

    public SudokuBoard createClone() throws CloneNotSupportedException {
        return templateBoard.clone();
    }
}
