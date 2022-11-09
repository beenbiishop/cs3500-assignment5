package model.transformations;

import java.awt.*;

import model.Image;
import model.ImageImpl;
import model.ImageTransformation;

/**
 * An implementation of the {@link ImageTransformation} interface representing a macro used to
 * transform images to a certain color transformation – greyscale, sepia.
 */
public class ColorTransformation implements ImageTransformation {

  private static final double[][] GREYSCALE_MATRIX = {{0.2126, 0.7152, .0722},
          {0.2126, 0.7152, .0722},
          {0.2126, 0.7152, .0722}};
  private static final double[][] SEPIA_MATRIX = {{0.393, 0.769, 0.189}, {0.349, 0.686, .168},
          {0.272, 0.534, 0.131}};
  private final ColorTransformations transformation;


  /**
   * Constructs a new ColorTransformation macro object with the given color transformation to apply.
   *
   * @param transformation the transformation to apply in the transformed image
   */
  public ColorTransformation(ColorTransformations transformation) {
    this.transformation = transformation;
  }


  @Override
  public Image transform(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();
    Color[][] oldPixels = image.getPixels();
    Color[][] newPixels = new Color[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        Color pixel = oldPixels[i][j];

        newPixels[i][j] = transformPixel(pixel);
      }
    }
    return new ImageImpl(newPixels);
  }

  /**
   * Returns the color of the pixel at the given row and column in the given image after applying
   * the color transformation matrix to it.
   *
   * @param pixel the pixel of the image being transformed
   * @return the new transformed color of the pixel
   */
  private Color transformPixel(Color pixel) {
    int[] rgb = new int[3];
    for (int k = 0; k < 3; k++) {
      rgb[0] = pixel.getRed();
      rgb[1] = pixel.getGreen();
      rgb[2] = pixel.getBlue();
    }
    int redVal = 0;
    int greenVal = 0;
    int blueVal = 0;

    switch (transformation) {
      case Sepia:
        for (int i = 0; i < 3; i++) {
          redVal += (rgb[i] * SEPIA_MATRIX[0][i]);
          greenVal += (rgb[i] * SEPIA_MATRIX[1][i]);
          blueVal += (rgb[i] * SEPIA_MATRIX[2][i]);
        }
        break;
      case Greyscale:
        for (int j = 0; j < 3; j++) {
          redVal += (rgb[j] * GREYSCALE_MATRIX[0][j]);
          greenVal += (rgb[j] * GREYSCALE_MATRIX[1][j]);
          blueVal += (rgb[j] * GREYSCALE_MATRIX[2][j]);
        }
        break;
    }

    // Clamps the RGB values to be between 0 and 255
    int red = Math.max(0, Math.min((int) Math.round(redVal), 255));
    int green = Math.max(0, Math.min((int) Math.round(greenVal), 255));
    int blue = Math.max(0, Math.min((int) Math.round(blueVal), 255));

    // Returns the new blurred pixel
    return new Color(red, green, blue);
  }

  /**
   * Represents a color transformation that can be applied in the image with this
   * transformation macro.
   */
  public enum ColorTransformations {
    Sepia, Greyscale;
  }
}
