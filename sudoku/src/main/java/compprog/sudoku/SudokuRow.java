package compprog.sudoku;

import java.util.List;

public class SudokuRow extends Sudoku9 {
    
    public SudokuRow(List<SudokuField> fields) {
        this.fields = fields;
    }

    public String rowToString() {
        return this.toString();
    }

    public int rowHashCode() {
        return this.hashCode();
    }

    public boolean rowEquals(Object obj) {
        return this.equals(obj);
    }
}
