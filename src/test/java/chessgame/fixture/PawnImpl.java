package chessgame.fixture;

import chessgame.domain.piece.Piece;
import chessgame.domain.piece.attribute.Color;
import chessgame.domain.piece.attribute.point.Movement;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.domain.piece.attribute.point.Rank;
import chessgame.domain.piece.kind.pawn.Pawn;
import java.util.Set;

public class PawnImpl extends Pawn {
    public PawnImpl(Point point, Color color) {
        super(point, color);
    }

    @Override
    protected Piece update(Point point) {
        throw new UnsupportedOperationException("잘못된 사용입니다.");
    }

    @Override
    protected Set<Movement> getAttackMovements() {
        throw new UnsupportedOperationException("잘못된 사용입니다.");
    }

    @Override
    protected Movement getDoubleStep() {
        throw new UnsupportedOperationException("잘못된 사용입니다.");
    }

    @Override
    protected Movement getSingleStep() {
        throw new UnsupportedOperationException("잘못된 사용입니다.");
    }

    @Override
    protected Rank getNeverMoveRank() {
        throw new UnsupportedOperationException("잘못된 사용입니다.");
    }
}
