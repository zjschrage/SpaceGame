package game.net.message;

import game.model.entities.Ship;
import game.model.utils.Coordinate;

import java.nio.ByteBuffer;

/*
 * Message Stream Format: 
 *  |..|       msg       ...|
 *  |..| x | y |theta|hp|...|
 */

public class Data {

    public static void encodeData(Ship ship, byte[] body) {
        Coordinate c = ship.getCoordinate();

        byte[] xBytes = new byte[8];
        ByteBuffer.wrap(xBytes).putDouble(c.x());

        byte[] yBytes = new byte[8];
        ByteBuffer.wrap(yBytes).putDouble(c.y());

        for (int i = 0; i < 8; i++) {
            body[i] = xBytes[i];
            body[i+8] = yBytes[i];
        }
    }

    public static Coordinate extractCoordinate(byte[] body) {
        byte[] xBytes = new byte[8];
        byte[] yBytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            xBytes[i] = body[i];
            yBytes[i] = body[i+8];
        }
        double x = ByteBuffer.wrap(xBytes).getDouble();
        double y = ByteBuffer.wrap(yBytes).getDouble();
        return new Coordinate(x, y);
    }

}