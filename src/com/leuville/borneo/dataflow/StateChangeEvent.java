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

import java.util.*;

/**
 * A StateChangeEvent describes the state change of a numerical data source.
 * It contains: the event date and type (VALUE, RANGE, STATUS).
 *
 * @version 1.1
 */
public class StateChangeEvent extends EventObject
{
 /**
  * The date of the change.
  */
 protected Date date;

 /**
  * The type of change.
  */
 protected int type;

 /**
  * The value change type.
  */
 public final static int VALUE = 0;

 /**
  * The bound change type.
  * Used when the minimum or the maximum is updated.
  */
 public final static int RANGE = 1;

 /**
  * The status change type.
  */
 public final static int STATUS = 2;

 /**
  * Constructs a new StateChangeEvent.
  */
 public StateChangeEvent (Object source, int type, Date date)
 {
  super(source);
  this.type = type;
  this.date = date;
 }

 /**
  * Constructs a new StateChangeEvent.
  */
 public StateChangeEvent (Object source)
 {
  this (source, VALUE, null);
 }

 /**
  * Constructs a new StateChangeEvent.
  */
 public StateChangeEvent (Object source, int type)
 {
  this (source, type, null);
 }

 /**
  * Get the date.
  */
 public Date getDate()
 {
  return date;
 }

 /**
  * Get the type.
  */
 public int getType()
 {
  return type;
 }
}
