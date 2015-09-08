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

import java.awt.Paint;

/**
 * This interface specifies what is needed to determine graphics attribute to draw a double value.
 *
 * @version 1.1
 */
public interface DoubleDataPaint extends java.io.Serializable
{
 /**
  * Return a Paint object used to display a value.
  * @param model  The model which describes the value.
  * @param value  The value to draw.
  */
 public Paint getPaint(DoubleDataModel model, double value);
}
