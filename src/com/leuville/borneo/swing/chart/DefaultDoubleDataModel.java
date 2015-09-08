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

import com.leuville.borneo.dataflow.*;
import javax.swing.event.*;
import java.io.Serializable;

/**
 * Default implementation of the DoubleDataModel interface.
 * A double data is a bounded double value with a status.
 * #see com.leuville.dataflow.NumericalDataModel
 *
 * @version 1.1
 */
public class DefaultDoubleDataModel implements DoubleDataModel, Serializable
{
 /**
  * The double value.
  */
 protected double value;

 /**
  * The minimum of all values stored in the list.
  */
 protected double min;

 /**
  * The maximum of all values stored in the list.
  */
 protected double max;

 /**
  * The value status.
  */
 protected int status;

 /**
  * The listeners waiting for model changes.
  */
 protected EventListenerList listenerList = new EventListenerList();

 /**
  * Creates a new DefaultDoubleDataModel.
  * value = 0.0, min = 0.0, max = 100.0, status = HIGH_CONFIDENCE.
  */
 public DefaultDoubleDataModel ()
 {
  this (0.0, Status.HIGH_CONFIDENCE, 0.0, 100.0);
 }

 /**
  * Creates a new DefaultDoubleDataModel.
  * @param value  The value.
  * @param status The status.
  * @param min    The minimum.
  * @param max    The maximum.
  */
 public DefaultDoubleDataModel (double value, int status, double min, double max)
 {
  checkRange(value, min, max);
  checkStatus(status);
  this.value = value;
  this.status = status;
  this.min = min;
  this.max = max;
 }

 /**
  * Returns the minimum.
  */
 public double getMinimum()
 {
  return min;
 }

 /**
  * Returns the maximum.
  */
 public double getMaximum()
 {
  return max;
 }

 /**
  * Returns the value.
  */
 public double getValue()
 {
  return value;
 }

 /**
  * Returns the status.
  */
 public int getStatus()
 {
  return status;
 }

 /**
  * Set the minimum and fires a RANGE StateChangeEvent.
  * @see #fireStateChanged(StateChangeEvent).
  */
 public void setMinimum(double min)
 {
  checkRange(value, min, max);
  this.min = min;
  fireStateChanged(StateChangeEvent.RANGE);
 }

 /**
  * Set the maximum and fires a RANGE StateChangeEvent.
  * @see #fireStateChanged(StateChangeEvent).
  */
 public void setMaximum(double max)
 {
  checkRange(value, min, max);
  this.max = max;
  fireStateChanged(StateChangeEvent.RANGE);
 }

 /**
  * Set the value and fires a VALUE StateChangeEvent.
  * @see #fireStateChanged(StateChangeEvent).
  */
 public void setValue(double value)
 {
  checkRange(value, min, max);
  this.value = value;
  fireStateChanged(StateChangeEvent.VALUE);
 }

 /**
  * Set the status and fires a STATUS StateChangeEvent.
  * @see #fireStateChanged(StateChangeEvent).
  */
 public void setStatus(int status)
 {
  checkStatus(status);
  this.status = status;
  fireStateChanged(StateChangeEvent.STATUS);
 }

 private void checkRange(double value, double min, double max)
 {
  if ((value < min) || (value > max)) {
          throw new IllegalArgumentException("invalid range properties");
  }
 }
 private void checkStatus(int status)
 {
  if (Status.isInvalidStatus(status)) {
          throw new IllegalArgumentException("invalid status property");
  }
 }

 /**
  * Adds a StateChangeListener.
  */
 public void addStateChangeListener(StateChangeListener l)
 {
        listenerList.add(StateChangeListener.class, l);
 }

 /**
  * Removes a StateChangeListener.
  */
 public void removeStateChangeListener(StateChangeListener l)
 {
        listenerList.remove(StateChangeListener.class, l);
 }

 /**
  * Run each ChangeListeners stateChanged() method.
  *
  * @see EventListenerList
  */
 protected void fireStateChanged(int type)
 {
  Object[] listeners = listenerList.getListenerList();
  for (int i = listeners.length - 2; i >= 0; i -=2 ) {
        if (listeners[i] == StateChangeListener.class) {
            StateChangeEvent stateChangeEvent = new StateChangeEvent(this, type);
            ((StateChangeListener)listeners[i+1]).stateChanged(stateChangeEvent);
        }
  }
 }
}
