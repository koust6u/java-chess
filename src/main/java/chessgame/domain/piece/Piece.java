package chessgame.domain.piece;

import static chessgame.domain.piece.kind.Score.BISHOP;
import static chessgame.domain.piece.kind.Score.KING;
import static chessgame.domain.piece.kind.Score.KNIGHT;
import static chessgame.domain.piece.kind.Score.PAWN;
import static chessgame.domain.piece.kind.Score.QUEEN;
import static chessgame.domain.piece.kind.Score.ROOK;

import chessgame.domain.piece.attribute.Color;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.domain.piece.kind.Score;

import chessgame.domain.piece.kind.jumping.King;
import chessgame.domain.piece.kind.jumping.Knight;
import chessgame.domain.piece.kind.pawn.Pawn;
import chessgame.domain.piece.kind.sliding.Bishop;
import chessgame.domain.piece.kind.sliding.Queen;
import chessgame.domain.piece.kind.sliding.Rook;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

public abstract class Piece {
    private static final Map<Score, BiFunction<Point, Color, Piece>> symbolToInstance
            = Map.of(
            BISHOP, Bishop::new,
            KING, King::new,
            ROOK, Rook::new,
            KNIGHT, Knight::new,
            QUEEN, Queen::new,
            PAWN, Pawn::from);


    protected Point point;
    protected final Color color;

    protected Piece(final Point point, final Color color) {
        this.point = point;
        this.color = color;
    }

    public static Piece from(final Score score, final Point point, final Color color) {
        return symbolToInstance.get(score).apply(point, color);
    }

    public abstract Score getScore();

    public Piece move(final Point destination) {
        validateSamePoint(destination);
        return update(destination);
    }

    private void validateSamePoint(final Point destination) {
        if (point.equals(destination)) {
            throw new IllegalArgumentException("동일한 위치로 이동할 수 없습니다.");
        }
    }

    protected abstract Set<Point> findLegalMovePoints(final Pieces pieces);

    protected abstract Piece update(final Point point);

    public boolean isEqualPoint(final Point point) {
        return this.point.equals(point);
    }

    public boolean isSameColor(final Piece piece) {
        return this.color.isSame(piece.color);
    }

    public boolean isSameColor(final Color color) {
        return this.color.isSame(color);
    }

    public boolean isOpposite(final Piece piece) {
        return this.color.isOpposite(piece.color);
    }

    public boolean isOpposite(final Color color) {
        return this.color.isOpposite(color);
    }

    public boolean isWhite() {
        return this.color.isWhite();
    }

    public boolean isSameFile(final Piece comparison) {
        return this.point.isSameFile(comparison.point);
    }

    public Point getPoint() {
        return point;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final Piece piece)) {
            return false;
        }
        return Objects.equals(point, piece.point) && color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, color);
    }

}
