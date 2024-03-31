package chessgame.view;

import chessgame.domain.piece.attribute.Color;

public enum Winner {
    BLACK("흑"),
    WHITE("백"),
    DRAW("무승부"),
    UNDETERMINED("게임 진행중");

    private final String value;

    Winner(String value) {
        this.value = value;
    }

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

    public boolean isDraw() {
        return DRAW == this;
    }

    public String getValue() {
        return value;
    }
}
