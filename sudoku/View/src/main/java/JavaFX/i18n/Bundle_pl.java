package JavaFX.i18n;
import java.util.ListResourceBundle;

public class Bundle_pl extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private Object[][] contents = {
            { "authors"   , "Autorzy:" },
            { "author1"   , "Michał Augustyniak" },
            { "author2", "Łukasz Rembowski" },
            { "version"   , "Wersja:" },
            { "versionNumber", "1.0" },
            { "aboutText", "Gra sudoku jest projektem zrealizowanym w ramach przedmiotu Component programming." },
            { "noFile", "Nie ma takiej nazwy pliku. " },
            { "btn1Error", "Wystąpił błąd podczas zmiany sceny! " },
    };
}