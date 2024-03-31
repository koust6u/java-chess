package chessgame.domain.piece.kind;

import chessgame.domain.piece.attribute.Color;

public enum Winner {
    BLACK,
    WHITE,
    DRAW,
    UNDETERMINED;

    public static Winner from(double whiteScore, double blackScore) {
        if (whiteScore < blackScore) {
            return BLACK;
        }
        if (whiteScore > blackScore) {
            return WHITE;
        }
        return DRAW;
    }

    public static Winner of(Color color) {
        if (color.isBlack()) {
            return BLACK;
        }
        return WHITE;
    }
}
