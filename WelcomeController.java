package assignment3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class WelcomeController {

    public WelcomeController(){}

    @FXML
    private void startGame(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("gamepage.fxml"));
        stage.setTitle("Scrabble");
        stage.setScene(new Scene(root, 700, 700));
        stage.show();
    }

    @FXML
    private void initialize() {

    }
}
