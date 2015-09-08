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

import java.util.*;
import java.io.*;
import javax.swing.*;
import com.leuville.borneo.dataflow.*;

/**
 * Default implementation of the DoubleDataListModel interface.
 *
 * @version 1.1
 */
public class DefaultTrendModel extends AbstractListModel implements TrendModel, Serializable
{
 /**
  * The point list.
  */
 protected TrendStore list;

 /**
  * The minimum for each point.
  */
 protected double min;

 /**
  * The maximum for each point.
  */
 protected double max;

 /**
  * Constructs a new  DefaultTrendModel.
  *
  * @param initialCapacity  The initial capacity.
  * @param min              The minimum value.
  * @param max              The maximum value.
  */
 public DefaultTrendModel (int initialCapacity, double min, double max)
 {
  list = new TrendStore (initialCapacity);
  this.min = min;
  this.max = max;
 }

 /**
  * Constructs a new  DefaultTrendModel.
  * capacity = 100.
  *
  * @param min              The minimum value.
  * @param max              The maximum value.
  */
 public DefaultTrendModel (double min, double max)
 {
  this (100, min, max);
 }

 /**
  * Set the capacity and fires a change event.
  * @see javax.swing.AbstractListModel#fireContentsChanged(Object, int, int)
  */
 public void setCapacity (int capacity)
 {
  list.setCapacity(capacity);
  fireContentsChanged(this, 0, getSize()-1);
 }

 /**
  * Get the list capacity.
  * @see TrendStore#getCapacity()
  */
 public int getCapacity()
 {
  return list.getCapacity();
 }

 /**
  * Get the list size.
  * @see TrendStore#getSize()
  */
 public int getSize()
 {
  return list.getSize();
 }

 /**
  * Get the minimum.
  */
 public double getMinimum()
 {
  return min;
 }

 /**
  * Set the minimum and fires an event.
  * @see javax.swing.AbstractListModel#fireContentsChanged(Object, int, int)
  */
 public void setMinimum(double min)
 {
  this.min = min;
  fireContentsChanged(this, 0, getSize()-1);
 }

 /**
  * Get the maximum.
  */
 public double getMaximum()
 {
  return max;
 }

 /**
  * Set the maximum and fires an event.
  * @see javax.swing.AbstractListModel#fireContentsChanged(Object, int, int)
  */
 public void setMaximum(double max)
 {
  this.max = max;
  fireContentsChanged(this, 0, getSize()-1);
 }

 /**
  * Get a value.
  */
 public double getValue(int index)
 {
  return list.getValue(index);
 }

 /**
  * Get all values.
  */
 public double[] getValue()
 {
  return list.getValue();
 }

 /**
  * Get all status.
  */
 public int[] getStatus()
 {
  return list.getStatus();
 }

 /**
  * Get a status.
  */
 public int getStatus(int index)
 {
  return list.getStatus(index);
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
  * Set all values and status and fires an event.
  * @see javax.swing.AbstractListModel#fireContentsChanged(Object, int, int)
  */
 public void setValue(double[] values, int[] status)
 {
  list.setValue(values, status);
  fireContentsChanged(this, 0, values.length-1);
 }

 /**
  * Set all values and fires an event.
  * Status are set to HIGH_CONFIDENCE.
  * @see setValue(double[], int[])
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
  list.addValue(value, status);
  int i = getSize()-1;
  fireIntervalAdded(this, i, i);
 }

 /**
  * Add a value whose status is HIGH_CONFIDENCE.
  * @see addValue(double, int)
  */
 public void addValue (double value)
 {
  addValue(value, Status.HIGH_CONFIDENCE);
 }

 /**
  * Set a value and fires an event.
  * @see javax.swing.AbstractListModel#fireContentsAdded(Object, int, int)
  */
 public void setValue(int index, double value)
 {
  list.setValue(index, value);
  fireContentsChanged(this, index, index);
 }

 /**
  * Set a status and fires an event.
  * @see javax.swing.AbstractListModel#fireContentsAdded(Object, int, int)
  */
 public void setStatus(int index, int status)
 {
  list.setStatus(index, status);
  fireContentsChanged(this, index, index);
 }

}
