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

package com.leuville.borneo.swing.chart;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.beans.*;
import javax.swing.*;
import com.leuville.borneo.swing.*;

/**
 * <IMG SRC="doc-files/HistoGraph.gif">
 * <p>A graph which displays data sources with bars.
 *
 * @version 1.1
 */
public class HistoGraph extends ListChart
{
  /**
   * Bars bounding rectangles.
   */
  transient protected com.leuville.borneo.swing.Rectangle [] rects;

  /**
   * Back rectangles of bars bounding rectangles.
   */
  transient protected java.awt.Rectangle [] bars;

  /**
   * An array of zero values (optimization purpose).
   */
  transient protected int [] origin;

  /**
   * Graphics command used to draw bars.
   */
  protected RectCommand command = new Fill3DRectCommand ();

  /**
   * Insets used to specify bar spacing.
   */
  protected Insets barInsets = new Insets (2, 2, 2, 2);

  /**
   * Constructs a new  HistoGraph.
   * orientation = Orientation.up, min = 0.0, max = 100.0
   */
  public HistoGraph ()
  {
    this (Orientation.up, 5, 0.0, 100.0);
  }

  /**
   * Constructs a new  HistoGraph.
   * @param orientation The orientation (up, down, left, right).
   * @param nb The number of bar graphs.
   * @param min The minimum value.
   * @param max The maximum value.
   */
  protected HistoGraph (Orientation orientation, int nb, double min, double max)
  {
    super (orientation, nb, min, max);
    if (nb <= 0)
      throw new IllegalArgumentException (Common.badNb);
    this.command = new Fill3DRectCommand ();
    initSources();
    setMinMax (min, max);
  }

  /**
   * Allocates bars and rects instance variables.
   * @see #initBarGraphs()
   */
  protected void initSources ()
  {
    int nbSources = model.getSize();
    this.bars = new java.awt.Rectangle [nbSources];
    this.rects = new com.leuville.borneo.swing.Rectangle [nbSources];
    initBarGraphs ();
  }

  /**
   * Initializes rects and colors.
   */
  protected void initBarGraphs ()
  {
    int n = rects.length;
    for (int i = 0; i < n; i++) {
      rects[i] = new com.leuville.borneo.swing.Rectangle ();
      if (getColor(i) == null)
        setColor(i, defaultColors[i % (defaultColors.length)]);
    }
  }

  /**
   * Reshapes the receiver, compute bar sizes.
   * @see #initSources()
   * @see #resizeBars()
   */
  protected void layoutComponent ()
  {
    super.layoutComponent ();
    int nbSources = model.getSize();
    initSources();
    this.origin = new int [nbSources];
    resizeBars ();
  }

  /**
   * Computes bar rectangles (rects instance variable) according to the current size.
   */
  protected void resizeBars ()
  {
    int n = bars.length;
    if (n == 0)
      return;
    int w, h;
    java.awt.Rectangle r = backRectangle;
    int ox;
    int oy;
    if (orientation.isVertical ()) {
      w = (r.width - n*(barInsets.right + barInsets.left))/ n;
      h = r.height;
      ox = r.x + barInsets.left;
      oy = r.y;
    } else {
      w = r.width ;
      h = (r.height - n*(barInsets.top + barInsets.bottom))/ n;
      ox = r.x ;
      oy = r.y + barInsets.top;
    }
    for (int i = 0; i < n; i++) {
      rects[i].x = ox;
      rects[i].y = oy;
      rects[i].width = w;
      rects[i].height = h;
      if (orientation.isVertical()) {
        ox += w + barInsets.right + barInsets.left;
      } else {
        oy += h + barInsets.bottom + barInsets.top;
      }
    }
  }

  /**
   * Replaces the current Graphics Command by the parameter.
   * The receiver is laid out.
   */
  public void setCommand (RectCommand command)
  {
    this.command = command;
    setBounds(getBounds());
  }

  /**
   * Returns the current Graphics Command.
   */
  public RectCommand getCommand ()
  {
    return command;
  }

  /**
   * Returns barInsets.
   */
  public Insets getBarInsets ()
  {
    return barInsets;
  }

  /**
   * Replace current barInsets by the parameter.
   */
  public void setBarInsets (Insets barInsets)
  {
    this.barInsets = barInsets;
    resizeBars();
    repaint();
  }

  /**
   * Paint the receiver.
   */
  protected void defaultPaintComponent (Graphics2D g)
  {
    g.setColor (getBackground());
    drawScale (g);
    for (int i = 0; i < bars.length; i++) {
      draw (g, i);
    }
    super.defaultPaintComponent(g);
  }

  /**
   * Draws the bar number <tt>i</tt>.
   * @param g The Graphics.
   * @param i Index of bar to draw.
   */
  protected void draw (Graphics2D g, int i)
  {
    if (bars[i] == null) {
      bars[i] = rects[i].backRectangle (getDepth ());
      origin[i] = getPixelValue (i, 0);
    }
    double val = model.getValue(i);
    orientation.update (bars[i], origin[i], getPixelValue (i, val));
    g.setPaint(paint.getPaint(model, val, i));
    command.exec (g, bars[i]);
    if (drawValue)
      drawValue(g, getColor(i), getFormattedValue(i), rects[i].getCenterX(), rects[i].getCenterY());
  }

  /**
   * Returns the pixel value needed to display <tt>value</tt> for bar number <tt>i</tt>.
   */
  final protected int getPixelValue (int i, double v)
  {
    return orientation.scaleValue (getMinimum(), getMaximum(), v, rects[i]);
  }

  /**
   * Returns the index of the bar containing the point (x, y).
   */
  public int barGraphContaining (int x, int y)
  {
    for (int i = 0; i < bars.length; i++) {
      if (bars[i].contains (x, y))
        return i;
    }
    return -1;
  }

  /**
   * Returns the depth.
   */
  public java.awt.Dimension getDepth ()
  {
    return command.getDepth ();
  }

  /**
   * Return the minimumSize needed to draw bars.
   */
   public Dimension getMinimumInsideSize ()
   {
    int n = model.getSize();
    int w = n*(barInsets.left+barInsets.right+10);
    int h = n*(barInsets.top+barInsets.bottom+10);
    return new Dimension (w, h);
   }

  /**
   * Set minimum and maximum for all sources.
   */
  protected void setMinMax (double min, double max)
  {
    Common.checkMinMax (min, max);
    setMinimum(min);
    setMaximum(max);
  }

 /**
  * Creates a string legend used as an OrientedChart area.
  * @see OrientedChart#setStringLegend(boolean)
  * @see OrientedChart#createNorthArea()
  * @see OrientedChart#createSouthArea()
  * @see OrientedChart#createEastArea()
  * @see OrientedChart#createWestArea()
  */
 protected JComponent createStringLegend()
 {
  return (stringLegend ? new StringLegend() : null);
 }

 /**
  * The StringLegend class.
  * Draws a string for each source using labels and colors attributes.
  */
 final private class StringLegend extends JComponent
 {
   /**
     * Construct a new StringLegend.
     * Bounds are set to the best size, according to the font choice.
     */
   public StringLegend ()
   {
     this.setSize (this.getPreferredSize());
  }
  /**
   * Return the preferred size, according to the font choice.
   */
  final public Dimension getPreferredSize()
  {
    Dimension prefSize = new Dimension (2, 2);
    int h = HistoGraph.this.legendFont.getSize();
    int nb = HistoGraph.this.getNbSource();
    if (HistoGraph.this.orientation.isHorizontal()) {
       prefSize.height += h*nb;
       int w = 0;
       for (int i = 0; i < nb; i++) {
        w = Math.max (w, Common.stringWidth (HistoGraph.this.legendFont, HistoGraph.this.getLabel(i)));
      }
      prefSize.width += w;
     } else {
      int w1 = 0;
      int w2 = 0;
       for (int i = 0; i < nb; i+=2) {
        w1 += 4+Common.stringWidth (HistoGraph.this.legendFont, getLabel(i));
      }
       for (int i = 1; i < nb; i+=2) {
        w2 += 4+Common.stringWidth (HistoGraph.this.legendFont, getLabel(i));
      }
      prefSize.height += 2*h + 2;
      prefSize.width += Math.max (w1, w2);
     }
    return prefSize;
  }
   /**
     * Draw the receiver.
     */
  final public void paintComponent (Graphics g)
  {
    super.paintComponent(g);
    Color sColor = g.getColor();
    Font save = g.getFont();
    g.setFont (HistoGraph.this.legendFont);
    FontMetrics fm = g.getFontMetrics ();
    int h = fm.getHeight();
    Point[] points = getStringPositions();
    for (int i = 0; i < points.length; i++) {
      g.setColor (HistoGraph.this.getColor(i));
      String info = HistoGraph.this.getLabel(i);
      int w = fm.stringWidth (info);
      if (HistoGraph.this.orientation.isVertical()) {
        int px = -getX() + points[i].x-w/2;
        int py = (i % 2 == 0 ? 3*h/4 : 6*h/4);
        g.drawString (info, px, py);
      } else {
        int px = 0;
        switch (HistoGraph.this.orientation.way) {
           case Orientation.LEFT:
               px = 1;
               break;
           case Orientation.RIGHT:
               px = getSize().width-w-1;
               break;
        }
        int py = -getY() + points[i].y+h/4;
        g.drawString (info, px, py);
       }
     }
     g.setFont (save);
     g.setColor (sColor);
   }
 }

 /**
  * Return an array of points used to align info strings
  * These points are calculated from the rects field.
  */
 final private Point[] getStringPositions ()
 {
   com.leuville.borneo.swing.Rectangle[] r = rects;
   Point[] p = new Point[r.length];
   switch (orientation.getWay()) {
     case Orientation.DOWN:
         for (int i = 0; i < r.length; i++) {
           p[i] = new Point (r[i].x+r[i].width/2, r[i].y);
         }
         return p;
     case Orientation.RIGHT:
         for (int i = 0; i < r.length; i++) {
           p[i] = new Point (r[i].x, r[i].y+r[i].height/2);
         }
         return p;
     case Orientation.UP:
         for (int i = 0; i < r.length; i++) {
           p[i] = new Point (r[i].x+r[i].width/2, r[i].y+r[i].height);
         }
         return p;
     case Orientation.LEFT:
         for (int i = 0; i < r.length; i++) {
           p[i] = new Point (r[i].x+r[i].width, r[i].y+r[i].height/2);
         }
         return p;
   }
   return null;
 }
}

