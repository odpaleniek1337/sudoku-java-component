package compprog.sudoku;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuBoard {
    private SudokuSolver sudokuSolver;
    private List<SudokuField> board;
    
    public SudokuBoard(SudokuSolver solver) {
       board = Arrays.asList(new SudokuField[81]);
       this.sudokuSolver = solver;
    }
    
    private void cellsToZero() {
        for (int x = 0; x < 81; x++) {
            setCellValue(x,0);
        }
    }
    
    private void createFields() {
        for (int x = 0; x < 81; x++) {
            board.set(x, new SudokuField()); 
        }
    }
    
    /**
      *Generates indexes of start hint cells.
      * 
      *@return  integer array of indexes 
    */
    private List<Integer> randomizeHints(int hints) {
        List<Integer> array;
        array = Arrays.asList(new Integer[hints]);
        for (int x = 0; x < hints; x++) {
            array.set(x, -1);
        }
        Random dice = new Random();
        for (int j = 0; j < hints; j++) {
            int number = dice.nextInt(81);
            boolean isUnique = true;
            for (int x : array) {
                if (x == number) {
                    isUnique = false;
                    j--;
                }
            }
            if (isUnique == true) {
                array.set(j, number);
            }
        }
        Collections.sort(array);
        return array;
    }
    
    public boolean isInRow(int number, int row) {
        for (int i = 0; i < 9; i++) {
            if (number == getCellValue(row * 9 + i)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isInColumn(int number, int column) {
        for (int i = 0; i < 9; i++) {
            if (number == getCellValue(column + 9 * i)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isInSquare(int number, int row, int column) {
        int rowFactor = row / 3 * 3;//we get first row from that square
        int colFactor = column / 3 * 3;//we get first column from that square
        for (int i = rowFactor; i < rowFactor + 3; i++) {
            for (int j = colFactor; j < colFactor + 3; j++) {
                if (number == getCellValue(i * 9 + j)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    /**
      *Hides cells with given indexes.
      * 
      *@param cells - array of integers
    */
    private void createPuzzle(List<Integer> cells) {
        //for (Integer i : cells)
        for (Integer i = 0; i < cells.size(); i++) {
            setCellValue(cells.get(i), 0);
        }
    }
    
    /**
      *Creates first row of our board.
    */
    public void makeBoard() {
        createFields();
        cellsToZero();
        Integer[] intArray = {1,2,3,4,5,6,7,8,9};
        List<Integer> firstRow = Arrays.asList(intArray);
        Collections.shuffle(firstRow);
        
        for (int i = 0; i < 9; i++) {
            setCellValue(i, intArray[i]);
        }
    } 
    
    /**
      *Hides all cells except randomized ones.
      * 
      *@param hints - amount of hidden cells
    */
    public void setBoardForGame(int hints) {
        createPuzzle(randomizeHints(hints)); 
    }
    
    public void display() {
        for (int i = 0; i < 9; i++) {
            int[] row = new int[9];
            for (int j = 0; j < 9; j++) {
                row[j] = getCellValue(i * 9 + j);
            }
            System.out.println(Arrays.toString(row));
        }
    }
    
    public int getCellValue(int cell) {
        return board.get(cell).getFieldValue();
    }
    
    public void setCellValue(int cell, int value) {
        board.get(cell).setFieldValue(value);
        int row = cell / 9;
        int column = cell % 9;
        int rowFactor = row / 3;
        int colFactor = column / 3;
        board.get(cell).addObservers(getRow(row),
                getColumn(column),getBox(rowFactor * 3 + colFactor));
        board.get(cell).notifyObservers();
        board.get(cell).setFieldValue(value);
    }
    
    public SudokuRow getRow(int rowNumber) {
        List<SudokuField> row;
        row = Arrays.asList(new SudokuField[9]);
        
        for (int i = 0; i < 9; i++) {
            row.set(i, board.get(rowNumber * 9 + i));
            
        }
        return new SudokuRow(row);
    }
    
    public SudokuColumn getColumn(int columnNumber) {
        List<SudokuField> column;
        column = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            column.set(i, board.get(columnNumber + 9 * i));
        }
        return new SudokuColumn(column);
    }
    
    public SudokuBox getBox(int boxNumber) {
        List<SudokuField> box;
        box = Arrays.asList(new SudokuField[9]);
        int rowFactor = boxNumber % 3;
        int colFactor = boxNumber / 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                box.set(3 * i + j, board.get(9 * i + (j + rowFactor * 3) + 27 * colFactor));
            }
        }
        return new SudokuBox(box);
    }
    
    protected boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (!(getColumn(i).verify() && getRow(i).verify()
                    && getBox(i).verify())) {
                return false;
            }
        }
        return true;
    }
    
    public void solveGame() {
        makeBoard();
        sudokuSolver.solve(this);
        display();
    }
}
