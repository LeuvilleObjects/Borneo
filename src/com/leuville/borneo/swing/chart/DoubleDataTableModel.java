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

import javax.swing.table.*;
import javax.swing.event.*;

/**
 * The model used by any chart component able to display a table of double values.
 *
 * @version 1.1
 */
public interface DoubleDataTableModel
{
 /**
  * Registers a TableModelListener.
  */
 public void addTableModelListener(TableModelListener l);
 /**
  * Unregisters a TableModelListener.
  */
 public void removeTableModelListener(TableModelListener l);

 /**
  * Get the minimum bound.
  */
 public double getMinimum();

 /**
  * Set the minimum bound.
  */
 public void setMinimum(double min);

 /**
  * Get the maximum bound.
  */
 public double getMaximum();

 /**
  * Set the maximum bound.
  */
 public void setMaximum(double max);

 /**
  * Get a value.
  */
 public double getValue(int curveNumber, int dataIndex);

 /**
  * Get the value list of a curve.
  */
 public double[] getValue(int curveNumber);

 /**
  * Get a status.
  */
 public int getStatus(int curveNumber, int dataIndex);

 /**
  * Set a value.
  */
 public void setValue(double value, int curveNumber, int dataIndex);

 /**
  * Set the value list of a curve.
  */
 public void setValue(double[] value, int curveNumber);

 /**
  * Set a status.
  */
 public void setStatus(int status, int curveNumber, int dataIndex);

 /**
  * Add a value with the default status.
  */
 public void addValue(int curveIndex, double value);
}
