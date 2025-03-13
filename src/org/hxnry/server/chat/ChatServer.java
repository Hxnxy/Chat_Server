package org.hxnry.server.chat;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ChatServer {

    public ChatServer(int port) throws IOException {
        System.out.println("Booting up the chat server...");
        ServerSocket server = new ServerSocket(port);
        server.setSoTimeout(0);

        while (true) {
            try {
                Socket clientSocket = server.accept();
                ChatHandler c = new ChatHandler(clientSocket);
                c.start();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
