package edu.yeditepe.cse212.battlecity.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageLoader {
	private static HashMap<String, BufferedImage> cache = new HashMap<>();
    private static final String BASE_PATH = "resources/battle_city_sprites/";
    
    public static BufferedImage load(String relativePath) {
        if(cache.containsKey(relativePath)) {
            return cache.get(relativePath);
        }
        
        try {
            File file = new File(BASE_PATH + relativePath);
            BufferedImage img = ImageIO.read(file);
            cache.put(relativePath, img);
            return img;
        }
        catch(IOException e) {
            System.err.println("Failed to load image: " + relativePath);
            return null;
        }
        catch(IllegalArgumentException e) {
            System.err.println("Image not found: " + relativePath);
            return null;
        }
    }
}
