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
    private Integer[] randomizeHints() {
        Integer[] array = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        Random dice = new Random();
        for (int j = 0; j < 17; j++) {
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
        return false;
    }
    
    private boolean isInColumn(int number, int columns){
        return false;
    }
    
    private boolean isInSquare(int number, int index){
        return false;
    }
    
    private void createPuzzle(Integer[] cells) {
        int index, column, row, number;
        Random random = new Random();
        for (int i = 0; i < 17; i++) {
            index = cells[i];
            column = index % 9;
            row = index / 9;
            number = random.nextInt(9);
            boolean isPossible = true;
            if (isInRow(number,row) || isInColumn(number,column) || isInSquare(number,index)){
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
        createPuzzle(randomizeHints());
    } 
    
    public void display(){
        for(int i = 0; i < 9;i++) {
            int[] row = new int[9];
            for(int j = 0; j < 9;j++) {
                row[j] = getCellValue(i*9+j);
            }
        }
    }
}
