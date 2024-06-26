package chessgame.domain.piece.kind.pawn;

import static chessgame.domain.piece.attribute.point.Movement.LEFT_UP;
import static chessgame.domain.piece.attribute.point.Movement.RIGHT_UP;
import static chessgame.domain.piece.attribute.point.Movement.UP;
import static chessgame.domain.piece.attribute.point.Movement.UP_UP;

import chessgame.domain.piece.Piece;
import chessgame.domain.piece.Pieces;
import chessgame.domain.piece.attribute.Color;
import chessgame.domain.piece.attribute.point.Movement;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.domain.piece.attribute.point.Rank;
import java.util.HashSet;
import java.util.Set;

public class WhitePawn extends Pawn {
    private static final Set<Movement> attackMovements = Set.of(RIGHT_UP, LEFT_UP);
    private static final Movement DOUBLE_STEP = UP_UP;
    private static final Movement NORMAL_STEP = UP;
    private static final Rank NEVER_MOVE_RANK = Rank.TWO;

    protected WhitePawn(final Point point, final Color color) {
        super(point, color);
    }

    @Override
    protected Set<Movement> getMovableDirection(final Pieces pieces) {
        final var availableMovement = new HashSet<Movement>();
        insertAbleToAttack(pieces, availableMovement);
        if (pieces.findPieceWithPoint(point.move(NORMAL_STEP)).isPresent()) {
            return availableMovement;
        }
        insertSpecialCase(pieces, availableMovement);
        availableMovement.add(NORMAL_STEP);
        return availableMovement;
    }

    @Override
    protected Piece update(final Point point) {
        return new WhitePawn(point, color);
    }

    private void insertAbleToAttack(final Pieces pieces, final HashSet<Movement> availableMovement) {
        attackMovements.stream()
                .filter(movement -> hasEnemy(pieces, movement))
                .forEach(availableMovement::add);
    }

    private void insertSpecialCase(final Pieces pieces, final HashSet<Movement> availableMovement) {
        if (point.rank() == NEVER_MOVE_RANK && pieces.findPieceWithPoint(point.move(DOUBLE_STEP)).isEmpty()) {
            availableMovement.add(DOUBLE_STEP);
        }
    }
}
