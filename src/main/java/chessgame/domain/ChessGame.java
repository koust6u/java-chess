package chessgame.domain;

import static chessgame.view.ChessCommand.*;

import chessgame.dao.ChessDao;
import chessgame.domain.piece.attribute.Color;
import chessgame.dto.RouteDto;
import chessgame.view.InputView;
import chessgame.view.ChessCommand;
import chessgame.view.OutputView;
import chessgame.view.Winner;
import java.util.Map;
import java.util.function.Function;

public class ChessGame {
    public final Map<ChessCommand, Function<ChessBoard, ChessBoard>> commandPerformances
            = Map.of(START, this::start, MOVE, this::move,
            STATUS, this::getGameResult, END, this::finish);

    private final ChessDao chessDao;
    private ChessBoard chessBoard;

    public ChessGame(ChessDao chessDao) {
        this.chessDao = chessDao;
        chessBoard = new ChessBoard();
    }

    public void run() {
        var chessCommand = ChessCommand.PENDING;
        OutputView.printCommandOptions();
        while (!chessCommand.isEnd()) {
            chessCommand = ExceptionHandler.handleInputWithRetry(this::proceed);
        }
    }

    private ChessCommand proceed() {
        final var chessCommand = InputView.inputChessCommand();
        chessBoard = commandPerformances.get(chessCommand).apply(chessBoard);
        OutputView.printChessBoard(chessBoard);
        return chessCommand;
    }

    private ChessBoard start(ChessBoard chessBoard) {
        chessDao.configuration();
        return chessDao.findCurrentChessBoard();
    }

    private ChessBoard move(ChessBoard chessBoard) {
        chessBoard.move(askRoute());
        return chessBoard;
    }

    private ChessBoard finish(ChessBoard chessBoard) {
        chessDao.saveGame(chessBoard);
        chessDao.savePieces(chessBoard);
        System.exit(0);

        return chessBoard;
    }

    private ChessBoard resetGame(ChessBoard chessBoard) {
        chessBoard.reset();
        chessDao.saveGame(chessBoard);
        chessDao.savePieces(chessBoard);
        return chessBoard;
    }

    public RouteDto askRoute() {
        final var source = InputView.inputChessPoint();
        final var destination = InputView.inputChessPoint();
        return new RouteDto(source, destination);
    }

    public ChessBoard getGameResult(ChessBoard chessBoard) {
        double whiteScore = chessBoard.findTotalScore(Color.WHITE);
        double blackScore = chessBoard.findTotalScore(Color.BLACK);
        Winner winner = chessBoard.findWinner();
        OutputView.printGameResult(whiteScore, blackScore, winner);
        resetGame(chessBoard);
        System.exit(0);

        return chessBoard;
    }

}
