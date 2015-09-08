/*
 * Copyright (c) Leuville Objects All Rights Reserved.
 *
 * Leuville Objects MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Leuville Objects SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

package com.leuville.borneo.swing;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

/**
  * A collection of utility methods.
  *
  * @version 1.1.1.1
  */
public class Utilities
{
 /**
  * Build an Image array.
  * Images are loaded by a MediaTracker.
  * The caller is locked until all requests are performed.
  * @param fileNames The filename array.
  * @exception InterruptedException.
  */
 public final static Image[] loadImages (String[] fileNames, Component c) throws InterruptedException
 {
   MediaTracker tracker = new MediaTracker (c);
   Toolkit toolkit = c.getToolkit();
   Image[] images = new Image [fileNames.length];
   for (int i = 0; i < fileNames.length; i++) {
     images[i] = toolkit.getImage (fileNames[i]);
     tracker.addImage (images[i], i);
   }
   tracker.waitForAll ();
   return images;
 }
 /**
  * return an Image loaded by a MediaTracker.
  * The caller is locked until all requests are performed.
  * @param fileName The filename.
  * @exception InterruptedException.
  */
 public final static Image loadImage (String fileName, Component c) throws InterruptedException
 {
   MediaTracker tracker = new MediaTracker (c);
   Toolkit toolkit = c.getToolkit();
   Image image = toolkit.getImage (fileName);
  tracker.addImage (image, 0);
   tracker.waitForAll ();
   return image;
 }
 private final static java.awt.image.ImageObserver nullObserver =
    new java.awt.image.ImageObserver() {
  public boolean imageUpdate(Image im, int infoFlags, int x, int y, int w, int h)
  {
      return false;
  }
    };
 /**
  * Grab pixels of an image.
  * @return An int array.
  */
 public final static int[] intArrayFromImage (Image image) throws IOException
 {
    int w = image.getWidth(nullObserver);
    int h = image.getHeight(nullObserver);
    int[] tab = new int [w*h];
    PixelGrabber pg = new PixelGrabber(image, 0, 0, w, h, tab, 0, w);
    try {
  pg.grabPixels();
  if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
      throw new IOException("failed to load image contents");
  }
  return tab;
    } catch (InterruptedException e) {
      throw new IOException("image load interrupted");
    }
 }
 /**
  * Serialize an image.
  */
 public final static void writeImage(Image image, ObjectOutputStream s) throws IOException
 {
    int[] t = Utilities.intArrayFromImage(image);
    int w = image.getWidth(nullObserver);
    int h = image.getHeight(nullObserver);
    s.writeInt(w);
    s.writeInt(h);
    s.writeObject(t);
 }
 /**
  * Build an image from a pixel array.
  */
 public final static Image imageFromIntArray (int[] pixels, int w, int h)
 {
    Image img = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(w, h, pixels, 0 , w));
    return img;
 }
 /**
  * Deserialize an image.
  */
 public final static Image readImage(ObjectInputStream s) throws IOException, ClassNotFoundException
 {
  int w = s.readInt();
  int h = s.readInt();
  int[] t = (int[])s.readObject();
  return Utilities.imageFromIntArray(t, w, h);
 }
 /**
  * Retuns the root container of a component.
  */
 public final static Container getRootParent (Component comp)
 {
  Container c = comp.getParent(), prev = null;
  while (c != null) {
    prev = c;
    c = c.getParent();
  }
  return prev;
 }

 /**
  * Avoid a bug in java.awt.geom.Arc2D package
  * @param  resPoint  The resulting point (not allocated for better performances).
  */
 public static void getArcPoint(Point2D resPoint, Arc2D arc, double extent)
 {
    // ! Clockwise order
    double angle = Math.toRadians(arc.getAngleStart() + extent);
    resPoint.setLocation (arc.getX() + (Math.cos(angle) * 0.5 + 0.5) * arc.getWidth(),
                          arc.getY() + ( - Math.sin(angle) * 0.5 + 0.5) * arc.getHeight());
 }
 /**
  * Avoid a bug in java.awt.geom.Arc2D package
  */
 public static Point2D getArcPoint(Arc2D arc, double extent)
 {
    // ! Clockwise order
    double angle = Math.toRadians(arc.getAngleStart() + extent);
    double x = arc.getX() + (Math.cos(angle) * 0.5 + 0.5) * arc.getWidth();
//
// VERSION d'origine JAVA2D
//    double y = arc.getY() + (Math.sin(angle) * 0.5 + 0.5) * arc.getHeight();
    double y = arc.getY() + ( - Math.sin(angle) * 0.5 + 0.5) * arc.getHeight();
    return new Point2D.Double(x, y);
 }

 public static double getAngleForPoint(Arc2D arc, int x, int y)
 {
  double px = x - arc.getCenterX();
  double py = arc.getCenterY() - y;
  double d = Math.sqrt(px*px+py*py);
  double a1 = Math.toDegrees(Math.acos(px/d));
  double a2 = Math.toDegrees(Math.asin(py/d));
  if (a2 < 0) {
    if (a1 > 90)
      return 360-a1;
    else
      return -a1;
  } else
    return a1;
 }

 public static Rectangle2D getBounds2D(Arc2D arc)
 {
  if (arc.isEmpty()) {
    return new Rectangle2D.Double(arc.getX(), arc.getY(), arc.getWidth(), arc.getHeight());
  }
  double x1, y1, x2, y2;
  if (arc.getArcType() == Arc2D.PIE) {
    x1 = y1 = x2 = y2 = 0.0;
  } else {
    x1 = y1 = 1.0;
    x2 = y2 = -1.0;
  }
  double angle = 0.0;
  for (int i = 0; i < 6; i++) {
    if (i < 4) {
      // 0-3 are the four quadrants
      angle += 90.0;
      if (!arc.containsAngle(angle)) {
        continue;
      }
    } else
      if (i == 4) {
        // 4 is start angle
        angle = arc.getAngleStart();
      } else {
        // 5 is end angle
        angle += arc.getAngleExtent();
      }
    double rads = Math.toRadians(angle);
    double xe = Math.cos(rads);
//
// SUN BUG FIX
//    double ye = Math.sin(rads);
    double ye = - Math.sin(rads);
    x1 = Math.min(x1, xe);
    y1 = Math.min(y1, ye);
    x2 = Math.max(x2, xe);
    y2 = Math.max(y2, ye);
 }
 double w = arc.getWidth();
 double h = arc.getHeight();
 x2 = (x2 - x1) * 0.5 * w;
 y2 = (y2 - y1) * 0.5 * h;
 x1 = arc.getX() + (x1 * 0.5 + 0.5) * w;
 y1 = arc.getY() + (y1 * 0.5 + 0.5) * h;
 return new Rectangle2D.Double(x1, y1, x2, y2);
 }

 /**
  * Draws a rectangle centered on (x, y)
  */
  public static void drawRectPlot (Graphics2D g, int x, int y, int size)
  {
      g.drawRect (x-size/2, y-size/2, size, size);
  }

 /**
  * Fills a rectangle centered on (x, y)
  */
  public static void fillRectPlot (Graphics2D g, int x, int y, int size)
  {
      g.fillRect (x-size/2, y-size/2, size, size);
  }
 /**
  * Draw a cross.
  */
  public static void drawCross (Graphics2D g, int x, int y, int size)
  {
    g.drawLine (x, y-size, x, y+size);
    g.drawLine (x-size, y, x+size, y);
  }
 /**
  * Draw a 3D Rect.
  */
  public static void draw3DRect (Graphics2D g, int x, int y, int width, int height, Dimension depth)
  {
    int p1x = x;
    int p1y = y;
    int p2x = x+width-1;
    int p2y = p1y;
    int p3x = p2x;
    int p3y = y+height-1;
    int p4x = x-depth.width+width-1;
    int p4y = y+depth.height+height-1;
    int p5x = x-depth.width;
    int p5y = p4y;
    int p6x = p5x;
    int p6y = y+depth.height;
    int p7x = p4x;     // front top right
    int p7y = p6y;
    g.drawLine (p1x, p1y, p2x, p2y);
    g.drawLine (p2x, p2y, p3x, p3y);
    g.drawLine (p3x, p3y, p4x, p4y);
    g.drawLine (p4x, p4y, p5x, p5y);
    g.drawLine (p5x, p5y, p6x, p6y);
    g.drawLine (p6x, p6y, p1x, p1y);
    g.drawLine (p6x, p6y, p7x, p7y);
    g.drawLine (p7x, p7y, p2x, p2y);
    g.drawLine (p7x, p7y, p4x, p4y);
  }
}



