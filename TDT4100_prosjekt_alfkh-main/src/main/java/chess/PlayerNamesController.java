package chess;

import java.io.IOException;

import chess.model.ChessGame;
import chess.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlayerNamesController {
    
    @FXML
    private Button sendData;

    @FXML
    private TextField white, black;

    @FXML
    private Text feedback;


    // Because of the FXML-tag this runs after the constructor.
    @FXML
    private void sendData(ActionEvent event){
        if (white.getText().equals("") || black.getText().equals("")){
            white.setStyle("-fx-border-color: #9a1010;" + "-fx-border-width: 2px;");
            black.setStyle("-fx-border-color: #9a1010;" + "-fx-border-width: 2px;");

            feedback.setText("Fill in both names to  continue:)");
        } else{
            Player playerWhite = new Player(white.getText(), 'W', true);
            Player playerBlack = new Player (black.getText(), 'B', false);
    
            ChessGame ChessGame = new ChessGame(playerWhite, playerBlack);
    
            Node node = (Node) event.getSource();
    
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
    
            try{
    
            stage.setUserData(ChessGame);
    
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxui/Chess.fxml"));
            Parent root = loader.load();
    
            ChessController chessController = loader.getController();
    
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("css/chess.css").toExternalForm());
    
            stage.setTitle("Chess");
            stage.setScene(scene);
            stage.show();
    
            chessController.setChessGame(ChessGame);
            } catch (IOException e){
                System.out.println("Something went wrong trying to open a new stage. Errormessage: " + e);
            }    
        }

    }

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
}
