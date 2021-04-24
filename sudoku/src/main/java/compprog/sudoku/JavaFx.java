package compprog.sudoku;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFx extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPne = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        Scene scene = new Scene(mainPne);
        stage.setScene(scene);
        stage.setTitle("sudoku");
        stage.show();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(sudokuSolver);
        board.solveGame();
        launch(args);
        FileSudokuBoardDao<SudokuBoard> dao = new FileSudokuBoardDao<>("file.txt");
        dao.write(board);
        SudokuBoard board1 = dao.read();
        String string = board1.toString();
        System.out.println(string);
    }
}
