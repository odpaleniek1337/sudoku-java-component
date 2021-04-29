package compprog.sudoku;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {
    @Override
    public boolean solve(SudokuBoard board) {
        int row = 0;
        int col = 0;
        boolean isEmpty = false;
        Integer[] intArray = {1,2,3,4,5,6,7,8,9};
        List<Integer> listRow = Arrays.asList(intArray);
        Collections.shuffle(listRow);
        
        //Alghorithm starts here:
        for (row = 1;row < 9;row++) {
            for (col = 0;col < 9;col++) {
                if (board.getCellValue(row * 9 + col) == 0) {
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
            if (!board.isInRow(number,row) 
                    && !board.isInColumn(number,col) 
                    && !board.isInSquare(number,row,col)) {
                board.setCellValue(row * 9 + col, number);
            
            if (solve(board)) {
               return true;
            }
            board.setCellValue(row * 9 + col, 0);
            }
        }
        return false;
    }
}
