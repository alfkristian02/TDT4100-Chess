package chess.model;

public class Rook extends AbstractPiece{

    private char type;
    private boolean hasMoved;
    
    public Rook(char color, char y, int x, boolean hasMoved) {
        super(y, x);

        if (color == 'W'){
            type = '\u2656';
        } else{
            type = '\u265C';
        }

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
}
