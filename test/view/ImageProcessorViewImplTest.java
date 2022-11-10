package view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the ImageProcessorViewImpl class and its methods.
 */
public class ImageProcessorViewImplTest {

  ImageProcessorView view;
  Appendable appendable;

  @Before
  public void setUp() {
    this.appendable = new StringBuilder();
    this.view = new ImageProcessorViewImpl(appendable);
  }

  @Test
  public void testNullConstructor() {
    try {
      new ImageProcessorViewImpl(null);
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Appendable cannot be null", e.getMessage());
    }
  }

  @Test
  public void testRenderMessage() {
    this.view.renderMessage("Message to Render.");
    assertEquals("Message to Render.", this.appendable.toString());
  }

  @Test
  public void testRenderMessageError() {
    this.view = new MockImageProcessorViewImpl(this.appendable);
    try {
      this.view.renderMessage("Message to Render.");
      fail("Expected an IllegalStateException to be thrown");
    } catch (IllegalStateException e) {
      assertEquals("Unable to render message", e.getMessage());
    }
  }

  @Test
  public void testRenderWelcome() {
    this.view.renderWelcome();
    assertEquals("Welcome to the Image Processor!" + System.lineSeparator()
        + "* Enter \"menu\" to see the list of supported commands or \"quit\" to exit the program"
        + System.lineSeparator() + "* After entering a command, hit return to process it"
        + System.lineSeparator() + "Command: ", this.appendable.toString());
  }

  @Test
  public void testRenderWelcomeError() {
    this.view = new MockImageProcessorViewImpl(this.appendable);
    try {
      this.view.renderWelcome();
      fail("Expected an IllegalStateException to be thrown");
    } catch (IllegalStateException e) {
      assertEquals("Unable to render message", e.getMessage());
    }
  }

  @Test
  public void testRenderMenu() {
    this.view.renderMenu();
    StringBuilder sb = new StringBuilder();
    sb.append("Supported commands:");
    sb.append(System.lineSeparator());
    sb.append("* \"quit\" - quits the program");
    sb.append(System.lineSeparator());
    sb.append("* \"menu\" - displays the menu of commands");
    sb.append(System.lineSeparator());
    sb.append("* \"load\" <path> <filename> - loads an image (identified by given name)");
    sb.append(" into the processor");
    sb.append(System.lineSeparator());
    sb.append("* \"save\" <path> <filename> - saves an image to an output");
    sb.append(" file");
    sb.append(System.lineSeparator());
    sb.append("* \"visualize-<component>\" <filename> <new filename> - transforms an image to");
    sb.append(" a new greyscale image using a chosen component");
    sb.append(System.lineSeparator());
    sb.append("    * component can be \"red\", \"green\", \"blue\", \"value\",");
    sb.append(" \"intensity\", or \"luma\"");
    sb.append(System.lineSeparator());
    sb.append("* \"brighten\" <amount> <filename> <new filename> - transforms an image to a new");
    sb.append(" image brightened by an amount");
    sb.append(System.lineSeparator());
    sb.append("* \"darken\" <amount> <filename> <new filename> - transforms an image to a new");
    sb.append(" image darkened by an amount");
    sb.append(System.lineSeparator());
    sb.append("* \"horizontal-flip\" <filename> <new filename> - horizontally flips an image");
    sb.append(" to a new image");
    sb.append(System.lineSeparator());
    sb.append("* \"vertical-flip\" <filename> <new filename> - vertically flips an image");
    sb.append(" to a new image");
    sb.append(System.lineSeparator());
    sb.append("* \"greyscale\" <filename> <new filename> - transforms an image to a new");
    sb.append(" greyscale filtered image");
    sb.append(System.lineSeparator());
    sb.append("* \"sepia\" <filename> <new filename> - transforms an image to a new");
    sb.append(" sepia filtered image");
    sb.append(System.lineSeparator());
    sb.append("* \"blur\" <filename> <new filename> - transforms an image to a new");
    sb.append(" blurred image");
    sb.append(System.lineSeparator());
    sb.append("* \"sharpen\" <filename> <new filename> - transforms an image to a new");
    sb.append(" sharpened image");
    sb.append(System.lineSeparator());
    assertEquals(sb.toString(), this.appendable.toString());
  }

  @Test
  public void testRenderMenuError() {
    this.view = new MockImageProcessorViewImpl(this.appendable);
    try {
      this.view.renderMenu();
      fail("Expected an IllegalStateException to be thrown");
    } catch (IllegalStateException e) {
      assertEquals("Unable to render message", e.getMessage());
    }
  }
}