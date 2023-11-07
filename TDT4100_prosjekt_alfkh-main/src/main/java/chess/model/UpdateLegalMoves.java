package chess.model;

import java.util.ArrayList;

public class UpdateLegalMoves{

    private ArrayList<AbstractPiece> inputPieces;

    public void update(ArrayList<AbstractPiece> pieces) {

        inputPieces = new ArrayList<>();

        if(pieces == null){
            throw new IllegalArgumentException("No pieces in this list");
        }
        
        for (AbstractPiece piece : pieces) {
            inputPieces.add(piece);
            if (piece instanceof King){
                King king = (King) piece;
                king.setInCheck(false);
                king.addCastleMove(null);
            }
        }

        for (AbstractPiece piece : pieces) {
            if (piece.getType() == '\u2659') findLegalMovesWhitePawn(piece);
            if (piece.getType() == '\u265F') findLegalMovesBlackPawn(piece);
            if (piece.getType() == '\u2658' || piece.getType() == '\u265E') findLegalMovesKnight(piece);
            if (piece.getType() == '\u2657' || piece.getType() == '\u265D') findLegalMovesBishop(piece);
            if (piece.getType() == '\u2655' || piece.getType() == '\u265B') findLegalMovesQueen(piece);
            if (piece.getType() == '\u2654' || piece.getType() == '\u265A') findLegalMovesKing(piece);
        }

        for (AbstractPiece piece : pieces){
            if (piece.getType() == '\u2656' || piece.getType() == '\u265C') findLegalMovesRook(piece);
        }
    }

    private AbstractPiece pieceOnSquare(String s) {
        for (AbstractPiece piece : inputPieces) {
            if (s.equals("" + piece.getY() + piece.getX())) return piece;
        }        
        return null;
    }

    private boolean isDifferentColor(AbstractPiece p1, AbstractPiece p2){
        return p1.isWhite() != p2.isWhite();
    }

    private String squareUp(AbstractPiece piece, int i){
        return ("" + piece.getY() + (piece.getX() + i));
     }

    private String squareDown(AbstractPiece piece, int i){
       return ("" + piece.getY() + (piece.getX() - i));
    }

    private String squareRigth(AbstractPiece piece, int i) {
        return ("" + ((char) (piece.getY() + i)) + (piece.getX()));
    }

    private String squareLeft(AbstractPiece piece, int i) {
        return "" + ((char) (piece.getY() - i)) + (piece.getX());
    }

    private String squareUpRigth(AbstractPiece piece, int i) {
        return ("" + ((char) (piece.getY() + i)) + (piece.getX() + i));
    }

    private String squareUpLeft(AbstractPiece piece, int i) {
        return ("" + ((char) (piece.getY() - i)) + (piece.getX() + i));
    }

    private String squareDownRigth(AbstractPiece piece, int i) {
        return ("" + ((char) (piece.getY() + i)) + (piece.getX() - i));
    }

    private String squareDownLeft(AbstractPiece piece, int i) {
        return ("" + ((char) (piece.getY() - i)) + (piece.getX() - i));
    }

    private void findLegalMovesWhitePawn(AbstractPiece piece) {
        piece.addLegalMove(null);

        Pawn pawn = (Pawn) piece;

        if (pieceOnSquare(squareUp(piece, 1)) == null){
            piece.addLegalMove(squareUp(piece, 1));
            if (!pawn.getHasMoved() && pieceOnSquare(squareUp(piece, 2)) == null){
                piece.addLegalMove(squareUp(piece, 2));
            }
        }

        if (pieceOnSquare(squareUpRigth(piece, 1)) != null && !pieceOnSquare(squareUpRigth(piece, 1)).isWhite()){
            if(pieceOnSquare(squareUpRigth(piece, 1)) instanceof King){
                King king = (King) pieceOnSquare(squareUpRigth(piece, 1));
                king.setInCheck(true);
            }

            piece.addLegalMove(squareUpRigth(piece, 1));
        }
        
        if (pieceOnSquare(squareUpLeft(piece, 1)) != null && !pieceOnSquare(squareUpLeft(piece, 1)).isWhite()){
            if(pieceOnSquare(squareUpLeft(piece, 1)) instanceof King){
                King king = (King) pieceOnSquare(squareUpLeft(piece, 1));
                king.setInCheck(true);
            }
            piece.addLegalMove(squareUpLeft(piece, 1));
        }
    }

    private void findLegalMovesBlackPawn(AbstractPiece piece) {
        piece.addLegalMove(null);

        Pawn pawn = (Pawn) piece;

        if (pieceOnSquare(squareDown(piece, 1)) == null){
            piece.addLegalMove(squareDown(piece, 1));

            if (!pawn.getHasMoved() && pieceOnSquare(squareDown(piece, 2)) == null){
                piece.addLegalMove(squareDown(piece, 2));
            }
        } 

        if (pieceOnSquare(squareDownLeft(piece, 1)) != null && pieceOnSquare(squareDownLeft(piece, 1)).isWhite()){
            if(pieceOnSquare(squareDownLeft(piece, 1)) instanceof King){
                King king = (King) pieceOnSquare(squareDownLeft(piece, 1));
                king.setInCheck(true);
            }

            piece.addLegalMove(squareDownLeft(piece, 1));
        }
        
        if (pieceOnSquare(squareDownRigth(piece, 1)) != null && pieceOnSquare(squareDownRigth(piece, 1)).isWhite()){
            if(pieceOnSquare(squareDownRigth(piece, 1)) instanceof King){
                King king = (King) pieceOnSquare(squareDownRigth(piece, 1));
                king.setInCheck(true);
            }

            piece.addLegalMove(squareDownRigth(piece, 1));
        }
    }

    private void findLegalMovesRook(AbstractPiece piece) {
        piece.addLegalMove(null);

        Rook rook = (Rook) piece;

        boolean loop = true;
        int a = 0;

        while(loop){
            if (piece.getX() != (8 - a) && pieceOnSquare(squareUp(piece, a + 1)) == null){
                piece.addLegalMove(squareUp(piece, a + 1));
            } else{
                if (pieceOnSquare(squareUp(piece, a + 1)) != null && isDifferentColor(pieceOnSquare(squareUp(piece, a + 1)), piece) ){
                    if(pieceOnSquare(squareUp(piece, 1)) instanceof King){
                        King king = (King) pieceOnSquare(squareUp(piece, 1));
                        king.setInCheck(true);
                    }
        

                    piece.addLegalMove(squareUp(piece, a + 1));
                }
                loop = false;
            }
            a ++;
        }

        boolean loop2 = true;
        a = 0;

        while(loop2){
            if (piece.getX() != (1 + a) && pieceOnSquare(squareDown(piece, 1 + a)) == null){
                piece.addLegalMove(squareDown(piece, 1 + a));
            } else{
                if (pieceOnSquare(squareDown(piece, 1 + a)) != null && isDifferentColor(pieceOnSquare(squareDown(piece, 1 + a)), piece) ){
                    if(pieceOnSquare(squareDown(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareDown(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareDown(piece, 1 + a));
                }
                loop2 = false;
            }

            a ++;
        }

        boolean loop3 = true;
        a = 0;

        while(loop3){
            if (piece.getY() != ((char) ('h' - a)) && pieceOnSquare(squareRigth(piece, 1 + a)) == null){
                piece.addLegalMove(squareRigth(piece, 1 + a));
            } else{
                if (pieceOnSquare(squareRigth(piece, 1 + a)) != null && isDifferentColor(pieceOnSquare(squareRigth(piece, 1 + a)), piece) ){
                    if(pieceOnSquare(squareRigth(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareRigth(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareRigth(piece, 1 + a));
                }

                if (pieceOnSquare(squareRigth(piece, 1 + a)) != null && !isDifferentColor(pieceOnSquare(squareRigth(piece, 1 + a)), piece) && pieceOnSquare(squareRigth(piece, 1 + a)) instanceof King){
                    King king = (King) pieceOnSquare(squareRigth(piece, 1 + a));
                    if (!king.getHasMoved() && !rook.getHasMoved()){
                        if (piece.isWhite()){
                            king.addCastleMove("c1");    
                        }else{
                            king.addCastleMove("c8");    
                        }    
                    }
                }

                loop3 = false;
            }

            a ++;
        }

        boolean loop4 = true;
        a = 0;

        while(loop4){
            if (piece.getY() != ((char) ('a' + a)) && pieceOnSquare(squareLeft(piece, 1 + a)) == null){
                piece.addLegalMove(squareLeft(piece, 1 + a));
            } else{
                if (pieceOnSquare(squareLeft(piece, 1 + a)) != null && isDifferentColor(pieceOnSquare(squareLeft(piece, 1 + a)), piece) ){
                    if(pieceOnSquare(squareLeft(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareLeft(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareLeft(piece, 1 + a));
                }

                if (pieceOnSquare(squareLeft(piece, 1 + a)) != null && !isDifferentColor(pieceOnSquare(squareLeft(piece, 1 + a)), piece) && pieceOnSquare(squareLeft(piece, 1 + a)) instanceof King){
                    King king = (King) pieceOnSquare(squareLeft(piece, 1 + a));

                    if (!king.getHasMoved() && !rook.getHasMoved()){
                        if (piece.isWhite()){
                            king.addCastleMove("g1");    
                        }else{
                            king.addCastleMove("g8");    
                        }    
                    }
                }

                loop4 = false;
            }

            a ++;
        }
    }

    private void findLegalMovesKnight(AbstractPiece piece){
        piece.addLegalMove(null);

        if (piece.getY() != 'h' && piece.getX() != 7 && piece.getX() != 8 && pieceOnSquare("" + ((char) (piece.getY() + 1)) + (piece.getX() + 2)) == null){
            piece.addLegalMove("" + ((char) (piece.getY() + 1)) + (piece.getX() + 2));
        } else {
            if (pieceOnSquare("" + ((char) (piece.getY() + 1)) + (piece.getX() + 2)) != null && isDifferentColor(pieceOnSquare("" + ((char) (piece.getY() + 1)) + (piece.getX() + 2)), piece)){
                if(pieceOnSquare("" + ((char) (piece.getY() + 1)) + (piece.getX() + 2)) instanceof King){
                    King king = (King) pieceOnSquare("" + ((char) (piece.getY() + 1)) + (piece.getX() + 2));
                    king.setInCheck(true);
                }
    
                piece.addLegalMove("" + ((char) (piece.getY() + 1)) + (piece.getX() + 2));
            }
        }

        if (piece.getY() != 'h' && piece.getY() != 'g' && piece.getX() != 8 && pieceOnSquare("" + ((char) (piece.getY() + 2)) + (piece.getX() + 1)) == null){
            piece.addLegalMove("" + ((char) (piece.getY() + 2)) + (piece.getX() + 1));
        } else {
            if (pieceOnSquare("" + ((char) (piece.getY() + 2)) + (piece.getX() + 1)) != null && isDifferentColor(pieceOnSquare("" + ((char) (piece.getY() + 2)) + (piece.getX() + 1)), piece)){
                if(pieceOnSquare("" + ((char) (piece.getY() + 2)) + (piece.getX() + 1)) instanceof King){
                    King king = (King) pieceOnSquare("" + ((char) (piece.getY() + 2)) + (piece.getX() + 1));
                    king.setInCheck(true);
                }
    
                piece.addLegalMove("" + ((char) (piece.getY() + 2)) + (piece.getX() + 1));
            }
        }

        if (piece.getY() != 'h' && piece.getY() != 'g' && piece.getX() != 1 && pieceOnSquare("" + ((char) (piece.getY() + 2)) + (piece.getX() - 1)) == null){
            piece.addLegalMove("" + ((char) (piece.getY() + 2)) + (piece.getX() - 1));
        } else {
            if (pieceOnSquare("" + ((char) (piece.getY() + 2)) + (piece.getX() - 1)) != null && isDifferentColor(pieceOnSquare("" + ((char) (piece.getY() + 2)) + (piece.getX() - 1)), piece)){
                if(pieceOnSquare("" + ((char) (piece.getY() + 2)) + (piece.getX() - 1)) instanceof King){
                    King king = (King) pieceOnSquare("" + ((char) (piece.getY() + 2)) + (piece.getX() - 1));
                    king.setInCheck(true);
                }
    
                piece.addLegalMove("" + ((char) (piece.getY() + 2)) + (piece.getX() - 1));
            }
        }

        if (piece.getY() != 'h' && piece.getX() != 1 && piece.getX() != 2 && pieceOnSquare("" + ((char) (piece.getY() + 1)) + (piece.getX() - 2)) == null){
            piece.addLegalMove("" + ((char) (piece.getY() + 1)) + (piece.getX() - 2));
        } else {
            if (pieceOnSquare("" + ((char) (piece.getY() + 1)) + (piece.getX() - 2)) != null && isDifferentColor(pieceOnSquare("" + ((char) (piece.getY() + 1)) + (piece.getX() - 2)), piece)){
                if(pieceOnSquare("" + ((char) (piece.getY() + 1)) + (piece.getX() - 2)) instanceof King){
                    King king = (King) pieceOnSquare("" + ((char) (piece.getY() + 1)) + (piece.getX() - 2));
                    king.setInCheck(true);
                }
    
                piece.addLegalMove("" + ((char) (piece.getY() + 1)) + (piece.getX() - 2));
            }
        }

        if (piece.getY() != 'a' && piece.getX() != 1 && piece.getX() != 2 && pieceOnSquare("" + ((char) (piece.getY() - 1)) + (piece.getX() - 2)) == null){
            piece.addLegalMove("" + ((char) (piece.getY() - 1)) + (piece.getX() - 2));
        } else {
            if (pieceOnSquare("" + ((char) (piece.getY() - 1)) + (piece.getX() - 2)) != null && isDifferentColor(pieceOnSquare("" + ((char) (piece.getY() - 1)) + (piece.getX() - 2)), piece)){
                if(pieceOnSquare("" + ((char) (piece.getY() - 1)) + (piece.getX() - 2)) instanceof King){
                    King king = (King) pieceOnSquare("" + ((char) (piece.getY() - 1)) + (piece.getX() - 2));
                    king.setInCheck(true);
                }
    
                piece.addLegalMove("" + ((char) (piece.getY() - 1)) + (piece.getX() - 2));
            }
        }

        if (piece.getY() != 'a' && piece.getY() != 'b' && piece.getX() != 1 && pieceOnSquare("" + ((char) (piece.getY() - 2)) + (piece.getX() - 1)) == null){
            piece.addLegalMove("" + ((char) (piece.getY() - 2)) + (piece.getX() - 1));
        } else {
            if (pieceOnSquare("" + ((char) (piece.getY() - 2)) + (piece.getX() - 1)) != null && isDifferentColor(pieceOnSquare("" + ((char) (piece.getY() - 2)) + (piece.getX() - 1)), piece)){
                if(pieceOnSquare("" + ((char) (piece.getY() - 2)) + (piece.getX() - 1)) instanceof King){
                    King king = (King) pieceOnSquare("" + ((char) (piece.getY() - 2)) + (piece.getX() - 1));
                    king.setInCheck(true);
                }
    
                piece.addLegalMove("" + ((char) (piece.getY() - 2)) + (piece.getX() - 1));
            }
        }

        if (piece.getY() != 'a' && piece.getY() != 'b' && piece.getX() != 8 && pieceOnSquare("" + ((char) (piece.getY() - 2)) + (piece.getX() + 1)) == null){
            piece.addLegalMove("" + ((char) (piece.getY() - 2)) + (piece.getX() + 1));
        } else {
            if (pieceOnSquare("" + ((char) (piece.getY() - 2)) + (piece.getX() + 1)) != null && isDifferentColor(pieceOnSquare("" + ((char) (piece.getY() - 2)) + (piece.getX() + 1)), piece)){
                if(pieceOnSquare("" + ((char) (piece.getY() - 2)) + (piece.getX() + 1)) instanceof King){
                    King king = (King) pieceOnSquare("" + ((char) (piece.getY() - 2)) + (piece.getX() + 1));
                    king.setInCheck(true);
                }
    
                piece.addLegalMove("" + ((char) (piece.getY() - 2)) + (piece.getX() + 1));
            }
        }

        if (piece.getY() != 'a' && piece.getX() != 7 && piece.getX() != 8 && pieceOnSquare("" + ((char) (piece.getY() - 1)) + (piece.getX() + 2)) == null){
            piece.addLegalMove("" + ((char) (piece.getY() - 1)) + (piece.getX() + 2));
        } else {
            if (pieceOnSquare("" + ((char) (piece.getY() - 1)) + (piece.getX() + 2)) != null && isDifferentColor(pieceOnSquare("" + ((char) (piece.getY() - 1)) + (piece.getX() + 2)), piece)){
                if(pieceOnSquare("" + ((char) (piece.getY() - 1)) + (piece.getX() + 2)) instanceof King){
                    King king = (King) pieceOnSquare("" + ((char) (piece.getY() - 1)) + (piece.getX() + 2));
                    king.setInCheck(true);
                }
    
                piece.addLegalMove("" + ((char) (piece.getY() - 1)) + (piece.getX() + 2));
            }
        }
    }


    private void findLegalMovesBishop(AbstractPiece piece) {
        piece.addLegalMove(null);

        boolean loop = true;
        int a = 0;

        while (loop){
            if ((char) (piece.getY() + a) != 'h' && (piece.getX() + a) != 8 && pieceOnSquare(squareUpRigth(piece, 1 + a)) == null){
                piece.addLegalMove(squareUpRigth(piece, 1 + a));
            } else{
                if (pieceOnSquare(squareUpRigth(piece, 1 + a)) != null && isDifferentColor(pieceOnSquare(squareUpRigth(piece, 1 + a)), piece)){
                    if(pieceOnSquare(squareUpRigth(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareUpRigth(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareUpRigth(piece, 1 + a));
                }
                loop = false;
            }

            a ++;
        }

        boolean loop2 = true;
        a = 0;

        while (loop2){
            if ((char) (piece.getY() + a) != 'h' && (piece.getX() - a) != 1 && pieceOnSquare(squareDownRigth(piece, 1 + a)) == null){
                piece.addLegalMove(squareDownRigth(piece, 1 + a));
            } else{
                if (pieceOnSquare(squareDownRigth(piece, 1 + a)) != null && isDifferentColor(pieceOnSquare(squareDownRigth(piece, 1 + a)), piece)){
                    if(pieceOnSquare(squareDownRigth(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareDownRigth(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareDownRigth(piece, 1 + a));
                }
                loop2 = false;
            }

            a ++;
        }

        boolean loop3 = true;
        a = 0;

        while (loop3){
            if ((char) (piece.getY() - a) != 'a' && (piece.getX() + a) != 8 && pieceOnSquare(squareUpLeft(piece, 1 + a)) == null){
                piece.addLegalMove(squareUpLeft(piece, 1 + a));
            } else{
                if (pieceOnSquare(squareUpLeft(piece, 1 + a)) != null && isDifferentColor(pieceOnSquare(squareUpLeft(piece, 1 + a)), piece)){
                    if(pieceOnSquare(squareUpLeft(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareUpLeft(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareUpLeft(piece, 1 + a));
                }
                loop3 = false;
            }

            a ++;
        }

        boolean loop4 = true;
        a = 0;

        while (loop4){
            if ((char) (piece.getY() - a) != 'a' && (piece.getX() - a) != 1 && pieceOnSquare(squareDownLeft(piece, 1 + a)) == null){
                piece.addLegalMove(squareDownLeft(piece, 1 + a));
            } else{
                if (pieceOnSquare(squareDownLeft(piece, 1 + a)) != null && isDifferentColor(pieceOnSquare(squareDownLeft(piece, 1 + a)), piece)){
                    if(pieceOnSquare(squareDownLeft(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareDownLeft(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareDownLeft(piece, 1 + a));
                }
                loop4 = false;
            }

            a ++;
        }  
    }

    private void findLegalMovesQueen(AbstractPiece piece) {
        piece.addLegalMove(null);

        boolean loop = true;
        int a = 0;

        while(loop){
            if (piece.getX() != (8 - a) && pieceOnSquare(squareUp(piece, a + 1)) == null){
                piece.addLegalMove(squareUp(piece, a + 1));
            } else{
                if (pieceOnSquare(squareUp(piece, a + 1)) != null && isDifferentColor(pieceOnSquare(squareUp(piece, a + 1)), piece) ){
                    if(pieceOnSquare(squareUp(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareUp(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareUp(piece, a + 1));
                }
                loop = false;
            }
            a ++;
        }

        boolean loop2 = true;
        a = 0;

        while(loop2){
            if (piece.getX() != (1 + a) && pieceOnSquare(squareDown(piece, 1 + a)) == null){
                piece.addLegalMove(squareDown(piece, 1 + a));
            } else{
                if (pieceOnSquare(squareDown(piece, 1 + a)) != null && isDifferentColor(pieceOnSquare(squareDown(piece, 1 + a)), piece) ){
                    if(pieceOnSquare(squareDown(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareDown(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareDown(piece, 1 + a));
                }
                loop2 = false;
            }

            a ++;
        }

        boolean loop3 = true;
        a = 0;

        while(loop3){
            if (piece.getY() != ((char) ('h' - a)) && pieceOnSquare(squareRigth(piece, 1 + a)) == null){
                piece.addLegalMove(squareRigth(piece, 1 + a));
            } else{
                if (pieceOnSquare(squareRigth(piece, 1 + a)) != null && isDifferentColor(pieceOnSquare(squareRigth(piece, 1 + a)), piece) ){
                    if(pieceOnSquare(squareRigth(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareRigth(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareRigth(piece, 1 + a));
                }
                loop3 = false;
            }

            a ++;
        }

        boolean loop4 = true;
        a = 0;

        while(loop4){
            if (piece.getY() != ((char) ('a' + a)) && pieceOnSquare(squareLeft(piece, 1 + a)) == null){
                piece.addLegalMove(squareLeft(piece, 1 + a));
            } else{
                if (pieceOnSquare(squareLeft(piece, 1 + a)) != null && isDifferentColor(pieceOnSquare(squareLeft(piece, 1 + a)), piece) ){
                    if(pieceOnSquare(squareLeft(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareLeft(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareLeft(piece, 1 + a));
                }
                loop4 = false;
            }

            a ++;
        }

        boolean loop5 = true;
        a = 0;

        while (loop5){
            if ((char) (piece.getY() + a) != 'h' && (piece.getX() + a) != 8 && pieceOnSquare(squareUpRigth(piece, 1 + a)) == null){
                piece.addLegalMove(squareUpRigth(piece, 1 + a));
            } else{
                if (pieceOnSquare(squareUpRigth(piece, 1 + a)) != null && isDifferentColor(pieceOnSquare(squareUpRigth(piece, 1 + a)), piece)){
                    if(pieceOnSquare(squareUpRigth(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareUpRigth(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareUpRigth(piece, 1 + a));
                }
                loop5 = false;
            }

            a ++;
        }

        boolean loop6 = true;
        a = 0;

        while (loop6){
            if ((char) (piece.getY() + a) != 'h' && (piece.getX() - a) != 1 && pieceOnSquare(squareDownRigth(piece, 1 + a)) == null){
                piece.addLegalMove(squareDownRigth(piece, 1 + a));
            } else{
                if (pieceOnSquare(squareDownRigth(piece, 1 + a)) != null && isDifferentColor(pieceOnSquare(squareDownRigth(piece, 1 + a)), piece)){
                    if(pieceOnSquare(squareDownRigth(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareDownRigth(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareDownRigth(piece, 1 + a));
                }
                loop6 = false;
            }

            a ++;
        }

        boolean loop7 = true;
        a = 0;

        while (loop7){
            if ((char) (piece.getY() - a) != 'a' && (piece.getX() + a) != 8 && pieceOnSquare(squareUpLeft(piece, 1 + a)) == null){
                piece.addLegalMove(squareUpLeft(piece, 1 + a));
            } else{
                if (pieceOnSquare(squareUpLeft(piece, 1 + a)) != null && isDifferentColor(pieceOnSquare(squareUpLeft(piece, 1 + a)), piece)){
                    if(pieceOnSquare(squareUpLeft(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareUpLeft(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareUpLeft(piece, 1 + a));
                }
                loop7 = false;
            }

            a ++;
        }

        boolean loop8 = true;
        a = 0;

        while (loop8){
            if ((char) (piece.getY() - a) != 'a' && (piece.getX() - a) != 1 && pieceOnSquare(squareDownLeft(piece, 1 + a)) == null){
                piece.addLegalMove(squareDownLeft(piece, 1 + a));
            } else{
                if (pieceOnSquare(squareDownLeft(piece, 1 + a)) != null && isDifferentColor(pieceOnSquare(squareDownLeft(piece, 1 + a)), piece)){
                    if(pieceOnSquare(squareDownLeft(piece, 1 + a)) instanceof King){
                        King king = (King) pieceOnSquare(squareDownLeft(piece, 1 + a));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareDownLeft(piece, 1 + a));
                }
                loop8 = false;
            }

            a ++;
        } 

    }

    private void findLegalMovesKing(AbstractPiece piece) {
        piece.addLegalMove(null);

        if (piece.getX() != 1){
            if (pieceOnSquare(squareDown(piece, 1)) == null){
                piece.addLegalMove(squareDown(piece, 1));
            }

            if (pieceOnSquare(squareDown(piece, 1)) != null && isDifferentColor(pieceOnSquare(squareDown(piece, 1)), piece)){
                if(pieceOnSquare(squareDown(piece, 1)) instanceof King){
                    King king = (King) pieceOnSquare(squareDown(piece, 1));
                    king.setInCheck(true);
                }
    
                piece.addLegalMove(squareDown(piece, 1));
            }

            if (piece.getY() != 'a'){
                if (pieceOnSquare(squareDownLeft(piece, 1)) == null) {
                    piece.addLegalMove(squareDownLeft(piece, 1));
                }

                if (pieceOnSquare(squareDownLeft(piece, 1)) != null && isDifferentColor(pieceOnSquare(squareDownLeft(piece, 1)), piece)){
                    if(pieceOnSquare(squareDownLeft(piece, 1)) instanceof King){
                        King king = (King) pieceOnSquare(squareDownLeft(piece, 1));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareDownLeft(piece, 1));
                }
            }

            if (piece.getY() != 'h'){
                if (pieceOnSquare(squareDownRigth(piece, 1)) == null) {
                    piece.addLegalMove(squareDownRigth(piece, 1));
                }

                if (pieceOnSquare(squareDownRigth(piece, 1)) != null && isDifferentColor(pieceOnSquare(squareDownRigth(piece, 1)), piece)){
                    if(pieceOnSquare(squareDownRigth(piece, 1)) instanceof King){
                        King king = (King) pieceOnSquare(squareDownRigth(piece, 1));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareDownRigth(piece, 1));
                }
            }
        }

        if (piece.getX() != 8){
            if (pieceOnSquare(squareUp(piece, 1)) == null){
                piece.addLegalMove(squareUp(piece, 1));
            }

            if (pieceOnSquare(squareUp(piece, 1)) != null && isDifferentColor(pieceOnSquare(squareUp(piece, 1)), piece)){
                if(pieceOnSquare(squareUp(piece, 1)) instanceof King){
                    King king = (King) pieceOnSquare(squareUp(piece, 1));
                    king.setInCheck(true);
                }
    
                piece.addLegalMove(squareUp(piece, 1));
            }

            if (piece.getY() != 'a'){
                if (pieceOnSquare(squareUpLeft(piece, 1)) == null) {
                    piece.addLegalMove(squareUpLeft(piece, 1));
                }

                if (pieceOnSquare(squareUpLeft(piece, 1)) != null && isDifferentColor(pieceOnSquare(squareUpLeft(piece, 1)), piece)){
                    if(pieceOnSquare(squareUpLeft(piece, 1)) instanceof King){
                        King king = (King) pieceOnSquare(squareUpLeft(piece, 1));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareUpLeft(piece, 1));
                }
            }
    
            if (piece.getY() != 'h'){
                if (pieceOnSquare(squareUpRigth(piece, 1)) == null) {
                    piece.addLegalMove(squareUpRigth(piece, 1));
                }

                if (pieceOnSquare(squareUpRigth(piece, 1)) != null && isDifferentColor(pieceOnSquare(squareUpRigth(piece, 1)), piece)){
                    if(pieceOnSquare(squareUpRigth(piece, 1)) instanceof King){
                        King king = (King) pieceOnSquare(squareUpRigth(piece, 1));
                        king.setInCheck(true);
                    }
        
                    piece.addLegalMove(squareUpRigth(piece, 1));
                }
            }
        }

        if (piece.getY() != 'h' && pieceOnSquare(squareRigth(piece, 1)) == null){
            piece.addLegalMove(squareRigth(piece, 1));
        } else{
            if (pieceOnSquare(squareRigth(piece, 1)) != null && isDifferentColor(pieceOnSquare(squareRigth(piece, 1)), piece)){
                if(pieceOnSquare(squareRigth(piece, 1)) instanceof King){
                    King king = (King) pieceOnSquare(squareRigth(piece, 1));
                    king.setInCheck(true);
                }
    
                piece.addLegalMove(squareRigth(piece, 1));
            }
        }

        if (piece.getY() != 'a' && pieceOnSquare(squareLeft(piece, 1)) == null){
            piece.addLegalMove(squareLeft(piece, 1));
        } else{
            if (pieceOnSquare(squareLeft(piece, 1)) != null && isDifferentColor(pieceOnSquare(squareLeft(piece, 1)), piece)){
                if(pieceOnSquare(squareLeft(piece, 1)) instanceof King){
                    King king = (King) pieceOnSquare(squareLeft(piece, 1));
                    king.setInCheck(true);
                }
    
                piece.addLegalMove(squareLeft(piece, 1));
            }
        }
    }
}