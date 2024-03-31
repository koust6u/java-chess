package chessgame.domain.piece;

import chessgame.domain.piece.kind.jumping.King;
import chessgame.domain.piece.kind.jumping.Knight;
import chessgame.domain.piece.kind.sliding.Bishop;
import chessgame.domain.piece.kind.sliding.Queen;
import chessgame.domain.piece.kind.sliding.Rook;
import chessgame.fixture.PawnImpl;
import chessgame.fixture.PieceImpl;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import chessgame.domain.piece.attribute.Color;
import chessgame.domain.piece.attribute.point.File;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.domain.piece.attribute.point.Rank;

import static chessgame.domain.piece.kind.Score.QUEEN;
import static chessgame.domain.piece.kind.Score.ROOK;
import static org.assertj.core.api.Assertions.assertThat;

class
PiecesTest {

    @Test
    @DisplayName("기물 목록을 포함한 일급컬렉션을 생성한다.")
    void create_with_PieceList() {
        Set<Piece> pieceList = Set.of(new PieceImpl(new Point(File.F, Rank.ONE), Color.BLACK),
                new PieceImpl(new Point(File.B, Rank.ONE), Color.WHITE));

        final var sut = new Pieces(pieceList);

        assertThat(sut).isInstanceOf(Pieces.class);
    }

    @Test
    @DisplayName("해당 기물이 특정 기물들 사이에서 이동할 수 없으면 거짓을 반환한다.")
    void false_if_bishop_piece_can_move() {
        final var sut = new Pieces(Set.of(
                new Bishop(new Point(File.A, Rank.ONE), Color.BLACK),
                new Bishop(new Point(File.C, Rank.THREE), Color.WHITE),
                new Queen(new Point(File.E, Rank.FIVE), Color.WHITE)));

        final var piece = sut.findPieceWithPoint(new Point(File.A, Rank.ONE))
                .get();

        final var result = sut.canReplace(piece, new Point(File.E, Rank.FIVE));
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("해당 기물이 특정 기물들 사이에서 이동할 수 없으면 거짓을 반환한다.")
    void false_if_rook_piece_can_move() {
        final var sut = new Pieces(Set.of(
                new Rook(new Point(File.A, Rank.ONE), Color.BLACK),
                new Bishop(new Point(File.C, Rank.ONE), Color.WHITE),
                new Queen(new Point(File.E, Rank.FIVE), Color.WHITE)));

        final var piece = sut.findPieceWithPoint(new Point(File.A, Rank.ONE))
                .get();

        final var result = sut.canReplace(piece, new Point(File.E, Rank.ONE));
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("해당 기물이 특정 기물들 사이에서 이동할 수 없으면 거짓을 반환한다.")
    void false_if_queen_piece_can_move() {
        final var sut = new Pieces(Set.of(
                new Queen(new Point(File.A, Rank.ONE), Color.BLACK),
                new Bishop(new Point(File.C, Rank.THREE), Color.WHITE),
                new Queen(new Point(File.E, Rank.FIVE), Color.WHITE)));

        final var piece = sut.findPieceWithPoint(new Point(File.A, Rank.ONE))
                .get();

        final var result = sut.canReplace(piece, new Point(File.E, Rank.FIVE));
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("해당 퀸이 특정 기물들 사이에서 이동할 수 있으면 참을 반환한다.")
    void true_if_queen_piece_can_move() {

        final var sut = new Pieces(Set.of(
                new Queen(new Point(File.A, Rank.ONE), Color.BLACK),
                new Bishop(new Point(File.C, Rank.ONE), Color.WHITE),
                new Queen(new Point(File.E, Rank.ONE), Color.WHITE)));

        final var piece = sut.findPieceWithPoint(new Point(File.A, Rank.ONE))
                .get();

        final var result = sut.canReplace(piece, new Point(File.A, Rank.THREE));
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("나이트는 도착하는 위치에 아군 기물이 있으면 거짓을 반환한다.")
    void false_if_knight_piece_move_friend_point() {
        final var sut = new Pieces(Set.of(
                new Knight(new Point(File.A, Rank.ONE), Color.BLACK),
                new Queen(new Point(File.C, Rank.TWO), Color.BLACK)));

        final var piece = sut.findPieceWithPoint(new Point(File.A, Rank.ONE))
                .get();

        final var result = sut.canReplace(piece, new Point(File.C, Rank.TWO));
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("킹은 도착하는 위치에 아군 기물이 있으면 거짓을 반환한다.")
    void false_if_king_piece_move_friend_point() {
        final var sut = new Pieces(Set.of(
                new King(new Point(File.A, Rank.ONE), Color.BLACK),
                new Queen(new Point(File.B, Rank.TWO), Color.BLACK)));

        final var piece = sut.findPieceWithPoint(new Point(File.A, Rank.ONE))
                .get();

        final var result = sut.canReplace(piece, new Point(File.B, Rank.TWO));
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("킹은 도착하는 위치에 아군 기물이 아니면 참을 반환한다.")
    void true_if_king_piece_move_friend_point() {
        final var sut = new Pieces(Set.of(
                new King(new Point(File.A, Rank.ONE), Color.BLACK),
                new Queen(new Point(File.B, Rank.TWO), Color.WHITE)));

        final var piece = sut.findPieceWithPoint(new Point(File.A, Rank.ONE))
                .orElseThrow();

        final var result = sut.canReplace(piece, new Point(File.B, Rank.TWO));
        assertThat(result).isTrue();
    }


    @Test
    @DisplayName("기물을 해당 좌표로 이동시킨다.")
    void move_piece_to_point() {
        final var sut = new Pieces(Set.of(
                new Knight(new Point(File.A, Rank.ONE), Color.BLACK),
                new Queen(new Point(File.C, Rank.TWO), Color.BLACK)));

        final var piece = sut.findPieceWithPoint(new Point(File.A, Rank.ONE)).orElseThrow();

        sut.replace(piece, new Point(File.B, Rank.THREE));

        final var findPiece = sut.findPieceWithPoint(new Point(File.B, Rank.THREE))
                .orElseThrow();
        assertThat(findPiece).isEqualTo(new Knight(new Point(File.B, Rank.THREE), Color.BLACK));
    }

    @Test
    @DisplayName("흑/백 색깔을 바탕으로 해당 팀의 총점을 계산한다.")
    void calculate_team_total_score_by_color() {
        // Given
        final var sut = new Pieces(Set.of(new Queen(new Point(File.A, Rank.ONE), Color.WHITE),
                new Rook(new Point(File.A, Rank.TWO), Color.WHITE),
                new Rook(new Point(File.F, Rank.ONE), Color.BLACK)));

        // When
        final var totalScore = sut.calculateTeamScore(Color.WHITE);

        // Then
        assertThat(totalScore).isEqualTo(QUEEN.getValue() + ROOK.getValue());
    }

    @Test
    @DisplayName("같은 file에 두개 이상의 폰이 존재할시 각 0.5점으로 점수를 계산한다.")
    void score_is_one_point_five_score_when_exist_more_then_two_pawns_on_the_same_file() {
        //Give
        final var specialCaseScore = 0.5;
        final var sut = new Pieces(Set.of(new PawnImpl(new Point(File.A, Rank.ONE), Color.WHITE),
                new PawnImpl(new Point(File.A, Rank.TWO), Color.WHITE),
                new Rook(new Point(File.F, Rank.ONE), Color.BLACK)));


        // When
        final var totalScore = sut.calculateTeamScore(Color.WHITE);

        // Then
        assertThat(totalScore).isEqualTo(specialCaseScore * 2);
    }

    @Test
    @DisplayName("기물의 색을 기준으로 해당 킹이 체크 상태인지 알 수 있다.(참)")
    void can_know_current_king_is_check_by_color_return_true_case() {
        final var sut = new Pieces(Set.of(new Rook(new Point(File.A, Rank.ONE), Color.WHITE),
                new King(new Point(File.A, Rank.EIGHT), Color.BLACK)));

        assertThat(sut.canRemovable(new Point(File.A, Rank.EIGHT), Color.BLACK)).isTrue();
    }


    @Test
    @DisplayName("기물의 색을 기준으로 해당 킹이 체크 상태인지 알 수 있다.(거짓)")
    void can_know_current_king_is_check_by_color_return_false_case() {
        final var sut = new Pieces(Set.of(new Rook(new Point(File.A, Rank.ONE), Color.WHITE),
                new King(new Point(File.C, Rank.EIGHT), Color.BLACK)));

        assertThat(sut.canRemovable(new Point(File.C, Rank.EIGHT), Color.BLACK)).isFalse();
    }



    /*
   ........ 8
   ........ 7
   ........ 6
   ........ 5
   ..XXX... 4
   ..XXX... 3
   ..XXX... 2
   ........ 1
   abcdefgh
     */
    @Test
    @DisplayName("기물의 색을 기준으로 해당 킹이 체크메이트 상태인지 알 수 있다.(참)")
    void can_know_current_king_is_checkmate_by_color_return_true_case() {
        final var sut = new Pieces(Set.of(new King(new Point(File.D, Rank.THREE), Color.WHITE),
                new Rook(new Point(File.C, Rank.TWO), Color.BLACK),
                new Rook(new Point(File.E, Rank.FOUR), Color.BLACK),
                new Bishop(new Point(File.E, Rank.TWO), Color.BLACK)));

        assertThat(sut.canRemovable(new Point(File.D, Rank.THREE), Color.WHITE)).isTrue();
    }


    /*
   ........ 8
   ........ 7
   ........ 6
   ........ 5
   ..XXX... 4
   ..XKX... 3
   ..XXX... 2
   ........ 1
   abcdefgh
     */
    @Test
    @DisplayName("기물의 색을 기준으로 해당 킹이 체크메이트 상태인지 알 수 있다.(거짓)")
    void can_know_current_king_is_checkmate_by_color_return_false_case() {
        final var sut = new Pieces(Set.of(new King(new Point(File.D, Rank.THREE), Color.WHITE),
                new Rook(new Point(File.C, Rank.TWO), Color.BLACK),
                new Rook(new Point(File.E, Rank.FOUR), Color.BLACK)));

        assertThat(sut.canRemovable(new Point(File.D, Rank.THREE), Color.WHITE)).isFalse();
    }
}
