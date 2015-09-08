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
 * A command able to draw a cross using awt.Graphics drawLine() method.
 *
 * @version 1.2
 */
public class DrawCrossCommand extends DrawPointCommand {
  /**
    * Size of the cross.
    */
  public int size ;
  /**
    * Default command initialization.
    */
  public static GraphicsCommand defaultCommand = new DrawCrossCommand ();
  /**
    * Creates a new DrawCrossCommand with a size of 1.
    */
  public DrawCrossCommand ()
  {
    this(1);
  }
  /**
    * Creates a new DrawCrossCommand.
    */
  public DrawCrossCommand (int size)
  {
    this.size = size;
  }
  /**
    * Execute this command according to parameters.
    * @param g Graphics object needed to draw.
    * @param x The X coordinate of the point.
    * @param y The Y coordinate of the point.
    */
  public void exec (Graphics g, Object o)
  {
    Point2D p = (Point2D)o;
    int x = (int)p.getX();
    int y = (int)p.getY();
    g.drawLine (x, y-size, x, y+size);
    g.drawLine (x-size, y, x+size, y);
  }
  /**
   * Returns the name of the command.
   */
  public String toString ()
  {
    return "Cross";
  }
}

