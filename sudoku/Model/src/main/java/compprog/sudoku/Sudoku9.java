package compprog.sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *Abstract class for similar objects containing SudokuFields.
 */
public abstract class Sudoku9 implements Observer, Cloneable {
    protected List<SudokuField> fields;
    
    public Sudoku9() {
        fields = Arrays.asList(new SudokuField[9]);
    }

    /**
     * Returns true when given object has correct values, false otherwise.
     *
     * @return bool
     */
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
            //System.out.println("Verification succesful :)");
        } else {
            //System.out.println("Verification unsuccesful :(");
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fields", fields)
                .toString();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
