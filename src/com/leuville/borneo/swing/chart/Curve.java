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

package com.leuville.borneo.swing.chart;

import com.leuville.borneo.util.FIFO;
import com.leuville.borneo.swing.Utilities;

import java.awt.*;
import java.awt.geom.*;
import java.util.Observable;
import java.util.Date;
import java.io.Serializable;

/**
  * A Curve is used by a Trend or MultiTrend object to draw a list of values.
  * Type implementations provided : Curve.Dot, Curve.Line, Curve.ThinBar, Curve.Polygon,
  * Curve.Plain, Curve.Histo, Curve.Histo3D ...
  *
  * @version 1.1
  */
public abstract class Curve implements Serializable
{
 protected Insets in = new Insets (2, 2, 2, 2);

 /**
  * The drawValue property indicates wheter the value must be drawn.
  */
 protected boolean drawValue = true;

 /**
  * <IMG SRC="doc-files/SpaceObject.gif">
  */
 public Dimension getDepth()
 {
   return ChartComponent.defaultNullDepth;
 }

 /**
  * Constructs a new Curve.
  */
 protected Curve (boolean drawValue)
 {
   this.drawValue = drawValue;
 }

 /**
  * Draw a point.
  * @param g          The Graphics used to draw.
  * @param trendModel The data model.
  * @param paint      The paint object.
  * @param o1         The first orientation of the Curve (right for instance).
  * @param o2         The second orientation of the Curve (up for instance).
  * @param limits     The curve bounds.
  * @param step       The distance between two points (pixel value).
  * @param valueIndex The index of the point in the model.
  * @param cellNumber The number of cell in which to draw (0 is the last cell).
  */
 abstract public void draw (Graphics2D g, TrendModel trendModel, DoubleDataListPaint paint,
          Orientation o1, Orientation o2, com.leuville.borneo.swing.Rectangle limits,
          int step, int valueIndex, int cellNumber);

 /**
  * Draw a value in a rectangle.
  * @param g          The graphics.
  * @param trendModel The data model.
  * @param paint      The paint object.
  * @param valueIndex The index of the point in the model.
  * @param val        The value.
  * @param p          The center of the rectangle.
  */
 final public void drawValue (Graphics2D g, TrendModel trendModel, DoubleDataListPaint paint, int valueIndex,
          double val, Point p)
 {
    this.drawValue (g, trendModel, paint, valueIndex, val, p.x, p.y);
 }

 /**
  * Draw a value in a rectangle.
  * @param g          The graphics.
  * @param trendModel The data model.
  * @param paint      The paint object.
  * @param valueIndex The index of the point in the model.
  * @param val        The value.
  * @param x          The x center of the rectangle.
  * @param y          The y center of the rectangle.
  */
 final public void drawValue (Graphics2D g, TrendModel trendModel, DoubleDataListPaint paint, int valueIndex,
          double val, int x, int y)
 {
    if (!drawValue)
      return;
    String info = Common.roundString (val, 1);
    FontMetrics fm = g.getFontMetrics();
    int h = fm.getHeight();
    int w = fm.stringWidth (info);
    java.awt.Rectangle r = new java.awt.Rectangle (x-w/2-in.left, y-in.top-h/2,
                  w+in.right, h+in.bottom);
    g.setPaint (paint.getPaint(trendModel, val, valueIndex));
    g.fillRect (r.x, r.y, r.width, r.height);
    g.setColor (Color.black);
    g.drawRect (r.x, r.y, r.width, r.height);
    g.drawString (info, r.x+in.left, r.y+h-(r.height-h)/2);
 }

  /**
   * Simple dot Curve type.
   */
  public static class Dot extends Curve
  {
    public Dot ()
    {
      super(false);
    }
    private transient Point tmp = new Point();
    /**
     * @see Orientation#getPoint
     * @see Utilities#drawCross
     */
    public void draw (Graphics2D g, TrendModel model, DoubleDataListPaint paint, Orientation o1, Orientation o2,
          com.leuville.borneo.swing.Rectangle limits, int step, int valueIndex, int cellNumber)
    {
      double v = model.getValue (valueIndex);
      // computes point coordinates
      o1.getPoint (tmp, limits, o2, v, model.getMinimum(), model.getMaximum(), step, cellNumber);
      g.setPaint(paint.getPaint(model, v, valueIndex));
      Utilities.drawCross(g, tmp.x, tmp.y, 2);
      this.drawValue (g, model, paint, valueIndex, v, tmp);
    }
    public String toString()
    {
      return "Dot as cross";
    }
  }

  /**
   * Value Curve type.
   */
  public static class Value extends Curve
  {
    public Value ()
    {
      super(true);
    }

    private transient Point tmp = new Point();
    /**
     * @see Orientation#getPoint
     */
    public void draw (Graphics2D g, TrendModel model, DoubleDataListPaint paint, Orientation o1, Orientation o2,
          com.leuville.borneo.swing.Rectangle limits, int step, int valueIndex, int cellNumber)
    {
      double v = model.getValue (valueIndex);
      // computes point coordinates
      o1.getPoint (tmp, limits, o2, v, model.getMinimum(), model.getMaximum(), step, cellNumber);
      this.drawValue (g, model, paint, valueIndex, v, tmp);
    }
    public String toString()
    {
      return "Value";
    }
  }

  /**
   * Plot Curve type.
   */
  public static class Plot extends Curve
  {
    public Plot ()
    {
      super(false);
    }
    private transient Point tmp = new Point();
    /**
     * @see Orientation#getPoint
     * @see Utilities#fillRectPlot
     */
    public void draw (Graphics2D g, TrendModel model, DoubleDataListPaint paint, Orientation o1, Orientation o2,
          com.leuville.borneo.swing.Rectangle limits, int step, int valueIndex, int cellNumber)
    {
      double v = model.getValue (valueIndex);
      // computes point coordinates
      o1.getPoint (tmp, limits, o2, v, model.getMinimum(), model.getMaximum(), step, cellNumber);
      g.setPaint(paint.getPaint(model, v, valueIndex));
      Utilities.fillRectPlot(g, tmp.x, tmp.y, 4);
      this.drawValue (g, model, paint, valueIndex, v, tmp);
    }
    public String toString()
    {
      return "Plot";
    }
  }

  /**
   * Line Curve type (drawn line between points).
   */
  public static class Line extends Curve
  {
    public Line ()
    {
      this(false);
    }
    public Line (boolean drawValue)
    {
      super(drawValue);
    }
    private transient Line2D tmp = new Line2D.Double(0, 0, 0, 0);
    /**
     * @see Orientation#getLine
     */
    public void draw (Graphics2D g, TrendModel model, DoubleDataListPaint paint, Orientation o1, Orientation o2,
          com.leuville.borneo.swing.Rectangle limits, int step, int valueIndex, int cellNumber)
    {
      if (valueIndex < 1)
        return;
      double v1 = model.getValue (valueIndex-1);
      double v2 = model.getValue (valueIndex);
      // computes the Line
      o1.getLine (tmp, limits, o2, v1, v2, model.getMinimum(), model.getMaximum(), step, cellNumber);
      g.setPaint(paint.getPaint(model, v2, valueIndex));
      g.draw(tmp);
      this.drawValue (g, model, paint, valueIndex, v1, (int)tmp.getX1(), (int)tmp.getY1());
    }
  }

  /**
   * Plotted-line Curve type (drawn line between points + a plot for each point).
   */
  public static class PlottedLine extends Curve
  {
    private transient Line2D tmp = new Line2D.Double(0, 0, 0, 0);
    /**
     * @see Orientation#getLine
     * @see Utilities#drawRectPlot
     */
    public void draw (Graphics2D g, TrendModel model, DoubleDataListPaint paint, Orientation o1, Orientation o2,
          com.leuville.borneo.swing.Rectangle limits, int step, int valueIndex, int cellNumber)
    {
      if (valueIndex < 1)
        return;
      double v1 = model.getValue (valueIndex-1);
      double v2 = model.getValue (valueIndex);
      // computes the Line
      o1.getLine (tmp, limits, o2, v1, v2, model.getMinimum(), model.getMaximum(), step, cellNumber);
      g.setPaint(paint.getPaint(model, v2, valueIndex));
      g.draw(tmp);
      // draw the plot
      Utilities.drawRectPlot(g, (int)tmp.getX2(), (int)tmp.getY2(), 5);
    }
  }

  /**
   * Thin Bar Curve type (vertical or horizontal line).
   */
  public static class ThinBar extends Curve
  {
     public ThinBar ()
     {
       this(true);
     }
     public ThinBar (boolean showValue)
     {
       super(showValue);
     }
    private transient Line2D tmp = new Line2D.Double(0, 0, 0, 0);
    /**
     * @see Orientation#getThinBar
     */
    public void draw (Graphics2D g, TrendModel model, DoubleDataListPaint paint, Orientation o1, Orientation o2,
            com.leuville.borneo.swing.Rectangle limits, int step, int valueIndex, int cellNumber)
    {
      double v = model.getValue (valueIndex);
      // compute the thinBar
      o1.getThinBar (tmp, limits, o2, v, model.getMinimum(), model.getMaximum(), step, cellNumber);
      g.setPaint(paint.getPaint(model, v, valueIndex));
      g.draw(tmp);
      this.drawValue (g, model, paint, valueIndex, v, (int)tmp.getX1(), (int)tmp.getY1());
    }
  }

  /**
   * Polygon Curve type (each value is drawn as a polygon).
   */
  public static class Polygon extends Curve
  {
    public Polygon ()
    {
      super(false);
    }
    private transient Line2D l = new Line2D.Double(0, 0, 0, 0);
    private transient java.awt.Polygon o = new java.awt.Polygon();
    /**
     * @see Orientation#getPolygon
     * @see Orientation#getLine
     */
    public void draw (Graphics2D g, TrendModel model, DoubleDataListPaint paint, Orientation o1, Orientation o2,
          com.leuville.borneo.swing.Rectangle limits, int step, int valueIndex, int cellNumber)
    {
      if (valueIndex < 1)
        return;
      double v1 = model.getValue (valueIndex-1);
      double v2 = model.getValue (valueIndex);
      // compute the polygon
      o1.getPolygon (o, limits, o2, v1, v2, model.getMinimum(), model.getMaximum(), step, cellNumber);
      g.setPaint(paint.getPaint(model, v2, valueIndex));
      g.draw(o);
      o.npoints = 0;
      o1.getLine (l, limits, o2, v1, v2, model.getMinimum(), model.getMaximum(), step, cellNumber);
      this.drawValue (g, model, paint, valueIndex, v1, (int)l.getX1(), (int)l.getY1());
    }
  }

  /**
   * Plain Curve type (filled curve).
   */
  public static class Plain extends Curve
  {
    public Plain ()
    {
      super(false);
    }
    private transient Line2D l = new Line2D.Double(0, 0, 0, 0);
    private transient java.awt.Polygon o = new java.awt.Polygon();
    /**
     * @see Orientation#getPolygon
     * @see Orientation#getLine
     */
    public void draw (Graphics2D g, TrendModel model, DoubleDataListPaint paint, Orientation o1, Orientation o2,
          com.leuville.borneo.swing.Rectangle limits, int step, int valueIndex, int cellNumber)
    {
      if (valueIndex < 1)
        return;
      double v1 = model.getValue (valueIndex-1);
      double v2 = model.getValue (valueIndex);
      // compute the polygon
      o1.getPolygon (o, limits, o2, v1, v2,
            model.getMinimum(), model.getMaximum(), step, cellNumber);
      g.setPaint(paint.getPaint(model, v2, valueIndex));
      g.fill(o);
      o.npoints = 0;
      o1.getLine (l, limits, o2, v1, v2, model.getMinimum(), model.getMaximum(), step, cellNumber);
      this.drawValue (g, model, paint, valueIndex, v1, (int)l.getX1(), (int)l.getY1());
    }
  }

  /**
   * Histo Curve type (2D bargraph).
   */
  public static class Histo extends Curve
  {
    protected int spacing = 0;
    public Histo ()
    {
      this(0);
    }
    public Histo (int spacing)
    {
       super(false);
      this.spacing = spacing;
    }
    private transient Point o = new Point();
    private transient java.awt.Rectangle bar = new java.awt.Rectangle();
    /**
     * @see Orientation#getRectangle
     * @see java.awt.Graphics2D#fill
     */
    public void draw (Graphics2D g, TrendModel model, DoubleDataListPaint paint, Orientation o1, Orientation o2,
          com.leuville.borneo.swing.Rectangle limits, int step, int valueIndex, int cellNumber)
    {
      double v = model.getValue (valueIndex);
      double min = model.getMinimum();
      double max = model.getMaximum();
      if (v < min)
        return;
      // compute the describing rectangle for the bar
      o1.getRectangle (bar, limits, o2, v, min, max, step, cellNumber);
      if (o2.isVertical()) {
        bar.x += spacing;
        bar.width -= spacing;
      } else {
        bar.y += spacing;
        bar.height -= spacing;
      }
      g.setPaint(paint.getPaint(model, v, valueIndex));
      g.fill(bar);
      o1.getPoint (o, limits, o2, v, min, max, step, cellNumber);
      this.drawValue (g, model, paint, valueIndex, v, o);
    }
  }

  /**
   * Histo3D Curve type (3D bargraph).
   */
  public static class Histo3D extends Curve
  {
    protected int spacing = 0;
    public Histo3D ()
    {
      this(0);
    }
    public Histo3D (int spacing)
    {
       super(false);
      this.spacing = spacing;
    }
    private static com.leuville.borneo.swing.RectCommand command = com.leuville.borneo.swing.Fill3DRectCommand.defaultCommand;
    private transient Point o = new Point();
    private transient java.awt.Rectangle bar = new java.awt.Rectangle();
    /**
     * @see Orientation#getRectangle
     */
    public void draw (Graphics2D g, TrendModel model, DoubleDataListPaint paint, Orientation o1, Orientation o2,
          com.leuville.borneo.swing.Rectangle limits, int step, int valueIndex, int cellNumber)
    {
      double v = model.getValue (valueIndex);
      double min = model.getMinimum();
      double max = model.getMaximum();
      if (v < min)
        return;
      // compute the describing rectangle for the bar
      o1.getRectangle (bar, limits, o2, v, min, max, step, cellNumber);
      if (o2.isVertical()) {
        bar.x += spacing;
        bar.width -= spacing;
      } else {
        bar.y += spacing;
        bar.height -= spacing;
      }
      g.setPaint(paint.getPaint(model, v, valueIndex));
      command.exec(g, bar);
      o1.getPoint (o, limits, o2, v, min, max, step, cellNumber);
      this.drawValue (g, model, paint, valueIndex, v, o);
    }
    public Dimension getDepth()
    {
       return command.getDepth();
    }
   }
  /**
   * Convenience DOT Curve type.
   */
  public static Curve DOT = new Dot ();
  /**
   * Convenience PLOT Curve type.
   */
  public static Curve PLOT = new Plot ();
  /**
   * Convenience LINE Curve type.
   */
  public static Curve LINE = new Line ();
  /**
   * Convenience PLOTTEDLINE Curve type.
   */
  public static Curve PLOTTEDLINE = new PlottedLine ();
  /**
   * Convenience THINBAR Curve type.
   */
  public static Curve THINBAR = new ThinBar ();
  /**
   * Convenience POLYGON Curve type.
   */
  public static Curve POLYGON = new Polygon ();
  /**
   * Convenience PLAIN Curve type.
   */
  public static Curve PLAIN = new Plain ();
  /**
   * Convenience HISTO Curve type.
   */
  public static Curve HISTO = new Histo (2);
  /**
   * Convenience HISTO3D Curve type.
   */
  public static Curve HISTO3D = new Histo3D (2);

  /**
   * Creates a new Curve.
   */
  protected Curve ()
  {
  }

  /**
   * Draws last point of the receiver.
   * @param g           Graphics object.
   * @param trendModel  The data model.
   * @param paint       The paint object.
   * @param o1          Main orientation.
   * @param o2          Second orientation.
   * @param limits      Bounds for this last point only (as a rectangle).
   * @param step        Step between two values.
   */
  public void drawLastValue (Graphics2D g, TrendModel model, DoubleDataListPaint paint, Orientation o1, Orientation o2,
                       com.leuville.borneo.swing.Rectangle limits, int step)
  {
    draw (g, model, paint, o1, o2, limits, step, model.getSize()-1, 0);
  }
}


