package chessgame.domain.piece.attribute.point;

import java.util.Arrays;

public enum File {
    A('a', 0),
    B('b', 1),
    C('c', 2),
    D('d', 3),
    E('e', 4),
    F('f', 5),
    G('g', 6),
    H('h', 7);
    private final char value;
    private final int order;

    File(final char value, final int order) {
        this.value = value;
        this.order = order;
    }

    public static File from(final char value) {
        return Arrays.stream(values())
                .filter(file -> file.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("%c는 파일에 존재하지 않습니다.", value)));
    }


    public static File from(final String value) {
        final var charValue = value.toLowerCase().charAt(0);
        return Arrays.stream(values())
                .filter(file -> file.value == charValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s는 파일에 존재하지 않습니다.", value)));
    }
    public boolean isFarLeft() {
        return order == 0;
    }

    public boolean isFarRight() {
        return order == values().length - 1;
    }

    public boolean canMoveLeft(final int step) {
        return order - step >= 0;
    }

    public boolean canMoveRight(final int step) {
        return order + step < values().length;
    }

    public File moveLeft(final int step) {
        if (canMoveLeft(step)) {
            return values()[order - step];
        }
        throw new IllegalStateException("움직일 수 없는 위치입니다.");
    }

    public File moveRight(final int step) {
        if (canMoveRight(step)) {
            return values()[order + step];
        }
        throw new IllegalStateException("움직일 수 없는 위치입니다.");
    }

}
