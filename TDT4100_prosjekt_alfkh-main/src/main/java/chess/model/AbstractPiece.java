package chess.model;

import java.util.ArrayList;

public abstract class AbstractPiece {

    private char y;
    private int x;
    private ArrayList<String> legalMoves;

    public AbstractPiece(char y, int x) {
        this.y = y;
        this.x = x;
        legalMoves = new ArrayList<>();
    }

    public char getY() {
        return y;
    }

    public void setY(char y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public ArrayList<String> getLegalMoves(){
        return new ArrayList<>(legalMoves);
    }

    public void addLegalMove(String s) {
        if (s == null){
            legalMoves.clear();     
        } else{
            legalMoves.add(s);
        }
    }

    public void move(String destination) {
        if (getLegalMoves().contains(destination)){
            setY(destination.charAt(0));
            setX(Character.getNumericValue(destination.charAt(1)));
            if (this instanceof King){
                King king = (King) this;
                king.setHasMoved();
            } else if (this instanceof Pawn){
                Pawn pawn = (Pawn) this;
                pawn.setHasMoved();
            } else if(this instanceof Rook){
                Rook rook = (Rook) this;
                rook.setHasMoved();
            }
        } else if(this instanceof King){
            King king = (King) this;
            if(king.getCastleMoves().contains(destination)){
                king.setY(destination.charAt(0));
                king.setX(Character.getNumericValue(destination.charAt(1)));
                king.setHasMoved();
            } else{
                throw new IllegalStateException("Not a valid move");
            }
        } else{
            throw new IllegalStateException("Not a valid move");
        }
    }

    public abstract char getType();

    public abstract boolean isWhite();
}
