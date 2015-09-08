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
 * This interface describes a bounded double data associated with a status.
 *
 * @version 1.1
 */
public interface NumericalDataModel
{
 /**
  * Get the value.
  */
 public double getValue();

 /**
  * Set the value.
  */
 public void setValue(double value);

 /**
  * Get the status.
  * @see Status.
  */
 public int getStatus();

 /**
  * Set the status.
  * @see Status
  */
 public void setStatus(int status);

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
}
