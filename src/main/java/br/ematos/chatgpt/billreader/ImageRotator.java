package br.ematos.chatgpt.billreader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageRotator {

  /**
   * @param inputFilePath The input image file path
   * @param outputFilePath The output image file path
   * @param rotationAngle Define the rotation angle in radians (e.g., 90 degrees)
   */
  public static String rotateImage(
      String inputFilePath, String outputFilePath, Double rotationAngle) {

    log.info(
        "Start rotating image with parameters: \n\t<inputFilePath> {}, \n\t<outputFilePath> {}, \n\t<rotationAngle> {}",
        inputFilePath,
        outputFilePath,
        rotationAngle);

    try {
      log.info("Load the input image");
      BufferedImage inputImage = ImageIO.read(new File(inputFilePath));
      double rotationAngleRadians = Math.toRadians(rotationAngle);

      log.info("Calculate the new dimensions to accommodate the rotated image");
      int newWidth =
          (int) Math.abs(inputImage.getHeight() * Math.sin(rotationAngleRadians))
              + (int) Math.abs(inputImage.getWidth() * Math.cos(rotationAngleRadians));
      int newHeight =
          (int) Math.abs(inputImage.getWidth() * Math.sin(rotationAngleRadians))
              + (int) Math.abs(inputImage.getHeight() * Math.cos(rotationAngleRadians));

      log.info("Create a new BufferedImage with the new dimensions");
      BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, inputImage.getType());

      log.info("Rotate the input image");
      Graphics2D g2d = rotatedImage.createGraphics();
      g2d.rotate(rotationAngleRadians, (double) newWidth / 2, (double) newHeight / 2);
      g2d.drawImage(
          inputImage,
          (newWidth - inputImage.getWidth()) / 2,
          (newHeight - inputImage.getHeight()) / 2,
          null);
      g2d.dispose();

      log.info("Save the rotated image to a file");
      File outputFile = new File(outputFilePath);
      ImageIO.write(rotatedImage, "jpg", outputFile);
      return outputFile.getPath();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String rotateImage(String inputFilePath, Double rotationAngle) {
    log.info(
        "Start rotating image with parameters: \n\t<inputFilePath> {}, \n\t<rotationAngle> {}",
        inputFilePath,
        rotationAngle);

    log.info("Applying default value for: \n\t<outputFilePath> output_rotated.jpg");
    return rotateImage(
        inputFilePath,
        Objects.requireNonNull(ImageRotator.class.getResource("/")).getPath()
            + "output_rotated.jpg",
        rotationAngle);
  }

  public static String rotateImage(String inputFilePath) {
    log.info("Start rotating image with parameters: \n\t<inputFilePath> {}", inputFilePath);
    log.info("Applying default value for: \n\t<rotationAngle> {}", 90.0);
    return rotateImage(inputFilePath, 90.0);
  }

  public static void main(String[] args) {
    rotateImage(
        Objects.requireNonNull(ImageRotator.class.getResource("/files/20230811_180950.jpg"))
            .getPath());
  }
}
