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

import javax.swing.*;
import javax.swing.event.*;

/**
  * This interface describes a model which manages a list of double values.
  * It is used by ListChart.
  *
  * @version 1.1
  */
public interface DoubleDataListModel extends ListModel
{
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
  * Set the minimum bound.
  */
 public void setMaximum(double max);

 /**
  * Get a value.
  */
 public double getValue(int index);

 /**
  * Get a status.
  */
 public int getStatus(int index);

 /**
  * Set a value.
  */
 public void setValue(int index, double value);

 /**
  * Set a status.
  */
 public void setStatus(int index, int status);

 /**
  * Set all values and status.
  */
 public void setValue(double[] values, int[] status);

 /**
  * Set all values.
  */
 public void setValue(double[] values);

 /**
  * Get all values.
  */
 public double[] getValue();

 /**
  * Get all status.
  */
 public int[] getStatus();

 /**
  * Append a value and a status.
  */
 public void addValue(double value, int status);

 /**
  * Append a value with throws default status.
  */
 public void addValue(double value);

}
