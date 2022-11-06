package view;

/**
 * This interface represents the view of the Image Processor. It contains methods that the
 * controller can call to render the view.
 */
public interface ImageProcessorView {

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IllegalStateException if transmission of the board to the provided data destination
   *                               fails
   */
  void renderMessage(String message) throws IllegalStateException;

  /**
   * Render the welcome message to the provided data destination.
   *
   * @throws IllegalStateException if transmission of the board to the provided data destination
   *                               fails
   */
  void renderWelcome() throws IllegalStateException;

  /**
   * Render the menu of commands to the provided data destination.
   *
   * @throws IllegalStateException if transmission of the board to the provided data destination
   *                               fails
   */
  void renderMenu() throws IllegalStateException;

}
