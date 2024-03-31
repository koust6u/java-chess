package chessgame.domain;

import chessgame.domain.piece.kind.Score;
import chessgame.domain.piece.kind.Winner;
import chessgame.domain.piece.kind.jumping.King;
import chessgame.domain.piece.kind.sliding.Queen;
import chessgame.domain.piece.kind.sliding.Rook;
import chessgame.dto.RouteDto;
import chessgame.fixture.PieceImpl;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import chessgame.domain.piece.attribute.Color;
import chessgame.domain.piece.Piece;
import chessgame.domain.piece.Pieces;
import chessgame.domain.piece.attribute.point.File;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.domain.piece.attribute.point.Rank;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static chessgame.domain.piece.kind.Score.*;
import static org.assertj.core.api.Assertions.assertThat;


class ChessBoardTest {
    @Test
    @DisplayName("기물들을 통해 체스판을 생성한다")
    void create_with_pieces() {
        final var pieces = new Pieces(Set.of(new PieceImpl(new Point(File.F, Rank.ONE), Color.BLACK),
                new PieceImpl(new Point(File.F, Rank.TWO), Color.BLACK)));

        final var sut = new ChessBoard(pieces);

        assertThat(sut).isInstanceOf(ChessBoard.class);
    }

    @Test
    @DisplayName("포인트에 기물이 있으면 기물을 반환한다.")
    void find_piece_with_point() {
        final var point = new Point(File.F, Rank.ONE);
        final var color = Color.BLACK;
        Set<Piece> pieceList = Set.of(new PieceImpl(point, color));
        final var pieces = new Pieces(pieceList);
        final var sut = new ChessBoard(pieces);

        final var result = sut.findPieceByPoint(point);

        assertThat(result).isEqualTo(new PieceImpl(point, color));
    }

    @Test
    @DisplayName("포인트에 기물이 없으면 예외를 발생한다.")
    void throw_exception_when_not_exist_point() {
        final var point = new Point(File.F, Rank.ONE);
        final var color = Color.BLACK;
        Set<Piece> pieceList = Set.of(new PieceImpl(point, color));
        final var pieces = new Pieces(pieceList);
        final var sut = new ChessBoard(pieces);
        final var notExistedPoint = new Point(File.D, Rank.FOUR);

        Assertions.assertThatThrownBy(() -> sut.findPieceByPoint(notExistedPoint))
                  .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("기본 체스판을 생성한다.")
    void create_default_board() {
        final var sut = ChessBoard.createDefaultBoard();

        final List<Rank> ranks = List.of(Rank.EIGHT, Rank.SEVEN, Rank.TWO, Rank.ONE);

        final var result = ranks.stream()
                                .map(rank -> getRankPieces(sut, rank))
                                .toList();


        List<Score> pieceList = List.of(ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK);
        List<Score> pawnList = IntStream.range(0, 8)
                                              .mapToObj(it -> PAWN)
                                              .toList();

        List<List<Score>> expected = List.of(pieceList, pawnList, pawnList, pieceList);
        assertThat(result).isEqualTo(expected);
    }

    private List<Score> getRankPieces(final ChessBoard chessBoard, final Rank rank) {
        return Arrays.stream(File.values())
                     .map(file -> new Point(file, rank))
                     .map(chessBoard::findPieceByPoint)
                     .map(Piece::getScore)
                     .toList();
    }

    @Test
    @DisplayName("기물들의 점수를 바탕으로 승리자를 가린다. (흑 승리)")
    void find_winner_by_each_team_score_black_win_case() {
        final var rook = new Rook(new Point(File.E, Rank.ONE), Color.WHITE);
        final var queen = new Queen(new Point(File.A, Rank.ONE), Color.BLACK);
        final var sut = new ChessBoard(new Pieces(Set.of(rook, queen)));

        assertThat(sut.findWinner()).isSameAs(Winner.BLACK);
    }


    @Test
    @DisplayName("기물들의 점수를 바탕으로 승리자를 가린다. (백 승리)")
    void find_winner_by_each_team_score_white_win_case() {
        final var rook = new Rook(new Point(File.E, Rank.ONE), Color.BLACK);
        final var queen = new Queen(new Point(File.A, Rank.ONE), Color.WHITE);
        final var sut = new ChessBoard(new Pieces(Set.of(rook, queen)));

        assertThat(sut.findWinner()).isSameAs(Winner.WHITE);
    }

    @Test
    @DisplayName("기물들의 점수를 바탕으로 승리자를 가린다. (무승부)")
    void find_winner_by_each_team_score_draw_case() {
        final var queen1 = new Queen(new Point(File.E, Rank.ONE), Color.BLACK);
        final var queen2 = new Queen(new Point(File.A, Rank.ONE), Color.WHITE);
        final var sut = new ChessBoard(new Pieces(Set.of(queen1, queen2)));

        assertThat(sut.findWinner()).isSameAs(Winner.DRAW);
    }


    @Test
    @DisplayName("체크메이트가 아니면 게임이 끝나지 않는다.")
    void game_over_if_king_dies_not_finish_case() {
        final var queen = new Queen(new Point(File.E, Rank.TWO), Color.WHITE);
        final var king = new King(new Point(File.A, Rank.ONE), Color.BLACK);
        final var sut = new ChessBoard(new Pieces(Set.of(queen, king)));
        assertThat(sut.move(new RouteDto("e2", "b2")))
                .isSameAs(Winner.UNDETERMINED);
    }

     /*
    ........ 8
    ........ 7
    ........ 6
    ........ 5
    ........ 4
    R....... 3
    XX..... 2
    kXQ..... 1
    abcdefgh
     */

    @Test
    @DisplayName("체크메이트가 되면 게임이 끝난다.")
    void game_over_if_king_dies() {
        final var queen = new Queen(new Point(File.D, Rank.ONE), Color.WHITE);
        final var rook = new Rook(new Point(File.A, Rank.THREE), Color.WHITE);
        final var king = new King(new Point(File.A, Rank.ONE), Color.BLACK);
        final var sut = new ChessBoard(new Pieces(Set.of(queen, rook, king)));

        assertThat(sut.move(new RouteDto("d1", "c1")))
                .isSameAs(Winner.WHITE);
    }
}

