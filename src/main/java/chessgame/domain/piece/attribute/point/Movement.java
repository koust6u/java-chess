package chessgame.domain.piece.attribute.point;

public enum Movement {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    RIGHT_UP(1, 1),
    LEFT_UP(-1, 1),
    RIGHT_DOWN(1, -1),
    LEFT_DOWN(-1, -1),
    UP_UP(0, 2),
    DOWN_DOWN(0, -2),
    LEFT_UP_UP(-1, 2),
    RIGHT_UP_UP(1, 2),
    LEFT_DOWN_DOWN(-1, -2),
    RIGHT_DOWN_DOWN(1, -2),
    LEFT_LEFT_UP(-2, 1),
    LEFT_LEFT_DOWN(-2, -1),
    RIGHT_RIGHT_UP(2, 1),
    RIGHT_RIGHT_DOWN(2, -1);

    private final int x;
    private final int y;

    Movement(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    int x() {
        return x;
    }

    int y() {
        return y;
    }
}
