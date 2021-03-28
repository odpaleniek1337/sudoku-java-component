package compprog.sudoku;

import java.util.HashSet;
import java.util.Set;

/**
 *Abstract class for similar objects containing SudokuFields.
 * 
 * @author 230461
 */
public abstract class Sudoku9 {
    protected SudokuField[] fields = new SudokuField[9];
    
    public boolean verify() {
        Set<Integer> foundNumbers = new HashSet<Integer>();
        for (int i = 0; i < 9; i++) {
            int potentialValue = fields[i].getFieldValue();
            if (foundNumbers.contains(potentialValue)) {
                return false;
            }
            foundNumbers.add(potentialValue);
        }
        return true;
    }
}
