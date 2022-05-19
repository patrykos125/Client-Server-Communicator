
package com.example.komunikator_klient;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;

public class Controler_login implements Initializable {

    @FXML
    private Button connectButton;
    @FXML
    private TextField adresIP;
    @FXML
    private TextField port;
    @FXML
    private TextField username;
    @FXML
    private Label usednickErroor;
    @FXML
    private Pane pane;


    static Logic logic;

    @FXML
    public void connectAction() throws IOException {

        logIn();


    }

    private void logIn() throws IOException  {
        String adresIPString = adresIP.getText();
        String portstring = port.getText();
        String usernameString = username.getText();

        logic = new Logic(adresIPString, portstring, usernameString);


        if (logic.validNick()) {

            usednickErroor.setText("");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));


            fxmlLoader.setControllerFactory(aClass -> {
                return new Controler_main(logic);
            });

            Parent root = fxmlLoader.load();


            Stage window = (Stage) connectButton.getScene().getWindow();
            window.setScene(new Scene(root, 600, 400));




        } else {
            usednickErroor.setText("Your nick is already used");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sendOnPresedEnter();

    }
    private void sendOnPresedEnter() {
        pane.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode()==ENTER){

                try {
                    logIn();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
