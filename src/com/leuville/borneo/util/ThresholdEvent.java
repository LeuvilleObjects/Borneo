/*
 *
 * Leuville Objects MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Leuville Objects SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

package com.leuville.borneo.util;

/**
 * This object describes a Threshold update.
 * It contains old and new boolean states and a reference on the Threshold itself.
 *
 * @version 1.1
 * @deprecated
 */

public class ThresholdEvent extends java.util.EventObject
{
/**
 * The Threshold oldState.
 */
protected boolean oldState;
/**
 * The Threshold newState.
 */
protected boolean newState;
/**
 * Constructs a new ThresholdEvent.
 */
public ThresholdEvent (Object source, boolean oldState, boolean newState)
{
  super (source);
  this.oldState = oldState;
  this.newState = newState;
}
/**
 * Returns the old state.
 */
public boolean getOldState ()
{
  return oldState;
}
/**
 * Returns the new state.
 */
public boolean getNewState ()
{
  return newState;
}
}
