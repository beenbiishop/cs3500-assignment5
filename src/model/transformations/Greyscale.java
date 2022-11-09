package model.transformations;

import java.awt.*;

import model.Image;
import model.ImageImpl;
import model.ImageTransformation;

/**
 * An implementation of the {@link ImageTransformation} interface representing a macro used to greyscale
 * an image.
 */
public class Greyscale implements ImageTransformation {

  private static final double[][] GREYSCALE_MATRIX = {{0.2126, 0.7152, .0722}, {0.2126, 0.7152, .0722},
          {0.2126, 0.7152, .0722}};

  @Override
  public Image transform(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    Color[][] oldPixels = image.getPixels();
    Color[][] newPixels = new Color[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        Color pixel = oldPixels[i][j];

        newPixels[i][j] = greyscale(pixel);
      }
    }
    return new ImageImpl(newPixels);
  }

  /**
   * Returns the color of the pixel at the given row and column in the given image after applying
   * the greyscaled matrix to it.
   *
   * @param pixel the pixel of the image being greyscaled
   * @return the new greyscaled color of the pixel
   */
  private Color greyscale(Color pixel) {
    int[] rgb = new int[3];
    for (int k = 0; k < 3; k++) {
      rgb[0] = pixel.getRed();
      rgb[1] = pixel.getGreen();
      rgb[2] = pixel.getBlue();
    }
    int redVal = 0;
    int greenVal = 0;
    int blueVal = 0;

    int[] matrix = new int[3];
      for(int j = 0; j < 3; j++) {
        redVal += (rgb[j] * GREYSCALE_MATRIX[0][j]);
        greenVal += (rgb[j] * GREYSCALE_MATRIX[1][j]);
        blueVal += (rgb[j] * GREYSCALE_MATRIX[2][j]);
      }
    // Clamps the RGB values to be between 0 and 255
    int red = Math.max(0, Math.min((int) Math.round(redVal), 255));
    int green = Math.max(0, Math.min((int) Math.round(greenVal), 255));
    int blue = Math.max(0, Math.min((int) Math.round(blueVal), 255));

    // Returns the new blurred pixel
    return new Color(red, green, blue);
  }
}
