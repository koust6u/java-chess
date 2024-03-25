package domain.piece.attribute.point;

import util.DirectionUtil;

import java.util.regex.Pattern;

public record Point(File file, Rank rank) {

    private static final Pattern pattern = Pattern.compile("[a-h][1-8]");

    public boolean canMoveUp() {
        return rank.canMoveUp(1);
    }

    public boolean canMoveDown() {
        return rank.canMoveDown(1);
    }

    public boolean canMoveLeft() {
        return file.canMoveLeft(1);
    }

    public boolean canMoveRight() {
        return file.canMoveRight(1);
    }

    public Point moveUp() {
        return new Point(file, rank.moveUp(1));
    }

    public Point moveDown() {
        return new Point(file, rank.moveDown(1));
    }

    public Point moveLeft() {
        return new Point(file.moveLeft(), rank);
    }

    public Point moveRight() {
        return new Point(file.moveRight(), rank);
    }

    public Point moveLeftUp() {
        return new Point(file.moveLeft(), rank.moveUp());
    }

    public Point moveRightUp() {
        return new Point(file.moveRight(), rank.moveUp());
    }

    public Point moveLeftDown() {
        return new Point(file.moveLeft(), rank.moveDown());
    }

    public Point moveRightDown() {
        return new Point(file.moveRight(), rank().moveDown());
    }

    public int getFileIndex() {
        return this.file.ordinal();
    }

    public int getRankIndex() {
        return this.rank.ordinal();
    }

    public static Point fromIndex(final Index index) {
        if (!index.isInBoundary()) {
            throw new IllegalArgumentException("파일과 랭크의 범위를 벗어났습니다.");
        }
        return new Point(File.findByIndex(index.horizontal()), Rank.findByIndex(index.vertical()));
    }

    public Direction calculate(final Point point) {
        return DirectionUtil.determineDirection(this, point);
    }


    public static Point from(final String value) {
        validate(value);
        final var file = File.from(value.charAt(0));
        final var rank = Rank.from(Integer.parseInt(value.substring(1)));
        return new Point(file, rank);
    }

    private static void validate(final String value) {
        final var matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("파일은 a~h이고, 랭크는 아래부터 위로 1~8까지입니다.");
        }
    }

    public Index toIndex() {
        return new Index(rank.ordinal(), file.ordinal());
    }
}
