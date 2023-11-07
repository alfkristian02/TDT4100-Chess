package chess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import chess.model.ChessGame;
import chess.model.King;
import chess.model.Pawn;
import chess.model.AbstractPiece;
import chess.model.Player;
import chess.model.Rook;

public class SaveHandler implements ISaveHandler{

    public void save(ChessGame chessGame, String filename) throws FileNotFoundException {
		try (PrintWriter writer = new PrintWriter(new File(getFilePath(filename)))) {
            writer.println(chessGame.getPlayerWhite().getName());
			writer.println(chessGame.getPlayerWhite().getColor());
			writer.println(chessGame.getPlayerWhite().getTurn());

            writer.println(chessGame.getPlayerBlack().getName());
			writer.println(chessGame.getPlayerBlack().getColor());
			writer.println(chessGame.getPlayerBlack().getTurn());

			String pieces = "";

			for (AbstractPiece piece : chessGame.getPiecesInPlay()) {
				if (piece.isWhite()){
					if (piece instanceof King){
						King king = (King) piece;
						pieces += "[" + piece.getType() + 'W' + piece.getY() + piece.getX() + king.getHasMoved() + "],";
					} else if (piece instanceof Pawn){
						Pawn pawn = (Pawn) piece;
						pieces += "[" + piece.getType() + 'W' + piece.getY() + piece.getX() + pawn.getHasMoved() + "],";
					} else if(piece instanceof Rook){
						Rook rook = (Rook) piece;
						pieces += "[" + piece.getType() + 'W' + piece.getY() + piece.getX() + rook.getHasMoved() + "],";
					} else{
						pieces += "[" + piece.getType() + 'W' + piece.getY() + piece.getX() + "],";
					}
				}

				else{
					if (piece instanceof King){
						King king = (King) piece;
						pieces += "[" + piece.getType() + 'B' + piece.getY() + piece.getX() + king.getHasMoved() + "],";
					} else if (piece instanceof Pawn){
						Pawn pawn = (Pawn) piece;
						pieces += "[" + piece.getType() + 'B' + piece.getY() + piece.getX() + pawn.getHasMoved() + "],";
					} else if(piece instanceof Rook){
						Rook rook = (Rook) piece;
						pieces += "[" + piece.getType() + 'B' + piece.getY() + piece.getX() + rook.getHasMoved() + "],";
					} else{
						pieces += "[" + piece.getType() + 'B' + piece.getY() + piece.getX() + "],";
					}
				}
			}

			writer.println(pieces);

			writer.close();
		}

	}

	public ChessGame load(String filename) throws FileNotFoundException {
		try (Scanner scanner = new Scanner(new File(getFilePath(filename)))) {

			String playerWhiteName = scanner.nextLine();
			char playerWhiteColor = (char) scanner.nextLine().charAt(0);
			boolean playerWhiteTurn;

			if (scanner.nextLine().equals("true")){
				playerWhiteTurn = true;
			}else{
				playerWhiteTurn = false;
			}

			Player playerWhite = new Player(playerWhiteName, playerWhiteColor, playerWhiteTurn);

			String playerBlackName = scanner.nextLine();
			char playerBlackColor = (char) scanner.nextLine().charAt(0);
			boolean playerBlackTurn;

			if (scanner.nextLine().equals("true")){
				playerBlackTurn = true;
			}else{
				playerBlackTurn = false;
			}

			Player playerBlack = new Player(playerBlackName, playerBlackColor, playerBlackTurn);

			//The pieces becomes actual objects in one of the constructors in ChessGame.java
			String[] pieces = scanner.nextLine().replace("[", "").replace("]", "").split(",");
			
			return new ChessGame(playerWhite, playerBlack, List.of(pieces));
		}
	}

	public static String getFilePath(String filename) {
		return SaveHandler.class.getResource("saves/").getFile() + filename + ".txt";
	}
}
