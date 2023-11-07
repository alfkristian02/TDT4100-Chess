//HUSKELISTE

// - Dokumentasjonskriving
    // - Forklare hvorfor ikke castle er testet isolert

    // - Huske å oppdatere klassediagram
// - Fikse noe feilsjekk på konstrøktør i abstractPiece kanskje?




package chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("fxui/StartPage.fxml")));
        scene.getStylesheets().add(getClass().getResource("css/startPage.css").toExternalForm());

        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
