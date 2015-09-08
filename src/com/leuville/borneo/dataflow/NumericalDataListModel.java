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
package com.leuville.borneo.dataflow;

/**
 * This interface describes a list of bounded double data.
 *
 * @version 1.1
 */
public interface NumericalDataListModel
{
 /**
  * Get the size of the list.
  */
 public int getSize();

 /**
  * Set the size of the list.
  */
 public void setSize(int size);

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
 public double getValue(int index);

 /**
  * Replace a value.
  */
 public void setValue(int index, double value);

 /**
  * Get all values.
  */
 public double[] getValue();

 /**
  * Replace all values.
  */
 public void setValue(double[] value);

 /**
  * Get a status.
  * @see Status
  */
 public int getStatus(int index);

 /**
  * Set a status.
  * @see Status
  */
 public void setStatus(int index, int status);

}
