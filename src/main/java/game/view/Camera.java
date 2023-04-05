package game.view;

import game.model.entities.Entity;

import java.util.ResourceBundle;

public class Camera {

    private ResourceBundle res = ResourceBundle.getBundle("view_properties");
    private double xOffset;
    private double yOffset;
    private double width;
    private double height;
    private int tilesInRow = 0;
    private int tilesInCol = 0;

    public Camera(double xOffset, double yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        width = Integer.parseInt(res.getString("FRAME_X"));
        height = Integer.parseInt(res.getString("FRAME_Y"));
    }

    public void setTiles(int tilesInRow, int tilesInCol) {
        this.tilesInRow = tilesInRow;
        this.tilesInCol = tilesInCol;
    }

    public void centerOnEntity(Entity e) {
        xOffset = (e.getHBox().x - width/2);
        yOffset = (e.getHBox().y - height/2);
        checkWhiteSpace();
    }

    public void checkWhiteSpace() {
        if (xOffset < 0) xOffset = 0;
        else if (xOffset > (tilesInRow)*32 - width) xOffset = (tilesInRow)*32 - width;
        if (yOffset < 0) yOffset = 0;
        else if (yOffset > (tilesInCol+2)*32 - height) yOffset = (tilesInCol+2)*32 - height;
    }

    public void move(double xDist, double yDist) {
        xOffset += xDist;
        yOffset += yDist;
    }

    public double getXOffset() {
        return xOffset;
    }

    public double getYOffset() {
        return yOffset;
    }

    public void setXOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public void setYOffset(double yOffset) {
        this.yOffset = yOffset;
    }

}
