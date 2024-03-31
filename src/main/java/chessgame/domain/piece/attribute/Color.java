package chessgame.domain.piece.attribute;

public enum Color {
    BLACK,
    WHITE;

    public static Color from(String color) {
        if (color.equals("black")) {
            return BLACK;
        }
        if (color.equals("white")) {
            return WHITE;
        }
        throw new IllegalArgumentException("없는 색입니다. ");
    }

    public boolean isSame(Color color) {
        return this == color;
    }

    public boolean isOpposite(Color color) {
        return this != color;
    }

    public Color getOpposite() {
        if (this == WHITE) {
            return BLACK;
        }
        return WHITE;
    }

    public boolean isBlack() {
        return this == BLACK;
    }

    public boolean isWhite() {
        return this == WHITE;
    }
}
