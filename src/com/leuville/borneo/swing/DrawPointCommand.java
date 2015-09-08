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
 * A command able to draw a line using awt.Graphics drawPoint() method.
 *
 * @version 1.2
 */
public class DrawPointCommand extends AbstractGraphicsCommand implements PointCommand
{
  /**
    * Default command initialization.
    */
  public static GraphicsCommand defaultCommand = new DrawPointCommand ();
  /**
    * Execute this command according to parameters.
    * @param g Graphics object needed to draw.
    * @param o The point object to draw.
    */
  public void exec (Graphics2D g, Object o)
  {
    Point2D p = (Point2D)o;
    int x = (int)p.getX();
    int y = (int)p.getY();
    g.drawLine(x, y, x, y);
  }
  /**
   * Returns the name of the command.
   */
  public String toString ()
  {
    return "Point";
  }
}

