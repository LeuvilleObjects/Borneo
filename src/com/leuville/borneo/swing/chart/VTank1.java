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
 * <IMG SRC="doc-files/VTank1.gif">
 * <p>A vertical tank.
 *
 * @version 1.1
 */
public class VTank1 extends AbstractTank
{
 /**
  * The top ellipse.
  */
 transient protected Ellipse2D ellipseUp;

 /**
  * The bottom ellipse.
  */
 transient protected Ellipse2D ellipseDown;

 /**
  * Constructs a new VTank1.
  */
 public VTank1()
 {
  super(0.40, 0.95, 0.25);
 }

 /**
  * Layout the receiver.
  */
 protected void layoutComponent()
 {
  Insets in = getInsets();
  int x = in.left;
  int y = in.top;
  int w = getWidth()-in.left-in.right;
  int h = getHeight()-in.top-in.bottom;
  double hpart = radiusFraction;
  Rectangle2D rup = new Rectangle2D.Double(x, y, w, hpart * h);
  Rectangle2D rdown = new Rectangle2D.Double(x, y+h-h*hpart, w, hpart * h);
  ellipseUp = new Ellipse2D.Double(rup.getX(), rup.getY(), rup.getWidth(), rup.getHeight());
  ellipseDown = new Ellipse2D.Double(rdown.getX(), rdown.getY(), rdown.getWidth(), rdown.getHeight());
  Arc2D up = new Arc2D.Double(rup, 0, -180, Arc2D.OPEN);
  Arc2D down = new Arc2D.Double(rdown, -180, 180, Arc2D.OPEN);
  cylinder = new GeneralPath();
  Point2D p1 = Utilities.getArcPoint(up, 0);
  cylinder.moveTo ((float)p1.getX(), (float)p1.getY());
  cylinder.append(up, true);
  Point2D p2 = Utilities.getArcPoint(up, -180);
  cylinder.lineTo((float)(p2.getX()), (float)(p1.getY()+h-h*hpart));
  cylinder.append(down, true);
  cylinder.lineTo ((float)p1.getX(), (float)p1.getY());
  cylinder.closePath();
  p2 = Utilities.getArcPoint(up, -90);
  Point2D p3 = Utilities.getArcPoint(down, 90);
  double rw = w * hFraction;
  double cylinderH = p3.getY() - p2.getY() - 2;
  double rh = cylinderH * vFraction;
  componentBounds = new Rectangle2D.Double(p2.getX()-0.5*rw, p2.getY()+0.5*(cylinderH-rh), rw, rh);
 }

 /**
  * Paint the receiver.
  */
 protected void paintComponent (Graphics g)
 {
  Graphics2D g2d = (Graphics2D)g;
  Composite save = g2d.getComposite();
  g2d.setColor(getForeground().brighter());
  g2d.fill(ellipseUp);
  g2d.setColor(getForeground().darker().darker());
  g2d.fill(ellipseDown);
  g2d.setColor(getForeground());
  g2d.setComposite(alpha);
  g2d.fill(cylinder);
  g2d.setColor(getForeground().darker());
  g2d.setComposite(save);
  g2d.draw(ellipseUp);
  g2d.draw(ellipseDown);
  g2d.draw(cylinder);
 }
}
