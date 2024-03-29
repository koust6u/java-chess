package chessgame;

import chessgame.domain.ChessGame;

public class Application {
    public static void main(String[] args) {
        final var chessGame = new ChessGame();

        chessGame.start();
    }
}
