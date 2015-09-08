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
import javax.swing.event.*;
import javax.swing.*;

/**
 * <IMG SRC="doc-files/PieChart.gif">
 * <p>A PieChart.
 *
 * @version 1.1
 */
public class PieChart extends ChartComponent
{
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
     * Returns the color associated to the curveNumber.
     * @see MultiTrend#getColor(int)
     */
    public Paint getPaint(DoubleDataListModel model, double value, int index)
    {
      return getColor(index);
    }
  }

  /**
   * Creates the default paint object.
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
  protected DoubleDataListModel model;

 /**
   * The default model listener.
   * @see #createListDataListener()
   */
  protected ListDataListener listDataListener = createListDataListener();

  private class ModelListener implements ListDataListener, Serializable
  {
    public void contentsChanged(ListDataEvent event)
    {
      PieChart.this.repaint();
    }
    public void intervalAdded(ListDataEvent event)
    {
      PieChart.this.repaint();
    }
    public void intervalRemoved(ListDataEvent event)
    {
      PieChart.this.repaint();
    }
  }

  /**
   * Creates the model listener.
   */
  protected ListDataListener createListDataListener()
  {
    return new ModelListener();
  }

  /**
   * Set the data model and fires a PropertyChangeEvent.
   * @see DoubleDataListModel#removeListDataListener(StateChangeListener)
   * @see DoubleDataListModel#addListDataListener(StateChangeListener)
   * @see javax.swing.JComponent#firePropertyChange(String, Object, Object)
   */
  public void setModel(DoubleDataListModel model)
  {
    DoubleDataListModel oldModel = this.model;
    if (this.model != null) {
      this.model.removeListDataListener(listDataListener);
    }
    this.model = model;
    if (this.model != null) {
      this.model.addListDataListener(listDataListener);
    }
    firePropertyChange("model", oldModel, this.model);
  }

  /**
   * Return the model.
   */
  public DoubleDataListModel getModel()
  {
    return model;
  }

   /**
    * Manages color and label indexed properties.
    */
   protected ChartSettings settings = new ChartSettings();

   /**
    * Get a label.
    * @see ChartSettings#getLabel(int)
    */
   public String getLabel(int index)
   {
    return settings.getLabel(index);
   }

   /**
    * Get all labels.
    * @see ChartSettings#getLabel()
    */
   public String[] getLabel()
   {
    return settings.getLabel();
   }

   /**
    * Set a label.
    * @see ChartSettings#setLabel(int, String)
    */
   public void setLabel(int index, String label)
   {
    settings.setLabel(index, label);
   }

   /**
    * Set labels.
    * @see ChartSettings#setLabel(String[])
    */
   public void setLabel(String labels[])
   {
    settings.setLabel(labels);
   }

   /**
    * Get a color.
    * @see ChartSettings#getColor(int)
    */
   public Color getColor(int index)
   {
    return settings.getColor(index);
   }

   /**
    * Get all colors.
    * @see ChartSettings#getColor()
    */
   public Color[] getColor()
   {
    return settings.getColor();
   }

   /**
    * Set a color.
    * @see ChartSettings#getColor(int, java.awt.Color)
    */
   public void setColor(int index, Color color)
   {
    settings.setColor(index, color);
   }

   /**
    * Set all colors.
    * @see ChartSettings#getColor(java.awt.Color[])
    */
   public void setColor(Color colors[])
   {
    settings.setColor(colors);
   }

  /**
   * An arc used to draw pies.
   */
  transient protected Arc2D.Double arc;

  /**
   * The arc used to draw the legend.
   */
  transient protected Arc2D.Double legendArc;

  /**
   * The start position as an angle, relative to 3pm.
   */
  protected int startPosition;

  /**
   * The range angle, relative to startPosition.
   */
  protected int range = 360;

  /**
   * Constructs a new PieChart.
   * startPosition is set to 90 and range is set to 360.
   */
  public PieChart ()
  {
    this (90, 360);
  }

  /**
   * Constructs a new PieChart.
   */
  public PieChart (int startPosition, int range)
  {
    this.startPosition = startPosition;
    this.range = range;
    this.model = new DefaultDoubleDataListModel(0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    model.addListDataListener(listDataListener);
  }

  /**
   * Get an indexed value.
   */
  public double getValue(int i)
  {
    return model.getValue(i);
  }

  /**
   * Layout the receiver.
   */
  protected void layoutComponent ()
  {
    super.layoutComponent ();
    java.awt.Rectangle ib = getInsideBounds();
    arc = new Arc2D.Double (ib.x, ib.y, ib.width, ib.height, startPosition, range, Arc2D.PIE);
    int dw = ib.width / 6;
    int dh = ib.height / 6;
    legendArc = new Arc2D.Double (ib.x+dw, ib.y+dh, ib.width-2*dw, ib.height-2*dh,
                                  arc.getAngleStart(), arc.getAngleExtent(), Arc2D.PIE
        );
  }

  /**
   * Updates the double buffer.
   */
  protected void defaultPaintComponent (Graphics2D g)
  {
    double start = startPosition;
    double angle = 0;
    Font f = g.getFont();
    final int n = model.getSize ();
    final double sum = getSum();
    if (sum == 0) {
        arc.setAngleExtent(range);
        g.setColor (getLegendColor());
        g.draw(arc);
    } else {
      Point2D[] legendPoints = new Point2D.Double[n];
      for (int i = 0; i < n; i++) {
        double val = model.getValue(i);
        angle = range *  val / sum;
        arc.setAngleStart(start);
        arc.setAngleExtent(angle);
        legendArc.setAngleStart(start);
        legendArc.setAngleExtent(angle*0.5);
        g.setPaint(paint.getPaint(model, val, i));
        g.fill(arc);
        g.setColor (getLegendColor());
        g.draw(arc);
        if (drawValue) {
          legendPoints[i] = Utilities.getArcPoint(legendArc, angle*0.5);
        }
        start += angle;
      }
      if (drawValue)
        for (int i = 0; i < legendPoints.length; i++) {
          drawValue(g, getColor(i), getFormattedValue(i), legendPoints[i].getX(), legendPoints[i].getY());
        }
    }
    super.defaultPaintComponent(g);
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
   * Returns the startPosition attribute.
   */
  public int getAngleStart ()
  {
    return startPosition;
  }

  /**
   * Returns the range attribute.
   */
  public int getAngleExtent ()
  {
    return range;
  }

  /**
   * Return the minimum.
   */
  public double getMinimum()
  {
    return model.getMinimum();
  }

  /**
   * Return the range attribute.
   */
  public double getMaximum()
  {
    return model.getMaximum();
  }

  /**
   * Set the startPosition attribute.
   * Generates a repaint request.
   */
  public void setAngleStart (int start)
  {
    startPosition = start;
    setBounds (getBounds());
  }

  /**
   * Set the range attribute.
   * Generates a repaint request.
   */
  public void setAngleExtent (int angle)
  {
    range = angle;
    setBounds (getBounds());
  }

  /**
   * Return the minimumSize.
   */
   public Dimension getMinimumSize ()
   {
     return new Dimension(50, 50);
   }

   /**
     * Return the formatted value of a source as a String.
     * @param i The number of the source.
     */
   public String getFormattedValue(int i)
   {
    return Common.roundString (new Double(getValue(i)), getMaximum()-getMinimum(), defaultValueDisplayPrecision);
   }
/*
   public int arcContaining (int x, int y)
   {
    for (int i = 0; i < bars.length; i++) {
      if (bars[i].contains (x, y))
        return i;
    }
    return -1;
   }
*/
  }

