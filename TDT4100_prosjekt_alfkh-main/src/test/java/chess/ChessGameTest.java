package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.model.ChessGame;
import chess.model.Player;

public class ChessGameTest {

    private ChessGame chessGame;
    private Player player1 = new Player("Alf", 'W', true);
    private Player player2 = new Player("Kristian", 'B', false);

    @BeforeEach
    public void setup() {
        chessGame = new ChessGame(player1, player2);
    }
    
    @Test
	public void testPrivateFields() {
        for (Field field : ChessGame.class.getDeclaredFields()) {
            Assertions.assertTrue(Modifier.isPrivate(field.getModifiers()),
            "Expected field " + field.getName() + " to be private!");
        }
    }

    @Test
    public void testFirstConstructor() {
        chessGame = new ChessGame(player1, player2);

        // Checking for the correct amount of pieces on the board
        assertEquals(32, chessGame.getPiecesInPlay().size());
        
        //Checking if legalMoves has been updated
        assertNotNull(chessGame.getPiecesInPlay().get(0).getLegalMoves());
    }

    @Test
    public void testSecondConstructor() {
        List<String> pieces = List.of("♟Ba5true", "♟Bc7false", "♙Wa4true", "♙Wb5true", "♜Ba8true", "♖Wc6true", "♝Bd1", "♚Bb7true", "♔Wb4true");
        chessGame = new ChessGame(player1, player2, pieces);

        assertEquals(9, chessGame.getPiecesInPlay().size());
    }

    @Test
    public void testGetters() {
        assertNull(chessGame.getPieceBySquare("a3"));
        assertNotNull("a2");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            chessGame.getPieceBySquare("");
            chessGame.getPieceBySquare("j2");
            chessGame.getPieceBySquare("aa2");
            chessGame.getPieceBySquare("a-2");
        });

        assertEquals(player1, chessGame.getPlayerWhite());
        assertEquals(player2, chessGame.getPlayerBlack());

        assertEquals(32, chessGame.getPiecesInPlay().size());
        assertEquals(16, chessGame.getWhitePiecesInPlay().size());
        assertEquals(16, chessGame.getBlackPiecesInPlay().size());
    }

    @Test
    public void testMove() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            chessGame.move(null, null);
        });

        Assertions.assertThrows(IllegalStateException.class, () -> {
            //Empty input
			chessGame.move(chessGame.getBlackPiecesInPlay().get(0), "");

            //It is not blacks turn
			chessGame.move(chessGame.getBlackPiecesInPlay().get(0), "a2");

            //Invalid destination
            chessGame.move(chessGame.getBlackPiecesInPlay().get(0), "hogaboga");

            //Destination is a square, but the piece does not move like that.
            chessGame.move(chessGame.getBlackPiecesInPlay().get(0), "a2");
		});

        //Moves a white pawn one square
        Assertions.assertDoesNotThrow(() -> {
            chessGame.move(chessGame.getWhitePiecesInPlay().get(0), "a4");
        });

        //The turns has been updated correctly
        assertFalse(chessGame.getPlayerWhite().getTurn());
        assertTrue(chessGame.getPlayerBlack().getTurn());

        //The pawn that has been moved, should only have one legal move
        assertEquals(1, chessGame.getWhitePiecesInPlay().get(0).getLegalMoves().size());

        //Testing that the castle method runs when intended
        chessGame = new ChessGame(player1, player2, List.of("♚Be8false", "♔We1false", "♙Wb5true", "♜Ba8false", "♜Bh8false", "♖Wa1false", "♖Wh1false"));

        //Casteling the black king. "g8" should not be a legal move if it wasnt for castling. 
        Assertions.assertDoesNotThrow(() -> {
            chessGame.move(chessGame.getBlackPiecesInPlay().get(0), "g8");
        });

        assertTrue(chessGame.getPlayerWhite().getTurn());
        assertFalse(chessGame.getPlayerBlack().getTurn());
    }

    @Test
    public void testCapture() {
        chessGame = new ChessGame(player1, player2, List.of("♟Ba5true", "♟Bc7false", "♙Wa4true", "♙Wb5true", "♜Ba8true", "♖Wc6true", "♝Bd1", "♚Bb7true", "♔Wb4true"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //invalid iputs
            chessGame.capture(chessGame.getPieceBySquare("c7"), chessGame.getPieceBySquare("hei"));
            chessGame.capture(chessGame.getPieceBySquare(""), chessGame.getPieceBySquare("c7"));
            chessGame.capture(chessGame.getPieceBySquare(""), chessGame.getPieceBySquare(""));
            chessGame.capture(null, chessGame.getPieceBySquare("hei"));
            chessGame.capture(chessGame.getPieceBySquare(""),null);
            chessGame.capture(null, null);
		});

        Assertions.assertThrows(IllegalStateException.class, () -> {
            //Not blacks turn
            chessGame.capture(chessGame.getPieceBySquare("b4"), chessGame.getPieceBySquare("a5"));

            // Piece do not have the oppertunity to take this piece
            chessGame.capture(chessGame.getPieceBySquare("c6"), chessGame.getPieceBySquare("b7"));
        });
    }

    @Test
    public void testPawnPromotion() {
        chessGame = new ChessGame(player1, player2, List.of("♔Wc8true", "♚Bc5true", "♙Wa7true", "♙Wb6true", "♟Bd6true", "♟Ba2true", "♖Wb8true"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            chessGame.pawnPromotion(null, null);  

            // Two pieces that are not pawns should never have the opportunity to promote
            chessGame.pawnPromotion(chessGame.getPieceBySquare("c8"), chessGame.getPieceBySquare("c5"));
            
            // Can not promote to another color than the original
            chessGame.pawnPromotion(chessGame.getPieceBySquare("b6"), chessGame.getPieceBySquare("d6")); 
        });

        Assertions.assertDoesNotThrow(() -> {
            chessGame.move(chessGame.getPieceBySquare("a7"), "a8");
        });

        Assertions.assertThrows(IllegalStateException.class, () -> {
            // Can not promote a pawn taht is not on the 1st or 8th rank
            chessGame.pawnPromotion(chessGame.getPieceBySquare("b6"), chessGame.getPieceBySquare("b8")); 

            // Can not promote to king
            chessGame.pawnPromotion(chessGame.getPieceBySquare("a8"), chessGame.getPieceBySquare("c8")); 
        });
    }
}
