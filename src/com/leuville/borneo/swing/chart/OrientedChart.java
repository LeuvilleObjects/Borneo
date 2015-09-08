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
import javax.swing.*;

/**
 * A ChartComponent with an Orientation (N, S, W , E).
 * This abstract class must be derived.
 *
 * @version 1.1
 */
abstract public class OrientedChart extends ChartComponent
{
 /**
  * The north rectangle zone.
  */
 protected JComponent north;
 /**
  * The west rectangle zone.
  */
 protected JComponent west;
 /**
  * The south rectangle zone.
  */
 protected JComponent south;
 /**
  * The east rectangle zone.
  */
 protected JComponent east;
 /**
  * The legend font used to draw info strings.
  */
 protected Font legendFont = new Font ("default", Font.PLAIN, 11);
 /**
  * The value scale font.
  */
 protected Font valueFont = new Font ("default", Font.PLAIN, 10);
 /**
  * The valueScale control.
  */
 protected boolean valueScale;
 /**
  * The string legend control.
  */
 protected boolean stringLegend;
 /**
  * The scale fraction (ie the number of parts in which the background is divided into).
  */
 protected int scaleFraction = 10;
 /**
  * The orientation (default is Orientation.right).
  */
 protected Orientation orientation = Orientation.right;
 /**
  * The scale.
  */
 protected Scale scale;
 /**
  * Constructs a new Oriented Canvas.
  * The scale is set to null.
  */
 protected OrientedChart (Orientation orientation)
 {
  this.orientation = orientation;
  this.scale = null;
  setLayout(null);
 }
 /**
  * Return a clone of the receiver.
  */
 public Object clone() throws CloneNotSupportedException
 {
   OrientedChart copy = (OrientedChart)super.clone();
   copy.layoutComponent();
   return copy;
 }
 /**
  * Draws the scale box if not null.
  */
 protected void drawScale (Graphics g)
 {
   if (scale != null) {
//    scale.setBounds (r.x, r.y, r.width, r.height);
    scale.draw (g);
  }
 }
 /**
  * Returns true if there is a scale.
  */
 public boolean isScale ()
 {
   return (scale != null);
 }
 /**
  * Sets the scale on/off.
  */
 public void setScale (boolean mode)
 {
   if (mode) {
    scale = createScale ();
  } else {
    scale = null;
  }
  setBounds (getBounds());
 }
 /**
  * Instantiates the Scale object.
  */
 protected Scale createScale()
 {
  return new Scale();
 }
 /**
  * SizedView protocol.
  */
 public abstract double getMinimum();
 /**
  * SizedView protocol.
  */
 public abstract double getMaximum();
 /**
  * Returns the orientation.
  */
 public Orientation getOrientation()
 {
   return orientation;
 }
 /**
  * Set the orientation and modifies the scale if needed.
  * A repaint request is generated.
  * @see setAreas().
  */
 public void setOrientation (Orientation orientation)
 {
   this.orientation = orientation;
   layoutComponent();
   repaint();
 }
 /**
  * Areas are laid out.
  * @see setAreas
  * Set the legend font property.
  */
 public void setLegendFont (Font legendFont)
 {
  this.legendFont = legendFont;
  layoutComponent();
  repaint();
 }
 /**
  * Get the legend font property.
  */
 public Font getLegendFont()
 {
  return legendFont;
 }
 /**
  * Set the value font property.
  * Areas are laid out.
  * @see setAreas
  */
 public void setValueFont (Font valueFont)
 {
  this.valueFont = valueFont;
   layoutComponent();
   repaint();
 }
 /**
  * Get the value font property.
  */
 public Font getValueFont()
 {
  return valueFont;
 }

 /**
  * Call first the superclass implementation and draw north, west, south and east.
  */
 protected void defaultPaintComponent (Graphics2D g)
 {
 /*
   System.out.println ("insideBounds "+insideBounds);
   System.out.println ("spacing "+spacing);
   if (north != null) System.out.println ("north "+north.getBounds());
   if (west != null) System.out.println ("west "+west.getBounds());
   if (south != null) System.out.println ("south "+south.getBounds());
   if (east != null) System.out.println ("east "+east.getBounds());
   */
   super.defaultPaintComponent (g);
 }
 /**
  * Layout the receiver.
  */
 protected void layoutComponent ()
 {
  setAreas();
  int westW = (west == null ? 0 : west.getPreferredSize().width);
  int eastW = (east == null ? 0 : east.getPreferredSize().width);
  int northH = (north == null ? 0 : north.getPreferredSize().height);
  int southH = (south == null ? 0 : south.getPreferredSize().height);
  java.awt.Dimension targetSize = getSize();
   // get border insets
  java.awt.Insets tmp = (java.awt.Insets) getInsets().clone();
  tmp.top += northH + spacing.top;
  tmp.left += westW + spacing.left;
  tmp.bottom += southH + spacing.bottom;
  tmp.right += eastW + spacing.right;
  insideBounds.setBounds (tmp.left, tmp.top,
        targetSize.width-tmp.left-tmp.right,
        targetSize.height-tmp.top-tmp.bottom);
  if (north != null) {
    north.setBounds (insideBounds.x-spacing.left, getInsets().top,
        insideBounds.width+spacing.left+spacing.right,
        northH);
  }
  if (south != null) {
    south.setBounds (insideBounds.x-spacing.left, insideBounds.y+insideBounds.height+spacing.bottom,
        insideBounds.width+spacing.left+spacing.right, southH);
  }
  if (west != null) {
    west.setBounds (getInsets().left, insideBounds.y-spacing.top,
        westW, insideBounds.height+spacing.top+spacing.bottom);
  }
  if (east != null) {
    east.setBounds (insideBounds.x+insideBounds.width+spacing.right, insideBounds.y-spacing.top,
        eastW, insideBounds.height+spacing.top+spacing.bottom);
  }
  updateDrawableBounds();
  if (scale != null)
    scale.setBounds (backRectangle.x, backRectangle.y, backRectangle.width, backRectangle.height);
 }

 /**
  * Return the minimum size for this component.
  */
 public Dimension getMinimumSize()
 {
  int westW = (west == null ? 0 : west.getPreferredSize().width);
  int eastW = (east == null ? 0 : east.getPreferredSize().width);
  int northH = (north == null ? 0 : north.getPreferredSize().height);
  int southH = (south == null ? 0 : south.getPreferredSize().height);
  int westH = (west == null ? 0 : west.getPreferredSize().height);
  int eastH = (east == null ? 0 : east.getPreferredSize().height);
  int northW = (north == null ? 0 : north.getPreferredSize().width);
  int southW = (south == null ? 0 : south.getPreferredSize().width);
   java.awt.Insets insets = getInsets();
  Dimension internal = getMinimumInsideSize();
  int w = Math.max (internal.width, Math.max (northW, southW));
  int h = Math.max (internal.height, Math.max (eastH, westH));
  w += insets.left+insets.right+eastW+westW+spacing.left+spacing.right;
  h += insets.top+insets.bottom+northH+southH+spacing.top+spacing.bottom;
  return new Dimension (w, h);
 }
 /**
  * Return the minimum size of the drawable area.
  * This method should be overriden.
  * @return A Dimension(10, 10) object.
  */
 public Dimension getMinimumInsideSize()
 {
    return new Dimension(10, 10);
 }
 /**
  * Set the valueScale property.
  * @see OrientedChart#setAreas
  */
 public void setValueScale (boolean valueScale)
 {
   this.valueScale = valueScale;
   layoutComponent();
   repaint();
 }
 /**
  * Return the valueScale property.
  */
 public boolean getValueScale()
 {
   return valueScale;
 }
 /**
  * Set the stringLegend property.
  * @see OrientedChart#setAreas
  */
 public void setStringLegend (boolean stringLegend)
 {
   this.stringLegend = stringLegend;
   layoutComponent();
   repaint();
 }
 /**
  * Return the valueScale property.
  */
 public boolean getStringLegend()
 {
   return stringLegend;
 }
 /**
  * Creates the value scale legend.
  */
 protected JComponent createValueScale()
 {
  return (valueScale ? new ValueScale() : null);
 }
 /**
  * Creates the string legend.
  */
 protected JComponent createStringLegend()
 {
  return (stringLegend ? new StringLegend() : null);
 }

 /**
  * Creates the north area.
  * @return null if orientation is not down, calls createStringLegend() otherwise.
  */
 protected JComponent createNorthArea()
 {
  if (orientation.getWay() == Orientation.DOWN)
    return createStringLegend();
  else
    return null;
 }

 /**
  * Creates the east area.
  * @return null if orientation is not left, calls createStringLegend() otherwise.
  */
 protected JComponent createEastArea()
 {
  if (orientation.getWay() == Orientation.LEFT)
    return createStringLegend();
  else
    return null;
 }

 /**
  * Creates the south area.
  * @return createStringLegend() if orientation is up.
  * createValueScale() if orientation is left or right, null if it is down.
  */
 protected JComponent createSouthArea()
 {
  String info1 = getFormattedValue (getMinimum());
  String info2 = getFormattedValue (getMaximum());
  int w1 = Common.stringWidth (valueFont, info1);
  int w2 = Common.stringWidth (valueFont, info2);
  switch (orientation.getWay()) {
     case Orientation.UP:
      return createStringLegend ();
     case Orientation.RIGHT:
      if (valueScale) {
        spacing.left = Math.max(spacing.left, (int)Math.round(0.5*w1));
        spacing.right = Math.max(spacing.right, (int)Math.round(0.5*w2));
      }
      return createValueScale ();
     case Orientation.LEFT:
      if (valueScale) {
        spacing.left = Math.max(spacing.left, (int)Math.round(0.5*w2));
        spacing.right = Math.max(spacing.right, (int)Math.round(0.5*w1));
      }
      return createValueScale ();
    default:
      return null;
  }
 }

 /**
  * Creates the west area.
  * @return createValueScale() if orientation is up or down,
  * createStringLegend() if it is right, null if it is left.
  */
 protected JComponent createWestArea()
 {
  switch (orientation.getWay()) {
     case Orientation.UP:
     case Orientation.DOWN:
      if (valueScale) {
        int h = legendFont.getSize();
        spacing.top = Math.max(spacing.top, h);
        spacing.bottom = Math.max(spacing.bottom, h);
      }
      return createValueScale ();
     case Orientation.RIGHT:
      return createStringLegend ();
    default:
      return null;
  }
 }
 /**
  * Create valueScale and stringLegend.
  * The north, west, south and east zone are updated.
  * The spacing is modified.
  * @see OrientedChart#setBounds
  */
 protected void setAreas ()
 {
   if (north != null) {
    remove (north);
   }
   if (south != null) {
    remove (south);
   }
   if (east != null) {
    remove (east);
   }
   if (west != null) {
    remove (west);
   }
   north = createNorthArea();
   if (north != null)
      add(north);
   south = createSouthArea();
   if (south != null)
      add(south);
   east = createEastArea();
   if (east != null)
      add(east);
   west = createWestArea();
   if (west != null)
      add(west);
   validate();
 }
 /**
  * Return an array of coordinates used to align the valueScale
  * with the background scale.
  * These coordinates may be x or y absolute coordinates according to orientation.
  */
 protected int[] getScalePositions (Rectangle r)
 {
  double max;
  double min;
  if (orientation.isVertical()) {
    min = r.x;
    max = r.x + r.width - 1;
  } else {
    min = r.y;
    max = r.y + r.height - 1;
  }
  double step = (max - min) / scaleFraction;
  double d = min;
  int[] pos = new int [scaleFraction+1];
  for (int i = 0; i < scaleFraction+1; i++) {
    pos[i] = orientation.scaleValue (min, max, d, r);
    d += step;
  }
  return pos;
 }
 /**
  * Return a string representation of a value.
  * @see Common#roundString(double, double, double);
  */
 protected String getFormattedValue (double value)
 {
   return Common.roundString (value, getMaximum()-getMinimum(), defaultValueDisplayPrecision);
 }
 /**
  * Set the scale fraction (ie the number of parts in which the background is divided).
  * The component is laid out.
  */
 public void setScaleFraction (int scaleFraction)
 {
   this.scaleFraction = scaleFraction;
   setBounds (getBounds());
 }
 /**
  * Return the scale fraction.
  */
 public int getScaleFraction ()
 {
   return scaleFraction;
 }

 /**
  * The ValueScale class.
  */
 public class ValueScale extends JComponent
 {
  private transient Dimension prefSize = new Dimension (0, 0);
  /**
    * Construct a new ValueScale.
    * Bounds are set to the best size, according to the font choice.
    */
  public ValueScale ()
  {
      this.setSize(this.getPreferredSize());
  }
  /**
   * Return the preferred size, according to the font choice.
   */
  public Dimension getPreferredSize()
  {
     prefSize.width = 0;
     prefSize.height = 0;
     int h = OrientedChart.this.legendFont.getSize();
     if (OrientedChart.this.orientation.isVertical()) {
      prefSize.height += h*(OrientedChart.this.scaleFraction+1);
      String info1 = OrientedChart.this.getFormattedValue (OrientedChart.this.getMinimum());
      String info2 = OrientedChart.this.getFormattedValue (OrientedChart.this.getMaximum());
      Font f = OrientedChart.this.valueFont;
      int w = Math.max (Common.stringWidth (f, info1), Common.stringWidth (f, info2));
      prefSize.width += w;
     } else {
       prefSize.height += 2*h + 2;
      Font f = OrientedChart.this.valueFont;
      double min = OrientedChart.this.getMinimum();
      double max = OrientedChart.this.getMaximum();
      int w1 = 0, w2 = 0;
      for (int i = 0; i < OrientedChart.this.scaleFraction; i+=2) {
          double value = min + i * (max-min) / OrientedChart.this.scaleFraction;
          String info = OrientedChart.this.getFormattedValue (value);
          w1 += Common.stringWidth(f, info);
      }
      for (int i = 1; i < OrientedChart.this.scaleFraction; i+=2) {
          double value = min + i * (max-min) / OrientedChart.this.scaleFraction;
          String info = OrientedChart.this.getFormattedValue (value);
          w2 += Common.stringWidth(f, info);
      }
      prefSize.width += Math.max(w1, w2);
     }
     return prefSize;
   }
   /**
     * Draw the receiver.
     */
   public void paintComponent (Graphics g)
   {
    Graphics2D g2d = (Graphics2D)g;

    Color sColor = g.getColor();
    g.setColor (OrientedChart.this.legendColor);
    Font save = g.getFont();
    g.setFont (OrientedChart.this.valueFont);
    FontMetrics fm = g.getFontMetrics ();
    double min = OrientedChart.this.getMinimum();
    double max = OrientedChart.this.getMaximum();
    int h = fm.getHeight();
    int[] pos = getScalePositions(OrientedChart.this.backRectangle()); // scale can be null here
    boolean drawPoint = !(OrientedChart.this.isScale());
    boolean vertical = OrientedChart.this.orientation.isVertical();
    Point p = new Point(getInsets().left, getInsets().top);
    Line2D line = new Line2D.Double (0, 0, 0, 0);
    Dimension depth = OrientedChart.this.getDepth();
    for (int i = 0; i < pos.length; i++) {
      double value = min + i * (max-min) / OrientedChart.this.scaleFraction;
      String info = OrientedChart.this.getFormattedValue (value);
      int w = fm.stringWidth (info);
      if (vertical) {
        double position = -getY() -p.y + pos[i] + depth.height;
        double px = p.x+getSize().width-2-w;
        double py = position+h/4;
        g2d.drawString (info, (float)px, (float)py);
        if (drawPoint) {
          int lx = p.x+getSize().width-getInsets().right-1;
          line.setLine(lx, position, lx, position);
          g2d.draw (line);
        }
      } else {
        double position = -getX() -p.x + pos[i] - depth.width;
        double px = position-w/2;
        double py = p.y + (i % 2 == 0 ? h : 2*h) - 1;
        g2d.drawString (info, (float)px, (float)py);
        if (drawPoint) {
          line.setLine (position, p.y, position, p.y);
          g2d.draw (line);
        }
       }
     }
     g.setFont (save);
     g.setColor (sColor);
   }
 }
 /**
  * The Scale class, used to draw the background scale.
  */
 public class Scale extends com.leuville.borneo.swing.Rectangle
 {
  private transient Point p0 = new Point(0, 0);
  private transient Point p1 = new Point(0, 0);
  /**
   * Draws the receiver on a Graphics.
   */
  public void draw (Graphics g)
  {
    int tab0x = 0, tab0y = 0, tab0nx = 0, tab0ny = 0;
    int tab1x = 0, tab1y = 0, tab1nx = 0, tab1ny = 0;
    int fraction = OrientedChart.this.scaleFraction;
    int[] pos = getScalePositions(this);
    Dimension depth = getDepth();
    boolean is3D = (depth.width != 0 && depth.height != 0);
    g.setColor (OrientedChart.this.legendColor);
    for (int i = 0; i < pos.length; i++) {
      orientation.newLine (this, pos[i], p0, p1);
      g.drawLine (p0.x, p0.y, p1.x, p1.y);
      int x1 = p1.x;
      int y1 = p1.y;
      if (i == 0) {
          tab0x = x1;
          tab0y = y1;
          tab1x = x1-depth.width;
          tab1y = y1+depth.height;
      } else if (i == fraction) {
          tab0nx = x1;
          tab0ny = y1;
          tab1nx = x1-depth.width;
          tab1ny = y1+depth.height;
      }
      g.drawLine (x1, y1, x1-depth.width, y1+depth.height);
      if (orientation.isVertical()) {
         if (is3D)
          g.drawLine (x1-depth.width-2, y1+depth.height, x1-depth.width+2, y1+depth.height);
      } else {
        if (is3D)
          g.drawLine (x1-depth.width, y1+depth.height-2, x1-depth.width, y1+depth.height+2);
      }
    }
    if (is3D) {
      g.drawLine (tab0x, tab0y, tab1x, tab1y);
      g.drawLine (tab1x, tab1y, tab1nx, tab1ny);
      g.drawLine (tab1nx, tab1ny, tab0nx, tab0ny);
      g.drawLine (tab0nx, tab0ny, tab0x, tab0y);
    }
  }
 }
 private class StringLegend extends JComponent
 {
  public Dimension getPreferredSize()
  {
     return new Dimension (Common.stringWidth(OrientedChart.this.legendFont, OrientedChart.this.getLabel(0)), OrientedChart.this.legendFont.getSize());
  }
  public void paintComponent(Graphics g)
  {
    String info = OrientedChart.this.getLabel(0);
    g.setFont (OrientedChart.this.getLegendFont());
    g.setColor(OrientedChart.this.getLegendColor());
    double x = getX() + 0.5*getWidth() - 0.5 * Common.stringWidth(g.getFont(), info);
    double y = getY() + 0.5*getHeight() + g.getFont().getSize() / 4;
    ((Graphics2D)g).drawString (info, (float)x, (float)y);
  }
 }
}


