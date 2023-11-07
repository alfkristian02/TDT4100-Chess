package chess.model;

public class Queen extends AbstractPiece{
    
    private char type;
    
    public Queen(char color, char y, int x) {
        super(y, x);

        if (color == 'W'){
            type = '\u2655';
        } else{
            type = '\u265B';
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
