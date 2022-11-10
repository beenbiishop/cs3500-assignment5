package view;

import java.io.IOException;

/**
 * Implements the {@link ImageProcessorView} interface and handles the appending of messages sent to
 * the user by the controller.
 */
public class ImageProcessorViewImpl implements ImageProcessorView {

  private final Appendable appendable;

  /**
   * Constructs a new ImageProcessorViewImpl object with the given appendable.
   *
   * @param appendable the appendable to append the text to the user
   * @throws IllegalArgumentException if the appendable is null
   */
  public ImageProcessorViewImpl(Appendable appendable) throws IllegalArgumentException {
    if (appendable == null) {
      throw new IllegalArgumentException("Appendable cannot be null");
    }
    this.appendable = appendable;
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException {
    try {
      this.appendable.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Unable to render message");
    }
  }

  @Override
  public void renderWelcome() throws IllegalStateException {
    this.renderMessage("Welcome to the Image Processor!" + System.lineSeparator());
    this.renderMessage("* Enter \"menu\" to see the list of supported commands or \"quit\""
        + " to exit the program" + System.lineSeparator()
        + "* After entering a command, hit return to process it" + System.lineSeparator()
        + "Command: ");
  }

  @Override
  public void renderMenu() throws IllegalStateException {
    this.renderMessage("Supported commands:" + System.lineSeparator());
    this.renderMessage("* \"quit\" - quits the program" + System.lineSeparator());
    this.renderMessage("* \"menu\" - displays the menu of commands" + System.lineSeparator());
    this.renderMessage(
        "* \"load\" <path> <filename> - loads an image (identified by given name) into the"
            + " processor" + System.lineSeparator());
    this.renderMessage(
        "* \"save\" <path> <filename> - saves an image to an output file" + System.lineSeparator());
    this.renderMessage(
        "* \"visualize-<component>\" <filename> <new filename> - transforms an image to a new"
            + " greyscale image using a chosen component" + System.lineSeparator());
    this.renderMessage(
        "    * component can be \"red\", \"green\", \"blue\", \"value\", \"intensity\", or \"luma\""
            + System.lineSeparator());
    this.renderMessage("* \"brighten\" <amount> <filename> <new filename> - transforms an image"
        + " to a new image brightened by an amount" + System.lineSeparator());
    this.renderMessage("* \"darken\" <amount> <filename> <new filename> - transforms an image to a "
        + "new image darkened by an amount" + System.lineSeparator());
    this.renderMessage(
        "* \"horizontal-flip\" <filename> <new filename> - horizontally flips an image"
            + " to a new image" + System.lineSeparator());
    this.renderMessage("* \"vertical-flip\" <filename> <new filename> - vertically flips an image"
        + " to a new image" + System.lineSeparator());
    this.renderMessage(
        "* \"greyscale\" <filename> <new filename> - transforms an image to a new greyscale"
            + " filtered image" + System.lineSeparator());
    this.renderMessage("* \"sepia\" <filename> <new filename> - transforms an image to a new sepia"
        + " filtered image" + System.lineSeparator());
    this.renderMessage(
        "* \"blur\" <filename> <new filename> - transforms an image to a new blurred image"
            + System.lineSeparator());
    this.renderMessage(
        "* \"sharpen\" <filename> <new filename> - transforms an image to a new sharpened image"
            + System.lineSeparator());
  }

}
