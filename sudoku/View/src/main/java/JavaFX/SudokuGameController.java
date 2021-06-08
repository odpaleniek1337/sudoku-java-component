package JavaFX;

import compprog.sudoku.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import compprog.sudoku.SudokuBoardDaoFactory;
import exceptions.FileException;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SudokuGameController {

    @FXML
    private GridPane boardGrid;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField filenameField;
    @FXML
    private Label saveLabel;

    private final List<TextField> fields;
    private SudokuBoard board;
    private SudokuBoardDaoFactory factory;
    Dao<SudokuBoard> factoryDao;
    ResourceBundle bundle = StageController.setBundle();

    PauseTransition visiblePause = new PauseTransition(
            Duration.seconds(3)
    );

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
        visiblePause.setOnFinished(
                event -> saveLabel.setVisible(false)
        );
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
                fields.get(i).setStyle("-fx-opacity: 1;" + "-fx-background-color: rgb(189,77,0)");
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

    @FXML
    public void closeSaveLabel() {
        saveLabel.setVisible(false);
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
    private void saveSudoku() {
        try {
            if (!filenameField.getText().equals("")) {
                SudokuBoard saveBoard = gameToBoard();
                String filename = "./" + filenameField.getText() + ".sudoku";
                factoryDao = factory.getFileDao(filename);
                factoryDao.write(saveBoard);
                saveLabel.setText(bundle.getString("saveComplete"));
            } else {
                saveLabel.setText(bundle.getString("saveNoFilename"));
            }
            saveLabel.setVisible(true);
            visiblePause.play();
        } catch(IOException exception) {
            Logger logger = LoggerFactory.getLogger(StageController.class);
            logger.error("Error occurred during saving game!!");
        }
    }

    private SudokuBoard loadSudoku() throws Exception {
        try {
            String filename = StageController.filename;
            factoryDao = factory.getFileDao(filename);
            SudokuBoard boardRead = factoryDao.read();
            return boardRead;
        } catch (Exception exception) {
            throw new Exception("Error occurred during loading game!!", exception);
        }
    }

    @FXML
    public void initialize() throws Exception {
        try {
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
        } catch (Exception exception) {
            throw new Exception("Error occurred during initializing SudokuGameController!!", exception);
        }
    }
}