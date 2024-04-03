package chessgame.domain.piece.kind;

import java.util.Arrays;

public enum Score {
    KING(0, "k"),
    KNIGHT(2.5, "n"),
    PAWN(1, "p"),
    QUEEN(9, "q"),
    ROOK(5, "r"),
    BISHOP(3, "b");

    private final String symbol;
    private final double value;

    Score(final double score, final String symbol) {
        this.symbol = symbol;
        this.value = score;
    }

    public double getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }
}
