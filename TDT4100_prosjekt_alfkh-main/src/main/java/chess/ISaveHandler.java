package chess;

import java.io.FileNotFoundException;

import chess.model.ChessGame;

public interface ISaveHandler {

    public void save(ChessGame chessGame, String filename) throws FileNotFoundException;

    public ChessGame load(String filename) throws FileNotFoundException;
}
