package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chess.model.ChessGame;
import chess.model.Player;

public class SaveHandlerTest {
    private Player player1 = new Player("Alf", 'W', true);;
    private Player player2 = new Player("Kristian", 'B', false);;
    private ChessGame chessGame = new ChessGame(player1, player2);;
    private SaveHandler saveHandler = new SaveHandler();;

    @Test
    public void testSaveToFile() {
        Assertions.assertDoesNotThrow(() -> {
            saveHandler.save(chessGame, "testCodeFile");
        });
    }

    @Test
    public void testReadFromFile() throws IOException{

        ChessGame newChessGame = saveHandler.load("testCodeFile");

        assertEquals(chessGame.getPlayerWhite().getName(), newChessGame.getPlayerWhite().getName());
        assertEquals(chessGame.getPlayerWhite().getColor(), newChessGame.getPlayerWhite().getColor());
        assertEquals(chessGame.getPlayerWhite().getTurn(), newChessGame.getPlayerWhite().getTurn());

        assertEquals(chessGame.getPlayerBlack().getName(), newChessGame.getPlayerBlack().getName());
        assertEquals(chessGame.getPlayerBlack().getColor(), newChessGame.getPlayerBlack().getColor());
        assertEquals(chessGame.getPlayerBlack().getTurn(), newChessGame.getPlayerBlack().getTurn());

        assertEquals(chessGame.getPiecesInPlay().size(), newChessGame.getPiecesInPlay().size());

        //See if the pieces in the two lists have the same position etc.
        Assertions.assertDoesNotThrow(() -> {
            for (int i = 0; i < chessGame.getPiecesInPlay().size(); i++) {
                assertEquals(chessGame.getPiecesInPlay().get(i).getType(), newChessGame.getPiecesInPlay().get(i).getType());
                assertEquals(chessGame.getPiecesInPlay().get(i).getX(), newChessGame.getPiecesInPlay().get(i).getX());
                assertEquals(chessGame.getPiecesInPlay().get(i).getY(), newChessGame.getPiecesInPlay().get(i).getY());
            }    
        });
    }

    @Test
    public void testInvalidFileRead() {
        Assertions.assertThrows(Exception.class, () -> {
            ChessGame cg = saveHandler.load("invalid-save");
            cg.getPiecesInPlay();
        });
    }
}
