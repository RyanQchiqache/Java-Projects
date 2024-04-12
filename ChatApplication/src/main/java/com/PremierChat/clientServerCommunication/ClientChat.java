package com.PremierChat.clientServerCommunication;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ClientChat extends Application {

    private int port = 1234;
    private String address;

    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    private TextArea output;
    private TextField input;
    private Button sendButton;

    @Override
    public void start(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog("localhost");
        dialog.setTitle("IP Address Input");
        dialog.setHeaderText("Connect to Chat Server");
        dialog.setContentText("Enter the IP Address:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(ip -> address = ip);

        if (address != null && !address.isEmpty()) {
            initializeConnection();
        }

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2b2b2b, #1e1e1e);");

        // Background image if needed
        /*Image backgroundImage = new Image(getClass().getResource("/path/to/your/image.jpg").toExternalForm());
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        root.setBackground(new Background(background));*/

        output = new TextArea();
        output.setEditable(false);
        output.setStyle("-fx-background-color: #333; -fx-text-fill: #ddd; -fx-font-family: 'Consolas';");

        input = new TextField();
        input.setPromptText("Enter your message here...");
        input.setStyle("-fx-background-color: #555; -fx-text-fill: #ddd; -fx-font-family: 'Consolas';");

        sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #5a5; -fx-text-fill: #fff;");
        sendButton.setOnAction(event -> {
            sendMessage(input.getText());
            input.clear();
        });

        HBox inputBox = new HBox(5, input, sendButton);
        inputBox.setAlignment(Pos.BOTTOM_CENTER);
        inputBox.setPadding(new Insets(10, 20, 10, 20));

        root.setBottom(inputBox);
        root.setCenter(new ScrollPane(output));

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Chat Client");
        primaryStage.setScene(scene);
        primaryStage.show();

        input.setOnAction(event -> {
            sendMessage(input.getText());
            input.clear();
        });
    }

    private void initializeConnection() {
        try {
            clientSocket = new Socket(address, port);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            Thread receiveThread = new Thread(this::receiveMessage);
            receiveThread.start();
        } catch (IOException ex) {
            showAlert("Connection Failed", "Could not connect to the server: " + ex.getMessage());
        }
    }

    private void receiveMessage() {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                String msg = message;
                Platform.runLater(() -> output.appendText(msg + "\n"));
            }
        } catch (IOException ex) {
            Platform.runLater(() -> showAlert("Connection Lost", "Lost connection to the server: " + ex.getMessage()));
        }
    }

    private void sendMessage(String message) {
        if (message != null && !message.isEmpty()) {
            out.println(message);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
