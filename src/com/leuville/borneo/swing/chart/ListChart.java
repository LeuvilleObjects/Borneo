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
import java.beans.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * This class is abstract and must be derived.
 * It provides common mechanisms for charts able to displays multiple data sources.
 *
 * @version 1.1
 */
public abstract class ListChart extends OrientedChart
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
     * Returns an indexed color according to index parameter.
     * @see ListChart#getColor(int)
     */
    public Paint getPaint(DoubleDataListModel model, double value, int index)
    {
      return ListChart.this.getColor(index);
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
  protected DoubleDataListModel model;

  /**
   * Get the data model.
   */
  public DoubleDataListModel getModel()
  {
    return model;
  }

  /**
   * Set the data model.
   * @see DoubleDataListModel#removeListDataListener(ListDataListener)
   * @see DoubleDataListModel#addListDataListener(ListDataListener)
   */
  public void setModel (DoubleDataListModel model)
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
      ListChart.this.repaint();
    }
    public void intervalAdded(ListDataEvent event)
    {
      ListChart.this.setBounds(ListChart.this.getBounds());
    }
    public void intervalRemoved(ListDataEvent event)
    {
      ListChart.this.setBounds(ListChart.this.getBounds());
    }
  }

   /**
    *
    */
   //abstract protected void initSources();

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
    * Return the info associated to the source.
    * @param i The number of the source.
    */
   public String getDecorationString(int i)
   {
     return getLabel(i);
   }

  /**
   * Constructs a new ListChart.
   * orientation = Orientation.up, min = 0.0, max = 100.0
   */
  protected ListChart ()
  {
    this (Orientation.up, 4, 0.0, 100.0);
  }

  /**
   * Constructs a new ListChart.
   * model = a DefaultDoubleDataListModel instance.
   */
  protected ListChart (Orientation orientation, int nbSource, double min, double max)
  {
    super (orientation);
    model = new DefaultDoubleDataListModel(nbSource, min, max);
    model.addListDataListener(listDataListener);
    setColor(new Color[nbSource]);
    setLabel(new String[nbSource]);
  }

/*

   public Object clone() throws CloneNotSupportedException
   {
     ListChart copy = (ListChart)super.clone();
    copy.model = (DefaultDoubleDataListModel) model.clone();
    copy.listDataListener = (ListDataListener)listDataListener.clone();
    copy.model.addListDataListener(copy.listDataListener);
     copy.settings = (ChartSettings) settings.clone ();
     return copy;
   }
*/

   /**
     * Return the formatted value of a source as a String.
     * @param i The number of the source.
     */
   public String getFormattedValue(int i)
   {
    return Common.roundString (new Double(getValue(i)), getMaximum()-getMinimum(), defaultValueDisplayPrecision);
   }

   /**
    * Get an indexed value.
    */
   public double getValue(int index)
   {
     return model.getValue(index);
   }

  /**
   * Returns min.
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
    com.leuville.borneo.swing.chart.Common.checkMinMax (min, getMaximum());
    model.setMinimum(min);
  }

  /**
   * Returns max.
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
    com.leuville.borneo.swing.chart.Common.checkMinMax (getMinimum(), max);
    model.setMaximum(max);
  }

}
