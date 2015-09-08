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

import com.leuville.borneo.util.*;
import com.leuville.borneo.dataflow.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

/**
 * Default implementation of the DoubleDataListModel interface.
 *
 * @version 1.1
 */
public class DefaultDoubleDataListModel extends AbstractListModel implements DoubleDataListModel, Serializable
{
 /**
  * The list used to store double values.
  */
 protected List list;

 /**
  * The minimum of all values stored in the list.
  */
 protected double min;

 /**
  * The maximum of all values stored in the list.
  */
 protected double max;

 /**
  * Constructs a new  DefaultDoubleDataListModel.
  *
  * @param initialCapacity  The initial capacity.
  * @param min              The minimum value.
  * @param max              The maximum value.
  */
 public DefaultDoubleDataListModel (int initialCapacity, double min, double max)
 {
  list = createList (initialCapacity);
  for (int i = 0; i < initialCapacity; i++) {
    list.add(new DoubleData(0.0, Status.HIGH_CONFIDENCE));
  }
  this.min = min;
  this.max = max;
 }

 /**
  * Constructs a new DefaultDoubleDataListModel.
  * The capacity is initialized to 100.
  *
  * @param min              The minimum value.
  * @param max              The maximum value.
  */
 public DefaultDoubleDataListModel (double min, double max)
 {
  this (100, min, max);
 }

 /**
  * Instanciates the list object.
  */
 protected List createList (int initialCapacity)
 {
  return new ArrayList(initialCapacity);
 }

 /**
  * Returns the list size.
  */
 public int getSize()
 {
  return list.size();
 }

 /**
  * Get the minimum property.
  */
 public double getMinimum()
 {
  return min;
 }

 /**
  * Set the minimum property and fires a change event.
  * @see javax.swing.AbstractListModel#fireIntervalAdded(Object, int, int)
  */
 public void setMinimum(double min)
 {
  double old = this.min;
  this.min = min;
  if (old != this.min) {
    fireIntervalAdded(this, 0, getSize()-1);
  }
 }

 /**
  * Get the maximum property.
  */
 public double getMaximum()
 {
  return max;
 }

 /**
  * Set the maximum property and fires a change event.
  * @see javax.swing.AbstractListModel#fireIntervalAdded(Object, int, int)
  */
 public void setMaximum(double max)
 {
  double old = this.max;
  this.max = max;
  if (old != this.max) {
    fireIntervalAdded(this, 0, getSize()-1);
  }
 }

 /**
  * Get a value.
  */
 public double getValue(int index)
 {
  return ((DoubleData)list.get(index)).value;
 }

 /**
  * Get a status.
  */
 public int getStatus(int index)
 {
  return ((DoubleData)list.get(index)).status;
 }

 /**
  * Get the value list as an array.
  */
 public double[] getValue()
 {
  final int n = getSize();
  double[] res = new double[n];
  for (int i = 0; i < n; i++) {
    res[i] = getValue(i);
  }
  return res;
 }

 /**
  * Get the status list as an array.
  */
 public int[] getStatus()
 {
  final int n = getSize();
  int[] res = new int[n];
  for (int i = 0; i < n; i++) {
    res[i] = getStatus(i);
  }
  return res;
 }

 /**
  * List model protocol.
  * @return  The value as a Double instance.
  */
 public Object getElementAt(int index)
 {
  return new Double(getValue(index));
 }

 /**
  * Set a value.
  * @see javax.swing.AbstractListModel#fireContentsChanged(Object, int, int)
  */
 public void setValue(int index, double value)
 {
  DoubleData d = (DoubleData)list.get(index);
  d.value = value;
  fireContentsChanged(this, index, index);
 }

 /**
  * Set a status.
  * @see javax.swing.AbstractListModel#fireContentsChanged(Object, int, int)
  */
 public void setStatus(int index, int status)
 {
  DoubleData d = (DoubleData)list.get(index);
  d.status = status;
  fireContentsChanged(this, index, index);
 }

 /**
  * Set values and status.
  * @see javax.swing.AbstractListModel#fireIntervalRemoved(Object, int, int)
  * @see javax.swing.AbstractListModel#fireIntervalAdded(Object, int, int)
  * @see javax.swing.AbstractListModel#fireContentsChanged(Object, int, int)
  */
 public void setValue(double[] values, int[] status)
 {
  if (values.length != status.length) {
    throw new IllegalArgumentException("values and status do not have the same size");
  }
  boolean bigChange = (values.length != getSize());
  if (bigChange) {
    fireIntervalRemoved(this, 0, getSize()-1);
  }
  list.clear();
  final int n = values.length;
  for (int i = 0; i < n; i++) {
    list.add(new DoubleData(values[i], status[i]));
  }
  if (bigChange) {
    fireIntervalAdded(this, 0, n-1);
  } else {
    fireContentsChanged(this, 0, n-1);
  }
 }

 /**
  * Set values.
  * @see #setValue(double[], int[])
  */
 public void setValue(double[] values)
 {
  int[] status = new int[values.length];
  for (int i = 0; i < values.length; i++) {
    status[i] = Status.HIGH_CONFIDENCE;
  }
  setValue(values, status);
 }

 /**
  * Add a value and a status.
  * @see javax.swing.AbstractListModel#fireIntervalAdded(Object, int, int)
  */
 public void addValue(double value, int status)
 {
  list.add(new DoubleData(value, status));
  int i = getSize()-1;
  fireIntervalAdded(this, i, i);
 }

 /**
  * Add a value with HIGH_CONFIDENCE status.
  * @see #addValue(double, int)
  */
 public void addValue (double value)
 {
  addValue(value, Status.HIGH_CONFIDENCE);
 }
}
