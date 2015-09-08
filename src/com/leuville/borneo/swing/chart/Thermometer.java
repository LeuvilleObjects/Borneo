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
import java.io.*;
import com.leuville.borneo.swing.*;
import com.leuville.borneo.dataflow.*;

/**
 * <IMG SRC="doc-files/Thermometer.gif">
 *
 * @version 1.1
 */
public class Thermometer extends ChartComponent
{
  /**
   * The paint object used to determine graphics attributes for the bar of the bargraph.
   */
  protected DoubleDataPaint paint = createDoubleDataPaint();

  /**
   * The default paint implementation.
   */
  protected class DefaultDoubleDataPaint implements DoubleDataPaint, Serializable
  {
    /**
     * Returns the foreground color of the enclosing bargraph.
     */
    public Paint getPaint(DoubleDataModel model, double value)
    {
      return getForeground();
    }
  }

  /**
   * Instantiates the paint object.
   */
  protected DoubleDataPaint createDoubleDataPaint()
  {
    return new DefaultDoubleDataPaint();
  }

  /**
   * Set the paint property.
   */
  public void setPaint(DoubleDataPaint paint)
  {
    this.paint = paint;
    repaint();
  }

  /**
   * Get the paint property.
   */
  public DoubleDataPaint getPaint()
  {
    return paint;
  }

  /**
   * The data model.
   */
  protected DoubleDataModel model;

  /**
   * The default state change listener.
   * @see #createStateChangeListener()
   */
  protected StateChangeListener stateChangeListener = createStateChangeListener();

  private class ModelListener implements StateChangeListener, Serializable
  {
    public void stateChanged(StateChangeEvent event)
    {
      Thermometer.this.stateChanged(event);
    }
  }

  /**
   * Called when a model change occurs.
   */
  protected void stateChanged(StateChangeEvent event)
  {
    switch (event.getType()) {
        case StateChangeEvent.RANGE:
          setBounds(getBounds());
          break;
        case StateChangeEvent.VALUE:
        case StateChangeEvent.STATUS:
          repaint();
          break;
      }
  }

  /**
   * Creates the model state change listener.
   */
  protected StateChangeListener createStateChangeListener()
  {
    return new ModelListener();
  }

  /**
   * Returns data model that handles the sliders three
   * fundamental properties: minimum, maximum, value.
   *
   * @see #setModel
   */
  public DoubleDataModel getModel()
  {
    return model;
  }

  /**
   * Set the data model and fires a PropertyChangeEvent.
   * @see DoubleDataModel#removeStateChangeListener(StateChangeListener)
   * @see DoubleDataModel#addStateChangeListener(StateChangeListener)
   * @see javax.swing.JComponent#firePropertyChange(String, Object, Object)
   */
  public void setModel(DoubleDataModel model)
  {
    DoubleDataModel oldModel = this.model;
    if (oldModel != null) {
      oldModel.removeStateChangeListener(stateChangeListener);
    }
    this.model = model;
    if (this.model != null) {
      this.model.addStateChangeListener(stateChangeListener);
    }
    firePropertyChange("model", oldModel, this.model);
  }

  /**
   * Constructs a new Thermometer.
   * min = 0.0, max = 100.0
   */
  public Thermometer ()
  {
    this (0.0, 100.0);
  }

  /**
   * Constructs a new Thermometer.
   * model = a DefaultDoubleDataModel instance
   */
  public Thermometer (double min, double max)
  {
    Common.checkMinMax (min, max);
    spacing = new Insets (0, 0, 0, 0);
    model = new DefaultDoubleDataModel(0.0, Status.HIGH_CONFIDENCE, min, max);
    model.addStateChangeListener(stateChangeListener);
  }

  transient private Arc2D top;
  transient private Arc2D bottom;
  transient private GeneralPath thermometer;
  /**
   * The bar limits.
   */
  transient private Rectangle2D rectangle;
  transient private Rectangle2D legendRect;

  /**
   * Layout the receiver.
   */
  protected void layoutComponent ()
  {
    Insets in = getInsets();
    int x = in.left;
    int y = in.top;
    int w = getWidth()-in.left-in.right;
    int h = getHeight()-in.top-in.bottom;

    double min = getMinimum();
    double max = getMaximum();
    Font f = getValueDrawerFont();
    String minString = Common.roundString (min, max-min, defaultValueDisplayPrecision);
    String maxString = Common.roundString (max, max-min, defaultValueDisplayPrecision);
    int w1 = Common.stringWidth (f, minString);
    int w2 = Common.stringWidth (f, maxString);

    double angle = 70;
    double radius = Math.min(0.4*w, 0.2*h);
    double radW = Math.cos(Math.toRadians(angle))*radius;
    double maxW = Math.max(w1, w2);
    bottom = new Arc2D.Double(x+0.5*(w-2*radius), y+h-2*radius, 2*radius, 2*radius, angle, -360+2*(90-angle), Arc2D.CHORD);
    double diff = maxW-(radius-radW);
    if (diff > 0)
      bottom.setArcByCenter(bottom.getCenterX()+0.5*diff, bottom.getCenterY()+0.5*diff, radius-0.5*diff, bottom.getAngleStart(),
                            bottom.getAngleExtent(), bottom.getArcType());
    thermometer = new GeneralPath();
    Point2D p1 = Utilities.getArcPoint(bottom, 0);
    thermometer.moveTo((float)p1.getX(), (float)p1.getY());
    thermometer.append(bottom, false);
    Point2D p2 = Utilities.getArcPoint(bottom, bottom.getAngleExtent());
    double topRadius = 0.5 * (p1.getX()-p2.getX());
    top = new Arc2D.Double (bottom.getCenterX()-topRadius, y, 2*topRadius, 2*topRadius, 180, -180, Arc2D.OPEN);
//    top = new Arc2D.Double (bottom.getCenterX()-topRadius, y, 2*topRadius, 2*topRadius, 180, -180, Arc2D.CHORD);
    Point2D p3 = Utilities.getArcPoint(top, 0);
    Point2D p4 = Utilities.getArcPoint(top, top.getAngleExtent());
    thermometer.moveTo((float)p2.getX(), (float)p2.getY());
    thermometer.lineTo((float)p3.getX(), (float)p3.getY());
    thermometer.append(top, false);
    thermometer.moveTo((float)p4.getX(), (float)p4.getY());
    thermometer.lineTo((float)p1.getX(), (float)p1.getY());
    thermometer.moveTo((float)p1.getX(), (float)p1.getY());
    thermometer.closePath();
    // p3 p4
    // p2 p1
    int fh = f.getSize();
    double p3y = Math.max(p3.getY(), fh);
    rectangle = new Rectangle2D.Double (p3.getX(), p3y, p4.getX()-p3.getX(), p2.getY()-p3y);
  }

  transient private Rectangle2D bar = new Rectangle2D.Double(0, 0, 0, 0);
  transient private Line2D line = new Line2D.Double(0, 0, 0, 0);

  /**
   * Paint the receiver.
   */
  protected void defaultPaintComponent (Graphics2D g)
  {
    double min = model.getMinimum();
    double max = model.getMaximum();
    double value = model.getValue();
    double hProp = (value-min)/(max-min);
    double h = rectangle.getHeight() * hProp;
    double rx = rectangle.getX();
    double ry = rectangle.getY();
    double rw = rectangle.getWidth();
    bar.setRect(rx, ry + rectangle.getHeight() - h, rw, h);
    g.setPaint(paint.getPaint(model, model.getValue()));
    g.fill(bottom);
    g.fill(bar);
    g.setColor(legendColor);
    g.draw(thermometer);
    h = rectangle.getHeight();
    for (int i = 0; i < 10; i++) {
      double y =  ry + i * h * 0.10;
      line.setLine(rx, y, rx + 0.5 * rw, y);
      g.draw(line);
    }
    double[] val = new double[] {getMaximum(), getMinimum()+0.5*(getMaximum()-getMinimum()), getMinimum()};
    double[] pos = new double[] {0, 0.5, 1.0};
    Font f = getValueDrawerFont();
    int fh = f.getSize();
    g.setFont(f);
    for (int i = 0; i < val.length; i++) {
      String s = Common.roundString (val[i], max-min, defaultValueDisplayPrecision);
      int w = Common.stringWidth (f, s);
      double px = rx - w;
      double py = ry + pos[i] * h + fh/4;
      g.drawString (s, (float)px, (float)py);
    }
    if (showUnit) {
      double cx = bottom.getCenterX();
      double cy = bottom.getCenterY();
      int w = Common.stringWidth (f, unit);
      g.drawString (unit, (float)(cx-0.5*w), (float)(cy+0.5*fh));
      if (drawValue)
        drawValue(g, getColor(0), getFormattedValue(0), cx, cy + valueDrawerFont.getSize());
    } else {
      if (drawValue)
        drawValue(g, getColor(0), getFormattedValue(0), bottom.getCenterX(), bottom.getCenterY());
    }
  }

  /**
   * The unit string.
   */
  protected String unit = "°C";

  /**
   * Set the unit property.
   */
  public void setUnit (String unit)
  {
    this.unit = unit;
    repaint();
  }

  /**
   * Get the unit property.
   */
  public String getUnit()
  {
    return unit;
  }

  /**
   * The showUnit property indicates wheter the unit must be displayed or not.
   */
  protected boolean showUnit = true;

  /**
   * Set the showUnit property.
   */
  public void setShowUnit(boolean showUnit)
  {
    this.showUnit = showUnit;
    repaint();
  }

  /**
   * Get the showUnit property.
   */
  public boolean isShowUnit()
  {
    return showUnit;
  }

  /**
   * Set the model value.
   */
  public void setValue (double aValue)
  {
    model.setValue(aValue);
  }

  /**
   * Returns the current value.
   */
  public double getValue ()
  {
    return model.getValue();
  }

  /**
   * Return the value of a source.
   * @param i The number of the source.
   */
  public Number getValue(int i)
  {
   if (i != 0)
     throw new IllegalArgumentException();
   else
     return new Double(model.getValue());
  }

  /**
  * Return the formatted value of a source as a String.
  * @param i The number of the source.
  */
  public String getFormattedValue(int i)
  {
   if (i != 0)
     throw new IllegalArgumentException();
   else
    return Common.roundString (model.getValue(), model.getMaximum()-model.getMinimum(), defaultValueDisplayPrecision);
  }

  /**
   * Returns the lowest value represented by this bargraph
   */
  public double getMinimum()
  {
    return model.getMinimum();
  }

  /**
   * Set the minimum.
   * A repaint request is generated.
   */
  public void setMinimum (double min)
  {
    double oldMin = model.getMinimum();
    model.setMinimum(min);
    firePropertyChange("minimum", new Double(oldMin), new Double(min));
  }

  /**
   * Returns the highest value represented by this bargraph
   */
  public double getMaximum()
  {
    return model.getMaximum();
  }

  /**
   * Set the maximum.
   * A repaint request is generated.
   */
  public void setMaximum (double max)
  {
    double oldMax = model.getMaximum();
    model.setMaximum(max);
    firePropertyChange("maximum", new Double(oldMax), new Double(max));
  }

  /**
   * Return the minimumSize needed to draw the bar.
   * @return A Dimension(10, 10) object.
   */
   public Dimension getMinimumInsideSize ()
   {
     return new Dimension(10, 10);
   }
}
