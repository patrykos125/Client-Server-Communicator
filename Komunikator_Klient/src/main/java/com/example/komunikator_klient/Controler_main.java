package com.example.komunikator_klient;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.ENTER;

public class Controler_main implements Initializable {


    @FXML

    private VBox messageList;
    @FXML
    private TextField sendFiel;
    @FXML
    private Button sendButton;
    @FXML
    private ScrollPane scrollPane;

 private    Logic logic;


    public  Controler_main(Logic logic) {
        this.logic = logic;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sendButton.setOnAction(actionEvent -> sendMessage());
        logic.listenForMessage(messageList);
        SendOnPresedEnter();
        ScrollPaneGoDown();

    }

    public void sendMessage() {
        String message = sendFiel.getText();
        if (!message.isEmpty()) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 10));
            Text tex = new Text(message);
            TextFlow textFlow = new TextFlow(tex);
            textFlow.setStyle("-fx-color:rgb(255,255,255);"
                    + "-fx-background-color: rgb(15,125,242);"
                    + "-fx-background-radius: 20px;"
                    +"-fx-font-family: 'Roboto Thin';"
                     +"-fx-font-size: 14px");

            textFlow.setPadding(new Insets(5, 10, 5, 10));
            tex.setFill(Color.color(0.934, 0.934, 0.996));

            hBox.getChildren().add(textFlow);
            messageList.getChildren().add(hBox);


            logic.sendMessage(message);
            sendFiel.clear();


        }
    }

    public static void receiveMessage(String msg, VBox vbox) {

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text tex = new Text(msg);
        TextFlow textFlow = new TextFlow(tex);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235);"
                + "-fx-background-radius: 20px;"
                +"-fx-font-family: 'Roboto Thin';"
                +"-fx-font-size: 14px");

        textFlow.setPadding(new Insets(5, 10, 5, 10));


        hBox.getChildren().add(textFlow);


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);
            }
        });


    }

    private void SendOnPresedEnter() {
        sendFiel.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode()==ENTER){sendMessage();}

        });
    }

    private void ScrollPaneGoDown()   {

        messageList.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scrollPane.setVvalue((Double) t1);
            }
        });



    }
}




