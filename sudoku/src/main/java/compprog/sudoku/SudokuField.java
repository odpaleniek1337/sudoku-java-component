package compprog.sudoku;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SudokuField implements Observable {
    private int value;
    public List<Observer> observers = new ArrayList<Observer>();
    
    public SudokuField() {
        this.value = 0;
    }
    
    public int getFieldValue() {
        return this.value;
    }
    
    public void setFieldValue(int value) {
        this.value = value;
    }
    
    public void addObservers(Observer... addedObservers) {
        for (Observer observer : addedObservers) {
            this.observers.add(observer);
        }
    }
    
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(observer.verify());
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("SudokuField", value)
                .toString();
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != getClass()) {
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
