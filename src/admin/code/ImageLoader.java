package admin.code;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class ImageLoader {


    public static ImageIcon loadImage(String imagePath) {
        if (!imagePath.startsWith("/")) {
            imagePath = "/" + imagePath;
        }

        String externalImagePath = "C:\\Users\\hedra\\market_proj" + imagePath;
        File imageFile = new File(externalImagePath);

        if (imageFile.exists()) {
            return new ImageIcon(imageFile.getAbsolutePath());
        } else {

            URL imageUrl = ImageLoader.class.getResource(imagePath);
            if (imageUrl != null) {
                return new ImageIcon(imageUrl);
            } else {
                System.err.println("Image not found: " + imagePath);
                return null;
            }
        }
    }


    public static ImageIcon loadImage(String imagePath, int width, int height) {
        ImageIcon originalIcon = loadImage(imagePath);
        if (originalIcon != null) {
            Image img = originalIcon.getImage();
            Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImg);
        }
        return null;
    }
}