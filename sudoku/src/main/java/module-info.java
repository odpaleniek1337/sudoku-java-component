module sudoku {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires commons.lang3;

    opens compprog.sudoku to commons.lang3;
    exports compprog.sudoku to javafx.graphics;
}