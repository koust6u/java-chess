package chessgame.util;

import static chessgame.domain.piece.kind.Score.BISHOP;
import static chessgame.domain.piece.kind.Score.KING;
import static chessgame.domain.piece.kind.Score.KNIGHT;
import static chessgame.domain.piece.kind.Score.PAWN;
import static chessgame.domain.piece.kind.Score.QUEEN;
import static chessgame.domain.piece.kind.Score.ROOK;

import chessgame.domain.piece.Piece;
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
import java.util.function.BiFunction;

public class PieceTypeFinder {
    private static final Map<Score, BiFunction<Point, Color, Piece>> symbolToInstance
            = Map.of(
            BISHOP, Bishop::new,
            KING, King::new,
            ROOK, Rook::new,
            KNIGHT, Knight::new,
            QUEEN, Queen::new,
            PAWN, Pawn::from);

    public static Piece toPiece(final Score score, final Point point, final Color color) {
        return symbolToInstance.get(score).apply(point, color);
    }

}
