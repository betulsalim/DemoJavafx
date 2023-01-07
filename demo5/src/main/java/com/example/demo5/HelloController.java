package com.example.demo5;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HelloController extends Application {

    DataInputStream dis;
    DataOutputStream dos;
    TextArea textArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Chat Client");

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(8);
        root.setAlignment(Pos.BOTTOM_LEFT);

        textArea = new TextArea();
        textArea.setPrefHeight(400);
        textArea.setEditable(false);

        HBox hBox = new HBox();
        hBox.setSpacing(8);

        TextField textField = new TextField();
        textField.setPrefWidth(300);

        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> {
            try {
                dos.writeUTF(textField.getText());
                textField.clear();
                textField.requestFocus();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        hBox.getChildren().addAll(textField, sendButton);

        root.getChildren().addAll(textArea, hBox);

        Scene scene = new Scene(root, 400, 450);
        stage.setScene(scene);
        stage.show();

        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8888);
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    String message = dis.readUTF();
                    textArea.appendText(message + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
