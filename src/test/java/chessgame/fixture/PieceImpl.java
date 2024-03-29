package chessgame.fixture;

import chessgame.domain.piece.Pieces;
import chessgame.domain.piece.attribute.Color;
import chessgame.domain.piece.Piece;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.domain.piece.kind.Score;
import java.util.Set;

public class PieceImpl extends Piece {
    public PieceImpl(final Point point, final Color color) {
        super(point, color);
    }

    @Override
    public Score getScore() {
        throw new UnsupportedOperationException("사용할 수 없습니다.");
    }

    @Override
    protected Set<Point> findLegalMovePoints(Pieces pieces) {
        return null;
    }

    @Override
    protected Piece update(Point point) {
        return null;
    }

}
