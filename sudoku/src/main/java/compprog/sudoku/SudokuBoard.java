package compprog.sudoku;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SudokuBoard {
    private int[] board = new int[81];
    
    public void fillBoard() {
        // Randomize first row
        Integer[] intArray = {1,2,3,4,5,6,7,8,9};
        List<Integer> firstRow = Arrays.asList(intArray);
        Collections.shuffle(firstRow);
        System.out.println(Arrays.toString(intArray));
        
        //append firstRow to board
        for(int i=0;i<9;i++){
            board[i] = intArray[i];
        }
        //System.out.println(Arrays.toString(board));
        
        for(int j=1;j<9;j++){
            Integer[] newArray = intArray.clone();
            List<Integer> nextRow = Arrays.asList(newArray);
            Collections.shuffle(nextRow);
            for(int k=0;k<9;k++){
                
            }
            System.out.println(Arrays.toString(newArray));
        }
    } 
}
