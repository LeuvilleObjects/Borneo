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
import com.leuville.borneo.swing.*;
import javax.swing.*;

/**
 * ChartLegend provides a way to build generic active legends for charts.
 * Source charts have to implement the ChartInfo interface.
 * The ChartLegend must be registered as a PropertyChangeListener of it's source chart.
 * On the above example, a ChartLegend is linked to an HistoGraph.
 * The ChartLegend object is displayed on the right.
 * <P><IMG SRC="./images/charts/ChartLegend.gif"></P>
 *
 * @version 1.1
 */
public class ChartLegend extends JPanel implements PropertyChangeListener
{
 /**
  * The SourceLegend inner class defines the canvas element that represents
  * one single chart source.
  */
 public class SourceLegend extends JComponent
 {
  /**
   * The preferred size.
   */
  private Dimension prefSize;
  /**
   * The color rectangle size.
   */
  private Dimension colorRectSize = new Dimension (5, 5);
  private int spacing = 4;
  /**
   * The insets (ie borders sizes).
   */
  private Insets insets = new Insets (1, 1, 1, 1);
  /**
   * The source number.
   */
  private int sourceNumber;
  /**
   * Construct a new SourceLegend object.
   * @param sourceNumber The source number.
   */
  public SourceLegend (int sourceNumber)
  {
    this.sourceNumber = sourceNumber;
    this.setBorder (null);
    this.setFont(new Font ("default", Font.PLAIN, 12));
    this.prefSize = new Dimension (insets.left+insets.right, insets.top+insets.bottom);
    Font font = this.getFont();
    int h = font.getSize();
    colorRectSize.height = Math.max (h, colorRectSize.height);
    colorRectSize.width = colorRectSize.height;
    int w = com.leuville.borneo.swing.chart.Common.stringWidth(font, chart.getLabel(sourceNumber)+" : XXXXXX");
    w += colorRectSize.width+spacing;
    prefSize.height += h;
    prefSize.width += w;
    this.setSize (prefSize);
  }
  /**
   * Paint the contents.
   */
  protected void paintComponent (Graphics g)
  {
    Font save = g.getFont();
    g.setFont (this.getFont());
    String info = chart.getLabel (sourceNumber);
    String value = chart.getFormattedValue (sourceNumber);
    if (info != null) {
      value = info + " : " + value;
    }
    FontMetrics fm = g.getFontMetrics();
    int h = fm.getHeight();
    int w = fm.stringWidth (value);
    colorRectSize.height = Math.max (h, colorRectSize.height);
    colorRectSize.width = colorRectSize.height;
    int width = insets.left+colorRectSize.width+spacing+w+insets.right;
    int height = insets.top+colorRectSize.height+insets.bottom;
    prefSize = new Dimension (width, height);
    g.setColor (chart.getColor(sourceNumber));
    g.fillRect (insets.left, insets.top, colorRectSize.width, colorRectSize.height);
    g.setColor (Color.black);
    g.drawString (value, insets.left+colorRectSize.width+spacing, insets.top+h-(height-h)/2);
    g.setFont (save);
  }
  /**
   * Return the preferred size.
   */
  public Dimension getMinimumSize ()
  {
    return prefSize;
  }
 }
 /**
  * The associated chart object, used through the ChartInfo interface.
  */
 protected ChartInfo chart;
 /**
  * The array of SourceLegend canvases.
  */
 protected SourceLegend[] legend;
 /**
  * The label.
  */
 protected JLabel label;
 /**
  * Construct a new ChartLegend.
  * The default orientation is Orientation.down.
  */
 public ChartLegend ()
 {
   orientation = Orientation.down;
 }
 /**
  * Construct a new ChartLegend.
  * The default orientation is Orientation.down.
  */
 public ChartLegend (ChartInfo chart)
 {
   this (chart, Orientation.down);
 }
 /**
  * Construct a new ChartLegend.
  */
 public ChartLegend (ChartInfo chart, Orientation orientation)
 {
  setChartInfo(chart);
  setOrientation (orientation);
  setLabelBackground (getBackground());
  setLegendBackground (getBackground());
 }
 /**
  * Set the chartInfo property.
  */
 public void setChartInfo(ChartInfo chart)
 {
  if (this.chart != null) {
    this.chart.removePropertyChangeListener (this);
  }
  this.chart = chart;
  chart.addPropertyChangeListener (this);
  layoutComponent();
 }

 /**
  * Get the chartInfo property.
  */
 public ChartInfo getChartInfo()
 {
  return chart;
 }

 /**
  * Set the orientation property.
  */
 public void setOrientation(Orientation orientation)
 {
  this.orientation = orientation;
  layoutComponent();
 }

 /**
  * Get the orientation property.
  */
 public Orientation getOrientation()
 {
  return orientation;
 }

 /**
  * The orientation property.
  *
  * @see Orientation
  */
 protected Orientation orientation;

 /**
  * Layout the receiver.
  */
 protected void layoutComponent()
 {
  removeAll();
  int n = chart.getNbSource ();
  legend = new SourceLegend [n];
  if (orientation.isVertical()) {
    setLayout (new GridLayout (n+1, 1));
  } else {
    setLayout (new GridLayout (1, n+1));
  }
  label = new JLabel (chart.getName());
  add (label, 0);
  if ((orientation == Orientation.up) || (orientation == Orientation.left)) {
    for (int i = n; i > 0; i--) {
      legend[n-i] = new SourceLegend (i-1);
      add (legend[n-i], -1);
    }
  } else {
    for (int i = 0; i < n; i++) {
      legend[i] = new SourceLegend (i);
      add (legend [i], -1);
    }
  }
 }
 /**
  * Repaint the entire legend.
  */
 public void propertyChange (PropertyChangeEvent e)
 {
   for (int i = 0; i < legend.length; i++) {
     legend[i].repaint();
   }
 }
 /**
  * Set the label background color.
  */
 public void setLabelBackground(Color color)
 {
    label.setBackground(color);
 }
 /**
  * Get the label background color.
  */
 public Color getLabelBackground()
 {
    return label.getBackground();
 }
 /**
  * Set the legend panel background color.
  */
 public void setLegendBackground(Color color)
 {
    for (int i = 0; i < legend.length; i++) {
  legend[i].setBackground (color);
  legend[i].repaint();
    }
 }
 /**
  * Get the legend panel background color.
  */
 public Color getLegendBackground()
 {
    return (legend.length == 0 ? getBackground() : legend[0].getBackground());
 }}

