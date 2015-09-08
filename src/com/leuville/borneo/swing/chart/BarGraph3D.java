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

import com.leuville.borneo.swing.Fill3DRectCommand;

/**
 * <IMG SRC="doc-files/BarGraph3D.gif">
 * <p>A 3D BarGraph.
 *
 * @version 1.1
 */
public class BarGraph3D extends BarGraph
{
  /**
    * Constructs a new BarGraph3D able to represent a value between 0 and 100.
    *
    */
  public BarGraph3D ()
  {
    this (0.0, 100.0);
  }
  /**
    * Constructs a new BarGraph3D.
    * @param min lowest value of the bargraph.
    * @param max highest value of the bargraph.
    */
  public BarGraph3D (double min, double max)
  {
    this (min, max, Orientation.right);
  }
  /**
    * Constructs a new BarGraph3D.
    * @param min lowest value of the bargraph.
    * @param max highest value of the bargraph.
    * @param orientation orientation (up, down, right or left)
    */
  public BarGraph3D (double min, double max, Orientation orientation)
  {
    super (min, max, orientation);
    setCommand (new Fill3DRectCommand ());
  }
}

