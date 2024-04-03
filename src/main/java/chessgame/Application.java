package chessgame;

import chessgame.dao.ChessDao;
import chessgame.domain.ChessGame;

public class Application {
    public static void main(String[] args) {
        final var chessGame = new ChessGame(new ChessDao());

        chessGame.run();
    }

}
