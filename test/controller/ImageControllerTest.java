package controller;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.commands.BrightnessCmd;
import controller.commands.GreyscaleCmd;
import controller.commands.HorizontalFlipCmd;
import controller.commands.VerticalFlipCmd;
import controller.commands.VisualizeCmd;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import model.Image;
import model.ImageImpl;
import model.StoredImages;
import model.StoredImagesImpl;
import model.transformations.Visualize.Channel;
import org.junit.Before;
import org.junit.Test;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

/**
 * Contains all the testers for the controller and related classes. Tests each of the commands and
 * whether the controller parses and runs the program correctly.
 */
public class ImageControllerTest {

  private Image beforeImage;
  private Readable in;
  private Appendable appendable;
  private ImageProcessorView view;
  private StoredImages store;
  private ImageProcessorController controller1;


  @Before
  public void setUp() {
    Color[][] pixels = new Color[3][3];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixels[0][0] = new Color(128, 16, 216);
        pixels[0][1] = new Color(114, 17, 219);
        pixels[0][2] = new Color(105, 18, 222);
        pixels[1][0] = new Color(114, 17, 219);
        pixels[1][1] = new Color(97, 18, 224);
        pixels[1][2] = new Color(84, 18, 227);
        pixels[2][0] = new Color(105, 18, 222);
        pixels[2][1] = new Color(84, 18, 227);
        pixels[2][2] = new Color(61, 18, 231);

      }
    }

    this.beforeImage = new ImageImpl(pixels);

    String userCommandEx1 = "load res/ExampleImage.ppm ExampleImage" + System.lineSeparator()
        + "brighten 10 ExampleImage BrightenedImage";
    InputStream targetStreamEx1 = new ByteArrayInputStream(userCommandEx1.getBytes());
    this.in = new InputStreamReader(targetStreamEx1);

    this.appendable = new StringBuilder();
    this.view = new ImageProcessorViewImpl(appendable);
    this.store = new StoredImagesImpl();

  }

  @Test
  public void testInvalidConstructors() {
    try {
      new ImageProcessorControllerImpl(null, this.view, this.store);
      fail("Should throw exception with null input");
    } catch (IllegalArgumentException e) {
      assertEquals("Input, view, and store cannot be null", e.getMessage());
    }

    try {
      new ImageProcessorControllerImpl(this.in, null, this.store);
      fail("Should throw exception with null view");
    } catch (IllegalArgumentException e) {
      assertEquals("Input, view, and store cannot be null", e.getMessage());
    }

    try {
      new ImageProcessorControllerImpl(this.in, this.view, null);
      fail("Should throw exception with null image store");
    } catch (IllegalArgumentException e) {
      assertEquals("Input, view, and store cannot be null", e.getMessage());
    }
  }

  //checks if the inputs are being parsed correctly
  @Test
  public void testControllerInput() {
    StringBuilder log = new StringBuilder();
    StoredImages mockStore = new MockStoredImages(log);
    this.controller1 = new ImageProcessorControllerImpl(this.in, this.view, mockStore);

    this.controller1.run();
    assertEquals("The parsed string for a new file's name: exampleimage" + System.lineSeparator()
        + "The parsed string for the name of the file to modify: exampleimage"
        + System.lineSeparator() + "The parsed string for a new file's name: brightenedimage"
        + System.lineSeparator(), log.toString());
  }

  @Test
  public void testScript() {
    String userCommandEx2 = "loadr res/ExampleImage.ppm ExampleImage" + System.lineSeparator();
    InputStream targetStreamEx2 = new ByteArrayInputStream(userCommandEx2.getBytes());
    Readable in2 = new InputStreamReader(targetStreamEx2);
    this.appendable = new StringBuilder();
    this.view = new ImageProcessorViewImpl(this.appendable);
    this.controller1 = new ImageProcessorControllerImpl(in2, this.view, this.store);
    this.controller1.run();

    assertTrue(this.appendable.toString().contains("Error: Invalid command, please try again"));
  }

  @Test
  public void testInvalidPath() {
    String userCommandEx3 = "load res.ppm ExampleImage" + System.lineSeparator();
    InputStream targetStreamEx3 = new ByteArrayInputStream(userCommandEx3.getBytes());
    Readable in3 = new InputStreamReader(targetStreamEx3);
    this.appendable = new StringBuilder();
    this.view = new ImageProcessorViewImpl(this.appendable);
    this.controller1 = new ImageProcessorControllerImpl(in3, this.view, this.store);

    this.controller1.run();
    assertTrue(this.appendable.toString().contains("File \"res.ppm\" not found"));
  }


  @Test
  public void testPPMHandlerProcess() {
    String filePath = "res/ExampleImage.ppm";
    //this is what my file path is, I made 3 x 3 image for testing purposes

    Color[][] pixels = new Color[3][3];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixels[0][0] = new Color(128, 16, 216);
        pixels[0][1] = new Color(114, 17, 219);
        pixels[0][2] = new Color(105, 18, 222);
        pixels[1][0] = new Color(114, 17, 219);
        pixels[1][1] = new Color(97, 18, 224);
        pixels[1][2] = new Color(84, 18, 227);
        pixels[2][0] = new Color(105, 18, 222);
        pixels[2][1] = new Color(84, 18, 227);
        pixels[2][2] = new Color(61, 18, 231);

      }
    }

    ImageFileHandler ppmHandler = new ImagePPMHandler();

    assertArrayEquals(pixels, ppmHandler.process(filePath).getPixels());
  }

  @Test
  public void testPPMHandlerExport() {
    String filePath = "res/ExampleImage.ppm";
    String filePath2 = "res/exImage.ppm";

    ImageFileHandler ppmHandler1 = new ImagePPMHandler();
    Image processedImage = ppmHandler1.process(filePath);

    ImageFileHandler ppmHandler = new ImagePPMHandler();

    ppmHandler.export(this.beforeImage, filePath2);
    Image exportedImage = ppmHandler.process(filePath2);

    //Checks if the ppmHandler saved the image correctly under the new filePath.
    assertArrayEquals(processedImage.getPixels(), exportedImage.getPixels());
  }

  @Test
  public void testBrightnessCmd() {
    Appendable appendable = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(appendable);
    StoredImages store = new StoredImagesImpl();
    String fileName = "ExampleImage.ppm";
    String newFileName = "BrightenedImage.ppm";
    store.add(fileName, this.beforeImage, true);

    ImageProcessorCmd brightened = new BrightnessCmd(view, store, 10, fileName, newFileName);
    brightened.execute();

    Color[][] newPixels = new Color[3][3];
    for (int i = 0; i < newPixels.length; i++) {
      for (int j = 0; j < newPixels[0].length; j++) {
        newPixels[0][0] = new Color(138, 26, 226);
        newPixels[0][1] = new Color(124, 27, 229);
        newPixels[0][2] = new Color(115, 28, 232);
        newPixels[1][0] = new Color(124, 27, 229);
        newPixels[1][1] = new Color(107, 28, 234);
        newPixels[1][2] = new Color(94, 28, 237);
        newPixels[2][0] = new Color(115, 28, 232);
        newPixels[2][1] = new Color(94, 28, 237);
        newPixels[2][2] = new Color(71, 28, 241);

      }
    }

    assertArrayEquals(newPixels, store.retrieve(newFileName).getPixels());
  }

  @Test
  public void testHorizontalFlip() {
    Appendable appendable = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(appendable);
    StoredImages store = new StoredImagesImpl();
    String fileName = "ExampleImage.ppm";
    String newFileName = "HorizontalFlippedImage.ppm";
    store.add(fileName, this.beforeImage, true);
    ImageProcessorCmd horFlip = new HorizontalFlipCmd(view, store, fileName, newFileName);
    horFlip.execute();

    Color[][] newPixels = new Color[3][3];
    for (int i = 0; i < newPixels.length; i++) {
      for (int j = 0; j < newPixels[0].length; j++) {
        newPixels[0][0] = new Color(105, 18, 222);
        newPixels[0][1] = new Color(114, 17, 219);
        newPixels[0][2] = new Color(128, 16, 216);

        newPixels[1][0] = new Color(84, 18, 227);
        newPixels[1][1] = new Color(97, 18, 224);
        newPixels[1][2] = new Color(114, 17, 219);

        newPixels[2][0] = new Color(61, 18, 231);
        newPixels[2][1] = new Color(84, 18, 227);
        newPixels[2][2] = new Color(105, 18, 222);

      }
    }

    assertArrayEquals(newPixels, store.retrieve(newFileName).getPixels());
  }

  @Test
  public void testVerticalFlip() {
    Appendable appendable = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(appendable);
    StoredImages store = new StoredImagesImpl();
    String fileName = "ExampleImage.ppm";
    String newFileName = "VerticallyFlippedImage.ppm";
    store.add(fileName, this.beforeImage, true);
    ImageProcessorCmd verFlip = new VerticalFlipCmd(view, store, fileName, newFileName);
    verFlip.execute();

    Color[][] newPixels = new Color[3][3];
    for (int i = 0; i < newPixels.length; i++) {
      for (int j = 0; j < newPixels[0].length; j++) {
        newPixels[0][0] = new Color(105, 18, 222);
        newPixels[0][1] = new Color(84, 18, 227);
        newPixels[0][2] = new Color(61, 18, 231);

        newPixels[1][0] = new Color(114, 17, 219);
        newPixels[1][1] = new Color(97, 18, 224);
        newPixels[1][2] = new Color(84, 18, 227);

        newPixels[2][0] = new Color(128, 16, 216);
        newPixels[2][1] = new Color(114, 17, 219);
        newPixels[2][2] = new Color(105, 18, 222);

      }
    }

    assertArrayEquals(newPixels, store.retrieve(newFileName).getPixels());
  }

  @Test
  public void testVisualizeRed() {
    Appendable appendable = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(appendable);
    StoredImages store = new StoredImagesImpl();
    String fileName = "ExampleImage.ppm";
    String newFileName = "RedVisualizedImage.ppm";
    store.add(fileName, this.beforeImage, true);
    ImageProcessorCmd visualizeRed = new VisualizeCmd(view, store, Channel.Red, fileName,
        newFileName);
    visualizeRed.execute();

    Color[][] newPixels = new Color[3][3];
    for (int i = 0; i < newPixels.length; i++) {
      for (int j = 0; j < newPixels[0].length; j++) {
        newPixels[0][0] = new Color(128, 128, 128);
        newPixels[0][1] = new Color(114, 114, 114);
        newPixels[0][2] = new Color(105, 105, 105);

        newPixels[1][0] = new Color(114, 114, 114);
        newPixels[1][1] = new Color(97, 97, 97);
        newPixels[1][2] = new Color(84, 84, 84);

        newPixels[2][0] = new Color(105, 105, 105);
        newPixels[2][1] = new Color(84, 84, 84);
        newPixels[2][2] = new Color(61, 61, 61);


      }
    }
    assertArrayEquals(newPixels, store.retrieve(newFileName).getPixels());
  }

  @Test
  public void testVisualizeGreen() {
    Appendable appendable = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(appendable);
    StoredImages store = new StoredImagesImpl();
    String fileName = "ExampleImage.ppm";
    String newFileName = "GreenVisualizedImage.ppm";
    store.add(fileName, this.beforeImage, true);
    ImageProcessorCmd visualizeGreen = new VisualizeCmd(view, store, Channel.Green, fileName,
        newFileName);
    visualizeGreen.execute();

    Color[][] newPixels = new Color[3][3];
    for (int i = 0; i < newPixels.length; i++) {
      for (int j = 0; j < newPixels[0].length; j++) {
        newPixels[0][0] = new Color(16, 16, 16);
        newPixels[0][1] = new Color(17, 17, 17);
        newPixels[0][2] = new Color(18, 18, 18);

        newPixels[1][0] = new Color(17, 17, 17);
        newPixels[1][1] = new Color(18, 18, 18);
        newPixels[1][2] = new Color(18, 18, 18);

        newPixels[2][0] = new Color(18, 18, 18);
        newPixels[2][1] = new Color(18, 18, 18);
        newPixels[2][2] = new Color(18, 18, 18);

      }
    }
    assertArrayEquals(newPixels, store.retrieve(newFileName).getPixels());
  }

  @Test
  public void testVisualizeBlue() {
    Appendable appendable = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(appendable);
    StoredImages store = new StoredImagesImpl();
    String fileName = "ExampleImage.ppm";
    String newFileName = "BlueVisualizedImage.ppm";
    store.add(fileName, this.beforeImage, true);
    ImageProcessorCmd visualizeBlue = new VisualizeCmd(view, store, Channel.Blue, fileName,
        newFileName);
    visualizeBlue.execute();

    Color[][] newPixels = new Color[3][3];
    for (int i = 0; i < newPixels.length; i++) {
      for (int j = 0; j < newPixels[0].length; j++) {
        newPixels[0][0] = new Color(216, 216, 216);
        newPixels[0][1] = new Color(219, 219, 219);
        newPixels[0][2] = new Color(222, 222, 222);

        newPixels[1][0] = new Color(219, 219, 219);
        newPixels[1][1] = new Color(224, 224, 224);
        newPixels[1][2] = new Color(227, 227, 227);

        newPixels[2][0] = new Color(222, 222, 222);
        newPixels[2][1] = new Color(227, 227, 227);
        newPixels[2][2] = new Color(231, 231, 231);

      }
    }
    assertArrayEquals(newPixels, store.retrieve(newFileName).getPixels());
  }

  @Test
  public void testVisualizeLuma() {
    Appendable appendable = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(appendable);
    StoredImages store = new StoredImagesImpl();
    String fileName = "ExampleImage.ppm";
    String newFileName = "LumaVisualizedImage.ppm";
    store.add(fileName, this.beforeImage, true);
    ImageProcessorCmd visualizeLuma = new VisualizeCmd(view, store, Channel.Luma, fileName,
        newFileName);
    visualizeLuma.execute();

    Color[][] newPixels = new Color[3][3];
    for (int i = 0; i < newPixels.length; i++) {
      for (int j = 0; j < newPixels[0].length; j++) {
        newPixels[0][0] = new Color(54, 54, 54);
        newPixels[0][1] = new Color(52, 52, 52);
        newPixels[0][2] = new Color(51, 51, 51);

        newPixels[1][0] = new Color(52, 52, 52);
        newPixels[1][1] = new Color(49, 49, 49);
        newPixels[1][2] = new Color(47, 47, 47);

        newPixels[2][0] = new Color(51, 51, 51);
        newPixels[2][1] = new Color(47, 47, 47);
        newPixels[2][2] = new Color(42, 42, 42);

      }
    }
    assertArrayEquals(newPixels, store.retrieve(newFileName).getPixels());
  }

  @Test
  public void testVisualizeValue() {
    Appendable appendable = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(appendable);
    StoredImages store = new StoredImagesImpl();
    String fileName = "ExampleImage.ppm";
    String newFileName = "ValueVisualizedImage.ppm";
    store.add(fileName, this.beforeImage, true);
    ImageProcessorCmd visualizeValue = new VisualizeCmd(view, store, Channel.Value, fileName,
        newFileName);
    visualizeValue.execute();

    Color[][] newPixels = new Color[3][3];
    for (int i = 0; i < newPixels.length; i++) {
      for (int j = 0; j < newPixels[0].length; j++) {
        newPixels[0][0] = new Color(216, 216, 216);
        newPixels[0][1] = new Color(219, 219, 219);
        newPixels[0][2] = new Color(222, 222, 222);
        newPixels[1][0] = new Color(219, 219, 219);
        newPixels[1][1] = new Color(224, 224, 224);
        newPixels[1][2] = new Color(227, 227, 227);
        newPixels[2][0] = new Color(222, 222, 222);
        newPixels[2][1] = new Color(227, 227, 227);
        newPixels[2][2] = new Color(231, 231, 231);

      }
    }
    assertArrayEquals(newPixels, store.retrieve(newFileName).getPixels());
  }

  @Test
  public void testVisualizeIntensity() {
    Appendable appendable = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(appendable);
    StoredImages store = new StoredImagesImpl();
    String fileName = "ExampleImage.ppm";
    String newFileName = "IntensityVisualizedImage.ppm";
    store.add(fileName, this.beforeImage, true);
    ImageProcessorCmd visualizeIntensity = new VisualizeCmd(view, store, Channel.Intensity,
        fileName, newFileName);
    visualizeIntensity.execute();

    Color[][] newPixels = new Color[3][3];
    for (int i = 0; i < newPixels.length; i++) {
      for (int j = 0; j < newPixels[0].length; j++) {
        newPixels[0][0] = new Color(120, 120, 120);
        newPixels[0][1] = new Color(116, 116, 116);
        newPixels[0][2] = new Color(115, 115, 115);
        newPixels[1][0] = new Color(116, 116, 116);
        newPixels[1][1] = new Color(113, 113, 113);
        newPixels[1][2] = new Color(109, 109, 109);
        newPixels[2][0] = new Color(115, 115, 115);
        newPixels[2][1] = new Color(109, 109, 109);
        newPixels[2][2] = new Color(103, 103, 103);

      }
    }
    assertArrayEquals(newPixels, store.retrieve(newFileName).getPixels());
  }

  @Test
  public void testGreyscale() {
    Appendable appendable = new StringBuilder();
    ImageProcessorView view = new ImageProcessorViewImpl(appendable);
    StoredImages store = new StoredImagesImpl();
    String fileName = "ExampleImage.ppm";
    String newFileName = "GreyscaledExampleImage.ppm";
    store.add(fileName, this.beforeImage, true);
    ImageProcessorCmd greyscale = new GreyscaleCmd(view, store, fileName, newFileName);
    greyscale.execute();

    Color[][] newPixels = new Color[3][3];
    for (int i = 0; i < newPixels.length; i++) {
      for (int j = 0; j < newPixels[0].length; j++) {
        newPixels[0][0] = new Color(53, 53, 53);
        newPixels[0][1] = new Color(51, 51, 51);
        newPixels[0][2] = new Color(50, 50, 50);

        newPixels[1][0] = new Color(51, 51, 51);
        newPixels[1][1] = new Color(48, 48, 48);
        newPixels[1][2] = new Color(45, 45, 45);

        newPixels[2][0] = new Color(50, 50, 50);
        newPixels[2][1] = new Color(45, 45, 45);
        newPixels[2][2] = new Color(40, 40, 40);

      }
    }
    assertArrayEquals(newPixels, store.retrieve(newFileName).getPixels());
  }

}
