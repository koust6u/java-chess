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

    public ChessBoard(final Pieces pieces, final Color color) {
        this.turn = color;
        this.pieces = pieces;
    }

    public void reset() {
        this.pieces = ChessBoardGenerator.createDefaultPieces();
        this.turn = Color.WHITE;
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
        if (isGameOver()) {
            return Winner.of(turn);
        }
        turn = turn.getOpposite();
        return Winner.UNDETERMINED;
    }

    private void validate(final Piece piece, final Point endPoint) {
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
        final var whiteScore = pieces.calculateTeamScore(Color.WHITE);
        final var blackScore = pieces.calculateTeamScore(Color.BLACK);
        return Winner.from(whiteScore, blackScore);
    }

    public double findTotalScore(final Color color) {
        return pieces.calculateTeamScore(color);
    }

    public boolean isGameOver() {
        return pieces.isCheckmate(this.turn.getOpposite()) || pieces.hasNotKing(this.turn.getOpposite());
    }

    public static ChessBoard createDefaultBoard() {
        return ChessBoardGenerator.createDefaultBoard();
    }

    public Pieces getPieces() {
        return new Pieces(pieces);
    }

    public Color getTurn() {
        return turn;
    }

}
