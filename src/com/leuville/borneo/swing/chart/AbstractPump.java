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

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import com.leuville.borneo.swing.*;

/**
 * Abstract base class for pump backgrounds.
 *
 * @version 1.1
 */
abstract public class AbstractPump extends Background
{
 /**
  * The path used to draw the pump shape.
  */
 transient protected GeneralPath background = new GeneralPath();

 /**
  * The fraction of background bounds reserved to the active component.
  */
 protected double radiusFraction = 0.50;

 /**
  * Set the radiusFraction property.
  */
 public void setRadiusFraction(double radiusFRaction)
 {
  this.radiusFraction = radiusFraction;
  repaint();
 }
 /**
  * Get the radiusFraction property.
  */
 final public double getRadiusFraction()
 {
  return radiusFraction;
 }

 /**
  * Paints the pump shape.
  */
 protected void paintComponent(Graphics g)
 {
  super.paintComponent(g);
  Graphics2D g2d = (Graphics2D)g;
  g2d.setColor(getForeground());
  g2d.fill(background);
 }
}
