package game.net.message;

public enum MessageType {
    
    ASSIGN_ID,
    INFORM_PRESENCE,
    REQUEST_PRESENCE,
    DATA_REPORT,
    CLOSE;

    public static final MessageType values[] = values();

}
