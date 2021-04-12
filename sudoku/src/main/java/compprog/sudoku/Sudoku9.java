package compprog.sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *Abstract class for similar objects containing SudokuFields.
 * 
 * @author 230461
 */
public abstract class Sudoku9 implements Observer {
    protected List<SudokuField> fields;
    
    public Sudoku9() {
        fields = Arrays.asList(new SudokuField[9]);
    }
    
    public boolean verify() {
        Set<Integer> foundNumbers = new HashSet<Integer>();
        for (int i = 0; i < 9; i++) {
            int potentialValue = fields.get(i).getFieldValue();
            if (foundNumbers.contains(potentialValue)) {
                return false;
            }
            foundNumbers.add(potentialValue);
        }
        return true;
    }
    
    @Override
    public void update(boolean verify) {
        if (verify) {
            System.out.println("Verification succesful :)");
        } else {
            System.out.println("Verification unsuccesful :(");
        }
    }
}
