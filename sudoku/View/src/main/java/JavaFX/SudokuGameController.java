package JavaFX;

import compprog.sudoku.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import compprog.sudoku.SudokuBoardDaoFactory;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;


public class SudokuGameController {

    @FXML
    private GridPane boardGrid;

    @FXML
    private TextArea textArea;

    private final List<TextField> fields;
    private SudokuBoard board;
    private SudokuBoardDaoFactory factory;
    Dao<SudokuBoard> factoryDao = factory.getFileDao("sudokuState.txt");
    ResourceBundle bundle = StageController.setBundle();

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
        factory = new SudokuBoardDaoFactory();
        board = new SudokuBoard(new BacktrackingSudokuSolver());
    }

    private void displaySudoku() {
        boardGrid.setStyle("-fx-background-image: url('background_board.PNG')");

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
        SudokuBoard boardCheck = gameToBoard();
        boardCheck.display();
        if (boardCheck.checkBoard()) {
            textArea.setText(bundle.getString("correctBoardText"));
        } else {
            textArea.setText(bundle.getString("wrongBoardText"));
        }
    }

    private SudokuBoard gameToBoard() {
        List<Integer> fieldsChecked = new ArrayList<Integer>();
        SudokuBoard newBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 81; i++) {
            TextField cell = (TextField) boardGrid.getChildren().get(i);
            if (cell.getText().matches("[1-9]")) {
                fieldsChecked.add(Integer.parseInt(cell.getText()));
            } else {
                fieldsChecked.add(0);
                cell.setText("");
            }
        }
        newBoard.makeNewBoard(fieldsChecked);
        return newBoard;
    }

    @FXML
    private void saveSudoku() throws IOException, ClassNotFoundException {
        SudokuBoard saveBoard = gameToBoard();
        factoryDao.write(saveBoard);
    }

    private SudokuBoard loadSudoku() throws IOException, ClassNotFoundException {
        SudokuBoard boardRead = factoryDao.read();
        return boardRead;
    }

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        if (!StageController.loadingGame) {
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
        } else {
            board = loadSudoku();
        }
        displaySudoku();
    }
}