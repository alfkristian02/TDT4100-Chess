package chess.model;

public class Knight extends AbstractPiece{

    private char type;
    
    public Knight(char color, char y, int x) {
        super(y, x);
        
        if (color == 'W'){
            type = '\u2658';
        } else{
            type = '\u265E';
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
