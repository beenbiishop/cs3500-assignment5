package controller.commands;

import controller.ImageProcessorCmd;
import view.ImageProcessorView;

/**
 * Class that represents a command, "Menu", that the processor can handle. Implements the
 * {@code ImageProcessorCmd} interface and executes the command. Displays the menu.
 */
public class MenuCmd implements ImageProcessorCmd {

  private final ImageProcessorView view;

  /**
   * Constructs a Menu command.
   *
   * @param view the view to display the menu to.
   * @throws IllegalArgumentException if the view is null.
   */
  public MenuCmd(ImageProcessorView view) throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    this.view = view;
  }

  @Override
  public void execute() {
    this.view.renderMenu();
    this.view.renderMessage("Command: ");
  }

}
