package chessgame.domain.piece.kind.pawn;

import static chessgame.domain.piece.attribute.point.Movement.LEFT_UP;
import static chessgame.domain.piece.attribute.point.Movement.RIGHT_UP;
import static chessgame.domain.piece.attribute.point.Movement.UP;
import static chessgame.domain.piece.attribute.point.Movement.UP_UP;

import chessgame.domain.piece.Piece;
import chessgame.domain.piece.attribute.Color;
import chessgame.domain.piece.attribute.point.Movement;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.domain.piece.attribute.point.Rank;
import java.util.Set;

public class WhitePawn extends Pawn {
    protected WhitePawn(final Point point, final Color color) {
        super(point, color);
    }

    @Override
    protected Set<Movement> getAttackMovements() {
        return Set.of(RIGHT_UP, LEFT_UP);
    }

    @Override
    protected Movement getDoubleStep() {
        return UP_UP;
    }

    @Override
    protected Movement getSingleStep() {
        return UP;
    }

    @Override
    protected Rank getNeverMoveRank() {
        return Rank.TWO;
    }


    @Override
    protected Piece update(final Point point) {
        return new WhitePawn(point, color);
    }


}
