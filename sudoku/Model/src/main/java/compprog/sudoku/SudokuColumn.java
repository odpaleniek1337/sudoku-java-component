package compprog.sudoku;

import java.io.Serializable;
import java.util.List;

public class SudokuColumn extends Sudoku9 implements Serializable {
    
    public SudokuColumn(List<SudokuField> fields) {
        this.fields = fields;
    }

    public String columnToString() {
        return this.toString();
    }

    public int columnHashCode() {
        return this.hashCode();
    }

    public boolean colummnEquals(Object obj) {
        return this.equals(obj);
    }

    public SudokuColumn cloneColumn() throws CloneNotSupportedException {
        SudokuColumn clone = (SudokuColumn) super.clone();
        return clone;
    }
}
