package gui;

import compprog.sudoku.BacktrackingSudokuSolver;
import compprog.sudoku.Dao;
import compprog.sudoku.JdbcSudokuBoardDao;
import compprog.sudoku.SudokuBoard;
import compprog.sudoku.SudokuBoardDaoFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;
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

    private List<TextField> fields;
    private SudokuBoard board;
    private SudokuBoardDaoFactory factory;
    Dao<SudokuBoard> factoryDao;
    ResourceBundle bundle = StageController.setBundle();

    PauseTransition visiblePause = new PauseTransition(
            Duration.seconds(3)
    );

    /**
     * Creating, setting and insert text fields to a gridPane.
     * Here program is binding SudokuBoard with gridPane fields and
     * set textFields textFormatter for allowing only one [1-9] digit.
     */
    @FXML
    public void initialize() throws NoSuchMethodException {
        fields = new ArrayList();
        for (int i = 0; i < 81; i++) {
            TextField field = new TextField();
            field.setMaxHeight(25);
            field.setMaxWidth(25);
            field.setAlignment(Pos.CENTER);
            field.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
                if (!change.getText().matches("[1-9]")) {
                    change.setText("");
                }
                return change;
            }));
            Bindings.bindBidirectional(field.textProperty(),
                    JavaBeanIntegerPropertyBuilder.create()
                            .bean(board.getSudokuField(i))
                            .name("value")
                            .build(), new NumberStringConverter());
            int temp = i;
            field.textProperty().addListener((
                    ObservableValue<? extends String> observable, String oldVal, String newVal) -> {
                if (newVal.length() > 1) {
                    Platform.runLater(() -> {
                        field.setText("");
                    });
                    board.setCellValue(temp, 0);
                }
            });

            fields.add(i, field);
        }
        //factory = new SudokuBoardDaoFactory();
        visiblePause.setOnFinished(
                event -> saveLabel.setVisible(false)
        );
        displaySudoku();
    }

    /**
     * Checking what type of game program should load (new or load)
     * and setting appropriate difficulty in case of new game.
     */
    public SudokuGameController() {
        try {
            if (!StageController.loadingGame) {
                board = new SudokuBoard(new BacktrackingSudokuSolver());
                board.solveGame();

                if (StageController.diff == null) {
                    board.setBoardForGame(20);
                } else {
                    switch (StageController.diff) {
                        case EASY -> board.setBoardForGame(20);
                        case MEDIUM -> board.setBoardForGame(35);
                        case HARD -> board.setBoardForGame(50);
                        default -> { }
                    }
                }
            } else {
                board = loadDB();
            }
        } catch (Exception exception) {
            try {
                throw new Exception("Error during init SudokuGameController!!", exception);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void displaySudoku() {
        board.display();
        boardGrid.setStyle("-fx-background-image: url('background_board.PNG')");
        for (int i = 0; i < 81; i++) {
            int row = i / 9;
            int column = i % 9;

            if (board.getCellValue(i) == 0) {
                fields.get(i).setText("");
            }
            if (!board.getEditable(i)) {
                fields.get(i).setDisable(true);
                fields.get(i).setStyle("-fx-opacity: 1;" + "-fx-background-color: rgb(189,77,0)");
            }
            boardGrid.add(fields.get(i), column, row);
        }
    }

    @FXML
    private void checkSudoku() {
        board.display();
        if (board.checkBoard()) {
            textArea.setText(bundle.getString("correctBoardText"));
        } else {
            textArea.setText(bundle.getString("wrongBoardText"));
        }
    }

    @FXML
    public void closeSaveLabel() {
        saveLabel.setVisible(false);
    }

    @FXML
    private void saveSudoku() {
        try {
            if (!filenameField.getText().equals("")) {
                String filename = "./" + filenameField.getText() + ".sudoku";
                factoryDao = factory.getFileDao(filename);
                factoryDao.write(board);
                saveLabel.setText(bundle.getString("saveComplete"));
            } else {
                saveLabel.setText(bundle.getString("saveNoFilename"));
            }
            saveLabel.setVisible(true);
            visiblePause.play();
        } catch (IOException exception) {
            Logger logger = LoggerFactory.getLogger(StageController.class);
            logger.error("Error occurred during saving game!!");
        }
    }

    @FXML
    private void saveDB() throws Exception {
            if (!filenameField.getText().equals("")) {
                String filename = filenameField.getText();
                JdbcSudokuBoardDao dao =
                        (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getJdbcSudokuBoardDao(filename);
                dao.write(board);
                saveLabel.setText(bundle.getString("saveComplete"));
            } else {
                saveLabel.setText(bundle.getString("saveNoFilename"));
            }
            saveLabel.setVisible(true);
            visiblePause.play();
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

    /**
     * Loads sudoku game from database.
     */
    public SudokuBoard loadDB() throws Exception {
        try {
            String filename = StageController.filename;
            JdbcSudokuBoardDao dao =
                     (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getJdbcSudokuBoardDao(filename);
            SudokuBoard boardRead = dao.read();
            return boardRead;
        } catch (Exception exception) {
            throw new Exception("Error occurred during loading game!!", exception);
        }
    }
}