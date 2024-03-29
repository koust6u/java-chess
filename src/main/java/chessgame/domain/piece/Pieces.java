package chessgame.domain.piece;

import chessgame.domain.piece.attribute.point.Point;
import chessgame.dto.PiecesDto;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Pieces {
    private final Set<Piece> values;

    public Pieces() {
        this(Collections.emptySet());
    }

    public Pieces(final Set<Piece> pieces) {
        this.values = new HashSet<>(pieces);
    }

    public boolean canReplace(final Piece piece, final Point endPoint) {
        return piece.findLegalMovePoints(this).contains(endPoint);
    }

    public void replace(final Piece piece, final Point endPoint) {
        final var pieceWithPoint = findPieceWithPoint(endPoint);
        pieceWithPoint.ifPresent(values::remove);
        values.remove(piece);
        Piece moved = piece.move(endPoint);
        values.add(moved);
    }

    public Optional<Piece> findPieceWithPoint(final Point point) {
        return values.stream()
                .filter(piece -> piece.isEqualPoint(point))
                .findAny();
    }

    public boolean isTeam(final Piece piece, final Point point) {
        final var optionalPiece = findPieceWithPoint(point);
        return optionalPiece.filter(piece::isSameColor).isPresent();
    }

    public boolean hasPiece(final Point endPoint) {
        return this.findPieceWithPoint(endPoint)
                .isPresent();
    }

    public boolean hasNothing(final Point endPoint) {
        return this.findPieceWithPoint(endPoint)
                .isEmpty();
    }

    public PiecesDto toDto() {
        return new PiecesDto(values.stream()
                .map(Piece::toDto)
                .toList());
    }

    public boolean isEmpty() {
        return this.values.isEmpty();
    }
}
