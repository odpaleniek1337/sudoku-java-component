package compprog.sudoku;

import java.io.Serializable;
import java.util.List;

public class SudokuRow extends Sudoku9 implements Serializable {
    
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
