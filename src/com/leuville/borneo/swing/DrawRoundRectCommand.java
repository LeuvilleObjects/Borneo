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
 * A command able to draw a rectangle with rounded edges using awt.Graphics drawRoundRect() method.
 *
 * @version 1.2
 */
public class DrawRoundRectCommand extends DrawRectCommand
{
  /**
    * Default command initialization.
    */
  public static RectCommand defaultCommand = new DrawRoundRectCommand (10, 10);
  /**
   * The width of the arc used to draw a round edge.
   */
  private int arcWidth;
  /**
   * The height of the arc used to draw a round edge.
   */
  private int arcHeight;
  /**
   * Construct a new DrawRoundRectCommand.
   * @param arcWidth Width of the arc used to draw a rounded edge.
   * @param arcHeight Height of the arc used to draw a rounded edge.
   */
  public DrawRoundRectCommand (int arcWidth, int arcHeight)
  {
    this.arcWidth = arcWidth;
    this.arcHeight = arcHeight;
  }
  private RoundRectangle2D.Double r = new RoundRectangle2D.Double();
  /**
    * Execute this command according to parameters.
    */
  public void exec (Graphics2D g, Object o)
  {
    Rectangle2D bounds = (Rectangle2D)o;
    r.setRoundRect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), arcWidth, arcHeight);
    g.draw (r);
  }
  /**
   * Returns the name of the command.
   */
  public String toString ()
  {
    return "Drawn rounded rectangle";
  }
}

