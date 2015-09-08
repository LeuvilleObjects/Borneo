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
 * A command able to draw a plot using awt.Graphics drawRect() method.
 *
 * @version 1.2
 */
public class DrawPlotCommand extends DrawPointCommand
{
  /**
    * Size of the plot.
    */
  public int size ;
  /**
    * Default command initialization.
    */
  public static GraphicsCommand defaultCommand = new DrawPlotCommand ();
  /**
    * Creates a new DrawPlotCommand with a size of 1.
    */
  public DrawPlotCommand ()
  {
    this(1);
  }
  /**
    * Creates a new DrawPlotCommand.
    */
  public DrawPlotCommand (int size)
  {
    this.size = size;
  }
  /**
    * Execute this command according to parameters.
    */
  public void exec (Graphics2D g, Object o)
  {
    Point2D p = (Point2D)o;
    int x = (int)p.getX();
    int y = (int)p.getY();
    g.drawRect (x-size/2, y-size/2, size, size);
  }
  /**
   * Returns the name of the command.
   */
  public String toString ()
  {
    return "Plot";
  }
}

