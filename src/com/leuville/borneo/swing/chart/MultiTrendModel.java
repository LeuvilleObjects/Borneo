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

/**
 * The model used by MultiTrend.
 *
 * @version 1.1
 */
public interface MultiTrendModel extends DoubleDataTableModel
{
 /**
  * Returns the number of curved.
  */
 public int getCurveNumber();

 /**
  * Returns the size of the biggest curve.
  */
 public int getSize();

 /**
  * Return the capacity.
  */
 public int getCapacity();

 /**
  * Set the capacity.
  */
 public void setCapacity(int capacity);

 /**
  * Get the TrendModel of a curve.
  */
 public TrendModel getTrendModel(int index);
}
