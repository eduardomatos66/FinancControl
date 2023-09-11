package br.ematos.chatgpt.billreader;

import java.io.File;
import java.util.Objects;
import net.sourceforge.tess4j.Tesseract;

public class BillReaderService {

  public static void main(String[] args) throws Exception {
    String resizedPath =
        ImageResizer.resizeImage(
            Objects.requireNonNull(
                    BillReaderService.class.getResource("/files/20230811_180950.jpg"))
                .getPath());
    String rotatedPath = ImageRotator.rotateImage(resizedPath);

    File billImageFile = new File(rotatedPath);

    if (billImageFile.isFile()) {
      Tesseract tesseract = new Tesseract();
      tesseract.setDatapath("C:\\tessdata\\tessdata");
      String extractedText = tesseract.doOCR(billImageFile);
      System.out.println(extractedText);
    }
  }
}
