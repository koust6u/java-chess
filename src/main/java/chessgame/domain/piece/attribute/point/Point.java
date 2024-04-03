package chessgame.domain.piece.attribute.point;

import java.util.Objects;
import java.util.regex.Pattern;

public record Point(File file, Rank rank) {

    private static final Pattern pattern = Pattern.compile("[a-h][1-8]");

    public Point move(final Movement direction) {
        return moveVertical(direction.getY()).moveHorizontal(direction.geyX());
    }

    private Point moveVertical(final int step) {
        if (step > 0) {
            return moveUp(step);
        }
        if (step < 0) {
            return moveDown(-step);
        }
        return this;
    }

    private Point moveHorizontal(final int step) {
        if (step > 0) {
            return moveRight(step);
        }
        if (step < 0) {
            return moveLeft(-step);
        }
        return this;
    }

    public boolean canMove(final Movement direction) {
        return canVerticalMove(direction.getY()) && canHorizontalMove(direction.geyX());
    }

    private boolean canVerticalMove(final int step) {
        if (step > 0) {
            return canMoveUp(step);
        }
        if (step < 0) {
            return canMoveDown(-step);
        }
        return true;
    }

    private boolean canHorizontalMove(final int step) {
        if (step > 0) {
            return canMoveRight(step);
        }
        if (step < 0) {
            return canMoveLeft(-step);
        }
        return true;
    }


    public boolean canMoveUp(final int step) {
        return rank.canMoveUp(step);
    }

    public boolean canMoveDown(final int  step) {
        return rank.canMoveDown(step);
    }

    public boolean canMoveLeft(final int step) {
        return file.canMoveLeft(step);
    }

    public boolean canMoveRight(final int step) {
        return file.canMoveRight(step);
    }

    public Point moveUp(final int step) {
        return new Point(file, rank.moveUp(step));
    }

    public Point moveDown(final int step) {
        return new Point(file, rank.moveDown(step));
    }

    public Point moveLeft(final int step) {
        return new Point(file.moveLeft(step), rank);
    }

    public Point moveRight(final int step) {
        return new Point(file.moveRight(step), rank);
    }

    public boolean isSameFile(final Point other) {
        return this.file == other.file;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return file == point.file && rank == point.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }

}
