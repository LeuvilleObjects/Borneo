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
import java.io.*;
import com.leuville.borneo.util.FIFO;
import com.leuville.borneo.swing.*;
import java.beans.*;
import javax.swing.event.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * <IMG SRC="doc-files/Trend.gif">
 * <p>A trend object displays a data source using a graphical curve.
 *
 * @version 1.2
 */
public class Trend extends OrientedChart
{
 /**
  * A Scale class able to draw a grid.
  */
 protected class Scale extends OrientedChart.Scale
 {
  private transient Line2D tmp = new Line2D.Double(0, 0, 0, 0);
  /**
   * Draw the scale.
   * If valueMark is true, a grid is drawn.
   */
  public void draw (Graphics g)
  {
    super.draw(g);
    if (!Trend.this.valueMark)
      return;
    double min = getMinimum();
    double max = getMaximum();
    for (int i = 0; i < nbPoints; i++) {
      scroll.getThinBar (tmp, backRectangle, orientation, max, min, max, step, i);
      ((Graphics2D)g).draw(tmp);
    }
  }
 }

 /**
  * Instanciates the scale object.
  */
 protected OrientedChart.Scale createScale()
 {
  return new Scale();
 }

 /**
  * The valueMark property.
  * If true, a mark is drawn for each value.
  */
 protected boolean valueMark;

 /**
  * Set the valueMark property.
  */
 public void setValueMark(boolean valueMark)
 {
  this.valueMark = valueMark;
  repaint();
 }

 /**
  * Get the valueMark property.
  */
 public boolean getValueMark()
 {
  return valueMark;
 }

 /**
  * Creates the north area.
  * @see OrientedChart#createNorthArea()
  * @see #createScrollBar()
  */
 protected JComponent createNorthArea()
 {
  if (orientation.getWay() == Orientation.DOWN)
    return createScrollBar();
  else
    return super.createNorthArea();
 }

 /**
  * Creates the east area.
  * @see OrientedChart#createEastArea()
  * @see #createScrollBar()
  */
 protected JComponent createEastArea()
 {
  if (orientation.getWay() == Orientation.LEFT)
    return createScrollBar();
  else
    return super.createEastArea();
 }

 /**
  * Creates the south area.
  * @see OrientedChart#createSouthArea()
  * @see #createScrollBar()
  */
 protected JComponent createSouthArea()
 {
  if (orientation.getWay() == Orientation.UP)
      return createScrollBar ();
  else
    return super.createSouthArea();
 }

 /**
  * Creates the west area.
  * @see OrientedChart#createWestArea()
  * @see #createScrollBar()
  */
 protected JComponent createWestArea()
 {
  if (orientation.getWay() == Orientation.RIGHT)
      return createScrollBar ();
  else
    return super.createWestArea();
 }

 /**
  * The scrollbar.
  */
 protected JScrollBar scrollbar;

 /**
  * Creates the scrollbar object:
  *   set the scrollbar instance variable,
  *   creates an AdjustmentListener which calls setLastIndex()
  * @see #setLastIndex(int)
  */
 protected JComponent createScrollBar()
 {
  if (!isScrollBar())
    return null;
  if (orientation.isVertical()) {
    scrollbar  = new JScrollBar(SwingConstants.HORIZONTAL);
  } else {
    scrollbar  = new JScrollBar(SwingConstants.VERTICAL);
  }
  // value, extent, min, max
  scrollbar.setValues(0, 100, 0, 100);
  updateScrollBar();
  scrollbar.addAdjustmentListener(new AdjustmentListener() {
    public void adjustmentValueChanged (AdjustmentEvent e)
    {
      Trend.this.lastIndex = Trend.this.scrollbar.getValue()+Trend.this.scrollbar.getVisibleAmount();
      Trend.this.repaint();
    }
  });
  return scrollbar;
 }

  /**
   * Updates the scrollbar according to internal state.
   * @see #modelDataUpdated(javax.swing.event.TableModelEvent)
   * @see #modelStructureChanged(javax.swing.event.TableModelEvent)
   */
  private void updateScrollBar()
  {
    int n = getCurveSize();
    if (scrollbar != null && n != 0) {
      int amount = (int)Math.round(100 * nbPoints / n);
      scrollbar.setValues(scrollbar.getValue()+scrollbar.getVisibleAmount()-amount, amount, 0, 100);
    }
  }

  protected static Font smallFont = new Font ("default", Font.PLAIN, 9);

  /**
   * The paint object used to determine graphics attributes for the bar of the bargraph.
   */
  protected DoubleDataListPaint paint = createDoubleDataListPaint();

  /**
   * The default paint implementation.
   */
  protected class DefaultDoubleDataListPaint implements DoubleDataListPaint, Serializable
  {
    /**
     * Returns an indexed color according to index parameter.
     * @see ListChart#getColor(int)
     */
    public Paint getPaint(DoubleDataListModel model, double value, int index)
    {
      return getForeground();
    }
  }

  /**
   * Instantiates the paint object.
   */
  protected DoubleDataListPaint createDoubleDataListPaint()
  {
    return new DefaultDoubleDataListPaint();
  }

  /**
   * Set the paint property.
   */
  public void setPaint(DoubleDataListPaint paint)
  {
    this.paint = paint;
    repaint();
  }

  /**
   * Get the paint property.
   */
  public DoubleDataListPaint getPaint()
  {
    return paint;
  }

  /**
   * The data model.
   */
  protected TrendModel model = null;

  /**
   * Get the data model.
   */
  public TrendModel getModel()
  {
    return model;
  }

  /**
   * Set the data model.
   * @see DoubleDataListModel#removeListDataListener(ListDataListener)
   * @see DoubleDataListModel#addListDataListener(ListDataListener)
   */
  public void setModel (TrendModel model)
  {
    if (this.model != null) {
      this.model.removeListDataListener (listDataListener);
    }
    this.model = model;
    if (model != null) {
      model.addListDataListener (listDataListener);
    }
  }

  /**
   * The model changes listener.
   * @see #createListDataListener()
   */
  protected ListDataListener listDataListener = createListDataListener();

  /**
   * Instanciates the model listener.
   */
  protected ListDataListener createListDataListener()
  {
    return new ModelListener();
  }

  private class ModelListener implements ListDataListener, Serializable
  {
    public void contentsChanged(ListDataEvent event)
    {
      Trend.this.trendUpdate(event);
    }
    public void intervalAdded(ListDataEvent event)
    {
      Trend.this.trendUpdate(event);
    }
    public void intervalRemoved(ListDataEvent event)
    {
      Trend.this.trendUpdate(event);
    }
  }

  /**
   * The scrollbar property.
   */
  protected boolean hasScrollBar = true;

  /**
   * Get the scrollbar property.
   */
  public boolean isScrollBar()
  {
    return hasScrollBar;
  }

  /**
   * Set the scrollbar property.
   */
  public void setScrollBar(boolean hasScrollBar)
  {
    this.hasScrollBar = hasScrollBar;
    setBounds(getBounds());
  }

  /**
   * This method is called each time a model data is modified.
   * @see #updateScrollBar()
   */
  protected void trendUpdate(ListDataEvent e)
  {
    updateScrollBar();
    repaint();
  }

  /**
   * The Curve object used to store parameters.
   */
  protected Curve curve;

  /**
   * The distance between two values.
   */
  protected int step;

  /**
   * The scroll direction.
   */
  protected Orientation scroll = Orientation.right;

  /**
   * Number of displayed points.
   */
  protected int nbPoints = 10;

  /**
   * Step state (true if greater than one).
   */
  protected boolean validStep;

  /**
   * The index of the last visible point.
   * This variable is used to associate the Trend with a Scrollbar.
   * Its value must be in [0, 100] range.
   * The 0 value means that the beginning of the trend is displayed.
   * The 100 value means that the end of the trend is displayed (default behaviour).
   */
  protected int lastIndex = -1;

  /**
   * Constructs a new Trend able to display 10 points
   * between 0.0 and 100.0.
   */
  public Trend ()
  {
    this (10, 0.0, 100.0);
  }

  /**
   * Constructs a new Trend without scale.
   * @param nbPoints The number of displayed points.
   * @param min The minimum value.
   * @param max The maximum value.
   */
  public Trend (int nbPoints, double min, double max)
  {
    this (nbPoints, min, max, false);
  }

  /**
   * Constructs a new Trend with a capacity set to 4*nbPoints.
   * model = DefaultTrendModel instance.
   *
   * @param nbPoints The number of displayed points.
   * @param min The minimum value.
   * @param max The maximum value.
   * @param hasScale The scale control.
   */
  public Trend (int nbPoints, double min, double max, boolean hasScale)
  {
    super (Orientation.up);
    Common.checkNbPoints (nbPoints);
    Common.checkMinMax (min, max);
    curve = Curve.LINE;
    // the model is set to store 4x the number of points
    model = new DefaultTrendModel (nbPoints*4, min, max);
    model.addListDataListener (listDataListener);
    this.nbPoints = nbPoints;
    this.scroll = Orientation.right;
    setScale (hasScale);
    validStep = false;
    spacing = new Insets (6, 6, 6, 6);
    setForeground (Color.red);
  }

  /**
   * Modifies the type.
   * A repaint request is generated.
   */
  public void setCurve (Curve curve)
  {
    if (curve == null) {
      throw new IllegalArgumentException("curve parameter is null");
    }
    this.curve = curve;
    setBounds (getBounds());
  }

  /**
   * Returns the type.
   */
  public Curve getCurve ()
  {
    return curve;
  }

  /**
   * Set the orientation.
   */
  public void setOrientation (Orientation orientation)
  {
    if (orientation.isVertical()) {
        if (scroll.isVertical()) {
      scroll = Orientation.right;
        }
    } else {
        if (scroll.isHorizontal()) {
      scroll = Orientation.up;
        }
    }
    super.setOrientation (orientation);
  }

  /**
   * Modifies the scroll orientation.
   * A repaint request is generated.
   */
  public void setScroll (Orientation scroll)
  {
    Common.checkScroll (scroll);
    Common.checkOrientations (orientation, scroll);
    this.scroll = scroll;
    setBounds (getBounds());
  }

  /**
   * Returns the scroll orientation.
   */
  public Orientation getScroll ()
  {
    return scroll;
  }

  /**
   * Layout the receiver.
   */
  protected void layoutComponent ()
  {
    super.layoutComponent ();
    updateStep ();
  }

  /**
   * Modifies the number of displayed points.
   * An approximation is made to get the best visual result according to the size of the trend.
   */
  public void setApproxNbPoints (int n)
  {
    int s;
    if (orientation.isVertical ()) {
      s = backRectangle.width / n;
      n = backRectangle.width / s;
    } else {
      s = backRectangle.height / n;
      n = backRectangle.height / s;
    }
    setNbPoints(n);
  }

  /**
   * Modifies the number of points.
   */
  public void setNbPoints (int nbPoints)
  {
    Common.checkNbPoints (nbPoints);
     this.nbPoints = nbPoints;
    updateStep();
    updateScrollBar();
    repaint();
  }

  /**
   * Returns the number of points.
   */
  public int getNbPoints ()
  {
     return nbPoints;
  }

  /**
   * Set the capacity of the Curve (ie the nb of stored points).
   * The number of points is modified if it is greater than the capacity.
   */
  public void setCapacity (int nb)
  {
    Common.checkNbPoints (nb);
     model.setCapacity (nb);
     setNbPoints (Math.min (getNbPoints(), nb));
    repaint();
  }

  /**
   * Returns the capacity of the Curve.
   */
  public int getCapacity ()
  {
     return model.getCapacity();
  }

  /**
   * Computes the step.
   */
  protected void updateStep ()
  {
    int s;
    if (orientation.isVertical ())
      s = backRectangle.width / nbPoints;
    else
      s = backRectangle.height / nbPoints;
    validStep = (s >= 1);
    step = s;
  }

  /**
   * Adds a new point and generates a repaint request.
   */
  public void addPoint (double aNumber)
  {
    model.addValue(aNumber);
    repaint ();
  }

  /**
   * Adds a new point and generates a repaint request.
   * Fires a PropertyChangeEvent.
   */
  public void setValue (double aValue)
  {
    Double oldValue;
    // if aValue is the first value, getValue() generates an exception
    try {
        oldValue = new Double(getValue());
    } catch (ArrayIndexOutOfBoundsException e) {
        oldValue = null;
    }
    Double newValue = new Double(aValue);
    addPoint (aValue);
    firePropertyChange("value", oldValue, newValue);
  }
  /**
   * Returns the current value (ie value of the last point).
   */
  public double getValue ()
  {
    return model.getValue(model.getSize()-1);
  }

  /**
   * Add an array of points to the Trend.
   */
  public void setValues (double[] values)
  {
    model.setValue(values);
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
       return new Double (getValue());
   }

  /**
   * Set the lastIndex property.
   * A repaint request is generated.
   */
  public void setLastIndex (int lastIndex)
  {
    Common.checkLastIndex (0, 100, lastIndex);
    this.lastIndex = lastIndex;
    repaint();
  }

  /**
   * Get the lastIndex property.
   */
  public int getLastIndex()
  {
      return lastIndex;
  }
  /**
   * Returns the curve size, ie the curve current number of points.
   */
  public int getCurveSize()
  {
      return model.getSize();
  }

  /**
   * Updates the double buffer.
   */
  protected void defaultPaintComponent (Graphics2D g)
  {
    com.leuville.borneo.swing.Rectangle b = backRectangle;
    drawScale (g);
    // points draw
    // draw only last visible points
    int siz = (int)Math.round((getCurveSize() * lastIndex) / 100);
    int up = Math.min (nbPoints, siz);
    Font save = g.getFont();
    g.setFont (smallFont);
    for (int j = 0; j < up; j++) {
      try {
        curve.draw (g, model, paint, scroll, orientation, b, step, siz-up+j, up-1-j);
      } catch (Exception ex) {
      }
    }
    g.setFont (save);
    if (drawValue) {
      drawValue(g, getColor(0), getFormattedValue(0), b.getCenterX(), b.getCenterY());
    }
    super.defaultPaintComponent(g);
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
      return Common.roundString (getValue(), getMaximum()-getMinimum(), defaultValueDisplayPrecision);
   }

  /**
   * Returns the minimum value.
   */
  public double getMinimum()
  {
    return model.getMinimum();
  }

  /**
   * Set the minimum value.
   */
  public void setMinimum (double min)
  {
    Common.checkMinMax (min, getMaximum());
    model.setMinimum(min);
  }

  /**
   * Returns the maximum value.
   */
  public double getMaximum()
  {
    return model.getMaximum();
  }

  /**
   * Set the maximum value.
   */
  public void setMaximum (double max)
  {
    Common.checkMinMax (getMinimum(), max);
    model.setMaximum(max);
  }

  /**
    * Returns the depth of the receiver. This depth is used to draw 3D objects.
    */
  public java.awt.Dimension getDepth ()
  {
    return curve.getDepth();
  }

  /**
   * Return the minimumSize.
   */
   public Dimension getMinimumInsideSize ()
   {
     return new Dimension (50, 50);
   }
}

