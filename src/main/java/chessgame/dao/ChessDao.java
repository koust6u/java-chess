package chessgame.dao;

import chessgame.domain.ChessBoard;
import chessgame.domain.piece.attribute.Color;
import java.sql.SQLException;

public class ChessDao {

    public static void saveGame(final ChessBoard chessBoard) {
        final var query = "INSERT INTO chess_game(turn) VALUES(?)";
        try (final var connection = ServiceConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, chessBoard.getTurn().name().toLowerCase());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("게임 저장에 실패했습니다.");
        }
    }

    public static Color findCurrentTurn() {
        final var topIndex = findTopIndex();
        final var query = "SELECT * FROM chess_game WHERE id = ?";
        try (final var connection = ServiceConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, topIndex);
            final var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Color.from(resultSet.getString("turn"));
        } catch (SQLException e) {
            throw new RuntimeException("게임 불러오기에 실패하였습니다.");
        }
    }

    public static int findTopIndex() {
        final var query = "SELECT id FROM chess_game ORDER BY id DESC LIMIT 1";
        try (final var connection = ServiceConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("id");
        } catch (SQLException e) {
            throw new RuntimeException("게임 불러오기에 실패하였습니다.");
        }
    }

    public void configuration() {
        if (isFirstGame()) {
            saveGame(ChessBoard.createDefaultBoard());
            PiecesDao.savePieces(ChessBoard.createDefaultBoard());
        }
    }


    public static boolean isFirstGame() {
        try (final var connection = ServiceConnector.getConnection();
             final var preparedStatement = connection.createStatement()) {
            final var sql = "SELECT COUNT(*) FROM chess_game";
            final var rs = preparedStatement.executeQuery(sql);
            rs.next();
            int rowCount = rs.getInt(1);
            return rowCount == 0;
        } catch (SQLException e) {
            throw new RuntimeException("게임 불러오기에 실패하였습니다.");
        }
    }

}
