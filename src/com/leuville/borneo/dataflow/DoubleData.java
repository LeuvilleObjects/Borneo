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
 * A DoubleData is a double value associated with a status.
 *
 * @version 1.1
 */
public class DoubleData
{
 /**
  * The double value.
  */
 public double value;

 /**
  * The status.
  * @see Status
  */
 public int status;

 /**
  * Construct a new DoubleData.
  * status = HIGH_CONFIDENCE
  */
 public DoubleData(double value)
 {
  this (value, Status.HIGH_CONFIDENCE);
 }

 /**
  * Construct a new DoubleData.
  */
 public DoubleData(double value, int status)
 {
  this.value = value;
  this.status = status;
 }
}
