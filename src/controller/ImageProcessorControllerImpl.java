package controller;

import controller.commands.BlurCmd;
import controller.commands.BrightnessCmd;
import controller.commands.HorizontalFlipCmd;
import controller.commands.LoadCmd;
import controller.commands.MenuCmd;
import controller.commands.SaveCmd;
import controller.commands.SharpenCmd;
import controller.commands.VerticalFlipCmd;
import controller.commands.VisualizeCmd;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.StoredImages;
import model.transformations.Visualize.Channel;
import view.ImageProcessorView;

/**
 * Implements the {@code ImageProcessorController} interface supporting the following commands.
 *
 * <ul>
 *   <li>"quit" - quits the program and discards loaded images</li>
 *   <li>"menu" - outputs the menu with supported commands to the user</li>
 *   <li>"load {@code <image file path>} {@code <name to load as>}" - loads a new image into the
 *   image processor"</li>
 *   <li>"save {@code <output file path>} {@code <name of image to save>}" - saves a loaded image
 *   to an output file"</li>
 *   <li>"visualize-{@code <value to visualize (red, blue, green, value, intensity, luma)>}
 *   {@code <name of image to transform>} {@code <name of new transformed image>}" - applies a
 *   visualization transformation to an image "</li>
 *   <li>"brighten {@code <integer to brighten by>} {@code <name of image to transform>}
 *   {@code <name of new transformed image>}" - applies a brighten transformation to an image"</li>
 *   <li>"darken {@code <integer to darken by>} {@code <name of image to transform>}
 *  {@code <name of new transformed image>}" - applies a darken transformation to an image"</li>
 *  <li>"horizontal-flip {@code <name of image to transform>}
 *  {@code <name of new transformed image>}" - applies a horizontal flip
 *  transformation to an image"</li>
 *  <li>"vertical-flip {@code <name of image to transform>} {@code <name of new transformed image>}"
 *  - applies a vertical flip transformation to an image"</li>
 * </ul>
 *
 * <p>
 * If an image name already exists in the image processor, the command will overwrite the existing
 * image with the new image.
 * </p>
 */
// TODO: Update JavaDoc with new commands
public class ImageProcessorControllerImpl implements ImageProcessorController {

  private final ImageProcessorView view;
  private final StoredImages store;
  private final Scanner scan;
  private final Map<String, Function<Scanner, ImageProcessorCmd>> commands;

  /**
   * Constructs a new controller object using the given {@link Readable} that represents user input,
   * {@link StoredImages} that represents the image store, and {@link Appendable} that represents
   * the output.
   *
   * @param input the input to read from
   * @param view  the view to display to
   * @param store the store to store images in
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public ImageProcessorControllerImpl(Readable input, ImageProcessorView view, StoredImages store)
      throws IllegalArgumentException {
    if (input == null || view == null || store == null) {
      throw new IllegalArgumentException("Input, view, and store cannot be null");
    }
    this.view = view;
    this.store = store;
    this.scan = new Scanner(input);
    this.commands = new HashMap<>();
  }

  @Override
  public void run() {
    this.view.renderWelcome();
    while (scan.hasNext()) {
      String command = scan.next();
      if (command.equalsIgnoreCase("q") || command.equalsIgnoreCase("quit")) {
        this.view.renderMessage("Quitting...");
        return;
      } else {
        ImageProcessorCmd c;
        this.addCommands();
        Function<Scanner, ImageProcessorCmd> cmd = this.commands.getOrDefault(command, null);
        try {
          if (cmd == null) {
            throw new IllegalArgumentException("Invalid command, please try again");
          } else {
            c = cmd.apply(scan);
            c.execute();
          }
        } catch (IllegalArgumentException e) {
          this.view.renderMessage(
              "Error: " + e.getMessage() + System.lineSeparator() + "Command: ");
        }
      }
    }
  }

  /**
   * Adds all supported commands and the lambda functions to create the command objects to this
   * controller object's map of valid commands.
   */
  // TODO: Add to readme added commands
  private void addCommands() {
    this.commands.put("menu", (Scanner s) -> new MenuCmd(this.view));
    this.commands.put("load",
        (Scanner s) -> new LoadCmd(this.view, this.store, s.next(), s.next()));
    this.commands.put("save",
        (Scanner s) -> new SaveCmd(this.view, this.store, s.next(), s.next()));
    this.commands.put("visualize-red",
        (Scanner s) -> new VisualizeCmd(this.view, this.store, Channel.Red, s.next(), s.next()));
    this.commands.put("visualize-green",
        (Scanner s) -> new VisualizeCmd(this.view, this.store, Channel.Green, s.next(), s.next()));
    this.commands.put("visualize-blue",
        (Scanner s) -> new VisualizeCmd(this.view, this.store, Channel.Blue, s.next(), s.next()));
    this.commands.put("visualize-value",
        (Scanner s) -> new VisualizeCmd(this.view, this.store, Channel.Value, s.next(), s.next()));
    this.commands.put("visualize-intensity",
        (Scanner s) -> new VisualizeCmd(this.view, this.store, Channel.Intensity, s.next(),
            s.next()));
    this.commands.put("visualize-luma",
        (Scanner s) -> new VisualizeCmd(this.view, this.store, Channel.Luma, s.next(), s.next()));
    this.commands.put("brighten",
        (Scanner s) -> new BrightnessCmd(this.view, this.store, s.nextInt(), s.next(), s.next()));
    this.commands.put("darken",
        (Scanner s) -> new BrightnessCmd(this.view, this.store, (s.nextInt() * -1), s.next(),
            s.next()));
    this.commands.put("horizontal-flip",
        (Scanner s) -> new HorizontalFlipCmd(this.view, this.store, s.next(), s.next()));
    this.commands.put("vertical-flip",
        (Scanner s) -> new VerticalFlipCmd(this.view, this.store, s.next(), s.next()));
    this.commands.put("blur",
        (Scanner s) -> new BlurCmd(this.view, this.store, s.next(), s.next()));
    this.commands.put("sharpen",
        (Scanner s) -> new SharpenCmd(this.view, this.store, s.next(), s.next()));
  }

}
