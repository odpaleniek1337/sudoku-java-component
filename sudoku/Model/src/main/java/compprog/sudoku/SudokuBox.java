package compprog.sudoku;

import java.io.Serializable;
import java.util.List;

public class SudokuBox extends Sudoku9 implements Serializable {
    
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
