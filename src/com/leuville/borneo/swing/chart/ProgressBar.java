/*
 *
 * Copyright (c) 1997, 1998 Leuville Objects All Rights Reserved.
 *
 * Leuville Objects MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Leuville Objects SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

package com.leuville.borneo.swing.chart;

/**
 * A ProgressBar.
 *
 * @version 1.1
 */
public class ProgressBar extends BarGraph
{
  /**
   * Constructs a new ProgressBar varying from 0 to 100
   * with a right orientation and a scale.
   */
  public ProgressBar ()
  {
    super (0.0, 100.0, Orientation.right, true);
    setCommand (new com.leuville.borneo.swing.FillRectCommand ());
  }
}

