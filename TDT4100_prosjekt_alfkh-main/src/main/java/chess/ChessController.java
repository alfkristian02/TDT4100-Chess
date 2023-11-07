package chess;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import chess.model.Bishop;
import chess.model.ChessGame;
import chess.model.King;
import chess.model.Knight;
import chess.model.Pawn;
import chess.model.AbstractPiece;
import chess.model.Queen;
import chess.model.Rook;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class ChessController{

    private ChessGame chessGame;
    private SaveHandler saveHandler;
    private ArrayList<Button> allButtons;

    @FXML
    private Button buttonClicked;

    @FXML
    private Text feedback, namePlayerWhite, namePlayerBlack, isInCheckText, gameEnd;

    @FXML
    private TextField filenameInput;

    @FXML
    private StackPane gameEndBackground;

    @FXML private Button a8, a7, a6, a5, a4, a3, a2, a1;
    @FXML private Button b8, b7, b6, b5, b4, b3, b2, b1;
    @FXML private Button c8, c7, c6, c5, c4, c3, c2, c1;
    @FXML private Button d8, d7, d6, d5, d4, d3, d2, d1;
    @FXML private Button e8, e7, e6, e5, e4, e3, e2, e1;
    @FXML private Button f8, f7, f6, f5, f4, f3, f2, f1;
    @FXML private Button g8, g7, g6, g5, g4, g3, g2, g1;
    @FXML private Button h8, h7, h6, h5, h4, h3, h2, h1;	


    // Load a game from the starting page when loading the app.
    // It is alo used when you start a new game after writing in the names.
    public void setChessGame(ChessGame chessGame) {
        this.chessGame = chessGame;

        namePlayerWhite.setText("Playing as white: " + chessGame.getPlayerWhite().getName());
        namePlayerBlack.setText("Playing as black: " + chessGame.getPlayerBlack().getName());

        updateBoard();
    }


    // All FXML-tags vil run after the constructor (an empty constructor in this case), including this one.
    // Setting fields
	@FXML
	private void initialize() {
        saveHandler = new SaveHandler();
        chessGame = new ChessGame(null, null);
        
		List<Button> buttonsToAdd = List.of(
            a8, a7, a6, a5, a4, a3, a2, a1,
            b8, b7, b6, b5, b4, b3, b2, b1,
            c8, c7, c6, c5, c4, c3, c2, c1,
            d8, d7, d6, d5, d4, d3, d2, d1,
            e8, e7, e6, e5, e4, e3, e2, e1,
            f8, f7, f6, f5, f4, f3, f2, f1,
            g8, g7, g6, g5, g4, g3, g2, g1,
            h8, h7, h6, h5, h4, h3, h2, h1
		);

        allButtons = new ArrayList<>(buttonsToAdd);

        setButtonAttributes();
		updateBoard();
	}


    // Updating the board, such as adding the pieces to the right squares, checking if there is any pawnpromotions
    // to make and if the game is over
	private void updateBoard() {
        int numberOfKings = 0;
        isInCheckText.setText("");

        for (Button button : allButtons) {
            button.setStyle("-fx-background-color: transparent;");
            button.setText("");
        }

        for (AbstractPiece piece : chessGame.getPiecesInPlay()){
            for (Button button : allButtons) {
                if (button.getId().equals("" + piece.getY() + piece.getX())){
                    button.setText("" + piece.getType());
                }
            }

            if (piece instanceof King){
                numberOfKings++;
                King king = (King) piece;
                if(king.getInCheck()){
                    if (!king.isWhite()){
                        isInCheckText.setText("Black is in check");
                    } else{
                        isInCheckText.setText("White is in check");
                    }
                }
            }
        }

        if (numberOfKings != 2){
            endGame();
        } else{
            for (AbstractPiece piece : chessGame.getPiecesInPlay()){
                if (piece instanceof Pawn && (piece.getX() == 1 || piece.getX() == 8)){
                    Pawn pawn = (Pawn) piece;
                    handlePawnPromotion(pawn);
                }
            }
        }

        buttonClicked = null;
	}

    // Setting what the buttons do. Long and tricky method. More comments inside it.
    private void setButtonAttributes(){

        for (Button button : allButtons) {
            DropShadow shadow = new DropShadow();

            // Sets a shadow when the mouse enters a button
            button.addEventHandler(MouseEvent.MOUSE_ENTERED, 
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                    button.setEffect(shadow);
                }
            });

            // Removes the shadow when the mouse is no longer hovering the button
            button.addEventHandler(MouseEvent.MOUSE_EXITED, 
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                    button.setEffect(null);
                }
            });
            
            // Sets what happens when a button (or piece) is clicked
            button.setOnAction(new EventHandler<ActionEvent>(){
                @Override

                public void handle(ActionEvent e) {

                    // When a button without a piece is clicked:
                    if (button.getText().equals("")){
                        if (buttonClicked == null){
                            feedback.setText("There is no piece on this square");
                        }else{
                            try {
                                chessGame.move(chessGame.getPieceBySquare(buttonClicked.getId()), button.getId());
                                updateBoard();
                                feedback.setText("");
                                buttonClicked = null;    
                            } catch (IllegalStateException wrongMove) {
                                feedback.setText(wrongMove.getMessage());
                                updateBoard();
                            }	
                        }
                    } 
                    
                    // If you press a button with your own piece ons it:
                    else if (buttonClicked == null || chessGame.getPieceBySquare(buttonClicked.getId()).isWhite() == chessGame.getPieceBySquare(button.getId()).isWhite()){

                        // Updates board, and then adds the color on the legal moves you can make with this piece.
                        if (chessGame.getWhitePiecesInPlay().contains(chessGame.getPieceBySquare(button.getId())) && chessGame.getPlayerWhite().getTurn()){
                            updateBoard();

                            for (String square : chessGame.getPieceBySquare(button.getId()).getLegalMoves()) {
                                for (Button btn : allButtons) {
                                    if (btn.getId().equals(square)){
                                        btn.setStyle("-fx-background-radius: 50px;" + "-fx-background-color: #bfbfbf;"); 
                                    } 
                                } 
                            }

                            if (chessGame.getPieceBySquare(button.getId()) instanceof King){
                                King king = (King) chessGame.getPieceBySquare(button.getId());

                                for (String square : king.getCastleMoves()) {
                                    for (Button btn : allButtons) {
                                        if (btn.getId().equals(square)){
                                            btn.setStyle("-fx-background-radius: 50px;" + "-fx-background-color: #bfbfbf;"); 
                                        } 
                                    } 
                                }
                            }
                            
                            buttonClicked = button;

                        } 
                        
                        // Updates board, and then adds the color on the legal moves you can make with this piece.
                        else if (chessGame.getBlackPiecesInPlay().contains(chessGame.getPieceBySquare(button.getId())) && chessGame.getPlayerBlack().getTurn()){
                            updateBoard();

                            for (String square : chessGame.getPieceBySquare(button.getId()).getLegalMoves()) {
                                for (Button btn : allButtons) {
                                    if (btn.getId().equals(square)){
                                        btn.setStyle("-fx-background-radius: 50px;" + "-fx-background-color: #bfbfbf;"); 
                                    } 
                                } 
                            }

                            if (chessGame.getPieceBySquare(button.getId()) instanceof King){
                                King king = (King) chessGame.getPieceBySquare(button.getId());

                                for (String square : king.getCastleMoves()) {
                                    for (Button btn : allButtons) {
                                        if (btn.getId().equals(square)){
                                            btn.setStyle("-fx-background-radius: 50px;" + "-fx-background-color: #bfbfbf;"); 
                                        } 
                                    } 
                                }
                            }

                            buttonClicked = button;

                        } 
                        
                        // If you press your opponents piece, and you don't have the option of taking it with the piece you have selected.
                        else {
                            String helpVariable;
                            if (!chessGame.getPieceBySquare(button.getId()).isWhite()){
                                helpVariable = "White";
                            } else{
                                helpVariable = "Black";
                            }

                            updateBoard();
                            feedback.setText(helpVariable + " to move.");
                        }


                        } 
                        
                        // Handles capture of a piece
                        else{
                            try {
                                chessGame.capture(chessGame.getPieceBySquare(button.getId()), chessGame.getPieceBySquare(buttonClicked.getId()));
                                updateBoard();
                                feedback.setText("");
                                buttonClicked = null;    
                            } catch (IllegalStateException errorMessage) {
                                feedback.setText(errorMessage.getMessage());
                                updateBoard();
                            }	
                        }
                    }  
            });
        }
    }

    // Creates a popup and lets you chose what piece you want to promote to
    private void handlePawnPromotion(Pawn pawn) {

        Alert alert = new Alert(AlertType.NONE);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("css/alert.css").toExternalForm());

        ButtonType buttonTypeOne;
        ButtonType buttonTypeTwo;
        ButtonType buttonTypeThree;
        ButtonType buttonTypeFour;


        if (pawn.isWhite()){
            buttonTypeOne = new ButtonType("\u2656");
            buttonTypeTwo = new ButtonType("\u2658");
            buttonTypeThree = new ButtonType("\u2657");
            buttonTypeFour = new ButtonType("\u2655");
        }else{
            buttonTypeOne = new ButtonType("\u265C");
            buttonTypeTwo = new ButtonType("\u265E");
            buttonTypeThree = new ButtonType("\u265D");
            buttonTypeFour = new ButtonType("\u265B");
        }

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeFour);

        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == buttonTypeOne){
            if (pawn.isWhite()){
                Rook newRook = new Rook('W', pawn.getY(), pawn.getX(), true);
                chessGame.pawnPromotion(pawn, newRook);
            }else{
                Rook newRook = new Rook('B', pawn.getY(), pawn.getX(), true);
                chessGame.pawnPromotion(pawn, newRook);
            }
        } else if (result.get() == buttonTypeTwo) {
            if (pawn.isWhite()){
                Knight newKnight = new Knight('W', pawn.getY(), pawn.getX());
                chessGame.pawnPromotion(pawn, newKnight);
            } else{
                Knight newKnight = new Knight('B', pawn.getY(), pawn.getX());
                chessGame.pawnPromotion(pawn, newKnight);
            }
        } else if (result.get() == buttonTypeThree) {
            if (pawn.isWhite()){
                Bishop newBishop = new Bishop('W', pawn.getY(), pawn.getX());
                chessGame.pawnPromotion(pawn, newBishop);
            } else{
                Bishop newBishop = new Bishop('B', pawn.getY(), pawn.getX());
                chessGame.pawnPromotion(pawn, newBishop);
            }
        } else if (result.get() == buttonTypeFour){
            if (pawn.isWhite()){
                Queen newQueen = new Queen('W', pawn.getY(), pawn.getX());
                chessGame.pawnPromotion(pawn, newQueen);
            } else{
                Queen newQueen = new Queen('B', pawn.getY(), pawn.getX());
                chessGame.pawnPromotion(pawn, newQueen);
            }
        }

        updateBoard();
    }

    // Saves to file
    @FXML
    private void handleSave() {
        if (filenameInput.getText().equals("")){
            filenameInput.setStyle("-fx-border-color: #9a1010;" + "-fx-border-width: 2px;");
            feedback.setText("Fill in filename please");
        } else{
            try{
                saveHandler.save(chessGame, filenameInput.getText());
                feedback.setText("Saved successfully");
            } catch (IOException e){
                feedback.setText("An error occured when trying to save to file");
            }
        }
    }


    // onAction for the back-button in the lower right
    @FXML
    private void handleBack(ActionEvent event){
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxui/StartPage.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("css/startPage.css").toExternalForm());

        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();

        } catch (IOException e){
            System.out.println("Something went wrong trying to open a new stage. Errormessage: " + e);
        } 
    }


    private void endGame() {
        for (Button button : allButtons){
            button.setDisable(true);
        }

        gameEndBackground.setStyle("-fx-background-color: #eeeed2;");

        for (AbstractPiece piece : chessGame.getPiecesInPlay()){
            if (piece instanceof King && piece.isWhite()){
                gameEnd.setText("White won the game!");
            }

            if (piece instanceof King && !piece.isWhite()){
                gameEnd.setText("Black won the game!");
            }
        }    
    }
}