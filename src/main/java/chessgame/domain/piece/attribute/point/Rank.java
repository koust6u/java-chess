package chessgame.domain.piece.attribute.point;

import java.util.Arrays;

public enum Rank {
    ONE(1, 0),
    TWO(2, 1),
    THREE(3, 2),
    FOUR(4, 3),
    FIVE(5, 4),
    SIX(6, 5),
    SEVEN(7, 6),
    EIGHT(8, 7);
    private final int value;
    private final int order;

    Rank(final int value, final int order) {
        this.value = value;
        this.order = order;
    }

    public static Rank from(final int value) {
        return Arrays.stream(values())
                .filter(rank -> rank.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("%d는 랭크에 존재하지 않습니다.", value)));
    }

    public boolean isTop() {
        return order == values().length - 1;
    }

    public boolean isBottom() {
        return order == 0;
    }

    public boolean canMoveUp(final int step) {
        return order + step < values().length;
    }

    public boolean canMoveDown(final int step) {
        return order - step >= 0;
    }

    public Rank moveUp(final int step) {
        if (canMoveUp(step)) {
            return values()[order + step];
        }
        throw new IllegalStateException("이동할 수 없는 위치입니다.");
    }

    public Rank moveDown(final int step) {
        if (canMoveDown(step)) {
            return values()[order - step];
        }
        throw new IllegalStateException("이동할 수 없는 위치입니다.");
    }

    public int getValue() {
        return value;
    }
}
