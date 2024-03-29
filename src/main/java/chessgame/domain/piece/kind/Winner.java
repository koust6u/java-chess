package chessgame.domain.piece.kind;

public enum Winner {
    BLACK,
    WHITE,
    DRAW;

    public static Winner from(double whiteScore, double blackScore) {
        if (whiteScore < blackScore) {
            return BLACK;
        }
        if (whiteScore > blackScore) {
            return WHITE;
        }
        return DRAW;
    }
}
