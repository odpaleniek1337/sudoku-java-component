package compprog.sudoku;

public interface Observer {
    void update(boolean verify); 
    
    boolean verify();
}
