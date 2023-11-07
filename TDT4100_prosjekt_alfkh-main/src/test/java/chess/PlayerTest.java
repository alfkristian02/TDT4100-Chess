package chess;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import chess.model.Player;

public class PlayerTest {
    
    private Player player = new Player("Alf", 'W', true);

    @Test
    public void testConstructor(){
        assertEquals("Alf", player.getName());
        assertEquals('W', player.getColor());
        assertTrue(player.getTurn());
    }

    @Test
    public void testSetters() {
        player.setTurn(false);
        assertFalse(player.getTurn());
    }

	@Test
	public void testPrivateFields() {
        for (Field field : Player.class.getDeclaredFields()) {
            Assertions.assertTrue(Modifier.isPrivate(field.getModifiers()),
            "Expected field " + field.getName() + " to be private!");
        }
	}
}
