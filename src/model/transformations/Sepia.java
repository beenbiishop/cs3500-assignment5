package model.transformations;

import java.awt.*;

import model.Image;
import model.ImageImpl;
import model.ImageTransformation;

public class Sepia implements ImageTransformation {

  private static final double[][] SEPIA_MATRIX = {{0.393, 0.769, 0.189}, {0.349, 0.686, .168},
          {0.272, 0.534, 0.131}};

  @Override
  public Image transform(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    Color[][] oldPixels = image.getPixels();
    Color[][] newPixels = new Color[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newPixels[i][j] = sepiaPixel(oldPixels[i][j]);
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
  private Color sepiaPixel(Color pixel) {

    double redVal = 0;
    double greenVal = 0;
    double blueVal = 0;

    for (int j = 0; j < 3; j++) {
      redVal += pixel.getRed() * SEPIA_MATRIX[0][j];
      greenVal += pixel.getGreen() * SEPIA_MATRIX[1][j];
      blueVal += pixel.getBlue() * SEPIA_MATRIX[2][j];
    }

    // Clamps the RGB values to be between 0 and 255
    int red = Math.max(0, Math.min((int) Math.round(redVal), 255));
    int green = Math.max(0, Math.min((int) Math.round(greenVal), 255));
    int blue = Math.max(0, Math.min((int) Math.round(blueVal), 255));

    // Returns the new blurred pixel
    return new Color(red, green, blue);
  }
}


