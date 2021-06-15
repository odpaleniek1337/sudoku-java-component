package gui;

import compprog.sudoku.JdbcSudokuBoardDao;
import compprog.sudoku.SudokuDifficulty;
import compprog.sudoku.SudokuLanguage;
import exceptions.DatabaseException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StageController {

    @FXML
    private Stage stage;
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private ToggleGroup languageGroup;
    @FXML
    private RadioMenuItem englishItem;
    @FXML
    private RadioMenuItem polskiItem;
    @FXML
    private ToggleGroup diffGroup;
    @FXML
    private ToggleButton easyBtn;
    @FXML
    private ToggleButton mediumBtn;
    @FXML
    private ToggleButton hardBtn;
    @FXML
    private Label aboutLabel;
    @FXML
    private Label notSelectedLabel;
    @FXML
    private ComboBox filenameComboBox;

    static String filename;

    static SudokuDifficulty diff;
    static SudokuLanguage language = SudokuLanguage.ENGLISH;
    static boolean loadingGame = false;
    static Locale locale;
    static Locale listLocale;

    /**
     * Initialize StageController.
     */
    @FXML
    public void initialize() throws IOException, DatabaseException {
        switch (language) {
            case POLSKI -> {
                polskiItem.setSelected(true);
                listLocale = new Locale("pl", "PL");
            }
            case ENGLISH -> {
                englishItem.setSelected(true);
                listLocale = new Locale("en", "EN");
            }
            default -> {
            }
        }
        ResourceBundle listBundle = ResourceBundle.getBundle("gui.i18n.Bundle", listLocale);
        aboutLabel.setText(listBundle.getObject("aboutText")
                + "\n"
                + listBundle.getObject("authors")
                + " " + listBundle.getObject("author1")
                + ", "
                + listBundle.getObject("author2")
                + "\n"
                + listBundle.getObject("version")
                + " "
                + listBundle.getObject("versionNumber"));

        //ObservableList<String> files = FXCollections.observableArrayList(
        //        findFiles(Paths.get("./"), "sudoku"));
        JdbcSudokuBoardDao dao = new JdbcSudokuBoardDao();
        ObservableList<String> files = FXCollections.observableArrayList(dao.getAllGames());
        filenameComboBox.setItems(files);
    }

    /**
     * Finds all sudoku saved games.
     *
     * @param path path to direction where sudoku saves are kept
     * @param fileExtension file extension of sudoku saved games
     * @return String List
     */
    public static List<String> findFiles(Path path, String fileExtension) throws IOException {
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be a directory!");
        }

        List<String> result;

        try (Stream<Path> walk = Files.walk(path)) {
            result = walk
                    .filter(p -> !Files.isDirectory(p))
                    .map(p -> p.getFileName().toString().toLowerCase())
                    .filter(f -> f.endsWith(fileExtension))
                    .collect(Collectors.toList());
        }

        return result;
    }

    /**
     * Sets proper language bundle.
     * @return bundle
     */
    public static ResourceBundle setBundle() {
        switch (language) {
            case ENGLISH -> locale = new Locale("en_UK");
            case POLSKI -> locale = new Locale("pl_PL");
            default -> { }
        }
        ResourceBundle bundle = ResourceBundle.getBundle("SudokuResource", locale);
        return bundle;
    }

    /**
     * Changing stage of our program for new game.
     *
     * @param event generated action
     */
    @FXML
    public void handleBtn1(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/SudokuGameView.fxml"), setBundle());
            root.setStyle("-fx-background-image: url('SudokuGameViewBG.png')");
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
           } catch (Exception exception) {
            Logger logger = LoggerFactory.getLogger(StageController.class);
            logger.warn(
                  (String) ResourceBundle.getBundle(
                      "javafx.i18n.Bundle", listLocale).getObject("btn1Error"));
        }
    }

    /**
     * Selecting saved sudoku game from ComboBox and checks if user selected any saved game.
     *
     * @param event generated action
     */
    @FXML void loadGame(ActionEvent event) throws Exception {
        try {
            filename = (String) filenameComboBox.getSelectionModel().getSelectedItem();
            if (!filenameComboBox.getSelectionModel().isEmpty()) {
                launchLoadedGame(event);
            } else {
                notSelectedLabel.setVisible(true);
            }
        } catch (IOException exception) {
            Logger logger = LoggerFactory.getLogger(StageController.class);
            logger.error("Cannot load game with given name!! - " + filename);
        }
    }

    /**
     * Changing stage of our program for load game.
     *
     * @param event generated action
     */
    @FXML
    public void launchLoadedGame(ActionEvent event) throws Exception {
        try {
            loadingGame = true;
            root = FXMLLoader.load(getClass().getResource("/SudokuGameView.fxml"), setBundle());
            root.setStyle("-fx-background-image: url('SudokuGameViewBG.png')");
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            throw new Exception("Error occurred during launching loaded game!!", exception);
        }
    }

    /**
     * Closing label with not selected any saved game information.
     */
    @FXML
    public void closeNotSelectedLabel() {
        notSelectedLabel.setVisible(false);
    }

    /**
     * Showing label with about information.
     */
    @FXML
    public void showAboutLabel() {
        aboutLabel.setVisible(true);
    }

    /**
     * Closing label with about information.
     */
    @FXML
    public void closeAboutLabel() {
        aboutLabel.setVisible(false);
    }

    /**
     * Reloading stage when language of application is changed.
     */
    public void reloadStage() throws Exception {
        try {
            stage = (Stage) root.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/MainView.fxml"), setBundle());
            root.setStyle("-fx-background-image: url('mainViewBG.png')");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            throw new Exception("Error occurred during reloading stage!!", exception);
        }
    }

    /**
     * Choosing difficulty of our game.
     */
    @FXML
    public void setDifficultyBtn() {
        diffGroup.getSelectedToggle();

        if (easyBtn.isSelected()) {
            diff = SudokuDifficulty.EASY;
        }
        if (mediumBtn.isSelected()) {
            diff = SudokuDifficulty.MEDIUM;
        }
        if (hardBtn.isSelected()) {
            diff = SudokuDifficulty.HARD;
        }
    }

    /**
     * Setting language of our application.
     */
    @FXML
    public void setLanguage() throws Exception {
        try {
            languageGroup.getSelectedToggle();
            if (englishItem.isSelected()) {
                language = SudokuLanguage.ENGLISH;
                reloadStage();
            }
            if (polskiItem.isSelected()) {
                language = SudokuLanguage.POLSKI;
                reloadStage();
            }
        } catch (Exception exception) {
            throw new Exception("Error occurred during setting language of the game!!", exception);
        }
    }
}