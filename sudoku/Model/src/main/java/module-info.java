module Model {
    requires org.apache.commons.lang3;
    requires java.sql;
    requires org.slf4j;
    requires com.h2database;

    opens compprog.sudoku to org.apache.commons.lang3, javafx.base;
    exports compprog.sudoku to View;
    exports exceptions to View;
}