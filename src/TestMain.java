import controller.ImageFileHandler;
import controller.ImageIOHandler;
import model.Image;

/**
 * A temporary main class used for testing new image transformations. Will be deleted before
 * submission.
 */
public class TestMain {

  /**
   * A temporary main method used for testing new image transformations. Will be deleted before
   * submission.
   *
   * @param args the command line arguments
   */
//  public static void main(String[] args) {
//
//    ImagePPMHandler handler = new ImagePPMHandler();
//    ImageTransformation transformation = new Blur();
//    Image image;
//    Image transformed;
//
//    try {
//      image = handler.process("res/ManhattanBlur.ppm");
//      System.out.println("Image loaded successfully");
//    } catch (Exception e) {
//      System.out.println("Error loading image");
//      return;
//    }
//
//    try {
//      transformed = transformation.transform(image);
//      System.out.println("Image transformed successfully");
//    } catch (Exception e) {
//      System.out.println("Error transforming image");
//      return;
//    }
//
//    try {
//      handler.export(transformed, "res/ManhattanBlur2.ppm");
//      System.out.println("Image exported successfully");
//    } catch (Exception e) {
//      System.out.println("Error saving image");
//    }
//  }
  public static void main(String[] args) {
    Image image = null;

    try {
      ImageFileHandler handler = new ImageIOHandler();
      image = handler.process("res/Check.png");
      System.out.println("Image loaded successfully");
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      ImageFileHandler handler = new ImageIOHandler();
      handler.export(image, "res/Check2.tiff");
      System.out.println("Image exported successfully");
    } catch (Exception error) {
      error.printStackTrace();
    }
  }
}