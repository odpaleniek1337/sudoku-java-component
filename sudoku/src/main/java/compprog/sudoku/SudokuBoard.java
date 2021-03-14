package compprog.sudoku;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuBoard {
    private int[] board = new int[81];
    
    public int getCellValue(int cell) {
        return board[cell];
    }
    
    private void setCellValue(int cell, int value) {
        board[cell] = value;
    }
    
    private void cellsToZero() {
        for (int x = 0; x < 81; x++) {
            setCellValue(x,0);
        }
    }
    
    /**
      *Generates indexes of start hint cells.
      * 
      *@return  integer array of indexes 
    */
    private Integer[] randomizeHints(int hints) {
        Integer[] array = new Integer[hints];
        for (int x = 0; x < hints; x++) {
            array[x] = -1;
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
                array[j] = number;
            }
        }
        Arrays.sort(array);
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
    private void createPuzzle(Integer[] cells) {
        for (int i = 0; i < cells.length; i++) {
            int index = cells[i];
            setCellValue(index, 0);
        }
    }
    
    /**
      *Creates first row of our board.
    */
    public void makeBoard() {
        cellsToZero();//check if all of them are zeros
        Integer[] intArray = {1,2,3,4,5,6,7,8,9};
        List<Integer> firstRow = Arrays.asList(intArray);
        Collections.shuffle(firstRow);
        
        for (int i = 0; i < 9; i++) {
            setCellValue(i, intArray[i]);
        }
    } 
    
    /**
      *Hides all cells except randomized ones.
    */
    public void setBoardForGame() {
        createPuzzle(randomizeHints(64)); 
    }
    
    public boolean fillBoard() {
        int row = 0;
        int col = 0;
        boolean isEmpty = false;
        Integer[] intArray = {1,2,3,4,5,6,7,8,9};
        List<Integer> listRow = Arrays.asList(intArray);
        Collections.shuffle(listRow);
        
        //Alghorithm starts here:
        for (row = 1;row < 9;row++) {
            for (col = 0;col < 9;col++) {
                if (getCellValue(row * 9 + col) == 0) {
                    isEmpty = true;
                    break;
                }
            }
            if (isEmpty == true) {
                break;
            }
        }
        
        if (isEmpty == false) {
            return true;
        }
        
        for (int o = 0;o < 9;o++) {
            int number = intArray[o];
            if (!isInRow(number,row) && !isInColumn(number,col) && !isInSquare(number,row,col)) {
                setCellValue(row * 9 + col, number);
            
            if (fillBoard()) {
               return true;
            }
            setCellValue(row * 9 + col, 0);
            }
        }
        return false;
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
}
