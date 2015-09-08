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
 * Abstract base class for tank backgrounds.
 *
 * @version 1.1
 */
abstract public class AbstractTank extends Background
{
 /**
  * The cylinder shape.
  */
 transient protected GeneralPath cylinder;

 /**
  * Semi-transparency effect.
  */
 static protected AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);

 /**
  * Constructs an AbstractTank.
  * @param  hFraction       The amount of horizontal space reserved to the active component.
  * @param  vFraction       The amount of vertical space reserved to the active component.
  * @param  radiusFraction  The radius of ellipses, expressed as an amount of horizontal of vertical space.
  */
 protected AbstractTank (double hFraction, double vFraction, double radiusFraction)
 {
  this.hFraction = hFraction;
  this.vFraction = vFraction;
  this.radiusFraction = radiusFraction;
 }

 /**
  * The amount of horizontal space reserved to the active component.
  */
 protected double hFraction = 0.50;

 /**
  * The amount of vertical space reserved to the active component.
  */
 protected double vFraction = 0.50;

 /**
  * Set the hFraction property.
  */
 public void setHFraction(double hFraction)
 {
  this.hFraction = hFraction;
  layoutComponent();
  repaint();
 }

 /**
  * Get the hFraction property.
  */
 final public double getHFraction()
 {
  return hFraction;
 }

 /**
  * Set the vFraction property.
  */
 public void setVFraction(double vFraction)
 {
  this.vFraction = vFraction;
  layoutComponent();
  repaint();
 }

 /**
  * Get the vFraction property.
  */
 final public double getVFraction()
 {
  return vFraction;
 }

 /**
  * The radius of ellipses, expressed as an amount of horizontal of vertical space.
  */
 protected double radiusFraction = 0.25;

 /**
  * Set the radiusFraction property.
  */
 public void setRadiusFraction(double radiusFraction)
 {
  this.radiusFraction = radiusFraction;
  layoutComponent();
  repaint();
 }

 /**
  * Get the radiusFraction property.
  */
 final public double getRadiusFraction()
 {
  return radiusFraction;
 }
}
