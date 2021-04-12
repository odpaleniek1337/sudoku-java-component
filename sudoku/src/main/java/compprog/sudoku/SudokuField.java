package compprog.sudoku;

import java.util.ArrayList;
import java.util.List;


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
}
