package chessgame.view;

import static chessgame.domain.ChessGame.askRoute;

import chessgame.domain.ChessBoard;
import chessgame.domain.ChessGame;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

public enum ChessCommand {
    PENDING(""),
    START("start"),
    END("end"),
    MOVE("move"),
    STATUS("status");

    private final String commandText;

    public static final Map<ChessCommand, Consumer<ChessBoard>> commandPerformances
            = Map.of(START, ChessBoard::reset, MOVE, chessBoard -> chessBoard.move(askRoute()),
            STATUS, ChessGame::getGameResult, END, chessBoard ->  System.exit(0));

    ChessCommand(final String commandText) {
        this.commandText = commandText;
    }

    public static ChessCommand from(final String commandText) {
        return Arrays.stream(values())
                .filter(value -> value.commandText.equals(commandText))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s는 없는 명령입니다.", commandText)));
    }

    public boolean isEnd() {
        return this == END;
    }

    public boolean isStart() {
        return this == START;
    }
}
