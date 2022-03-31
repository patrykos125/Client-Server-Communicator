package com.example.komunikator_klient;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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

            sendButton.setOnAction(actionEvent -> sendMessage());

            logic.listenForMessage(messageList);

        } else {
            usednickErroor.setText("Your nick is already used");
        }


    }

    public void sendMessage() {
        if (!sendFiel.getText().isEmpty()) {
            messageList.getChildren().add(new Text(sendFiel.getText()));

            logic.sendMessage(sendFiel.getText());

            sendFiel.clear();
        }


    }

    public static void receiveMessage(String msg, VBox vbox) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(new Text(msg));


            }
        });


    }


}