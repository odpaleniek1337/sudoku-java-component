module Model {
    requires commons.lang3;

    opens compprog.sudoku to commons.lang3, javafx.base;
    exports compprog.sudoku to View;
    exports exceptions to View;
}