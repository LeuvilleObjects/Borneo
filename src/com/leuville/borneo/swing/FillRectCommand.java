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
 * A command able to draw a filled Rectangle using awt.Graphics drawRect() method.
 *
 * @version 1.2
 */
public class FillRectCommand extends AbstractGraphicsCommand implements RectCommand
{
  /**
    * Default command initialization.
    */
  public static RectCommand defaultCommand = new FillRectCommand ();
  /**
    * Execute this command according to parameters.
    * @param g Graphics object needed to draw.
    * @param o The rectangle object to draw.
    */
  public void exec (Graphics2D g, Object o)
  {
    g.fill((Rectangle2D)o);
  }
  /**
   * Returns the name of the command.
   */
  public String toString ()
  {
    return "Filled rectangle";
  }
}

