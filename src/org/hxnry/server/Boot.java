package org.hxnry.server;


import org.hxnry.server.chat.ChatServer;

import java.io.IOException;

public class Boot {

    public static void main(String[] args) throws IOException {
        new ChatServer(20);
    }

}
