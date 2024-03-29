package chessgame.domain;

import static chessgame.view.ChessCommand.*;

import chessgame.dto.RouteDto;
import chessgame.view.InputView;
import java.util.Map;
import java.util.function.Consumer;
import chessgame.view.ChessCommand;
import chessgame.view.OutputView;

public class ChessGame {
    public void start() {
        final var chessBoard = new ChessBoard();
        var chessCommand = ChessCommand.PENDING;
        OutputView.printCommandOptions();
        while (chessCommand != END) {
            chessCommand = ExceptionHandler.handleInputWithRetry(() -> proceed(chessBoard));
        }
    }

    private ChessCommand proceed(final ChessBoard chessBoard) {
        final var chessCommand = InputView.inputChessCommand();
        if (chessCommand == END) {
            return END;
        }
        commandPerformances.get(chessCommand).accept(chessBoard);
        OutputView.printChessBoard(chessBoard.toDto());
        return chessCommand;
    }

    public static RouteDto askRoute() {
        final var source = ExceptionHandler.handleInputWithRetry(InputView::inputChessPoint);
        final var destination = ExceptionHandler.handleInputWithRetry(InputView::inputChessPoint);
        return new RouteDto(source, destination);
    }

}
