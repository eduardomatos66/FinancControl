package br.ematos.chatgpt.billreader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

@Slf4j
public class GroceryBillShapeDetection {

  public static void main(String[] args) {
    log.info("Load an image from file");
    String imagePath =
        Objects.requireNonNull(GroceryBillShapeDetection.class.getResource("/output_rotated.jpg"))
            .getPath();
    Mat image = Imgcodecs.imread(imagePath);

    if (image.empty()) {
      System.err.println("Could not open or find the image.");
      System.exit(-1);
    }

    log.info("Convert the image to grayscale");
    Mat grayscaleImage = new Mat();
    Imgproc.cvtColor(image, grayscaleImage, Imgproc.COLOR_BGR2GRAY);

    log.info("Apply edge detection (e.g., Canny edge detection)");
    Mat edges = new Mat();
    Imgproc.Canny(grayscaleImage, edges, 50, 150);

    log.info("Find contours in the edge image");
    List<MatOfPoint> contours = new ArrayList<>();
    Imgproc.findContours(
        edges, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

    log.info("Loop through detected contours and classify shapes");
    for (int i = 0; i < contours.size(); i++) {
      Mat contour = contours.get(i);
      double perimeter = Imgproc.arcLength(new MatOfPoint2f(contour), true);
      MatOfPoint2f approxCurve = new MatOfPoint2f();
      Imgproc.approxPolyDP(new MatOfPoint2f(contour), approxCurve, 0.02 * perimeter, true);

      int vertices = approxCurve.toList().size();

      log.info("Classify the shape based on the number of vertices");
      String shape;
      if (vertices == 3) {
        shape = "Triangle";
      } else if (vertices == 4) {
        shape = "Rectangle or Square";
      } else {
        shape = "Other";
      }

      log.info("Print the detected shape");
      System.out.println("Shape " + (i + 1) + ": " + shape);
    }
  }
}
