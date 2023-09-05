package br.ematos.chatgpt.billreader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;
import javax.imageio.ImageIO;

public class AutoCrop {

  // main method with test image from resources folder
  public static void main(String[] args) throws Exception {
    BufferedImage sourceImage =
        ImageIO.read(
            Objects.requireNonNull(
                BillReaderService.class.getResource("/files/20230815_100750.jpg")));

    BufferedImage croppedImage = autoCrop(sourceImage);

    ImageIO.write(croppedImage, "jpg", new File("cropped.jpg"));
  }

  private static BufferedImage autoCrop(BufferedImage sourceImage) {
    int left = 0;
    int right = 0;
    int top = 0;
    int bottom = 0;
    boolean firstFind = true;

    for (int x = 0; x < sourceImage.getWidth() - 10; x++) {
      for (int y = 0; y < sourceImage.getHeight() - 10; y++) {
        // pixel is not empty
        if (sourceImage.getRGB(x, y) != 0) {

          Color color = new Color(sourceImage.getRGB(x, y), true);
          if (color.getRed() > 180) {
            System.out.println(color);
            // we walk from left to right, thus x can be applied as left on first finding
            if (firstFind) {
              left = x;
            }

            // update right on each finding, because x can grow only
            right = x;

            // on first find apply y as top
            if (firstFind) {
              top = y;
            } else {
              // on each further find apply y to top only if a lower has been found
              top = Math.min(top, y);
            }

            // on first find apply y as bottom
            if (bottom == 0) {
              bottom = y;
            } else {
              // on each further find apply y to bottom only if a higher has been found
              bottom = Math.max(bottom, y);
            }
            firstFind = false;
          }
        }
      }
    }

    return sourceImage.getSubimage(left, top, right - left, bottom - top);
  }
}
