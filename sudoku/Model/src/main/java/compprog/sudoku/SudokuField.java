package compprog.sudoku;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class SudokuField implements Observable, Serializable, Comparable<SudokuField>, Cloneable {
    private int value;
    public List<Observer> observers = new ArrayList<>();
    
    public SudokuField() {
        this.value = 0;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        int oldValue = this.value;
        this.value = value;
    }

    public int getFieldValue() {
        return this.value;
    }
    
    public void setFieldValue(int value) {
        this.value = value;
    }

    /**
     * Adds observers to this field.
     *
     * @param addedObservers unspecified number of observers
     */
    public void addObservers(Observer... addedObservers) {
        for (Observer observer : addedObservers) {
            this.observers.add(observer);
        }
    }

    /**
     * Notifies observers to this field.
     */
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(observer.verify());
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("observers", observers)
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
    public int compareTo(SudokuField o) throws NullPointerException {
        return Integer.compare(this.value, o.getFieldValue());
    }

    @Override
    public SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }
}
