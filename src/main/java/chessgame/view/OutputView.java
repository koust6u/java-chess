package chessgame.view;

import static chessgame.domain.piece.kind.Score.BISHOP;
import static chessgame.domain.piece.kind.Score.KING;
import static chessgame.domain.piece.kind.Score.KNIGHT;
import static chessgame.domain.piece.kind.Score.PAWN;
import static chessgame.domain.piece.kind.Score.QUEEN;
import static chessgame.domain.piece.kind.Score.ROOK;

import chessgame.domain.ChessBoard;
import chessgame.domain.piece.Piece;
import chessgame.domain.piece.Pieces;
import chessgame.domain.piece.attribute.Color;
import chessgame.domain.piece.attribute.point.File;
import chessgame.domain.piece.attribute.point.Point;
import chessgame.domain.piece.attribute.point.Rank;
import chessgame.domain.piece.kind.Score;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class OutputView {

    private static final Map<Score, String> pieceText = Map.of(QUEEN, "q", KING, "k", ROOK, "r", BISHOP, "b", KNIGHT,
            "n", PAWN, "p");

    private OutputView() {
        throw new UnsupportedOperationException("생성할 수 없습니다.");
    }

    public static void printCommandOptions() {
        System.out.println("> 체스 게임을 시작합니다.");
        System.out.println("> 게임 시작 : start");
        System.out.println("> 게임 결과 : status");
        System.out.println("> 게임 종료 : end");
        System.out.print("> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public static void printExceptionMessage(final String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void printGameResult(double whiteScore, double blackScore, Winner winner) {
        System.out.printf("백의 정수는 %.1f입니다.%n", whiteScore);
        System.out.printf("흑의 점수는 %.1f입니다.%n", blackScore);
        if (winner.isDraw()) {
            System.out.println("무승부입니다. 게임의 승자는 없습니다.");
            return;
        }
        System.out.printf("따라서 게임의 승자는 %s입니다%n", winner);
    }

    public static void printChessBoard(ChessBoard chessBoard) {
        Arrays.stream(Rank.values())
                .sorted(Comparator.reverseOrder())
                .map(rank -> printChessBoardEachLine(chessBoard, rank))
                .forEach(System.out::println);
    }

    private static String printChessBoardEachLine(ChessBoard chessBoard, Rank rank) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(File.values())
                .map(file -> new Point(file, rank))
                .map(point ->  toUppercase(chessBoard, point, getEachPointText(chessBoard, point)))
                .forEach(sb::append);

        return sb.toString();
    }

    private static String toUppercase(ChessBoard chessBoard, Point point, String text) {
        Optional<Piece> pieceWithPoint = chessBoard.getPieces().findPieceWithPoint(point);
        if (pieceWithPoint.isPresent() && pieceWithPoint.get().isSameColor(Color.BLACK)) {
            return text.toUpperCase();
        }
        return text;
    }

    private static String getEachPointText(ChessBoard chessBoard, Point point) {
        Pieces pieces = chessBoard.getPieces();
        Optional<Piece> pieceWithPoint = pieces.findPieceWithPoint(point);
        if (pieceWithPoint.isEmpty()) {
            return ".";
        }
        return pieceText.get(pieceWithPoint.get().getScore());
    }
}
