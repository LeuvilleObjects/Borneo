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
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import com.leuville.borneo.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.*;

/**
 * <IMG SRC="doc-files/MultiTrend.gif">
 * <p>A graph which displays data sources with graphical curves.
 * The curve type is specified by an interface @see Curve
 *
 * @version 1.3
 */
public class MultiTrend extends OrientedChart
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
    if (!MultiTrend.this.valueMark)
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
  * Instanciates the scale object.
  */
 protected OrientedChart.Scale createScale()
 {
  return new Scale();
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
  *   creates an AdjustmentListener
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
      MultiTrend.this.lastIndex = MultiTrend.this.scrollbar.getValue()+MultiTrend.this.scrollbar.getVisibleAmount();
      MultiTrend.this.repaint();
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

  /**
   * The list of Paint objects (one per trend).
   */
  protected ArrayList paints = new ArrayList();

  /**
   * Set the paint property.
   */
  public void setPaint(int index, DoubleDataListPaint paint)
  {
    ChartSettings.setObject(paints, index, paint);
  }

  /**
   * Get the paint property.
   */
  public DoubleDataListPaint getPaint(int index)
  {
    try {
      return (DoubleDataListPaint)paints.get(index);
    } catch (IndexOutOfBoundsException e) {
      return null;
    }
  }

  /**
   * Get all paints.
   */
  public DoubleDataListPaint[] getPaint()
  {
    DoubleDataListPaint[] res = new DoubleDataListPaint[paints.size()];
    return (DoubleDataListPaint[])paints.toArray(res);
  }

  /**
   * Set all paints.
   */
  public void setPaint(DoubleDataListPaint[] paints)
  {
    ChartSettings.addArray(this.paints, paints);
  }

  /**
   * The default Paint class.
   */
  protected class DefaultPaint implements DoubleDataListPaint, Serializable
  {
    private int curveNumber;
    /**
     * Set the curve number.
     */
    public void setCurveNumber(int n)
    {
      curveNumber = n;
    }
    /**
     * Returns the color associated to the curveNumber.
     * @see MultiTrend#getColor(int)
     */
    public Paint getPaint(DoubleDataListModel model, double value, int pointIndex)
    {
      return MultiTrend.this.getColor(curveNumber);
    }
  }

  /**
   * The default Paint instance.
   */
  protected DefaultPaint defaultPaint = new DefaultPaint();

  /**
   * The data model.
   */
  protected MultiTrendModel model = null;

  /**
   * This method builds a TableModel to use with a swing JTable object.
   */
  public TableModel getTableModel()
  {
    class TableModel extends AbstractTableModel implements TableModelListener, java.io.Serializable {
      public int getRowCount()
      {
        return model.getCapacity();
      }
      public int getColumnCount()
      {
        return model.getCurveNumber();
      }
      public String getColumnName(int columnIndex)
      {
        return getLabel(columnIndex);
      }
      public Class getColumnClass(int columnIndex)
      {
        return Double.class;
      }
      public boolean isCellEditable(int rowIndex, int columnIndex)
      {
        return true;
      }
      public Object getValueAt(int rowIndex, int columnIndex)
      {
        int curveIndex = columnIndex;
        int pointIndex = rowIndex;
        try {
          return new Double(model.getValue(curveIndex, pointIndex));
        } catch(Exception e) {
          return new Double(0);
        }
      }
      public void setValueAt(Object aValue, int rowIndex, int columnIndex)
      {
        int curveIndex = columnIndex;
        int pointIndex = rowIndex;
        try {
          if (aValue instanceof Number)
           model.setValue(((Number)aValue).doubleValue(), curveIndex, pointIndex);
          else if (aValue instanceof String)
           model.setValue(new Double((String)aValue).doubleValue(), curveIndex, pointIndex);
        } catch (Exception e) {
          throw new IllegalArgumentException("parameter is not a number");
        }
      }
      public void addTableModelListener(TableModelListener l)
      {
        super.addTableModelListener(l);
        model.addTableModelListener(this);
      }
      public void removeTableModelListener(TableModelListener l)
      {
        super.removeTableModelListener(this);
        model.removeTableModelListener(this);
      }
      public void tableChanged(TableModelEvent e)
      {
        fireTableChanged(e);
      }
    };
    return new TableModel();
  }

  /**
   * Get the data model.
   */
  public MultiTrendModel getModel()
  {
    return model;
  }

  /**
   * Set the data model.
   * @see MultiTrendModel#removeTableModelListener(TableModelListener)
   * @see MultiTrendModel#addTableModelListener(TableModelListener)
   */
  public void setModel (MultiTrendModel model)
  {
    if (this.model != null) {
      this.model.removeTableModelListener (tableModelListener);
    }
    this.model = model;
    if (model != null) {
      model.addTableModelListener (tableModelListener);
    }
  }

  /**
   * The model changes listener.
   * @see #createTableModelListener
   */
  protected TableModelListener tableModelListener = createTableModelListener();

  /**
   * Instanciates the model listener.
   */
  protected TableModelListener createTableModelListener()
  {
    return new ModelListener();
  }

  private class ModelListener implements TableModelListener, Serializable
  {
    public void tableChanged(TableModelEvent event)
    {
      switch(event.getType()) {
        case TableModelEvent.INSERT:
        case TableModelEvent.DELETE: // HEADER_ROW is the same value
          modelStructureChanged(event);
          break;
        case TableModelEvent.UPDATE:
          modelDataUpdated(event);
          break;
      }
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
    layoutComponent();
    repaint();
  }

  /**
   * This method is called each time a model data is modified.
   * @see #updateScrollBar()
   */
  protected void modelDataUpdated(TableModelEvent e)
  {
    updateScrollBar();
    repaint();
  }

  /**
   * This method is called each time the model structure is modified.
   * @see #layoutComponent()
   * @see #updateScrollBar()
   */
  protected void modelStructureChanged (TableModelEvent event)
  {
    layoutComponent();
    updateScrollBar();
    repaint();
  }

   /**
    * The array of Curves (one per trend).
    */
   protected Curve[] curves;

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

  private static Font smallFont = new Font ("default", Font.PLAIN, 9);

  /**
   * The width of this objet divided by the number of points.
   */
  protected int step;

  /**
   * The number of displayed points.
   */
  protected int nbPoints = 10;

  /**
   * Scrolling orientation (right, left).
   */
  protected Orientation scroll = Orientation.right;

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
  protected int lastIndex = 100;

  /**
   * Constructs a new MultiTrend.
   * orientation = Orientation.up, scroll = Orientation.right
   */
  public MultiTrend ()
  {
    this (Orientation.up, Orientation.right);
  }

  /**
   * Constructs a new MultiTrend.
   * @param orientation The orientation.
   * @param scroll The scroll orientation.
   */
  public MultiTrend (Orientation orientation, Orientation scroll)
  {
    this (orientation, scroll, false);
  }

  /**
   * Constructs a new MultiTrend.
   * model = a DefaultMultiTrendModel instance.
   *
   * @param orientation The orientation.
   * @param scroll The scroll orientation.
   * @param hasScale A boolean indicating whether a graphical scale is drawn in the background.
   */
  public MultiTrend (Orientation orientation, Orientation scroll, boolean hasScale)
  {
    super (orientation);
    curves = new Curve[] { Curve.LINE, Curve.LINE, Curve.LINE };
    model = new DefaultMultiTrendModel(3, 100);
    model.addTableModelListener(tableModelListener);
    this.scroll = scroll;
    setScale (hasScale);
    setNbPoints (10);
    spacing = new Insets (6, 6, 6, 6);
  }

  /**
   * Set the list of sources.
   */
  public void setCurve (Curve [] curves)
  {
    for (int i = 1; i < curves.length; i++) {
      if (!(curves[0].getDepth().equals(curves[i].getDepth()))) {
        throw new IllegalArgumentException("curves do not have the same depth");
      }
    }
    this.curves = curves;
    setBounds(getBounds());
  }

  /**
   * Get the list of sources.
   */
  public Curve[] getCurve ()
  {
    return curves;
  }

  /**
   * Returns a Curve from an index.
   */
  public Curve getCurve (int i)
  {
    return curves[i % curves.length];
  }

  /**
   * Replaces a Curve using an index.
   */
  public void setCurve (int i, Curve curve)
  {
      if (!(curves[0].getDepth().equals(curve.getDepth()))) {
        throw new IllegalArgumentException("curves do not have the same depth");
      }
    curves[i % curves.length] = curve;
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
   * Set or modifies the number of displayed points.
   * Generates a repaint request.
   * @see #updateStep()
   * @see #updateScrollBar()
   */
  public void setNbPoints (int nbPoints)
  {
    Common.checkNbPoints (nbPoints);
    this.nbPoints = nbPoints;
    updateStep ();
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
   * Set the capacity of all Curves (ie the nb of stored points).
   * The number of points is modified if it is greater than the capacity.
   */
  public void setCapacity (int nb)
  {
    Common.checkNbPoints (nb);
    model.setCapacity(nb);
    setNbPoints (Math.max (getNbPoints(), nb));
    repaint();
  }

  /**
   * Returns the capacity.
   */
  public int getCapacity ()
  {
    return model.getCapacity();
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
   * Updates the double buffer.
   * All curves are entirely re-drawn.
   * Scale is drawn if requested.
   */
  protected void defaultPaintComponent (Graphics2D g)
  {
    com.leuville.borneo.swing.Rectangle b = backRectangle;
    drawScale (g);
    // points draw
    int siz = (int)Math.round((getCurveSize() * lastIndex) / 100);
    int up = Math.min (nbPoints, siz);
    Font save = g.getFont();
    g.setFont (smallFont);
    for (int i = 0, n = model.getCurveNumber(); i < n; i++) {
      TrendModel trendModel = model.getTrendModel(i);
      Curve c = getCurve(i);
      DoubleDataListPaint curvePaint = getPaint(i);
      if (curvePaint == null) {
        defaultPaint.setCurveNumber(i);
        curvePaint = defaultPaint;
      }
      // draw last visible points
      // start from beginning (because of 3D rendering operations)
      for (int j = 0; j < up; j++) {
        try {
          c.draw (g, trendModel, curvePaint, scroll, orientation, b, step, siz-up+j, up-1-j);
        } catch(Exception ex) {
        }
      }
    }
    g.setFont (save);
    if (drawValue)
      drawValue(g);
    super.defaultPaintComponent(g);
  }

  /**
   * Displays values.
   */
  protected void drawValue(Graphics2D g)
  {
    java.awt.Rectangle r = insideBounds;
    java.awt.Rectangle res = new java.awt.Rectangle();
    int n = getNbSource();
    com.leuville.borneo.swing.Rectangle[] rectangles = new com.leuville.borneo.swing.Rectangle[n];
    if (orientation.isHorizontal ()) {
      scroll.getRectangle (res, r, orientation, getMaximum(), getMinimum(), getMaximum(), r.height);
      int h = res.height / n;
      for (int i = 0; i < n; i++) {
        rectangles[i] = new com.leuville.borneo.swing.Rectangle(res.x, res.y+i*h, res.width, h);
      }
    } else {
      scroll.getRectangle (res, r, orientation, getMaximum(), getMinimum(), getMaximum(), r.width);
      int w = res.width / n;
      for (int i = 0; i < n; i++) {
        rectangles[i] = new com.leuville.borneo.swing.Rectangle(res.x+i*w, res.y, w, res.height);
      }
    }
    for (int i = 0; i < n; i++) {
      drawValue(g, getColor(i), getFormattedValue(i), rectangles[i].getCenterX(), rectangles[i].getCenterY());
    }
  }

  /**
   * Returns the minimum displayable value.
   */
  public double getMinimum()
  {
    return model.getMinimum();
  }

  /**
   * Set the minimum displayable value.
   * Generates a repaint request.
   */
  public void setMinimum (double min)
  {
    Common.checkMinMax (min, getMaximum());
    model.setMinimum(min);
  }

  /**
   * Returns the maximum displayable value.
   */
  public double getMaximum()
  {
    return model.getMaximum();
  }

  /**
   * Set the maximum displayable value.
   * Generates a repaint request.
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
    java.awt.Dimension d = new java.awt.Dimension (0, 0);
    for (int i = 0; i < curves.length; i++) {
      Curve c = getCurve(i);
      java.awt.Dimension d2 = c.getDepth ();
      if ((d2.width >= d.width) && (d2.height >= d.height))
         d = d2;
    }
    return d;
  }

  /**
   * Return the minimumSize needed to draw the bar.
   * @return A Dimension(50, 50) object.
   */
   public Dimension getMinimumInsideSize ()
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
   public double getValue(int index)
   {
     double[] val = model.getValue(index);
     return val[val.length-1];
   }

  /**
   * Returns the curve size, ie the curve current number of points.
   */
  public int getCurveSize()
  {
      return model.getSize();
  }
}

