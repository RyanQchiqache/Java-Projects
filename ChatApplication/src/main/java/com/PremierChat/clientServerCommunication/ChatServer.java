package com.PremierChat.clientServerCommunication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatServer {
    private final List<ClientHandlerChat> clients = new ArrayList<>();
    private ServerSocket serverSocket;
    private AtomicInteger clientIDs = new AtomicInteger(1);

    public ChatServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Chat server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                int clientID = clientIDs.getAndIncrement();
                ClientHandlerChat client = new ClientHandlerChat(clientSocket, this, clientID);
                clients.add(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void broadcastMessage(String message) {
        for (ClientHandlerChat client : clients) {
            client.sendMessage(message);
        }
    }

    public void sendPrivateMessage(int senderID, int recipientID, String privateMessage) {
        for (ClientHandlerChat client : clients) {
            if (client.getClientID() == recipientID || client.getClientID() == senderID) {
                client.sendMessage("(private) from " + senderID + " to " + recipientID + ": " + privateMessage);
            }
        }
    }

    public void remove(ClientHandlerChat clientHandler) {
        clients.remove(clientHandler);
    }

    public List<ClientHandlerChat> getClients() {
        return clients;
    }

    public static void main(String[] args) {
        new ChatServer(1234);
    }
}
