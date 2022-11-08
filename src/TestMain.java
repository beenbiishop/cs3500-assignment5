import controller.ImagePPMHandler;
import model.Image;
import model.ImageTransformation;
import model.transformations.Sharpen;

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
  public static void main(String[] args) {

    ImagePPMHandler handler = new ImagePPMHandler();
    ImageTransformation transformation = new Sharpen();
    Image image;
    Image transformed;

    try {
      image = handler.process("res/Manhattan.ppm");
      System.out.println("Image loaded successfully");
    } catch (Exception e) {
      System.out.println("Error loading image");
      return;
    }

    try {
      transformed = transformation.transform(image);
      System.out.println("Image transformed successfully");
    } catch (Exception e) {
      System.out.println("Error transforming image");
      return;
    }

    try {
      handler.export(transformed, "res/ManhattanSharpen.ppm");
      System.out.println("Image exported successfully");
    } catch (Exception e) {
      System.out.println("Error saving image");
    }
  }
}