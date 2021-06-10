package JavaFX;

import compprog.sudoku.SudokuDifficulty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import compprog.sudoku.SudokuLanguage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    @FXML
    public void initialize() throws IOException {
        switch (language) {
            case POLSKI -> {
                polskiItem.setSelected(true);
                listLocale = new Locale("pl", "PL");
            }
            case ENGLISH -> {
                englishItem.setSelected(true);
                listLocale = new Locale("en", "EN");
            }
        }
        ResourceBundle listBundle = ResourceBundle.getBundle("JavaFX.i18n.Bundle", listLocale);
        aboutLabel.setText(listBundle.getObject("aboutText") + "\n" + listBundle.getObject("authors") + " " + listBundle.getObject("author1") +
                ", " +listBundle.getObject("author2") + "\n" + listBundle.getObject("version") + " " + listBundle.getObject("versionNumber"));

        ObservableList<String> files = FXCollections.observableArrayList(findFiles(Paths.get("./"), "sudoku"));
        filenameComboBox.setItems(files);
    }

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

    public static ResourceBundle setBundle() {
        switch (language) {
            case ENGLISH -> locale = new Locale("en_UK");
            case POLSKI -> locale = new Locale("pl_PL");
        }
        ResourceBundle bundle = ResourceBundle.getBundle("SudokuResource", locale);
        return bundle;
    }

    @FXML
    public void handleBtn1(ActionEvent event) throws IOException {
 //       try {
            root = FXMLLoader.load(getClass().getResource("/SudokuGameView.fxml"), setBundle());
            root.setStyle("-fx-background-image: url('SudokuGameViewBG.png')");
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
//           } catch (Exception exception) {
//            Logger logger = LoggerFactory.getLogger(StageController.class);
//            logger.warn((String) ResourceBundle.getBundle("JavaFX.i18n.Bundle", listLocale).getObject("btn1Error"));
//        }
    }

    @FXML void loadGame(ActionEvent event) throws Exception {
        try {
            filename = (String)filenameComboBox.getSelectionModel().getSelectedItem();
            if (!filenameComboBox.getSelectionModel().isEmpty()) {
                launchLoadedGame(event);
            } else {
                notSelectedLabel.setVisible(true);
            }
        } catch(IOException exception) {
            Logger logger = LoggerFactory.getLogger(StageController.class);
            logger.error("Cannot load game with given name!! - " + filename);
        }
    }


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

    @FXML
    public void closeNotSelectedLabel() {
        notSelectedLabel.setVisible(false);
    }

    @FXML
    public void showAboutLabel() {
        aboutLabel.setVisible(true);
    }

    @FXML
    public void closeAboutLabel() {
        aboutLabel.setVisible(false);
    }

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