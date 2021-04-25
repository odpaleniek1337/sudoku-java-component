package compprog.sudoku;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSudokuBoardDao<SudokuBoard> implements Dao<SudokuBoard>, AutoCloseable {
    private String filename;

    public FileSudokuBoardDao(String filename) {
        this.filename = filename;
    }

    @Override
    public void write(SudokuBoard obj) throws IOException {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filename))) {
            writer.writeObject(obj);
        }
    }

    @Override
    public SudokuBoard read() throws IOException, ClassNotFoundException {
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(filename))) {
            return (SudokuBoard) reader.readObject();
        }
    }

    @Override
    public void close() {
        System.out.println("Closing");
    }
}
