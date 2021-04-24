package compprog.sudoku;

import java.io.*;

public class FileSudokuBoardDao<SudokuBoard> implements Dao<SudokuBoard>, AutoCloseable{
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
