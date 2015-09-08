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

/**
 * A command able to draw a filled 3D Rectangle.
 *
 * @version 1.2
 */
public class Fill3DRectCommand extends FillRectCommand implements Command3D
{
  /**
    * Default command initialization.
    */
  public static RectCommand defaultCommand = new Fill3DRectCommand ();
  /**
   * The color used to draw limits.
   */
  protected Color lineColor = Color.black;
  /**
   * The depth of this 3D object
   * <IMG SRC="doc-files/SpaceObject.gif">
   */
  protected java.awt.Dimension depth;
  /**
   * Optimization purpose.
   */
  private java.awt.Polygon pUp;
  private java.awt.Polygon pRight;
  /**
    * Constructs a new Fill3DRectCommand with a default depth of (5, 5).
    */
  public Fill3DRectCommand ()
  {
    this (5, 5);
  }
  /**
    * Constructs a new Fill3DRectCommand with a given depth.
    * @param w2 The depth width.
    * @param h2 The depth height.
    */
  public Fill3DRectCommand (int w2, int h2)
  {
    this.depth = new java.awt.Dimension (w2, h2);
    this.pUp = new Polygon(new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 4);
    this.pRight = new Polygon(new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}, 4);
  }
  /**
    * Execute this command according to parameters.
    * @param g Graphics object needed to draw.
    * @param o The rectangle object to draw.
    */
  public void exec (Graphics2D g, Object o)
  {
    Rectangle2D r = (Rectangle2D)o;
    exec(g, (int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
  }
  /**
    * draw a 3D rectangle using a back rectangle as parameter
    */
  public void exec (Graphics2D g, int x, int y, int width, int height)
  {
    Color gColor = g.getColor();
    int p1x = x;        // back top left
    int p1y = y;        // back top left
    int p2x = x+width-1;      // back top right
    int p2y = p1y;        // back top right
    int p3x = p2x;        // back bottom right
    int p3y = y+height-1;      // back bottom right
    int p4x = x-depth.width+width-1;  // front bottom right
    int p4y = y+depth.height+height-1;  // front bottom right
    int p5x = x-depth.width;    // front bottom left
    int p5y = p4y;        // front bottom left
    int p6x = p5x;        // front top left
    int p6y = y+depth.height;    // front top left
    int p7x = p4x;         // front top right
    int p7y = p6y;        // front top right
    g.fillRect (p6x, p6y, p7x - p6x +1, p5y - p6y + 1);
    g.setColor (gColor.darker());

    pUp.xpoints[0] = p1x;
    pUp.xpoints[1] = p2x;
    pUp.xpoints[2] = p7x;
    pUp.xpoints[3] = p6x;
    pUp.ypoints[0] = p1y;
    pUp.ypoints[1] = p2y;
    pUp.ypoints[2] = p7y;
    pUp.ypoints[3] = p6y;

    pRight.xpoints[0] = p7x;
    pRight.xpoints[1] = p2x;
    pRight.xpoints[2] = p3x;
    pRight.xpoints[3] = p4x;
    pRight.ypoints[0] = p7y;
    pRight.ypoints[1] = p2y;
    pRight.ypoints[2] = p3y;
    pRight.ypoints[3] = p4y;

    g.fillPolygon (pUp);
    g.fillPolygon (pRight);
    g.setColor (lineColor);
    g.drawPolygon (pUp);
    g.drawPolygon (pRight);
    g.drawLine (p6x, p6y, p5x, p5y);
    g.drawLine (p5x, p5y, p4x, p4y);
    g.setColor (gColor);
  }
  /**
   * Returns the depth.
   * <IMG SRC="doc-files/SpaceObject.gif">
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
    return "3-D filled rectangle";
  }
}

