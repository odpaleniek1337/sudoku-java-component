package compprog.sudoku;

public class SudokuRow implements Sudoku9 {
    private SudokuField[] row = new SudokuField[9];
    
    public SudokuRow(SudokuField[] fields)
    {
        this.row = fields;
    }
    
    @Override
    public boolean verify() {
        return true;
    }
}
