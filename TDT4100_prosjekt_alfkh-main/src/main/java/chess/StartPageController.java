package chess;

import java.io.FileNotFoundException;
import java.io.IOException;

import chess.model.ChessGame;
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

public class StartPageController {

    @FXML
    private Button newGame, loadGame;

    @FXML
    private Text fileLoadError;

    @FXML
    private TextField filenameInput;

    @FXML
    private void handleNewGame() throws IOException{
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("fxui/PlayerNames.fxml")));
        scene.getStylesheets().add(getClass().getResource("css/playerNames.css").toExternalForm());

        Stage stage = (Stage) newGame.getScene().getWindow();

        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleLoad(ActionEvent event) throws IOException{

        if (filenameInput.getText().equals("")){
            filenameInput.setStyle("-fx-border-color: #9a1010;" + "-fx-border-width: 2px;");
            fileLoadError.setText("Fill in the filename please");
            } else{
            SaveHandler saveHandler = new SaveHandler();
            ChessGame chessGame = null;

            try{
                chessGame = saveHandler.load(filenameInput.getText());

                Node node = (Node) event.getSource();

                Stage stage = (Stage) node.getScene().getWindow();

                stage.setUserData(chessGame);

                stage.close();    

                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxui/Chess.fxml"));
                Parent root = loader.load();
        
                ChessController chessController = loader.getController();
        
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("css/chess.css").toExternalForm());
        
                stage.setTitle("Chess");
                stage.setScene(scene);
                stage.show();
        
                chessController.setChessGame(chessGame);
        
            } 
            
            catch (FileNotFoundException fileNotFoundMessage){
                filenameInput.setStyle("-fx-border-color: #9a1010;" + "-fx-border-width: 2px;");
                fileLoadError.setText("There is no saved file with this filename:/");
            }

            catch (IOException e){
                System.out.println("Something went wrong trying to open a new stage. Errormessage: " + e);
            }

            catch (Exception formatError){
                filenameInput.setStyle("-fx-border-color: #9a1010;" + "-fx-border-width: 2px;");
                fileLoadError.setText("The format of this file is wrong:/");
            }
        }
    }
}
