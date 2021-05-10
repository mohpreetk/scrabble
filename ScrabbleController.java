package assignment3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ScrabbleController {

    private String word = "";
    ScrabbleModel model = new ScrabbleModel();
    ObservableList observableList = FXCollections.observableArrayList();

    @FXML
    private TextField inputField;

    @FXML
    private Label errorField;

    @FXML
    private Label success;

    @FXML
    private Label runningTotal;

    @FXML
    private Label gameOver;

    @FXML
    private Label A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q , R, S, T, U, V, W, X, Y, Z;

    @FXML
    private ListView previousWords;

    @FXML
    private void initialize() {
        errorField.setText("");
        success.setText("");
        model.populateLetterPoints();
        model.populateLetterCount();
        previousWords.setVisible(false);
        previousWords.setManaged(false);
    }

    @FXML
    private void letterClicked(ActionEvent event) {
        Button clickedBtn = (Button)event.getSource();
        word += clickedBtn.getText();
        inputField.setText(word);
    }

    @FXML
    private void wordSubmitted(ActionEvent event){
        Label[] labels = {A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q , R, S, T, U, V,
                W, X, Y, Z};
        errorField.setText("");
        success.setText("");
        String submittedWord = inputField.getText();
        for (Label value : labels) {
            if (Integer.parseInt(value.getText()) == 0) {
                value.getParent().setDisable(true);
            }
        }
        if(model.addWord(submittedWord)){
            runningTotal.setText(String.valueOf(model.totalPoints));
            inputField.setText("");
            word = "";
            success.setText("Word Accepted");
            for (Label label : labels) {
                int letterCount = model.updateLabels(label.getId().charAt(0),
                        Integer.parseInt(label.getText()));
                label.setText(String.valueOf(letterCount));
                if (letterCount == 0) {
                    label.getParent().setDisable(true);
                }
            }
            if(model.checkBagForNoVowels() || model.checkBagForNoWords()){
                Button button = (Button) event.getSource();
                button.getParent().setDisable(true);
                gameOver.setDisable(false);
                gameOver.setVisible(true);
                gameOver.setText("Game Over!!!");
            }
            observableList.setAll(model.wordHistory);
            previousWords.setItems(observableList);
        }
        else {
            errorField.setText(model.error);
            word = "";
        }
    }

    @FXML
    private void history(ActionEvent event) {
        Button history = (Button) event.getSource();
        if(history.getText().equals("History")) {
            previousWords.setVisible(true);
            previousWords.setManaged(true);
            history.setText("Collapse");
        }
        else if(history.getText().equals("Collapse")){
            previousWords.setVisible(false);
            previousWords.setManaged(false);
            history.setText("History");
        }
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("scrabblewelcome.fxml"));
        stage.setTitle("Scrabble");
        stage.setScene(new Scene(root, 700, 700));
        stage.show();
    }
}
