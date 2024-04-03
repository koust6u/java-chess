package chessgame.dao;

import chessgame.domain.ChessBoard;
import chessgame.domain.piece.Piece;
import chessgame.domain.piece.Pieces;
import chessgame.domain.piece.attribute.Color;
import chessgame.domain.piece.attribute.point.File;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.domain.piece.attribute.point.Rank;
import chessgame.domain.piece.kind.Score;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PiecesDao {

    public static void savePieces(final ChessBoard chessBoard) {
        final var topIndex = ChessDao.findTopIndex();
        chessBoard.getPieces().getValues()
                .forEach(piece -> savePiece(piece, topIndex));
    }

    private static void savePiece(final Piece piece, final int chessGameId) {
        final var query = "INSERT INTO pieces(chess_game_id, file, piece_rank, symbol, color) VALUES(?,?,?,?,?)";
        try (final var connection = JdbcConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, chessGameId);
            preparedStatement.setString(2, piece.getPoint().file().name());
            preparedStatement.setInt(3, piece.getPoint().rank().getValue());
            preparedStatement.setString(4, piece.getScore().getSymbol());
            preparedStatement.setString(5, piece.getColor().name().toLowerCase());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("게임 저장에 실패하였습니다.");
        }
    }

    public static ChessBoard findCurrentChessBoard() {
        int topIndex = ChessDao.findTopIndex();
        String query = "SELECT * FROM pieces WHERE chess_game_id = ?";
        try (final var connection = JdbcConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, topIndex);
            ResultSet resultSet = preparedStatement.executeQuery();
            return new ChessBoard(new Pieces(generatePieces(resultSet)), ChessDao.findCurrentTurn());
        } catch (SQLException e) {
            throw new RuntimeException("게임 불러오기에 실패했습니다.");
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


}
