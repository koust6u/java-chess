package domain.piece.attribute.point;

import java.util.function.UnaryOperator;

public enum Direction {
    UP(index -> new Index(index.nextVertical(), index.horizontal())),
    UP_RIGHT(index -> new Index(index.nextVertical(), index.nextHorizontal())),
    RIGHT(index -> new Index(index.vertical(), index.nextHorizontal())),
    DOWN_RIGHT(index -> new Index(index.prevVertical(), index.nextHorizontal())),
    DOWN(index -> new Index(index.prevVertical(), index.horizontal())),
    DOWN_LEFT(index -> new Index(index.prevVertical(), index.prevVertical())),
    LEFT(index -> new Index(index.vertical(), index.prevHorizontal())),
    UP_LEFT(index -> new Index(index.nextVertical(), index.prevHorizontal()));
    private UnaryOperator<Index> function;

    Direction(final UnaryOperator<Index> function) {
        this.function = function;
    }

    public Index move(Index index) {
        return function.apply(index);
    }
}