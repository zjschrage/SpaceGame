package game.net.message;

import java.util.Arrays;

/*
 * Message Stream Format: 
 *  |    hdr     | msg |
 *  |src|dst|type| msg |
 */

public class Message {

    public static final int HDR_ENTRIES = 4;
    public static final int MAX_MESSAGE_LEN = 128;
    public static final int MAX_BODY_LEN = MAX_MESSAGE_LEN - HDR_ENTRIES;

    public byte src;
    public byte dst;
    public byte scope;
    public byte type;
    public byte[] body;

    public Message() {
        body = new byte[MAX_BODY_LEN];
    }

    public Message(byte[] bstream) {
        this.src = bstream[0];
        this.dst = bstream[1];
        this.scope = bstream[2];
        this.type = bstream[3];
        this.body = Arrays.copyOfRange(bstream, HDR_ENTRIES, bstream.length);
    }

    public byte[] streamify() {
        byte[] stream = new byte[MAX_MESSAGE_LEN];
        stream[0] = src;
        stream[1] = dst;
        stream[2] = scope;
        stream[3] = type;
        for (int i = 0; i < Math.min(body.length, MAX_BODY_LEN); i++) {
            stream[i + HDR_ENTRIES] = body[i];
        }
        return stream;
    }

    public MessageScope getScope() { return MessageScope.values[scope]; }

    public MessageType getType() {
        return MessageType.values[type];
    }
    
}
