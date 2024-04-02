package chessgame.domain.piece.kind.pawn;

import static chessgame.domain.piece.attribute.point.Movement.DOWN;
import static chessgame.domain.piece.attribute.point.Movement.DOWN_DOWN;
import static chessgame.domain.piece.attribute.point.Movement.LEFT_DOWN;
import static chessgame.domain.piece.attribute.point.Movement.RIGHT_DOWN;

import chessgame.domain.piece.Piece;
import chessgame.domain.piece.attribute.Color;
import chessgame.domain.piece.attribute.point.Movement;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.domain.piece.attribute.point.Rank;
import java.util.Set;

public class BlackPawn extends Pawn {
    protected BlackPawn(final Point point, final Color color) {
        super(point, color);
    }

    @Override
    protected Set<Movement> getAttackMovements() {
        return Set.of(RIGHT_DOWN, LEFT_DOWN);
    }

    @Override
    protected Movement getDoubleStep() {
        return DOWN_DOWN;
    }

    @Override
    protected Movement getSingleStep() {
        return DOWN;
    }

    @Override
    protected Rank getNeverMoveRank() {
        return Rank.SEVEN;
    }

    @Override
    protected Piece update(final Point point) {
        return new BlackPawn(point, color);
    }

}
