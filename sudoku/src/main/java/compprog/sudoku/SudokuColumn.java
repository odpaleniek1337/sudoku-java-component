package compprog.sudoku;

public class SudokuColumn implements Sudoku9 {
    private SudokuField[] column = new SudokuField[9];
    
    public SudokuColumn(SudokuField[] fields)
    {
        this.column = fields;
    }
    
    @Override
    public boolean verify() {
        return true;
    }
}
