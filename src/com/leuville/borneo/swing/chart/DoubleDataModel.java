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

import com.leuville.borneo.dataflow.*;

/**
 * The model used by any chart component able to display a double value.
 *
 * @version 1.1
 */
public interface DoubleDataModel extends NumericalDataModel
{
 /**
  * Registers a StateChangeListener.
  */
 public void addStateChangeListener(StateChangeListener l);

 /**
  * Unregisters a StateChangeListener.
  */
 public void removeStateChangeListener(StateChangeListener l);
}
