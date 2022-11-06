import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import java.io.InputStreamReader;
import model.StoredImages;
import model.StoredImagesImpl;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

/**
 * Runs the image processor in the terminal for the user.
 */
public final class ImageProcessorRunner {

  /**
   * Initiates a new image processor intstance for the user.
   */
  public static void main(String[] args) {

    // Set the input stream
    Readable input = new InputStreamReader(System.in);

    // Initialize the view given the output stream appendable
    ImageProcessorView view = new ImageProcessorViewImpl(System.out);

    // Initialize the model to store the images
    StoredImages store = new StoredImagesImpl();

    // Initialize the controller with the input, view, and model
    ImageProcessorController controller = new ImageProcessorControllerImpl(input, view, store);

    // Run the controller
    controller.run();
  }
}
