package chess.model;

import java.util.ArrayList;

public class King extends AbstractPiece{
    
    private char type;
    private boolean hasMoved;
    private ArrayList<String> castleMoves;
    private boolean inCheck;
    
    public King(char color, char y, int x, boolean hasMoved){
        super(y, x);

        if (color == 'W'){
            type = '\u2654';
        } else{
            type = '\u265A';
        }

        castleMoves = new ArrayList<>();
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved() {
        hasMoved = true;
    }

    @Override
    public char getType(){
        return type;
    }

    @Override
    public boolean isWhite(){
        return type < '\u265A';
    }

    public void addCastleMove(String s) {
        if (s == null){
            castleMoves.clear();
        }else{
            castleMoves.add(s);
        }
    }

    public ArrayList<String> getCastleMoves() {
        return new ArrayList<>(castleMoves);
    }

    public boolean getInCheck() {
        return inCheck;
    }

    public void setInCheck(boolean inCheck) {
        this.inCheck = inCheck;
    }
}
