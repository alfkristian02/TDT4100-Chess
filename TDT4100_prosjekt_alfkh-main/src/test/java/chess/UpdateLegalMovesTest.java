package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chess.model.ChessGame;
import chess.model.King;
import chess.model.Player;
import chess.model.UpdateLegalMoves;

public class UpdateLegalMovesTest {

    private Player player1 = new Player("Alf", 'W', true);
    private Player player2 = new Player("Kristian", 'B', false);

    private ChessGame chessGame = new ChessGame(player1, player2);

    private UpdateLegalMoves ULM = new UpdateLegalMoves();

    @Test
    public void testStartPosition() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ULM.update(null);
        });

        // Updates all the legal moves in the starting position
        ULM.update(chessGame.getPiecesInPlay());

        ArrayList<String> emptyList = new ArrayList<>();
        ArrayList<String> actualLegalMovesRookOna1 = new ArrayList<>();
        ArrayList<String> actualLegalMovesKnightOnb1 = new ArrayList<>(List.of("c3", "a3"));
        ArrayList<String> actualLegalMovesBishopOnc1 = new ArrayList<>();
        ArrayList<String> actualLegalMovesQueenOnd1 = new ArrayList<>();
        ArrayList<String> actualLegalMovesPawnOne2 = new ArrayList<>(List.of("e3", "e4"));

        actualLegalMovesRookOna1.removeAll(chessGame.getPieceBySquare("a1").getLegalMoves());
        actualLegalMovesKnightOnb1.removeAll(chessGame.getPieceBySquare("b1").getLegalMoves());
        actualLegalMovesBishopOnc1.removeAll(chessGame.getPieceBySquare("c1").getLegalMoves());
        actualLegalMovesQueenOnd1.removeAll(chessGame.getPieceBySquare("d1").getLegalMoves());
        actualLegalMovesPawnOne2.removeAll(chessGame.getPieceBySquare("e2").getLegalMoves());

        assertEquals(emptyList, actualLegalMovesRookOna1);
        assertEquals(emptyList, actualLegalMovesKnightOnb1);
        assertEquals(emptyList, actualLegalMovesBishopOnc1);
        assertEquals(emptyList, actualLegalMovesQueenOnd1);
        assertEquals(emptyList, actualLegalMovesPawnOne2);
    }

    @Test
    public void testOtherPosition() {
        chessGame = new ChessGame(player1, player2, List.of("♟Bg2true", "♞Bd1", "♞Bh2", "♘We4", "♘Wh7", "♕Wg3", "♚Bh1true", "♔Wa7true"));

        ArrayList<String> emptyList = new ArrayList<>();
        ArrayList<String> actualLegalMovesPawnOng2 = new ArrayList<>(List.of("g1"));
        ArrayList<String> actualLegalMovesKnightOnd1 = new ArrayList<>(List.of("c3", "e3", "f2"));
        ArrayList<String> actualLegalMovesKnightOnh2 = new ArrayList<>(List.of("g4", "f3", "f1"));
        ArrayList<String> actualLegalMovesKnightOne4 = new ArrayList<>(List.of("c3", "c5", "d6", "d2", "f2", "g5", "f6"));
        ArrayList<String> actualLegalMovesKnightOnh7 = new ArrayList<>(List.of("f8", "f6", "g5"));
        ArrayList<String> actualLegalMovesQueenOng3 = new ArrayList<>(List.of("h2", "g2", "f2", "e1", "h3", "h4", "g4", "g5", "g6", "g7", "g8", "f4", "e5", "d6", "c7", "b8", "a3", "b3", "c3", "d3", "e3", "f3"));
        ArrayList<String> actualLegalMoveskingOnh1 = new ArrayList<>(List.of("g1"));
        ArrayList<String> actualLegalMovesKingOna7 = new ArrayList<>(List.of("a8", "b8", "b7", "b6", "a6"));

        actualLegalMovesPawnOng2.removeAll(chessGame.getPieceBySquare("g2").getLegalMoves());
        actualLegalMovesKnightOnd1.removeAll(chessGame.getPieceBySquare("d1").getLegalMoves());
        actualLegalMovesKnightOnh2.removeAll(chessGame.getPieceBySquare("h2").getLegalMoves());
        actualLegalMovesKnightOne4.removeAll(chessGame.getPieceBySquare("e4").getLegalMoves());
        actualLegalMovesKnightOnh7.removeAll(chessGame.getPieceBySquare("h7").getLegalMoves());
        actualLegalMovesQueenOng3.removeAll(chessGame.getPieceBySquare("g3").getLegalMoves());
        actualLegalMoveskingOnh1.removeAll(chessGame.getPieceBySquare("h1").getLegalMoves());
        actualLegalMovesKingOna7.removeAll(chessGame.getPieceBySquare("a7").getLegalMoves());

        assertEquals(emptyList, actualLegalMovesPawnOng2);
        assertEquals(emptyList, actualLegalMovesKnightOnd1);
        assertEquals(emptyList, actualLegalMovesKnightOnh2);
        assertEquals(emptyList, actualLegalMovesKnightOne4);
        assertEquals(emptyList, actualLegalMovesKnightOnh7);
        assertEquals(emptyList, actualLegalMovesQueenOng3);
        assertEquals(emptyList, actualLegalMoveskingOnh1);
        assertEquals(emptyList, actualLegalMovesKingOna7);

        King king = (King) chessGame.getPieceBySquare("a7");

        assertEquals(king.getCastleMoves(), emptyList);
    }
}
