# Image Processor

_Smita Rosemary and Ben Bishop – CS3500 Fall 2022, Northeastern University_

## Overview

This project represents an Image Processor that allows you to manipulate and enhance a given image.

The Image Processor allows a user to load an Image into the program, save it, and apply
transformations such as:

* Visualizing the red, blue, green, alpha, luma, or intensity channels of each pixel in an image as
  a greyscale image
* Vertically or horizontally flipping an image
* Brightening or darkening an image
* Blurring or sharpening an image
* Filtering an image to greyscale or sepia

The user interacts with the Image Processor through the command line interface (CLI). or they can
also specify a script file to run as a command line argument, which will run the script and exit the
program. More details on interacting with the program can be found in the [USEME.md](USEME.md) file.

The program currently supports "PPM" images and any image type supported by the Java ImageIO
library. We guarantee support for "PNG", "JPG/JPEG", and "BMP" images. The program will
automatically detect the format to save to/load from based on the file extension.

We have provided two test images located in the `res` folder named `ExampleImage.ppm`
and `ExampleImage2.png`. We have also processed this image with all available commands to visualize
transformations without running the program and saved them to the subfolder `res/processed`
. `ExampleImage.ppm` was created by Smita Rosemary, and has been used with her
permission. `ExampleImage2.png` was created by Ben Bishop, and has been used with his permission.

## How to Use

Instructions on how to use the program can be found in the [USEME.md](USEME.md) file.

## Changelog

### Assignment 5 (11/10/2022)

_Second version. Retained all existing functionality, and added support for new transformations and
the ability to run from a script file._

#### Changes

* Added support for blurring, sharpening, and applying a sepia or greyscale filter to an image.
    * Added more implementations of the `ImageTransformation` interface in the model (`Blur`
      , `Sharpen`, `Sepia`, and `Greyscale`) to apply these filters to `Image`s.
    * Added a new implementation of the `ImageProcessorCmd` interface in the controller
      (`FilterCmd`) to handle the execution of the `blur`, `sharpen`, `sepia`, and `greyscale`
      commands.
    * Modified the `addCommands` method in the `ImageProcessorControllerImpl` class to add the four
      new commands to the map of supported commands.
    * Updated the `renderMenu` method in the `ImageProcessorViewImpl` to include the new filters in
      the menu.
    * Added new test methods in both the `ImageControllerTest` and `ImageTransformationTest` classes
      to test the new commands.
    * Added new example images in the `res/processed` folder to demonstrate the new filters.
* Added a new higher-resolution example image in the `res` folder (`ExampleImage2.png`) to make
  visualization of the new filters easier.
* Added support for loading and saving images from more conventional file formats
    * Added a new implementation of the `ImageFileHandler` interface in the
      controller (`ImageIOHandler`) to handle loading and saving images using the Java ImageIO
      library. This library supports a wide variety of image formats, including "png", "jpg", "jpeg"
      , and "bmp". These four formats are guaranteed to be supported by the program, but others may
      be as well.
    * Modified the `LoadCmd` and `SaveCmd` classes to check the file format of the image to be
      loaded/saved and use the `ImageIOHandler` if it is not a "ppm" image.
    * Added new test methods in the `ImageControllerTest` class to test loading/saving images using
      the new file formats.
* Added support for loading and running a script file as a command line option.
    * Modified the `ImageProcessorRunner` class to check for the presence of
      the `-script <script text file>` command line option, and fallback to the standard CLI if it
      is not present.
* Updated the test methods in the `ImageProcessorViewImplTest` class to ensure the new menu is
  displayed.
* Added support for running the program from a JAR file (see [USEME.md](USEME.md) for details).
    * Added a new `MANIFEST.MF` file to the `META-INF` folder to specify the main class of the
      program.
    * Built the program into a JAR file located at `res/ImageProcessor.jar`.
* Updated the `README.md` file to include more information about the image processor and the new
  features implemented in this version.
* Created a new `USEME.md` file to document how to use the program.

#### Bug Fixes

* `ImagePPMHandler`
    * Fixed that the `process` method would throw an exception if the file was empty.
    * Fixed that the `process` method did not close scanners it opened.
* `Visualize`
    * Replaced typecasting with rounding in the `transform` method to result in more accurate
      transformations.
* Cleaned up JavaDoc comments to be more consistent and accurate.
* Cleaned up error messages to be more consistent and accurate.

### Assignment 4 (11/4/2022)

_Initial version. Included the following initial features:_

* Use the image processor within the terminal
* Load an image into the program
* Save an image from the program
* Brighten or darken an image
* Flip an image horizontally or vertically
* Visualize the red, blue, green, alpha, luma, or intensity channels of each pixel in an image as a
  greyscale image
* View a menu of commands
* Transform multiple images at once

## Class Diagram

To more easily visualize these classes, we have provided a class diagram below:

![Class Diagram](diagram.png)

## Class Overview

### Controller

* Interfaces
    * `ImageProcessorController` : Represents a controller for the image processor. As the user
      inputs commands, the controller validates the parameters and executes them. The controller
      also handles the exceptions thrown by the model and view, and displays them as messages to the
      user via the view.
        * Implementations:
            * `ImageProcessorControllerImpl`: Implements the `ImageProcessorController` interface
              supporting the above transformation commands as well as `load`, `menu`, and `save`.
    * `ImageProcessorCmd` : Represents a supported command that the image processor can handle. The
      command is executed by calling the `execute` method, and implemented subclasses of this
      interface will handle the execution of the command
        * Implementations:
            * `LoadCmd` : Implements the `ImageProcessorCmd` and represents the `load` command
              offered by the processor.
                * `MenuCmd` : Implements the `ImageProcessorCmd` and represents the `menu` command
                  offered by the processor.
                * `SaveCmd` : Implements the `ImageProcessorCmd` and represents the `save` command
                  offered by the processor.
                * `BrightnessCmd` : Implements the `ImageProcessorCmd` and represents the `brighten`
                  and `darken`
                  command offered by the processor.
                * `HorizontalFlipCmd` : Implements the `ImageProcessorCmd` and represents
                  the `horizontal-flip`
                  command offered by the processor.
                * `VerticalFlipCmd` : Implements the `ImageProcessorCmd` and represents
                  the `vertical-flip` command offered by the processor.
                * `VisualizeCmd` : Implements the `ImageProcessorCmd` and represents
                  the `visualize-<componenet>`
                  command offered by the processor.
    * `ImageFileHandler` :  Represents a model used to convert image files into `Image` objects.
        * Implementations:
            * `ImagePPMHandler` : Implements the `ImageFileHandler` interface for converting PPM
              image files into `Image` objects, and vice versa.

### Model

* Interfaces
    * `Image` : Represents an image and it's pixels.
        * Implementations:
            * `ImageImpl` : Implements the `Image` interface, each image is represented by a 2D
              array of colors.
    * `ImageTransformation` : Represents a macro that can be applied to an `Image` to transform its
      pixels in some way.
        * Implementations:
            * `Brightness` : Implements the `ImageTransformation` interface and represents a macro
              that adjusts the brightness of an image, both up the scale and down.
                * `HorizontalFlip` : Implements the `ImageTransformation` interface and represents a
                  macro that flips an image along the horizontal axis.
                * `VerticalFlip`: Implements the `ImageTransformation` interface and represents a
                  macro that flips an image along the vertical axis.
                * `Visualize` : Implements the `ImageTransformation` interface and represents a
                  macro that transforms the images to visualize the greyscale image by one of the
                  following color components : `visualize-red`, `visualize-blue`, `visualize-green`
                  , `visualize-value`,
                  `visualize-intensity`, `visualize-luma`.

    * `StoredImages` : Represents a collection of  `Image`s that have been loaded into the program
      by the user, identified by the image's file name selected by the user.
        * Implementations:
            * `StoredImagesImpl` : Implements the `StoredImages` interface. The stored images are
              represented by a Map<String, Image>, the string representing a fileName.

### View

* Interfaces
    * `ImageProcessorView` : This interface represents the view of the Image Processor. It contains
      methods that the controller can call to render the view.
        * Implementations:
            * `ImageProcessorViewImpl` : Implements the ImageProcessorView interface and it's
              methods. Handles appending all the messages from the controller to the user.

### Main Class

* `ImageProcessorRunner` :  Contains the main method which runs the image processor in the terminal
  for the user.
