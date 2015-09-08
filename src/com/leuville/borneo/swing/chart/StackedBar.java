/*
 *
 * Copyright (c) 1997, 1998 Leuville Objects All Rights Reserved.
 *
 * Leuville Objects MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Leuville Objects SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

package com.leuville.borneo.swing.chart;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import com.leuville.borneo.swing.*;
import java.io.*;

/**
 * <IMG SRC="doc-files/StackedBar.gif">
 * <p>A lightweight transparent StackedBar.
 *
 * @version 1.1
 */
public class StackedBar extends ListChart
{
  /**
   * The default command used to draw a bar.
   */
  public static RectCommand defaultCommand = new Fill3DRectCommand ();

  /**
   * Graphics command used to draw bars.
   */
  protected RectCommand command = defaultCommand;

  /**
   * Bars bounding backrectangles.
   */
  protected transient com.leuville.borneo.swing.Rectangle [] bars;

  /**
   * Constructs a new StackedBar.
   * orientation = Orientation.right
   */
  public StackedBar ()
  {
    this (Orientation.right, 3);
  }

  /**
   * Constructs a new StackedBar.
   */
  public StackedBar (Orientation orientation, int nb)
  {
    super (orientation, nb, 0.0, Double.POSITIVE_INFINITY);
    this.bars = new com.leuville.borneo.swing.Rectangle [model.getSize()];
  }

  /**
   * Layout the receiver.
   * @see #resizeBars()
   */
  protected void layoutComponent ()
  {
    super.layoutComponent ();
    int nbSource = model.getSize ();
    this.bars = new com.leuville.borneo.swing.Rectangle [nbSource];
    resizeBars ();
  }

  /**
   * Computes bar rectangles.
   */
  protected void resizeBars ()
  {
    int n = bars.length;
    for (int i = 0; i < n; i++) {
      bars[i] = new com.leuville.borneo.swing.Rectangle (backRectangle);
    }
  }

  /**
   * Returns the sum attribute.
   */
  public double getSum ()
  {
    final int n = model.getSize();
    double s = 0;
    for (int i = 0; i < n; i++) {
      s += model.getValue(i);
    }
    return s;
  }

  /**
   * Paints the receiver.
   */
  protected void defaultPaintComponent (Graphics2D g)
  {
    if (bars == null) {
      this.bars = new com.leuville.borneo.swing.Rectangle [model.getSize()];
      resizeBars ();
    }
    double sum = getSum();
    double min = 0, max = sum;
    com.leuville.borneo.swing.Rectangle b = backRectangle;
    int n = model.getSize ();
    // The drawing order is important in 3D rendering operations
    if ((orientation.way == Orientation.DOWN) || (orientation.way == Orientation.LEFT)) {
      for (int i = n-1; i > -1; i--) {
        double value = model.getValue(i);
        min = max - value;
        orientation.update (bars[i], getPixelValue (min, b), getPixelValue (max, b)
          );
        max = min;
        g.setPaint(paint.getPaint(model, value, i));
        command.exec (g, bars[i]);
        if (drawValue)
          drawValue(g, getColor(i), getFormattedValue(i), bars[i].getCenterX(), bars[i].getCenterY());
      }
    } else {
      for (int i = 0; i < n; i++) {
        double value = model.getValue(i);
        max = min + value;
        orientation.update (bars[i], getPixelValue (min, b), getPixelValue (max, b)
          );
        min = max;
        g.setPaint(paint.getPaint(model, value, i));
        command.exec (g, bars[i]);
        if (drawValue)
          drawValue(g, getColor(i), getFormattedValue(i), bars[i].getCenterX(), bars[i].getCenterY());
      }
    }
    super.defaultPaintComponent (g);
  }

   /**
     * Return the formatted value of a source as a String.
     * @param i The number of the source.
     */
   public String getFormattedValue(int i)
   {
    double sum = getSum();
    return Common.roundString (100*model.getValue(i) / sum,
            sum, defaultValueDisplayPrecision);
   }

  /**
   * Returns pixel value corresponding to the parameter.
   * According to the orientation attribute.
   */
  protected final int getPixelValue (double v, com.leuville.borneo.swing.Rectangle r)
  {
    return orientation.scaleValue (0, getSum(), v, r);
  }

  /**
   * Return the minimumSize.
   */
   public Dimension getMinimumInsideSize ()
   {
    if (orientation.isVertical())
        return new Dimension(10, 10*getNbSource());
    else
        return new Dimension(10*getNbSource(), 10);
   }

  /**
   * Modifies the command object used to draw the receiver.
   * Generates a repaint request.
   * @param command the new command
   * @see com.leuville.borneo.swing.RectCommand
   */
  public void setCommand (RectCommand command)
  {
    this.command = command;
    setBounds (getBounds());
  }

  /**
   * Returns the command object used to draw the receiver.
   */
  public RectCommand getCommand ()
  {
    return command;
  }

  /**
   * Returns the depth.
   */
  public java.awt.Dimension getDepth ()
  {
    return command.getDepth ();
  }

  /**
   * Hides the stringLegend setting.
   */
  public void setStringLegend (boolean stringLegend)
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Restores the serialized object.
   */
  private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException
  {
        s.defaultReadObject();
  }
}

