package com.PremierChat.clientServerCommunication;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Platform;

public class ClientChat extends Application {

    private int port = 1234;
    private String address;

    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    private TextArea output;
    private TextField input;

    private Button readyButton;
    private Button chatButton;
    private boolean ready = false;

    @Override
    public void start(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("IP Address Input");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the IP Address:");
        dialog.showAndWait().ifPresent(ip -> address = ip);

        if (address != null && !address.isEmpty()) {
            receiveMessage();
        }

        BorderPane root = new BorderPane();
        output = new TextArea();
        output.setEditable(false);
        input = new TextField();

        HBox buttonPanel = new HBox(10);
        readyButton = new Button("Ready");
        readyButton.setOnAction(e -> toggleReadyStatus());
        chatButton = new Button("Send Message");
        chatButton.setOnAction(e -> sendMessage(input.getText()));

        buttonPanel.getChildren().addAll(readyButton, chatButton, new Label("Selected Robot: ")); // Adjust or remove "Selected Robot:" if not relevant

        root.setBottom(input);
        root.setCenter(new ScrollPane(output));
        root.setTop(buttonPanel);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Chat");
        primaryStage.setScene(scene);
        primaryStage.show();

        input.setOnAction(event -> sendMessage(input.getText()));
    }

    private void toggleReadyStatus() {
        ready = !ready;
        String status = ready ? "Ready" : "Not Ready";
        readyButton.setText(status);
        sendMessage(ready ? "is ready" : "is not ready");
    }

    private void sendMessage(String message) {
        if (!message.isEmpty()) {
            out.println(message);
            out.flush();
            input.setText("");
        }
    }

    private void receiveMessage() {
        try {
            clientSocket = new Socket(address, port);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        String finalMsg = msg;
                        Platform.runLater(() -> output.appendText(finalMsg + "\n"));
                    }
                } catch (IOException ex) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Connection failed: " + ex.getMessage());
                        alert.showAndWait();
                    });
                    closeResources();
                }
            }).start();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to connect: " + e.getMessage());
            alert.showAndWait();
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
