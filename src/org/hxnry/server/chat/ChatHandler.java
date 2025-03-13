package org.hxnry.server.chat;


import org.hxnry.client.wrappers.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;

public class ChatHandler extends Thread {

    protected final Socket socket;
    protected final InetAddress address;
    protected final ObjectInputStream ois;
    protected final ObjectOutputStream oos;
    protected static final Vector<ChatHandler> handlers = new Vector<>();

    public ChatHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.address = socket.getInetAddress();
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.oos = new ObjectOutputStream(socket.getOutputStream());
    }

    public void run() {
        try {
            System.out.println("New Session from: " + address);
            handlers.addElement(this);
            while(socket.isConnected()) {
                Object object = null;
                while((object =(String) ois.readObject()) != null) {
                    if(object instanceof String) {
                        if(object.equals("~//-quit")) {
                            socket.close();
                            System.out.println("Closed connection with " + address);
                            break;
                        }
                        System.out.println(address + ": " + object);
                        broadcast(object.toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            handlers.removeElement(this);
            try {
                ois.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected void sendMessage(Message message) {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void broadcast(String message) throws InterruptedException {
        synchronized(ChatHandler.handlers) {
            Enumeration e = ChatHandler.handlers.elements();
            while(e.hasMoreElements()) {
                ChatHandler c = (ChatHandler) e.nextElement();
                try {
                    synchronized (c.oos) {
                        c.oos.writeObject(new Message("Broadcasted Message", message, 0, System.currentTimeMillis()));
                    }
                    c.oos.flush();
                } catch (IOException ex) {
                    c.join();
                }
            }
        }
    }

}
