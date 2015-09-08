/*
 * @(#) @(#) version 1.1 date 99/09/22  Laurent Nel
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
import javax.swing.*;
import com.leuville.borneo.swing.*;

/**
 * This class associates a Chart with a generic ChartLegend in a Panel.
 * The chart must implement the ChartInfo interface.
 *
 * @version 1.1
 */
public class Chart extends JPanel
{
 /**
  * The ChartComponent object.
  */
 protected ChartComponent chart;
 /**
  * The ChartLegend object.
  */
 protected ChartLegend legend;
 /**
  * Construct a new Chart formatted with a BorderLayout.
  * The chart is added to the center.
  * @param chart    The ChartComponent object.
  * @param legendPosition  The legend position expressed as a BorderLayout constraint.
  */
 public Chart (ChartComponent chart, String legendPosition)
 {
   this.chart = chart;
   setLayout (new java.awt.BorderLayout());
   init (chart, legendPosition);
   add (chart, java.awt.BorderLayout.CENTER);
   setBorder (BorderFactory.createEtchedBorder());
 }
 /**
  * Initializes the ChartLegend object.
  *
  * @param  chart           The chart component.
  * @param  legendPosition  The legend position (BorderLayout constant).
  */
 protected void init (ChartComponent chart, String legendPosition)
 {
   Orientation legendOrientation;
   if (legendPosition.equals(java.awt.BorderLayout.NORTH)
    || legendPosition.equals(java.awt.BorderLayout.SOUTH)) {
      legendOrientation = Orientation.right;
   } else {
      legendOrientation = Orientation.down;
   }
   legend = new ChartLegend (chart, legendOrientation);
   legend.setBorder (null);
   add (legend, legendPosition);
   legend.setLabelBackground (getBackground());
   legend.setLegendBackground (getBackground());
 }
 /**
  * Returns the chart reference.
  */
 public ChartComponent getChartComponent()
 {
    return chart;
 }
 /**
  * Return the chart legend reference.
  */
 public ChartLegend getChartLegend()
 {
    return legend;
 }
}
