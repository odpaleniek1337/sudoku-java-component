package JavaFX;

import compprog.sudoku.BacktrackingSudokuSolver;
import compprog.sudoku.SudokuBoard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;

public class SudokuGameController {

    @FXML
    private GridPane boardGrid;

    private final List<Label> fields;
    private SudokuBoard board;

    public SudokuGameController() {
        fields = new ArrayList();
        for (int i = 0; i < 81; i++) {
            Label field = new Label();
            field.setText("0");
            fields.add(i, field);
        }
        board = new SudokuBoard(new BacktrackingSudokuSolver());
    }

    private void displaySudoku() {
        for (int i = 0; i < 81; i++) {
            int row = i / 9;
            int column = i % 9;
            fields.get(i).setText(String.valueOf(board.getCellValue(i)));

            if(board.getCellValue(i) != 0) {
                boardGrid.add(fields.get(i), column, row);
            }
        }
    }

    @FXML
    public void initialize() {
        board.solveGame();

        if (StageController.diff == null) {
            board.setBoardForGame(20);
        } else {
            switch(StageController.diff) {
                case EASY -> board.setBoardForGame(20);
                case MEDIUM -> board.setBoardForGame(35);
                case HARD -> board.setBoardForGame(50);
            }
        }

        displaySudoku();
    }
}