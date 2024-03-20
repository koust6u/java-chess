import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.piece.point.File;
import domain.piece.point.Point;
import domain.piece.point.Rank;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PointTest {
    @Test
    @DisplayName("File(행) 과 Rank(열) 을 통해 포인트를 만든다.")
    void create_with_file_and_rank() {
        final var sut = new Point(File.E, Rank.ONE);

        assertThat(sut).isEqualTo(new Point(File.E, Rank.ONE));
    }

    @Test
    @DisplayName("파일과 랭크가 포함된 문자열을 통해 포인트를 만든다.")
    void create_point_with_string_that_contain_file_and_rank() {
        final String value = "b2";

        final var sut = Point.from(value);

        assertThat(sut.file()).isEqualTo(File.B);
        assertThat(sut.rank()).isEqualTo(Rank.TWO);
    }

    @ParameterizedTest
    @ValueSource(strings = {"b22", "c", "b3c4", ""})
    @DisplayName("2글자가 아닌 문자열을 통해 포인트를 만들 시 예외를 발생시킨다.")
    void throw_exception_when_string_length_is_not_two(String invalidValue) {
        assertThatThrownBy(() -> Point.from(invalidValue))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"z3", "i9", "i1", "k0"})
    @DisplayName("파일 문자가 a~h를 벗어나거나 , 랭크 문자가 1~8 을 벗어나면 예외를 발생시킨다.")
    void throw_exception_when_file_or_rank_char_out_of_range(String invalidValue) {
        assertThatThrownBy(() -> Point.from(invalidValue))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
