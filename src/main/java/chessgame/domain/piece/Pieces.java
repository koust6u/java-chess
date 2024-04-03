package chessgame.domain.piece;

import chessgame.domain.piece.attribute.Color;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.domain.piece.kind.pawn.Pawn;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Pieces {
    private final Set<Piece> values;
    public Pieces() {
        this(Collections.emptySet());
    }

    public Pieces(final Pieces pieces) {
        this.values = new HashSet<>(pieces.values);
    }

    public Pieces(final Set<Piece> pieces) {
        this.values = new HashSet<>(pieces);
    }

    public boolean canReplace(final Piece piece, final Point endPoint) {
        return piece.findLegalMovePoints(this).contains(endPoint);
    }

    public void replace(final Piece piece, final Point endPoint) {
        values.removeIf(value -> value.isEqualPoint(endPoint));
        values.remove(piece);
        Piece moved = piece.move(endPoint);
        values.add(moved);
    }

    public Optional<Piece> findPieceWithPoint(final Point point) {
        return values.stream()
                .filter(piece -> piece.isEqualPoint(point))
                .findAny();
    }

    public double calculateTeamScore(final Color color) {
        return calculatePawnScore(color) + calculateScoreExceptPawn(color);
    }

    private double calculatePawnScore(final Color color) {
        return values.stream()
                .filter(piece -> piece.isSameColor(color))
                .filter(this::isPawn)
                .filter(this::hasSamePawnWithSameFile)
                .mapToDouble(pawn -> pawn.getScore().getValue() / 2)
                .sum();
    }

    private double calculateScoreExceptPawn(final Color color) {
        return values.stream()
                .filter(piece -> piece.isSameColor(color))
                .filter(piece -> !isPawn(piece))
                .mapToDouble(piece -> piece.getScore().getValue())
                .sum();
    }

    private boolean isPawn(final Piece piece) {
        return piece instanceof Pawn;
    }

    private boolean hasSamePawnWithSameFile(final Piece findPiece) {
        return values.stream()
                .filter(piece -> piece.isSameColor(findPiece.color))
                .filter(piece -> piece.isSameFile(findPiece))
                .anyMatch(piece -> !piece.equals(findPiece));
    }

    public boolean isCheckmate(final Color color) {
        final var kingPoint = findKingPoint(color);
        if (!canRemovable(kingPoint, color)) {
            return false;
        }
        return getLegalMovePoints(kingPoint)
                .stream()
                .allMatch(point -> canRemovable(point, color));
    }

    private Set<Point> getLegalMovePoints(Point point) {
        return findPieceWithPoint(point)
                .orElseThrow(IllegalStateException::new)
                .findLegalMovePoints(this);
    }

    public boolean hasNotKing(final Color color) {
        return this.values.stream()
                .filter(piece -> piece.isSameColor(color))
                .filter(this::isKing)
                .findAny()
                .isEmpty();
    }

    public boolean canRemovable(Point findPoint, Color color) {
        return values.stream()
                .filter(piece -> piece.isOpposite(color))
                .flatMap(piece -> piece.findLegalMovePoints(this).stream())
                .anyMatch(point -> point.equals(findPoint));
    }

    private Point findKingPoint(Color color) {
        return values.stream()
                .filter(piece -> piece.isSameColor(color))
                .filter(this::isKing)
                .findAny()
                .map(piece -> piece.point)
                .orElseThrow(IllegalStateException::new);
    }

    public boolean isKing(Piece piece) {
        return piece.isKing();
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

    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    public Set<Piece> getValues() {
        return Collections.unmodifiableSet(this.values);
    }

}
