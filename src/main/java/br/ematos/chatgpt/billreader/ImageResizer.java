package br.ematos.chatgpt.billreader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageResizer {

  /**
   * @param inputFilePath The input image file path
   * @param outputFilePath The output image file path
   * @param scaleFactor Define the percentage by which you want to resize the image
   */
  public static String resizeImage(
      String inputFilePath, String outputFilePath, Double scaleFactor) {

    log.info(
        "Start rotating image with parameters: \n\t<inputFilePath> {}, \n\t<outputFilePath> {}, \n\t<scaleFactor> {}",
        inputFilePath,
        outputFilePath,
        scaleFactor);

    try {
      log.info("Load the input image");
      BufferedImage inputImage = ImageIO.read(new File(inputFilePath));

      log.info("Calculate the new dimensions");
      int newWidth = (int) (inputImage.getWidth() * scaleFactor);
      int newHeight = (int) (inputImage.getHeight() * scaleFactor);

      log.info("Create a new BufferedImage with the new dimensions");
      BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, inputImage.getType());

      log.info("Scale the input image to the new dimensions");
      Graphics2D g2d = resizedImage.createGraphics();
      g2d.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
      g2d.dispose();

      log.info("Save the resized image to a file");
      File outputFile = new File(outputFilePath);
      ImageIO.write(resizedImage, "jpg", outputFile);
      return outputFile.getPath();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String resizeImage(String inputFilePath, Double scaleFactor) {

    log.info(
        "Start rotating image with parameters: \n\t<inputFilePath> {}, \n\t<scaleFactor> {}",
        inputFilePath,
        scaleFactor);

    log.info("Applying default value for: \n\t<outputFilePath> output_rotated.jpg");

    return resizeImage(
        inputFilePath,
        Objects.requireNonNull(ImageResizer.class.getResource("/")).getPath()
            + "output_resized.jpg",
        scaleFactor);
  }

  public static String resizeImage(String inputFilePath) {

    log.info("Start rotating image with parameters: \n\t<inputFilePath> {}", inputFilePath);
    log.info("Applying default value for: \n\t<scaleFactor> {}", 0.5);

    return resizeImage(inputFilePath, 0.5);
  }

  public static void main(String[] args) throws Exception {
    resizeImage(
        Objects.requireNonNull(ImageResizer.class.getResource("/files/20230811_180950.jpg"))
            .getPath());
  }
}
