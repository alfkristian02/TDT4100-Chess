package chess.model;

public class Player {

    private String name;
    private char color;
    private boolean turn;

    public Player(String name, char color, boolean turn){
        this.name = name;
        this.color = color;
        this.turn = turn;
    }

    public String getName() {
        return name;
    }

    public char getColor() {
        return color;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }

    public boolean getTurn() {
        return turn;
    }
}
