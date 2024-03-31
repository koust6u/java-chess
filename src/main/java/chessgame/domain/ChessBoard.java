package chessgame.domain;

import chessgame.domain.piece.Piece;
import chessgame.domain.piece.Pieces;
import chessgame.domain.piece.attribute.Color;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.view.Winner;
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
        this.turn = Color.WHITE;
        this.pieces = pieces;
    }

    public void reset() {
        this.pieces = ChessBoardGenerator.createDefaultPieces();
    }

    public Piece findPieceByPoint(final Point point) {
        return this.pieces.findPieceWithPoint(point)
                .orElseThrow(() -> new IllegalArgumentException("해당 포인트에는 기물이 없습니다"));
    }

    public Winner move(final RouteDto dto) {
        final var piece = findPieceByPoint(dto.getStartPoint());
        final var endPoint = dto.getEndPoint();
        validate(piece, endPoint);
        pieces.replace(piece, endPoint);
        turn = turn.getOpposite();
        if (isGameOver()) {
            return Winner.of(turn.getOpposite());
        }
        return Winner.UNDETERMINED;
    }

    private void validate(Piece piece, Point endPoint) {
        validateGameProceed();
        validateCorrectTurn(piece);
        validateCanReplace(piece, endPoint);
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

    private void validateCanReplace(final Piece piece, final Point endPoint) {
        if (!pieces.canReplace(piece, endPoint)) {
            throw new IllegalArgumentException(
                    String.format("%s로 이동할 수 없습니다.", endPoint));
        }
    }


    public Winner findWinner() {
        double whiteScore = pieces.calculateTeamScore(Color.WHITE);
        double blackScore = pieces.calculateTeamScore(Color.BLACK);
        return Winner.from(whiteScore, blackScore);
    }

    public double findTotalScore(Color color) {
        return pieces.calculateTeamScore(color);
    }

    public boolean isGameOver() {
        return pieces.isCheckmate(this.turn);
    }

    public static ChessBoard createDefaultBoard() {
        return ChessBoardGenerator.createDefaultBoard();
    }

    public Pieces getPieces() {
        return new Pieces(pieces);
    }
}
