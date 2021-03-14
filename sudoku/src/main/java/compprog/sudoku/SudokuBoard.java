package compprog.sudoku;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuBoard {
    private int[] board = new int[81];
    
    private int getCellValue(int cell) {
        return board[cell];
    }
    
    private void setCellValue(int cell, int value) {
        board[cell] = value;
    }
    
    private void cellsToZero() {
        for(int x = 0; x < 81; x++) {
            setCellValue(x,0);
        }
    }
    
    /**
      *Generates indexes of start hint cells
      * 
      *@return  integer array of indexes 
    */
    private Integer[] randomizeHints(int hints) {
        Integer[] array = new Integer[hints];
        for (int x = 0; x < hints;x++){
            array[x] = -1;
        }
        Random dice = new Random();
        for (int j = 0; j < hints; j++) {
            int number = dice.nextInt(81);
            boolean isUnique = true;
            for (int x : array){
                if (x == number) {
                    isUnique = false;
                    j--;
                }
            }
            if (isUnique == true) {
                array[j] = number;
            }
        }
        return array;
    }
    
    private boolean isInRow(int number, int row){
        for(int i = 0; i < 9; i++) {
            if(number == getCellValue(row*9+i)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isInColumn(int number, int column){
        for(int i = 0; i < 9; i++) {
            if(number == getCellValue(column+9*i)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isInSquare(int number, int row, int column){
        int rowFactor = row / 3 * 3;//we get first row from that square
        int colFactor = column / 3 * 3;//we get first column from that square
        for(int i = rowFactor; i < rowFactor + 3; i++) {
            for(int j = colFactor; j < colFactor + 3; j++) {
                if(number == getCellValue(i*9+j)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private void createPuzzle(Integer[] cells) {
        int index, column, row, number;
        Random random = new Random();
        for (int i = 0; i < cells.length; i++) {
            index = cells[i];
            column = index % 9;
            row = index / 9;
            number = 1+random.nextInt(9);//0-8->1-9
            boolean isPossible = true;
            if (isInRow(number,row) || isInColumn(number,column) || isInSquare(number,row, column)){
                isPossible = false;
                i--;
            }
            if (isPossible == true) {
                board[index] = number;
            }
        }
    }
    
    public void fillBoard() {
        cellsToZero();//check if all of them are zeros
        createPuzzle(randomizeHints(17)); //here you can specify how many hints you want to give
    } 
    
    public void display(){
        for(int i = 0; i < 9;i++) {
            int[] row = new int[9];
            for(int j = 0; j < 9;j++) {
                row[j] = getCellValue(i*9+j);
            }
            System.out.println(Arrays.toString(row));
        }
    }
}
