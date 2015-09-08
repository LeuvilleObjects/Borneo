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

import java.awt.*;
import java.awt.geom.*;

/**
 * A command able to draw a 3D rectangle.
 *
 * @version 1.2
 */
public class Draw3DRectCommand extends DrawRectCommand implements Command3D
{
  /**
    * Default command initialization.
    */
  public static RectCommand defaultCommand = new Draw3DRectCommand ();
  /**
    * The 3D depth.
    * <IMG SRC="doc-files/SpaceObject.gif">
    */
  protected java.awt.Dimension depth;
  /**
   * Optimization purpose.
   */
  private java.awt.Polygon polygon;
  /**
    * Constructs a new command with a default (5, 5) depth.
    */
  public Draw3DRectCommand ()
  {
    this (5, 5);
  }
  /**
    * Constructs a new command.
    */
  public Draw3DRectCommand (int w2, int h2)
  {
    this.depth = new java.awt.Dimension (w2, h2);
    this.polygon = new java.awt.Polygon(
            new int[] {0,0,0,0,0,0},
            new int[] {0,0,0,0,0,0},
            6);
  }
  /**
    * Execute this command according to parameters.
    */
  public void exec (Graphics2D g, Object o)
  {
    Rectangle2D bounds = (Rectangle2D)o;
    int x = (int)bounds.getX();
    int y = (int)bounds.getY();
    int width = (int)bounds.getWidth();
    int height = (int)bounds.getHeight();
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

    polygon.xpoints[0] = p1x;
    polygon.xpoints[1] = p2x;
    polygon.xpoints[2] = p3x;
    polygon.xpoints[3] = p4x;
    polygon.xpoints[4] = p5x;
    polygon.xpoints[5] = p6x;
    polygon.ypoints[0] = p1y;
    polygon.ypoints[1] = p2y;
    polygon.ypoints[2] = p3y;
    polygon.ypoints[3] = p4y;
    polygon.ypoints[4] = p5y;
    polygon.ypoints[5] = p6y;

    g.drawPolygon (polygon);
    g.drawLine (p6x, p6y, p7x, p7y);
    g.drawLine (p7x, p7y, p2x, p2y);
    g.drawLine (p7x, p7y, p4x, p4y);
  }
  /**
    * Returns the depth.
    */
  public java.awt.Dimension getDepth ()
  {
    return depth;
  }
  /**
   * Returns the name of the command.
   */
  public String toString ()
  {
    return "3-D drawn rectangle";
  }
}

