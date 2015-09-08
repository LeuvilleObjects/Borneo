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
import java.io.*;
import com.leuville.borneo.swing.*;
import com.leuville.borneo.dataflow.*;

/**
 * <IMG SRC="doc-files/BarGraph.gif">
 * <p>The bar graphs base class.
 *
 * @version 1.1
 */
public class BarGraph extends OrientedChart
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
    final public Paint getPaint(DoubleDataModel model, double value)
    {
      return BarGraph.this.getForeground();
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
   * The model.
   */
  protected DoubleDataModel model;

  /**
   * The default state change listener.
   * @see #createStateChangeListener()
   */
  protected StateChangeListener stateChangeListener = createStateChangeListener();
  /**
   * Pixel coordinate of the origin (0).
   */
  protected int origin;
  /**
   * Graphical command used to draw this bargraph.
   */
  protected RectCommand command = new FillRectCommand ();
  /**
   * Rectangle of the current bar.
   */
  protected java.awt.Rectangle bar;
  /**
    * Constructs a new BarGraph able to represent a value between 0 and 100.
    */
  public BarGraph ()
  {
    this (0.0, 100.0);
  }
  /**
    * Constructs a new BarGraph.
    * @param min lowest value of the bargraph.
    * @param max highest value of the bargraph.
    */
  public BarGraph (double min, double max)
  {
    this (min, max, Orientation.right);
  }
  /**
    * Constructs a new BarGraph.
    * This method instantiates a DefaultDoubleDataModel.
    *
    * @param min lowest value of the bargraph.
    * @param max highest value of the bargraph.
    * @param orientation orientation (up, down, right or left)
    */
  public BarGraph (double min, double max, Orientation orientation)
  {
    super (orientation);
    Common.checkMinMax (min, max);
    this.command = new FillRectCommand ();
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
          BarGraph.this.setBounds(BarGraph.this.getBounds());
          break;
        case StateChangeEvent.VALUE:
        case StateChangeEvent.STATUS:
          BarGraph.this.repaint();
          BarGraph.this.fireStateChanged(event);
          break;
      }
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
   * Adds a StateChangeListener to the bargraph.
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
   * Removes a StateChangeListener from the bargraph.
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
   * Send a ChangeEvent, whose source is this bargrap, to
   * each listener.  This method method is called each time
   * a ChangeEvent is received from the model.
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
    * Constructs a new BarGraph.
    * @param min lowest value of the bargraph.
    * @param max highest value of the bargraph.
    * @param orientation orientation (up, down, right or left)
    * @param hasScale graphical scale
    */
  public BarGraph (double min, double max, Orientation orientation, boolean hasScale)
  {
    this (min, max, orientation);
    setScale (hasScale);
  }
  /**
   * Layout the component.
   */
  protected void layoutComponent ()
  {
    super.layoutComponent();
    bar = null;
  }
  /**
   * Modifies the command object used to draw the bargraph.
   * Generates a repaint request.
   * @param command The new command.
   * @see com.leuville.borneo.swing.RectCommand
   */
  public void setCommand (RectCommand command)
  {
    this.command = command;
    setBounds (getBounds());
  }
  /**
   * Returns the command object used to draw the bargraph.
   */
  final public RectCommand getCommand ()
  {
    return command;
  }
  /**
   * Modify the value represented by the bargraph if it is a different one.
   * @param aValue new double value
   * A repaint request is generated.
   * Fires a PropertyChangeEvent.
   */
  public void setValue (double aValue)
  {
    Double old = new Double(getValue());
    model.setValue(aValue);
    firePropertyChange("value", old, new Double(aValue));
  }
  /**
   * Returns the current value.
   */
  final public double getValue ()
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
    * The filled property.
    * Indicates if the background must be painted with the background color or not.
    */
   protected boolean filled = false;

   /**
    * Get the filled property.
    */
   final public boolean isFilled()
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
   * 1. Draw the scale if needed
   * 2. Draw the bargraph using the offScreen Graphics.
   */
  protected void defaultPaintComponent (Graphics2D g)
  {
    double min = model.getMinimum();
    double max = model.getMaximum();
    double value = model.getValue();
    if (bar == null) {
      origin = orientation.scaleValue (min, max, 0, backRectangle);
      // bar will be modified, so we MUST get our own rectangle here !
      bar = new com.leuville.borneo.swing.Rectangle(backRectangle);
    }
    drawScale (g);
    if (filled) {
      g.setColor(getBackground());
      command.exec (g, backRectangle);
    }
    orientation.update (bar, origin, orientation.scaleValue (min, max, value, backRectangle));
    drawBar(g, bar);
    super.defaultPaintComponent (g);
  }

  /**
   * Draws the bar corresponding to the current model value.
   * 1. get the paint setting using the paint object.
   * 2. draw the bar.
   * 3. draw the value if the draw value property is set to true.
   */
  protected void drawBar(Graphics2D g, java.awt.Rectangle bar)
  {
    g.setPaint(paint.getPaint(model, model.getValue()));
    command.exec (g, bar);
    if (drawValue) {
      drawValue(g, getColor(0), getFormattedValue(0), backRectangle.getCenterX(), backRectangle.getCenterY());
    }
  }

 /**
   * Returns the depth hold by the command attribute
   */
  final public java.awt.Dimension getDepth ()
  {
    return command.getDepth ();
  }

  /**
   * Get the model minimum property.
   */
  final public double getMinimum()
  {
    return model.getMinimum();
  }
  /**
   * Set the minimum and modifies the scale if needed.
   */
  public void setMinimum (double min)
  {
    double oldMin = model.getMinimum();
    model.setMinimum(min);
    firePropertyChange("minimum", new Double(oldMin), new Double(min));
  }
  /**
   * Get the model maximum property.
   */
  final public double getMaximum()
  {
    return model.getMaximum();
  }
  /**
   * Set the maximum and modifies the scale if needed.
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

