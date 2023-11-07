package chess.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;


public class ChessGame {

    private Player playerWhite;
    private Player playerBlack;

    private ArrayList<AbstractPiece> piecesInPlay;

    private UpdateLegalMoves ULM;

    public ChessGame(Player p1, Player p2) {
        this.playerWhite = p1;
        this.playerBlack = p2;

        // For å lettere instansere brikkene riktig.
        final List<String> pawnsToAdd = List.of(
            "Ba7", "Bb7", "Bc7", "Bd7", "Be7", "Bf7", "Bg7", "Bh7",
            "Wa2", "Wb2", "Wc2", "Wd2", "We2", "Wf2", "Wg2", "Wh2"
        );

        final List<String> rooksToAdd = List.of(
            "Ba8", "Bh8",
            "Wa1", "Wh1"
        );

        final List<String> knightsToAdd = List.of(
            "Bb8", "Bg8",
            "Wb1", "Wg1"
        );

        final List<String> bishopsToAdd = List.of(
            "Bc8", "Bf8",
            "Wc1", "Wf1"
        );

        final List<String> queensToAdd = List.of(
            "Bd8",
            "Wd1"
        );

        final List<String> kingsToAdd = List.of(
            "Be8",
            "We1"
        );

        piecesInPlay = new ArrayList<>();

        for (String element : pawnsToAdd) {
            piecesInPlay.add(new Pawn(element.charAt(0), element.charAt(1), Character.getNumericValue(element.charAt(2)), false));
        }

        for (String element : rooksToAdd) {
            piecesInPlay.add(new Rook(element.charAt(0), element.charAt(1), Character.getNumericValue(element.charAt(2)), false));
        }

        for (String element : knightsToAdd) {
            piecesInPlay.add(new Knight(element.charAt(0), element.charAt(1), Character.getNumericValue(element.charAt(2))));
        }

        for (String element : bishopsToAdd) {
            piecesInPlay.add(new Bishop(element.charAt(0), element.charAt(1), Character.getNumericValue(element.charAt(2))));
        }

        for (String element : queensToAdd) {
            piecesInPlay.add(new Queen(element.charAt(0), element.charAt(1), Character.getNumericValue(element.charAt(2))));
        }

        for (String element : kingsToAdd) {
            piecesInPlay.add(new King(element.charAt(0), element.charAt(1), Character.getNumericValue(element.charAt(2)), false));
        }

        ULM = new UpdateLegalMoves();
        ULM.update(new ArrayList<>(getPiecesInPlay()));
    }


    public ChessGame(Player white, Player black, Collection<String> pieces) {
        this.playerWhite = white;
        this.playerBlack = black;

        piecesInPlay = new ArrayList<>();

        for (String string : pieces) {
            if (string.charAt(0) == '\u2659' || string.charAt(0) == '\u265F'){
                piecesInPlay.add(new Pawn(string.charAt(1), string.charAt(2), Character.getNumericValue(string.charAt(3)), Boolean.parseBoolean(string.substring(4))));
            }

            if (string.charAt(0) == '\u2656' || string.charAt(0) == '\u265C'){
                piecesInPlay.add(new Rook(string.charAt(1), string.charAt(2), Character.getNumericValue(string.charAt(3)), Boolean.parseBoolean(string.substring(4))));
            }

            if (string.charAt(0) == '\u2658' || string.charAt(0) == '\u265E'){
                piecesInPlay.add(new Knight(string.charAt(1), string.charAt(2), Character.getNumericValue(string.charAt(3))));
            }

            if (string.charAt(0) == '\u2657' || string.charAt(0) == '\u265D'){
                piecesInPlay.add(new Bishop(string.charAt(1), string.charAt(2), Character.getNumericValue(string.charAt(3))));
            }

            if (string.charAt(0) == '\u2655' || string.charAt(0) == '\u265B'){
                piecesInPlay.add(new Queen(string.charAt(1), string.charAt(2), Character.getNumericValue(string.charAt(3))));
            }

            if (string.charAt(0) == '\u2654' || string.charAt(0) == '\u265A'){
                piecesInPlay.add(new King(string.charAt(1), string.charAt(2), Character.getNumericValue(string.charAt(3)), Boolean.parseBoolean(string.substring(4))));
            }
        }

        UpdateLegalMoves ULM = new UpdateLegalMoves();
        ULM.update(new ArrayList<>(getPiecesInPlay()));
    }

    public AbstractPiece getPieceBySquare(String s){
        if (s == null){
            throw new IllegalArgumentException("Invalid square");
        }

        if(Pattern.matches("[a-h][1-8]", s)){
            for (AbstractPiece piece : getPiecesInPlay()){
                if (s.equals("" + piece.getY() + piece.getX())){
                    return piece;
                }
            }
        return null;
        } else{
            throw new IllegalArgumentException("Invalid square");
        }


    }

    public Player getPlayerWhite() {
        return playerWhite;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }

    public ArrayList<AbstractPiece> getPiecesInPlay() {
        return new ArrayList<>(piecesInPlay);
    }

    public ArrayList<AbstractPiece> getWhitePiecesInPlay() {
        ArrayList<AbstractPiece> whitePieces = new ArrayList<>();

        for (AbstractPiece piece : piecesInPlay){
            if(piece.isWhite()){
                whitePieces.add(piece);
            }
        }

        return whitePieces;
    }

    public ArrayList<AbstractPiece> getBlackPiecesInPlay() {
        ArrayList<AbstractPiece> blackPieces = new ArrayList<>();

        for (AbstractPiece piece : piecesInPlay){
            if(!piece.isWhite()){
                blackPieces.add(piece);
            }
        }

        return blackPieces;
    }

    public void move(AbstractPiece piece, String destination){
        if(piece == null || destination == null){
            throw new IllegalArgumentException("Invalid input");
        }

        if (((playerWhite.getTurn() == true && piece.isWhite()) || (playerBlack.getTurn() == true && !piece.isWhite())) && piece.getLegalMoves().contains(destination)){

            piece.move(destination);

        } else if (piece instanceof King){

            King king = (King) piece;

            if (((playerWhite.getTurn() == true && king.isWhite()) || (playerBlack.getTurn() == true && !king.isWhite())) && king.getCastleMoves().contains(destination)){
                castle(destination, king);
            } else {
                throw new IllegalStateException("The piece does not move like that.");
            }
        } else{
            throw new IllegalStateException("The piece does not move like that.");
        }

        UpdateLegalMoves ULM = new UpdateLegalMoves();
        ULM.update(getPiecesInPlay());

        playerWhite.setTurn(!playerWhite.getTurn());
        playerBlack.setTurn(!playerBlack.getTurn());
    }

    public void capture(AbstractPiece capturedPiece, AbstractPiece piece) {
        if (capturedPiece == null || piece == null){
            throw new IllegalArgumentException("The input pieces can not be \"null\"");
        } else{
            if ((((playerWhite.getTurn() == true && piece.isWhite()) || (playerBlack.getTurn() == true && !piece.isWhite()))) && piece.getLegalMoves().contains(""+capturedPiece.getY()+capturedPiece.getX())){
                String s = ("" + capturedPiece.getY() + capturedPiece.getX());

                piecesInPlay.remove(piecesInPlay.indexOf(capturedPiece));

                this.move(piece, s);

            } else {
                String helpvar;
                if (piece.isWhite()){
                    helpvar = "White";
                } else{
                    helpvar = "Black";
                }

                throw new IllegalStateException(helpvar + " to move.");
            }
        }
    }

    public void pawnPromotion(AbstractPiece piece1, AbstractPiece piece2){
        if (piece1 == null || piece2 == null || !(piece1 instanceof Pawn || piece2 instanceof Pawn) || piece1.isWhite() != piece2.isWhite()){
            throw new IllegalArgumentException("Invalid input");
        }

        if (piece1 instanceof Pawn){
            if (piece1.getX() == 8 || piece1.getX() == 1){
                if (piece2 instanceof Rook || piece2 instanceof Knight || piece2 instanceof Bishop || piece2 instanceof Queen){
                    piecesInPlay.remove(piece1);
                    piecesInPlay.add(piece2);
                } else{
                    throw new IllegalStateException("You can not promote to this piece");
                }
            }else{
                throw new IllegalStateException("This pawn can not be promoted");
            }
        }

        if (piece2 instanceof Pawn){
            if (piece2.getX() == 8 || piece2.getX() == 1){
                if (piece1 instanceof Rook || piece1 instanceof Knight || piece1 instanceof Bishop || piece1 instanceof Queen){
                    piecesInPlay.remove(piece2);
                    piecesInPlay.add(piece1);
                } else{
                    throw new IllegalStateException("You can not promote to this piece");
                }
            }else{
                throw new IllegalStateException("This pawn can not be promoted at the moment");
            }
        }
    }

    private void castle(String destination, King king){
        if (king.getCastleMoves().contains(destination)){
            if (king.isWhite()){
                if (destination.equals("c1")){
                    getPieceBySquare("a1").move("d1");
                    king.move("c1");
                    king.addCastleMove(null);
                }

                if (destination.equals("g1")){
                    getPieceBySquare("h1").move("f1");
                    king.move("g1");
                    king.addCastleMove(null);
                }
            } else{
                if (destination.equals("c8")){
                    getPieceBySquare("a8").move("d8");
                    king.move("c8");
                    king.addCastleMove(null);
                }

                if (destination.equals("g8")){
                    getPieceBySquare("h8").move("f8");
                    king.move("g8");
                    king.addCastleMove(null);
                }
            }
        } else{
            throw new IllegalStateException("The king does not castle like that");
        }
    }
}
