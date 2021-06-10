package JavaFX.i18n;
import java.util.ListResourceBundle;

public class Bundle_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {
            { "authors"   , "Authors:" },
            { "author1"   , "Michał Augustyniak" },
            { "author2", "Łukasz Rembowski" },
            { "version"   , "Version:" },
            { "versionNumber", "1.0" },
            { "aboutText", "Sudoku game is a project made for Component programming classes." },
            { "noFile", "There is no such file. " },
            { "btn1Error", "Error occurred during changing stage! " },
    };
}
