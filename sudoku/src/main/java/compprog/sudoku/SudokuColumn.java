package compprog.sudoku;

import java.util.List;

public class SudokuColumn extends Sudoku9 {
    
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
}
