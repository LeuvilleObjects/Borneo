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
 * <IMG SRC="doc-files/HPump1.gif">
 * <p>An horizontal pump background.
 *
 * @version 1.1
 */
public class HPump1 extends AbstractPump
{
 /**
  * Constructs a new HPump1.
  */
 public HPump1()
 {
 }

 /**
  * Layout the receiver.
  * This method constructs the pump shape and computes active component bounds.
  */
 protected void layoutComponent()
 {
  double a = 45;
  Insets in = getInsets();
  int x = in.left;
  int y = in.top;
  int w = getWidth()-in.left-in.right;
  int h = getHeight()-in.top-in.bottom;
  double radius = Math.min(w, h);
  Rectangle2D r = new Rectangle2D.Double(x+0.5*(w-radius), y+0.5*(h-radius), radius, radius);
  Arc2D temp = new Arc2D.Double(r, 0, 360, Arc2D.PIE);
  double compRadius = radiusFraction * radius;
  componentBounds = new Rectangle2D.Double(temp.getCenterX()-0.5*compRadius, r.getCenterY()-0.5*compRadius, compRadius, compRadius);
  Point2D p1 = Utilities.getArcPoint(temp, a);
  background =  new GeneralPath();
  background.moveTo((float)p1.getX(), (float)p1.getY());
  background.lineTo((float)(x+w), (float)p1.getY());
  background.lineTo((float)(x+w), (float)r.getY());
  background.lineTo((float)(x+0.5*w), (float)r.getY());
  background.append(new Arc2D.Double(r, 90, 90+a, Arc2D.OPEN), true);
  Point2D p3 = Utilities.getArcPoint(temp, 180+a);
  background.lineTo((float)x, (float)p3.getY());
  background.lineTo((float)x, (float)(r.getY()+r.getHeight()));
  background.lineTo((float)(x+0.5*w), (float)(r.getY()+r.getHeight()));
  background.append(new Arc2D.Double(r, 270, 90+a, Arc2D.OPEN), true);
  background.closePath();
 }
}
