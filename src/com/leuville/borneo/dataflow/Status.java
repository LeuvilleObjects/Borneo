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
 * Status describes the state of a data.
 *
 * @version 1.1
 */
public class Status
{
 /**
  * The DISCONNECTED status.
  * The device which has produced the data is probably disconnected.
  */
 public static final int DISCONNECTED = 1;

 /**
  * The HIGH_CONFIDENCE status.
  * The data is reliable.
  */
 public static final int HIGH_CONFIDENCE = 0;

 /**
  * The LOW_CONFIDENCE status.
  * Intermediate state between DISCONNECTED and HIGH_CONFIDENCE.
  */
 public static final int LOW_CONFIDENCE = 2;

 /**
  * Check if the parameter if a valis or invalid status.
  */
 public static boolean isInvalidStatus(int status)
 {
  return ((status != DISCONNECTED) && (status != HIGH_CONFIDENCE) && (status != LOW_CONFIDENCE));
 }
}
