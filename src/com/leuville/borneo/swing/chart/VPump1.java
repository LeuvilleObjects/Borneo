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
 * <IMG SRC="doc-files/VPump1.gif">
 * <p>A vertical pump background.
 *
 * @version 1.1
 */
public class VPump1 extends AbstractPump
{
 /**
  * Constructs a new Vpump1.
  */
 public VPump1()
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
  background = new GeneralPath();
  Point2D p1 = Utilities.getArcPoint(temp, 0);
  background.moveTo((float)p1.getX(), (float)p1.getY());
  background.lineTo((float)p1.getX(), (float)y);
  Point2D p2 = Utilities.getArcPoint(temp, 45);
  background.lineTo((float)(p2.getX()), (float)y);
  background.lineTo((float)(p2.getX()), (float)p2.getY());
  background.append(new Arc2D.Double(r, a, 180-a, Arc2D.OPEN), true);
  Point2D p3 = Utilities.getArcPoint(temp, 180);
  background.lineTo((float)p3.getX(), (float)(y+h));
  Point2D p4 = Utilities.getArcPoint(temp, 180+a);
  background.lineTo((float)p4.getX(), (float)(y+h));
  background.lineTo((float)p4.getX(), (float)p4.getY());
  background.append(new Arc2D.Double(r, 180+a, 180-a, Arc2D.OPEN), true);
  background.closePath();
 }
}
