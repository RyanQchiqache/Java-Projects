package com.PremierChat.clientServerCommunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.PremierChat.clientServerCommunication.ChatServer;
import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;

public class ClientHandlerChat implements Runnable {
    private final ChatServer chatServer;
    private final Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private final int clientID;
    private String name;

    public ClientHandlerChat(Socket clientSocket, ChatServer chatServer, int clientID) {
        this.chatServer = chatServer;
        this.clientSocket = clientSocket;
        this.clientID = clientID;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            chatServer.broadcastMessage(clientSocket.getInetAddress().getHostAddress() + " connected");
            setName();
            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("@")) {
                    processPrivateMessage(message);
                } else {
                    chatServer.broadcastMessage(name + ": " + message);
                }
            }
        } catch (IOException e) {
            chatServer.remove(this);
            chatServer.broadcastMessage(name + " disconnected");
        } finally {
            closeResources();
        }
    }

    private void processPrivateMessage(String message) {
        int spaceIn = message.indexOf(" ");
        if (spaceIn != -1) {
            String recipient = message.substring(1, spaceIn);
            String privateMessage = message.substring(spaceIn + 1).trim();
            if (!privateMessage.isEmpty()) {
                chatServer.sendPrivateMessage(clientID, Integer.parseInt(recipient), privateMessage);
            }
        } else {
            sendMessage("Available clients: \n" + getClientsList());
        }
    }

    private String getClientsList() {
        StringBuilder clientList = new StringBuilder();
        for (ClientHandlerChat client : chatServer.getClients()) {
            clientList.append(client.getClientID()).append(": ").append(client.getName()).append("\n");
        }
        return clientList.toString();
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void setName() {
        Platform.runLater(() -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter Name");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter a name:");
            dialog.showAndWait().ifPresent(name -> {
                if (!name.trim().isEmpty()) {
                    this.name = name.trim();
                    sendMessage("Name accepted: " + this.name);
                }
            });
        });
    }

    public int getClientID() {
        return clientID;
    }

    public String getName() {
        return name;
    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
