package org.hxnry.client.wrappers;

import java.io.Serializable;

/**
 * @author Hxnry
 * @since June 02, 2016
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private String sender;
    private String message;
    private int id;
    private long messageId;

    public Message(final String sender, final String message, final int id, final long messageId) {
        this.sender = sender;
        this.message = message;
        this.id = id;
        this.messageId = messageId;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return message;
    }

    public int getId() {
        return id;
    }

    public long getMessageId() {
        return messageId;
    }

    @Override
    public String toString() {
        return "Message:";
    }
}

