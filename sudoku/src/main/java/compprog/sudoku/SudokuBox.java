package compprog.sudoku;

public class SudokuBox implements Sudoku9 {
    private SudokuField[] box = new SudokuField[9];
    
    public SudokuBox(SudokuField[] fields)
    {
        this.box = fields;
    }
    
    @Override
    public boolean verify() {
        return true;
    }
}
