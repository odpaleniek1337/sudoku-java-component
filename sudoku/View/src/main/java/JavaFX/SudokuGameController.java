package JavaFX;

import compprog.sudoku.BacktrackingSudokuSolver;
import compprog.sudoku.SudokuBoard;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SudokuGameController {

    @FXML
    private GridPane boardGrid;

    @FXML
    private TextArea textArea;

    private final List<TextField> fields;
    private SudokuBoard board;

    public SudokuGameController() {
        fields = new ArrayList();
        for (int i = 0; i < 81; i++) {
            TextField field = new TextField();

            field.setMaxHeight(25);
            field.setMaxWidth(25);
            field.setAlignment(Pos.CENTER);
            field.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if ((!field.getText().matches("[1-9]")) || field.getText().length() > 1) {
                    Platform.runLater(() -> {
                        field.setText("");
                    });
                }
            });

            fields.add(i, field);
        }
        board = new SudokuBoard(new BacktrackingSudokuSolver());
    }

    private void displaySudoku() {
        for (int i = 0; i < 81; i++) {
            int row = i / 9;
            int column = i % 9;
            fields.get(i).setText(String.valueOf(board.getCellValue(i)));
            if (board.getCellValue(i) == 0) {
                fields.get(i).setText("");
            } else {
                fields.get(i).setDisable(true);
                fields.get(i).setStyle("-fx-opacity: 1;");
            }
            boardGrid.add(fields.get(i), column, row);
        }
    }

    @FXML
    private void checkSudoku() {
        List<Integer> fieldsChecked = new ArrayList<Integer>();
        SudokuBoard newBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 1; i < 82; i++) {
            TextField cell = (TextField) boardGrid.getChildren().get(i);
            if (cell.getText().matches("[1-9]")) {
                fieldsChecked.add(Integer.parseInt(cell.getText()));
            } else {
                fieldsChecked.add(0);
                cell.setText("");
            }
        }

        newBoard.makeNewBoard(fieldsChecked);
        newBoard.display();
        if (newBoard.checkBoard()) {
            textArea.setText("Congratulations you have correctly completed Sudoku!");
        } else {
            textArea.setText("Something is wrong! :(");
        }
    }

    @FXML
    public void initialize() {
        board.solveGame();

        if (StageController.diff == null) {
            board.setBoardForGame(20);
        } else {
            switch (StageController.diff) {
                case EASY -> board.setBoardForGame(20);
                case MEDIUM -> board.setBoardForGame(35);
                case HARD -> board.setBoardForGame(50);
            }
        }

        displaySudoku();
    }
}