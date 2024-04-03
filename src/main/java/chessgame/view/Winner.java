package chessgame.view;

import chessgame.domain.piece.attribute.Color;

public enum Winner {
    BLACK,
    WHITE,
    DRAW,
    UNDETERMINED;

    public static Winner from(final double whiteScore, final double blackScore) {
        if (whiteScore < blackScore) {
            return BLACK;
        }
        if (whiteScore > blackScore) {
            return WHITE;
        }
        return DRAW;
    }

    public static Winner of(final Color color) {
        if (color.isBlack()) {
            return BLACK;
        }
        return WHITE;
    }

    public boolean isDraw() {
        return DRAW == this;
    }

    public boolean isDetermine() {
        return this != UNDETERMINED;
    }
}
