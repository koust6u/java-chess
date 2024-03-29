package chessgame.domain.piece.kind;

public enum PieceStatus {
    KING("k"),
    KNIGHT("n"),
    PAWN("p"),
    QUEEN("q"),
    ROOK("r"),
    BISHOP("b");

    private final String value;

    PieceStatus(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
