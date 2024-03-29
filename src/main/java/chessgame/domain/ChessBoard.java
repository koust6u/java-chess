package chessgame.domain;

import chessgame.domain.piece.Piece;
import chessgame.domain.piece.Pieces;
import chessgame.domain.piece.attribute.Color;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.domain.piece.kind.Winner;
import chessgame.dto.RouteDto;
import chessgame.factory.ChessBoardGenerator;

public class ChessBoard {
    private Pieces pieces;
    private Color turn;

    public ChessBoard() {
        turn = Color.WHITE;
        this.pieces = new Pieces();
    }

    public ChessBoard(final Pieces pieces) {
        this.pieces = pieces;
    }

    public void reset() {
        this.pieces = ChessBoardGenerator.createDefaultPieces();
    }

    public Piece findPieceByPoint(final Point point) {
        return this.pieces.findPieceWithPoint(point)
                .orElseThrow(() -> new IllegalArgumentException("해당 포인트에는 기물이 없습니다"));
    }

    public void move(final RouteDto dto) {
        validateGameProceed();
        final var startPoint = dto.getStartPoint();
        final var endPoint = dto.getEndPoint();
        final var piece = findPieceByPoint(startPoint);
        validateCorrectTurn(piece);
        validateCanReplace(piece, endPoint, startPoint);
        pieces.replace(piece, endPoint);
        turn = turn.getOpposite();
    }

    private void validateCorrectTurn(final Piece piece) {
        if (!piece.isSameColor(turn)) {
            throw new IllegalStateException("현재는 %s의 턴입니다.".formatted(turn.name()));
        }
    }

    private void validateGameProceed() {
        if (pieces.isEmpty()) {
            throw new IllegalStateException("게임을 시작하고 기물 이동을 요청해주새요.");
        }
    }

    private void validateCanReplace(final Piece piece, final Point endPoint, final Point startPoint) {
        if (!pieces.canReplace(piece, endPoint)) {
            throw new IllegalArgumentException(
                    String.format("%s 는 %s 에서 %s로 이동할 수 없습니다.", piece.getScore(), startPoint, endPoint));
        }
    }

    public Winner findWinner() {
        double whiteScore = pieces.calculateTeamScore(Color.WHITE);
        double blackScore = pieces.calculateTeamScore(Color.BLACK);
        return Winner.from(whiteScore, blackScore);
    }

    public static ChessBoard createDefaultBoard() {
        return ChessBoardGenerator.createDefaultBoard();
    }

}
