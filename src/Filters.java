
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class Filters {

    //Parlaklık ve Kontrast Ayarı
    public BufferedImage brightnessControl(Image image, float brightness) {
        BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics bg = bi.getGraphics();
        if (bi.getColorModel().hasAlpha()) {
            System.out.println("Image has got an alpha channel");
        }
        bg.drawImage(image, 0, 0, null);
        bg.dispose();
        RescaleOp rescaleOp = new RescaleOp(brightness, 0, null);
        rescaleOp.filter(bi, bi);
        return bi;
    }

    //Siyah - Beyaz Filtresi
    public BufferedImage whiteBlackFilter(BufferedImage bi) {
        Color white = new Color(255,255,255);
        Color black = new Color(0,0,0);
        int whitePixel = white.getRGB();
        int blackPixel = black.getRGB();

        for (int i = 0; i< bi.getWidth(); i++) {
            for (int j = 0; j< bi.getHeight(); j++) {
                Color color = new Color(bi.getRGB(i,j));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                if((red + green + blue)/ 3 <= 127){
                    bi.setRGB(i,j,blackPixel);
                } else {
                    bi.setRGB(i,j,whitePixel);
                }
            }
        }
        return bi;
    }
}
