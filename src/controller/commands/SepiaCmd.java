package controller.commands;

import controller.ImageProcessorCmd;
import model.Image;
import model.ImageTransformation;
import model.StoredImages;
import model.transformations.Sepia;
import view.ImageProcessorView;

/**
 * Class that represents a command, "Sepia", that the processor can handle. Implements the
 * {@code ImageProcessorCmd} interface and execute the command. Filters the image to sepia.
 */
public class SepiaCmd implements ImageProcessorCmd {

  private final ImageProcessorView view;
  private final StoredImages store;
  private final String fileName;
  private final String newFileName;

  /**
   * Constructs a Sepia command.
   *
   * @param view        the view to display the messages to.
   * @param store       the store to store images in.
   * @param fileName    the file name of the image to be transformed.
   * @param newFileName the file name of the new transformed image.
   * @throws IllegalArgumentException if any of the parameters are null.
   */
  public SepiaCmd(ImageProcessorView view, StoredImages store, String fileName, String newFileName)
      throws IllegalArgumentException {
    if (view == null || store == null || fileName == null || newFileName == null) {
      throw new IllegalArgumentException("View, store, and file names cannot be null");
    }
    this.view = view;
    this.store = store;
    this.fileName = fileName.toLowerCase();
    this.newFileName = newFileName.toLowerCase();
  }

  @Override
  public void execute() {
    Image retrieved = this.store.retrieve(this.fileName);
    ImageTransformation sepia = new Sepia();
    Image processed = sepia.transform(retrieved);
    this.store.add(this.newFileName, processed, true);
    this.view.renderMessage(
        "Applied sepia to \"" + this.fileName + "\" and saved as \"" + this.newFileName + "\""
            + System.lineSeparator() + "Command: ");
  }
}
