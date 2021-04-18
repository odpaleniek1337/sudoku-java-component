package compprog.sudoku;

import java.util.List;

public class SudokuBox extends Sudoku9 {
    
    public SudokuBox(List<SudokuField> fields) {
        this.fields = fields;
    }

    public String boxToString() {
        return this.toString();
    }

    public int boxHashCode() {
        return this.hashCode();
    }

    public boolean boxEquals(Object obj) {
        return this.equals(obj);
    }
}
