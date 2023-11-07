package chess.model;

public class Bishop extends AbstractPiece{

    private char type;
    
    public Bishop(char color, char y, int x) {
        super(y, x);

        if (color == 'W'){
            type = '\u2657'; 
        } else {
            type = '\u265D';
        }
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
