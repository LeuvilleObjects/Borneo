/*
 *
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

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Point;

/**
 * Implements Rectangle able to draw itself using a Graphics.
 *
 * @version 1.1
 */
public class Rectangle extends java.awt.Rectangle
{
  /**
   * Constructs a new Rectangle.
   */
  public Rectangle ()
  {
    super ();
  }
  /**
   * Constructs a new Rectangle.
   */
  public Rectangle (Dimension d)
  {
    super (d);
  }
  /**
   * Constructs a new Rectangle.
   */
  public Rectangle (int width, int height)
  {
    super (width, height);
  }
  /**
   * Constructs a new Rectangle.
   */
  public Rectangle (int x, int y, int width, int height)
  {
    super (x, y, width, height);
  }
  /**
   * Constructs a new Rectangle.
   */
  public Rectangle (Point p, Dimension d)
  {
    super (p, d);
  }
  /**
   * Constructs a new Rectangle.
   */
  public Rectangle (java.awt.Rectangle r)
  {
    this (r.x, r.y, r.width, r.height);
  }
  /**
   * Constructs a new Rectangle.
   */
  public Rectangle (Point topLeft, Point topRight, Point bottomLeft, Point bottomRight)
  {
    super (topLeft, new java.awt.Dimension (
            topRight.x-topLeft.x,
            bottomLeft.y-topLeft.y));
  }
  /**
   * Draws the receiver on a Graphics.
   */
  public void draw (Graphics g)
  {
    g.drawRect (x, y, width, height);
  }
  /**
   * Fills the receiver on a Graphics.
   */
  public void fill (Graphics g)
  {
    g.fillRect (x, y, width, height);
  }
  /**
   * Returns the front rectangle of a 3D rectangle.
   */
  public com.leuville.borneo.swing.Rectangle frontRectangle (java.awt.Dimension depth)
  {
    return new com.leuville.borneo.swing.Rectangle (
          x,
          y + depth.height,
          width - depth.width,
          height - depth.height
        );
  }
  /**
   * Returns the back rectangle of a 3D rectangle.
   */
  public com.leuville.borneo.swing.Rectangle backRectangle (java.awt.Dimension depth)
  {
    return new com.leuville.borneo.swing.Rectangle (
            x + depth.width ,
            y,
            width - depth.width,
            height - depth.height
          );
  }
  /**
   * Set the receiver to the front rectangle of the first parameter.
   * This method avoid object creation.
   */
  public void setToFrontRectangleOf (java.awt.Rectangle r, java.awt.Dimension depth)
  {
    x = r.x;
    y = r.y + depth.height;
    width = r.width - depth.width;
    height = r.height - depth.height;
  }
  /**
   * Set the receiver to the back rectangle of the first parameter.
   * This method avoid object creation.
   */
  public void setToBackRectangleOf (java.awt.Rectangle r, java.awt.Dimension depth)
  {
    x = r.x + depth.width;
    y = r.y;
    width = r.width - depth.width;
    height = r.height - depth.height;
  }
  /**
   * Returns the top left Point.
   */
  public Point topLeft ()
  {
    return new Point (x, y);
  }
  /**
   * Returns the top right Point.
   */
  public Point topRight ()
  {
    return new Point (x + width - 1, y);
  }
  /**
   * Returns the bottom left Point.
   */
  public Point bottomLeft ()
  {
    return new Point (x, y + height - 1);
  }
  /**
   * Returns the bottom right Point.
   */
  public Point bottomRight ()
  {
    return new Point (x + width - 1, y + height - 1);
  }
  /**
   * Returns the center.
   */
  public Point center ()
  {
    return new Point (x + width/2, y + height/2);
  }
}

