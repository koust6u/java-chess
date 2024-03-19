package domain;

import domain.piece.Piece;
import domain.piece.Pieces;
import domain.piece.point.Point;

public class ChessBoard {
    private final Pieces pieces;

    public ChessBoard(final Pieces pieces) {
        this.pieces = pieces;
    }

    public Piece findPieceByPoint(final Point point) {
        final var piece = this.pieces.getPieceWithPoint(point);
        return piece.orElseThrow(() -> new IllegalArgumentException("해당 포인트에는 기물이 없습니다"));
    }
}