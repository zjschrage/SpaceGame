package game.view.assets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Assets {

    public static final String IMG_RESOURCE_FOLDER = "/img/";

    private static Map<Integer, BufferedImage> spriteSheetMap;
    private static Map<AssetEnum, BufferedImage> entityMap;

    public Assets() {
        spriteSheetMap = new HashMap<>();
        entityMap = new HashMap<>();
        loadSprites();
    }

    public static BufferedImage getImage(AssetEnum a) {
        return entityMap.get(a);
    }

    private static void loadSprites() {
        loadSpriteSheet("spritesheet.png", 0);
        addImage(AssetEnum.SHIP, 0, 0, 0, 32, 32);
        addImage(AssetEnum.WALL, 0, 32*2, 32*3, 32, 32);
    }

    private static void loadSpriteSheet(String path, int id) {
        try {
            BufferedImage b = ImageIO.read(Assets.class.getResource(IMG_RESOURCE_FOLDER + path));
            spriteSheetMap.put(id, b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addImage(AssetEnum a, int id, int x, int y, int width, int height) {
        entityMap.put(a, crop(id, x, y, width, height));
    }

    private static BufferedImage crop(int id, int x, int y, int width, int height) {
        return spriteSheetMap.get(id).getSubimage(x, y, width, height);
    }

}
