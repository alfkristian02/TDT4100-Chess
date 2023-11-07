package chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chess.model.King;
import chess.model.Knight;
import chess.model.Pawn;
import chess.model.AbstractPiece;

public class PieceTest {

    //Jeg har tre type brikker
    //Alle brikker oppfører seg som en av disse:
    private AbstractPiece pieceWithHasMoved;
    private AbstractPiece pieceWithoutHasMoved;
    private AbstractPiece pieceWithCastleMoves;

    @BeforeEach
    public void setup() {
        pieceWithCastleMoves = new King('W', 'e', 1, false);
        pieceWithHasMoved = new Pawn('W', 'b', 2, false);
        pieceWithoutHasMoved = new Knight('B', 'g', 8);
    }

    @Test
	public void testPrivateFields() {
        for (Field field : AbstractPiece.class.getDeclaredFields()) {
            Assertions.assertTrue(Modifier.isPrivate(field.getModifiers()),
            "Expected field " + field.getName() + " to be private!");
        }
    }

    @Test
    public void testConstructor() {
        assertTrue(pieceWithHasMoved.isWhite());
        assertEquals('\u2659', pieceWithHasMoved.getType());
        assertEquals('b', pieceWithHasMoved.getY());
        assertEquals(2, pieceWithHasMoved.getX());
        assertEquals(0, pieceWithHasMoved.getLegalMoves().size());
    }

    @Test
    public void testSetters() {
        pieceWithHasMoved.setX(3);
        pieceWithHasMoved.setY('b');

        assertEquals(3, pieceWithHasMoved.getX());
        assertEquals(0, pieceWithHasMoved.getLegalMoves().size());

        pieceWithoutHasMoved.addLegalMove("a1");

        assertEquals(1, pieceWithoutHasMoved.getLegalMoves().size());
        assertEquals("a1", pieceWithoutHasMoved.getLegalMoves().get(0));

        //remove all leagal moves
        pieceWithoutHasMoved.addLegalMove("a2");
        pieceWithoutHasMoved.addLegalMove(null);

        assertEquals(0, pieceWithoutHasMoved.getLegalMoves().size());

        //Teste det samme, men med castleMoves, som kongen har
        King king = (King) pieceWithCastleMoves;

        king.addCastleMove("a1");

        assertEquals(1, king.getCastleMoves().size());
        assertEquals("a1", king.getCastleMoves().get(0));

        //remove all leagal moves
        king.addCastleMove("a2");
        king.addCastleMove(null);

        assertEquals(0, king.getLegalMoves().size());

    }

    @Test
    public void testMove() {
        King king = (King) pieceWithCastleMoves;

        king.addCastleMove("a1");
        king.addCastleMove("a2");
        
        king.move("a2");
        
        assertEquals('a', king.getY());
        assertEquals(2, king.getX());

        //Sjekker at hasMoved er oppdatert
        assertTrue(king.getHasMoved());

        //Sjekker at brikken kaster riktig unntak, når noen prøver å flytte den feil
        Assertions.assertThrows(IllegalStateException.class, () -> {
			king.move("-1a");
            king.move("a3");
            king.move("");
		});
    }
}
