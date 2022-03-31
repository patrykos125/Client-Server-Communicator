package com.example.komunikator_klient;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controler_login {
    //login
    @FXML
    Button connectButton;
    @FXML
       TextField adresIP;
    @FXML
      TextField port;
    @FXML
      TextField username;
    @FXML
      Label usednickErroor;


    /**
     * main windoow
     */
    @FXML

      VBox messageList;
    @FXML
      TextField sendFiel;
    @FXML
      Button sendButton;
    @FXML
      ScrollPane scrolPane;

    static Logic logic;

    @FXML
    public void connectAction() throws IOException {

        String adresIPString = adresIP.getText();
        String portstring = port.getText();
        String usernameString = username.getText();

        logic = new Logic(adresIPString, portstring, usernameString);


        if (logic.validNick()) {

            usednickErroor.setText("");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            fxmlLoader.setController(this);

            Parent root = fxmlLoader.load();

            Stage window = (Stage) connectButton.getScene().getWindow();
            window.setScene(new Scene(root, 600, 400));


        messageList.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scrolPane.setVvalue((Double) t1);
            }
        });


            sendButton.setOnAction(actionEvent -> sendMessage());
            logic.listenForMessage(messageList);


        } else {
            usednickErroor.setText("Your nick is already used");
        }


    }

    public void sendMessage() {
        String message =sendFiel.getText();
        if(!message.isEmpty()){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5,5,5,10));
            Text tex = new Text(message);
            TextFlow textFlow = new TextFlow(tex);
            textFlow.setStyle("-fx-color:rgb(239,242,255);"
                    +"-fx-background-color: rgb(15,125,242);"
                    +"-fx-background-radius: 20px;"+"-fx-font-family:'Arial';");

            textFlow.setPadding(new Insets(5,10,5,10));
            tex.setFill(Color.color(0.934,0.934,0.996));

            hBox.getChildren().add(textFlow);
            messageList.getChildren().add(hBox);


            logic.sendMessage(message);
            sendFiel.clear();


    }
    }

    public static  void receiveMessage(String msg, VBox vbox) {

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text tex = new Text(msg);
        TextFlow textFlow = new TextFlow(tex);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235);"
        +"-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5,10,5,10));
    //    tex.setFill(Color.color(0.934,0.934,0.996));

        hBox.getChildren().add(textFlow);





        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);
            }
        });






    }


}