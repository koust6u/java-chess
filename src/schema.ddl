CREATE TABLE chess_game (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            turn ENUM('white', 'black')
);

CREATE TABLE pieces (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        chess_game_id INT,
                        FOREIGN KEY (chess_game_id) REFERENCES chess_game(id),
                        file VARCHAR(1),
                        symbol VARCHAR(5),
                        piece_rank INT,
                        color ENUM('white', 'black');
);
