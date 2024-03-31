package chessgame.domain;

import static chessgame.view.ChessCommand.*;

import chessgame.domain.piece.attribute.Color;
import chessgame.dto.RouteDto;
import chessgame.view.InputView;
import chessgame.view.ChessCommand;
import chessgame.view.OutputView;
import chessgame.view.Winner;

public class ChessGame {

    public void start() {
        final var chessBoard = new ChessBoard();
        var chessCommand = ChessCommand.PENDING;
        OutputView.printCommandOptions();
        while (!chessCommand.isEnd()) {
            chessCommand = ExceptionHandler.handleInputWithRetry(() -> proceed(chessBoard));
        }
    }

    private ChessCommand proceed(final ChessBoard chessBoard) {
        final var chessCommand = InputView.inputChessCommand();
        commandPerformances.get(chessCommand).accept(chessBoard);
        OutputView.printChessBoard(chessBoard);
        return chessCommand;
    }

    public static RouteDto askRoute() {
        final var source = InputView.inputChessPoint();
        final var destination = InputView.inputChessPoint();
        return new RouteDto(source, destination);
    }

    public static void getGameResult(ChessBoard chessBoard) {
        double whiteScore = chessBoard.findTotalScore(Color.WHITE);
        double blackScore = chessBoard.findTotalScore(Color.BLACK);
        Winner winner = chessBoard.findWinner();
        OutputView.printGameResult(whiteScore, blackScore, winner);
        commandPerformances.get(END).accept(chessBoard);
    }
}
