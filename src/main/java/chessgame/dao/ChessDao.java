package chessgame.dao;

import chessgame.domain.ChessBoard;
import chessgame.domain.piece.Piece;
import chessgame.domain.piece.Pieces;
import chessgame.domain.piece.attribute.Color;
import chessgame.domain.piece.attribute.point.File;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.domain.piece.attribute.point.Rank;
import chessgame.domain.piece.kind.Score;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ChessDao {
    public static final String URL = "jdbc:mysql://localhost:13306/chess";
    public static final String USER = "user";
    public static final String PASSWORD = "password";

    private Connection getConnection() {
        loadDriver();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void saveGame(final ChessBoard chessBoard) {
        final var query = "INSERT INTO chess_game(turn) VALUES(?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, chessBoard.getTurn().name().toLowerCase());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void savePieces(final ChessBoard chessBoard) {
        final var topIndex = findTopIndex();
        chessBoard.getPieces().stream()
                .forEach(piece -> savePiece(piece, topIndex));
    }

    private void savePiece(final Piece piece, final int chessGameId) {
        final var query = "INSERT INTO pieces(chess_game_id, file, piece_rank, symbol, color) VALUES(?,?,?,?,?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, chessGameId);
            preparedStatement.setString(2, piece.getPoint().file().name());
            preparedStatement.setInt(3, piece.getPoint().rank().getValue());
            preparedStatement.setString(4, piece.getScore().getSymbol());
            preparedStatement.setString(5, piece.getColor().name().toLowerCase());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ChessBoard findCurrentChessBoard() {
        final var topIndex = findTopIndex();
        final var currentTurn = findCurrentTurn();
        final var query = "SELECT * FROM pieces WHERE chess_game_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, topIndex);
            final var resultSet = preparedStatement.executeQuery();
            return new ChessBoard(new Pieces(generatePieces(resultSet)), currentTurn);
        } catch (SQLException e) {
            return ChessBoard.createDefaultBoard();
        }
    }

    private Color findCurrentTurn() {
        final var topIndex = findTopIndex();
        final var query = "SELECT * FROM chess_game WHERE id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, topIndex);
            final var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Color.from(resultSet.getString("turn"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Set<Piece> generatePieces(final ResultSet resultSet) throws SQLException {
        final var pieceSet = new HashSet<Piece>();
        while (resultSet.next()) {
            final var file = File.from(resultSet.getString("file"));
            final var rank = Rank.from(resultSet.getInt("piece_rank"));
            final var color = Color.from(resultSet.getString("color"));
            final var symbol = Score.from(resultSet.getString("symbol"));
            final var generatedPiece = Piece.from(symbol, new Point(file, rank), color);
            pieceSet.add(generatedPiece);
        }
        return pieceSet;
    }

    private int findTopIndex() {
        final var query = "SELECT id FROM chess_game ORDER BY id DESC LIMIT 1";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void configuration() {
        if (isFirstGame()) {
            saveGame(ChessBoard.createDefaultBoard());
            savePieces(ChessBoard.createDefaultBoard());
        }
    }

    private boolean isFirstGame() {
        try (final var conn = getConnection();
             final var preparedStatement = conn.createStatement()) {
            final var sql = "SELECT COUNT(*) FROM chess_game";
            final var rs = preparedStatement.executeQuery(sql);
            rs.next();
            int rowCount = rs.getInt(1);
            return rowCount == 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
