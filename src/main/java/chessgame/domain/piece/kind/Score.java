package chessgame.domain.piece.kind;

public enum Score {
    KING(0),
    KNIGHT(2.5),
    PAWN(1),
    QUEEN(9),
    ROOK(5),
    BISHOP(3);

    private final double value;

    Score(final double score) {
        this.value = score;
    }


    public double getValue() {
        return value;
    }
}
