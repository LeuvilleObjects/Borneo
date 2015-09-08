/*
 * @(#) ChartInfo.java version 1.1 date 99/09/22  Laurent Nel
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

/**
 * This interface provides useful services to associate a ChartLegend
 * to any charts implementing it.
 *
 * @version 1.1
 */
public interface ChartInfo
{
 /**
  * Return the name of the chart.
  */
 public String getName();
 /**
  * Return the number of sources of the chart.
  * For instance, it is one in case of a BarGraph, many in case of a PieChart.
  */
 public int getNbSource();
 /**
  * Return the color of a source.
  * @param i The number of the source.
  */
 public java.awt.Color getColor(int i);
 /**
  * Return the value of a source.
  * @param i The number of the source.
  */
// public double getValue(int i);
 /**
  * Return the formatted value of a source as a String.
  * @param i The number of the source.
  */
 public String getFormattedValue(int i);
 /**
  * Return an information string associated to a source.
  * This may be a source name.
  * @param i The number of the source.
  */
 public String getLabel(int i);
 /**
  * Dependency mechanism.
  */
 public void addPropertyChangeListener(java.beans.PropertyChangeListener listener);
 /**
  * Dependency mechanism.
  */
 public void removePropertyChangeListener(java.beans.PropertyChangeListener listener);
}
