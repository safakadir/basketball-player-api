CREATE TABLE player (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    position VARCHAR(2) NOT NULL
);

CREATE TABLE player_history (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    player_id INTEGER NOT NULL,
    operation VARCHAR(10) NOT NULL,
    data TEXT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    username VARCHAR(50) NOT NULL
);

CREATE INDEX idx_player_history_player_id ON player_history (player_id);
