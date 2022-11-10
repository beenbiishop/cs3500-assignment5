import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
   * Initiates a new image processor instance for the user.
   *
   * @param args the arguments taken in by the main method
   */
  public static void main(String[] args) {
    Readable input = null;

    // If the user provides a script file, use that as the input, otherwise use the console
    if (args.length == 0) {
      input = new InputStreamReader(System.in);
    } else if (args.length == 2 && args[0].equals("-script")) {
      try {
        input = new BufferedReader(new FileReader(args[1]));
      } catch (FileNotFoundException e) {
        System.out.println("Log file not found.");
        return;
      }
    } else {
      System.out.println("Invalid arguments provided.");
      return;
    }

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
