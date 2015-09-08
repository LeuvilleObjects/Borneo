/*
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
import java.io.*;
import com.leuville.borneo.swing.*;
import com.leuville.borneo.dataflow.*;

/**
 * <IMG SRC="doc-files/EllipseGraph.gif">
 * A graph which draws a bounded value with a filled ellipse.
 *
 * @version 1.1
 */
public class EllipseGraph extends OrientedChart
{
  /**
   * The paint object used to set Graphics attributes.
   */
  protected DoubleDataPaint paint = createDoubleDataPaint();

  /**
   * A default DoubleDataPaint implementation.
   */
  protected class DefaultDoubleDataPaint implements DoubleDataPaint, Serializable
  {
    /**
     * Returns the EllipseGraph foreground color.
     */
    final public Paint getPaint(DoubleDataModel model, double value)
    {
      return EllipseGraph.this.getForeground();
    }
  }

  /**
   * Returns an instance of DefaultDoubleDataPaint.
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
   * The model.
   */
  protected DoubleDataModel model;

  /**
   * The model StateChangeEvent listener.
   */
  protected StateChangeListener stateChangeListener = createStateChangeListener();

  /**
   * Constructs a new EllipseGraph.
   * min = 0.0, max = 100.0.
   */
  public EllipseGraph ()
  {
    this (0.0, 100.0);
  }

  /**
   * Constructs a new EllipseGraph.
   * orientation = Orientation.right.
   */
  public EllipseGraph (double min, double max)
  {
    this (min, max, Orientation.right);
  }

  /**
   * Constructs a new EllipseGraph.
   * model = instance of DefaultDoubleDataModel.
   */
  public EllipseGraph (double min, double max, Orientation orientation)
  {
    super (orientation);
    Common.checkMinMax (min, max);
    spacing = new Insets (0, 0, 0, 0);
    model = new DefaultDoubleDataModel(min, Status.HIGH_CONFIDENCE, min, max);
    model.addStateChangeListener(stateChangeListener);
  }

  private class ModelListener implements StateChangeListener, Serializable
  {
    final public void stateChanged(StateChangeEvent event)
    {
      switch (event.getType()) {
        case StateChangeEvent.RANGE:
          EllipseGraph.this.setBounds(getBounds());
          break;
        case StateChangeEvent.VALUE:
        case StateChangeEvent.STATUS:
          EllipseGraph.this.repaint();
          EllipseGraph.this.fireStateChanged(event);
          break;
      }
    }
  }

  /**
   * Instanciates the model listener.
   */
  protected StateChangeListener createStateChangeListener()
  {
    return new ModelListener();
  }

  /**
   * Adds a StateChangeListener to the receiver.
   *
   * @param l the StateChangeListener to add
   * @see #fireStateChanged
   * @see #removeStateChangeListener
   */
  public void addStateChangeListener(StateChangeListener l)
  {
    listenerList.add(StateChangeListener.class, l);
  }


  /**
   * Removes a StateChangeListener from the receiver.
   *
   * @param l the StateChangeListener to remove
   * @see #fireStateChanged
   * @see #addStateChangeListener
   */
  public void removeStateChangeListener(StateChangeListener l)
  {
    listenerList.remove(StateChangeListener.class, l);
  }

  /**
   * Send a ChangeEvent, whose source is this object, to
   * each listener.  This method method is called each time
   * a StateChangeEvent is received from the model.
   *
   * @see #addStateChangeListener
   * @see EventListenerList
   */
  protected void fireStateChanged(StateChangeEvent event)
  {
    Object[] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
          if (listeners[i]==StateChangeListener.class) {
              ((StateChangeListener)listeners[i+1]).stateChanged(event);
          }
    }
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
   * Set the model property and fires a PropertyChangeEvent.
   * @see #removeStateChangeListener
   * @see #addStateChangeListener
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
   * Constructs a new EllipseGraph.
   */
  public EllipseGraph (double min, double max, Orientation orientation, boolean hasScale)
  {
    this (min, max, orientation);
    setScale (hasScale);
  }
  /**
   * EllipseGraph border property.
   */
 protected boolean ellipseBorder = false;

 /**
  * Set the ellipseBorder property.
  */
 public void setEllipseBorder(boolean ellipseBorder)
 {
  this.ellipseBorder = ellipseBorder;
  repaint();
 }

 /**
  * Get the ellipseBorder property.
  */
 public boolean isEllipseBorder()
 {
  return ellipseBorder;
 }

 /**
  * The ellipse object.
  */
 transient protected Ellipse2D ellipse;

 /**
  * The arc used to draw the current value.
  */
 transient protected Arc2D valueArc;

 /**
  * Layout the component.
  */
 protected void layoutComponent ()
 {
  super.layoutComponent();
  int x = backRectangle.x;
  int y = backRectangle.y;
  int w = backRectangle.width;
  int h = backRectangle.height;
  ellipse = new Ellipse2D.Double(x, y, w, h);
  valueArc = new Arc2D.Double(x, y, w, h, 0, 0, Arc2D.CHORD);
 }

 /**
  * Modifies the model value.
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
  * The filled property indicates whether the background ellipse
  * must be painted or not.
  */
 protected boolean filled = false;

 /**
  * Get the filled property.
  */
 public boolean isFilled()
 {
  return filled;
 }
 /**
  * Set the filled property.
  */
 public void setFilled(boolean filled)
 {
  this.filled = filled;
  repaint();
 }

 /**
  * Paint the ellipse graph.
  */
 protected void defaultPaintComponent (Graphics2D g)
 {
    double min = model.getMinimum();
    double max = model.getMaximum();
    double value = model.getValue();
    drawScale (g);
    if (filled) {
      g.setColor(getBackground());
      g.fill(ellipse);
    }
    double start = 0, extent = 0;
    double p = (value-min)/(max-min);
    switch (orientation.getWay()) {
      case Orientation.UP:
        start = -90 + p*180;
        extent = -90 - p*180 - start;
        break;
      case Orientation.DOWN:
        start = 90 - p*180;
        extent = 90 + p*180 - start;
        break;
      case Orientation.RIGHT:
        start = 180 - p*180;
        extent = 180 + p*180 - start;
        break;
      case Orientation.LEFT:
        start = 360 - p*360;
        extent = 360 + p*360 - start;
        break;
    }
    valueArc.setAngleStart (start);
    valueArc.setAngleExtent (extent);
    g.setPaint(paint.getPaint(model, model.getValue()));
    g.fill(valueArc);
    if (ellipseBorder) {
      g.setColor(getLegendColor());
      g.draw(ellipse);
    }
    if (drawValue)
        drawValue(g, getColor(0), getFormattedValue(0), ellipse.getCenterX(), ellipse.getCenterY());
    super.defaultPaintComponent (g);
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
