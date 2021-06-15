package compprog.sudoku;

import exceptions.DatabaseException;
import exceptions.NoBoardTableException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private static final String USER = "game";
    private static final String PASS = "player";
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private final String dbUrl;
    private Connection connection;
    private Statement statement;
    private String sudokuBoardName;

    /**
     * Constructor of JdbcSudokuBoardDao class.
     * Manage connection with database.
     */
    public JdbcSudokuBoardDao() {
        this.dbUrl = "jdbc:h2:file:./sudoku/Model/src/main/resources/sudokuDatabase";

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
            logger.error("Class was not found", ex);
        }
        try {
            connection = DriverManager.getConnection(dbUrl, USER, PASS);
            statement = connection.createStatement();
        } catch (SQLException e) {
            Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
            logger.error("Cannot connect with the database url", e);
        }
    }

    /**
     * Constructor of JdbcSudokuBoardDao class.
     * Manage connection with database and sets sudokuBoard filename.
     */
    public JdbcSudokuBoardDao(String name) {

        this.sudokuBoardName = name;
        this.dbUrl = "jdbc:h2:file:./sudoku/Model/src/main/resources/sudokuDatabase";

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
            logger.error("Class was not found", ex);
        }
        try {
            connection = DriverManager.getConnection(dbUrl, USER, PASS);
            statement = connection.createStatement();
        } catch (SQLException e) {
            Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
            logger.error("Cannot connect with the database url", e);
        }
    }

    /**
     * Creating list with all games saved in database.
     * @return List.
     */
    public List getAllGames() throws DatabaseException {
        try {
            String sqlGetGames =
                    "SELECT name FROM games " + ";";
            ResultSet resultSet = statement.executeQuery(sqlGetGames);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            ArrayList<String> gamesList = new ArrayList<>(columnCount);
            while (resultSet.next()) {
                int i = 1;
                while (i <= columnCount) {
                    gamesList.add(resultSet.getString(i++));
                }
            }
            return gamesList;
        } catch (SQLException sqlException) {
            throw new DatabaseException();
        }
    }

    private int getBoardID() throws DatabaseException {
        try {
            int id = 0;
            String sqlGetID =
                    "SELECT id FROM games "
                            + "WHERE name = '" + sudokuBoardName + "';";

            ResultSet resultSet = statement.executeQuery(sqlGetID);
            if (resultSet.last()) {
                id = resultSet.getInt(1);
            }
            if (id == 0) {
                throw new NoBoardTableException();
            }
            return id;
        } catch (SQLException sqlException) {
            throw new DatabaseException();
        }
    }

    private void createTablesIfNotExist() {
        String createGamesTable =
                "CREATE TABLE IF NOT EXISTS Games "
                        + "(id int not null primary key AUTO_INCREMENT,"
                        + " name VARCHAR(100));";

        String createBoardsTable =
                "CREATE TABLE IF NOT EXISTS Boards "
                        + "(id int not null primary key AUTO_INCREMENT,"
                        + " field int,"
                        + " value int,"
                        + " is_editable bit,"
                        + " game_id int references Games(id));";

        try {
            statement.executeUpdate(createGamesTable);
            statement.executeUpdate(createBoardsTable);
            connection.commit();
        } catch (SQLException sqlException) {
            Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
            logger.error("Error in creating database when it does not exist", sqlException);
            if (connection != null) {
                try {
                    logger.info("Rolling back");
                    connection.rollback();
                } catch (SQLException sqlException2) {
                    logger.error("Rollback not possible", sqlException2);
                }
            }
        }
    }

    private void createGame() throws SQLException {
        String checkGame = "SELECT COUNT(id) FROM Games "
                + "WHERE name = '" + sudokuBoardName + "';";

        ResultSet checkGameResultSet;

        checkGameResultSet = statement.executeQuery(checkGame);
        int number = 0;

        if (checkGameResultSet.last()) {
            number = checkGameResultSet.getInt(1);
        }

        if (number == 0) {
            String createBoard = "INSERT INTO "
                    + "Games (name) "
                    + "VALUES ('" + sudokuBoardName + "');";

            statement.executeUpdate(createBoard);

            try {
                connection.commit();
            } catch (SQLException sqlException) {
                Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
                logger.error("Cannot commit changes", sqlException);
                if (connection != null) {
                    try {
                        logger.error("Rolling back", sqlException);
                        connection.rollback();
                    } catch (SQLException ex1) {
                        logger.error("Couldn't roll back :((((", sqlException);
                    }
                }
            }
        }
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.makeNewBoard();
        int idBoard = 0;

        try {
            idBoard = getBoardID();
        } catch (Exception exception) {
            Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
            logger.error("Could not read from that name", exception);
            throw new RuntimeException();
        }
        try {
            for (int x = 0; x < 81; x++) {
                String get =
                        "SELECT value, is_editable "
                                + "FROM boards WHERE field = " + x
                                + " AND game_id = " + idBoard;

                ResultSet result = statement.executeQuery(get);

                if (result.last()) {
                    sudokuBoard.setCellValue(x, result.getInt(1));
                    sudokuBoard.setEditable(x, result.getBoolean(2));
                }
            }
        } catch (SQLException sqlException) {
            Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
            logger.error("Error loading value from field", sqlException);
        }

        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj) {
        createTablesIfNotExist();
        try {
            createGame();
            saveSudokuFields(obj, getBoardID());
        } catch (SQLException | DatabaseException sqlException) {
            Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
            logger.error("Error in writing newGame to DB", sqlException);
            throw new RuntimeException();
        }
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
                logger.error("Cannot close connection!", sqlException);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException sqlException) {
                Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
                logger.error("Cannot close statement!", sqlException);
            }
        }
    }

    private void saveSudokuFields(SudokuBoard board, int boardId) {
        for (int i = 0; i < 81; i++) {
            String insert =
                    "INSERT INTO boards "
                            + "(field, value, is_editable, game_id) "
                            + "VALUES ("
                            + i + ", "
                            + board.getCellValue(i) + ", "
                            + board.getEditable(i) + ", "
                            + boardId + ");";

            try {
                statement.executeUpdate(insert);
            } catch (SQLException ex) {
                Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
                logger.error("There has been a problem with saving sudoku field values", ex);
            }
        }

        try {
            connection.commit();
        } catch (SQLException ex) {
            Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
            logger.error("There has been with committing connection", ex);

            if (connection != null) {
                try {
                    logger.error("Rolling back", ex);
                    connection.rollback();
                } catch (SQLException ex1) {
                    logger.error("Couldn't roll back :((((", ex);
                }
            }
        }
    }
}
