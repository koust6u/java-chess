package chessgame.domain.piece.kind.pawn;

import chessgame.domain.piece.attribute.point.Movement;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.domain.piece.Piece;
import chessgame.domain.piece.Pieces;
import chessgame.domain.piece.attribute.Color;

import chessgame.domain.piece.attribute.point.Rank;
import chessgame.domain.piece.kind.Score;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Pawn extends Piece {
    protected Pawn(final Point point, final Color color) {
        super(point, color);
    }

    public static Pawn from(final Point point, final Color color) {
        if (color.isBlack()) {
            return new BlackPawn(point, color);
        }
        return new WhitePawn(point, color);
    }

    @Override
    protected Set<Point> findLegalMovePoints(final Pieces pieces) {
        return getMovableDirection(pieces).stream()
                .filter(direction -> super.point.canMove(direction))
                .map(direction -> super.point.move(direction))
                .filter(point -> !pieces.isTeam(this, point))
                .collect(Collectors.toSet());

    }

    protected abstract Set<Movement> getAttackMovements();

    protected abstract Movement getDoubleStep();

    protected abstract Movement getSingleStep();

    protected abstract Rank getNeverMoveRank();

    public Set<Movement> getMovableDirection(final Pieces pieces) {
        final var availableMovement = new HashSet<Movement>();
        insertAbleToAttack(pieces, availableMovement);
        if (pieces.findPieceWithPoint(point.move(getSingleStep())).isPresent()) {
            return availableMovement;
        }

        insertSpecialCase(pieces, availableMovement);
        availableMovement.add(getSingleStep());
        return availableMovement;
    }


    private void insertAbleToAttack(final Pieces pieces, final HashSet<Movement> availableMovement) {
        getAttackMovements().stream()
                .filter(movement -> point.canMove(movement))
                .filter(movement -> hasEnemy(pieces, movement))
                .forEach(availableMovement::add);
    }

    private void insertSpecialCase(final Pieces pieces, final HashSet<Movement> availableMovement) {
        if (point.rank() == getNeverMoveRank() && pieces.findPieceWithPoint(point.move(getDoubleStep())).isEmpty()) {
            availableMovement.add(getDoubleStep());
        }
    }

    protected final boolean hasEnemy(final Pieces pieces, final Movement movement) {
        if (!point.canMove(movement)) {
            return false;
        }
        return pieces.findPieceWithPoint(point.move(movement))
                .filter(this::isOpposite)
                .isPresent();
    }

    @Override
    public boolean isPawn() {
        return true;
    }

    @Override
    public Score getScore() {
        return Score.PAWN;
    }

}
