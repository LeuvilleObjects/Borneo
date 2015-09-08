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

import com.leuville.borneo.util.*;
import com.leuville.borneo.dataflow.*;
import java.io.*;

/**
 * A TrendStore is a FIFO list of values (double + status).
 *
 * @version 1.1
 */
public class TrendStore implements Serializable
{
 /**
  * The FIFO list.
  */
 protected FIFO list;

 /**
  * Creates a new TrendStore.
  */
 public TrendStore (int initialCapacity)
 {
  list = new FIFO(initialCapacity);
 }

 /**
  * Creates a new TrendStore.
  * capacity = 100
  */
 public TrendStore ()
 {
  this (100);
 }

 /**
  * Return the list size.
  * @see com.leuville.borneo.util.FIFO#getSize()
  */
 public int getSize()
 {
  return list.getSize();
 }

 /**
  * Return the list capacity.
  * @see com.leuville.borneo.util.FIFO#getCapacity()
  */
 public int getCapacity()
 {
  return list.getCapacity();
 }

 /**
  * Set the list capacity.
  * @see com.leuville.borneo.util.FIFO#setCapacity(int)
  */
 public void setCapacity(int capacity)
 {
  list.setCapacity(capacity);
 }

 /**
  * Get an indexed value.
  * @see com.leuville.borneo.util.FIFO#get(int)
  */
 public double getValue(int index)
 {
  return ((DoubleData)list.get(index)).value;
 }

 /**
  * Get an indexed status.
  * @see com.leuville.borneo.util.FIFO#get(int)
  */
 public int getStatus(int index)
 {
  return ((DoubleData)list.get(index)).status;
 }

 /**
  * Get all values.
  */
 public double[] getValue()
 {
  int n = list.size();
  double[] res = new double[n];
  for (int i = 0; i < n; i++) {
    res[i] = getValue(i);
  }
  return res;
 }

 /**
  * Get all status.
  */
 public int[] getStatus()
 {
  int n = list.size();
  int[] res = new int[n];
  for (int i = 0; i < n; i++) {
    res[i] = getStatus(i);
  }
  return res;
 }

 /**
  * Set an indexed value.
  */
 public void setValue (int index, double value)
 {
  ((DoubleData)list.get(index)).value = value;
 }

 /**
  * Set all values and status.
  */
 public void setValue(double[] values, int[] status)
 {
  if (values.length != status.length) {
    throw new IllegalArgumentException ("different sizes");
  } else {
    int n = values.length;
    list.clear();
    list.setCapacity(n);
    for (int i = 0; i < n; i++) {
      list.add (new DoubleData(values[i], status[i]));
    }
  }
 }

 /**
  * Set an indexed status.
  */
 public void setStatus (int index, int status)
 {
  ((DoubleData)list.get(index)).status = status;
 }

 /**
  * Append a new DoubleData (value, status) to the list.
  * @see com.leuville.borneo.util.FIFO#add(Object)
  */
 public void addValue(double value, int status)
 {
  list.add (new DoubleData(value, status));
 }

 /**
  * Append a new DoubleData (value, status = HIGH_CONFIDENCE) to the list.
  * @see #addValue(double, int)
  */
 public void addValue(double value)
 {
  addValue(value, Status.HIGH_CONFIDENCE);
 }
}
