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
 * <IMG SRC="doc-files/HTank1.gif">
 * <p>An horizontal tank.
 *
 * @version 1.1
 */
public class HTank1 extends AbstractTank
{
 /**
  * The right ellipse.
  */
 transient protected Ellipse2D ellipseRight;

 /**
  * The left ellipse.
  */
 transient protected Ellipse2D ellipseLeft;

 /**
  * Constructs a new HTank1.
  */
 public HTank1()
 {
  super(0.95, 0.40, 0.25);
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
  double wpart = radiusFraction;
  Rectangle2D rright = new Rectangle2D.Double(x+w-w*wpart, y, w*wpart, h);
  Rectangle2D rleft = new Rectangle2D.Double(x, y, w*wpart, h);
  ellipseRight = new Ellipse2D.Double(rright.getX(), rright.getY(), rright.getWidth(), rright.getHeight());
  ellipseLeft = new Ellipse2D.Double(rleft.getX(), rleft.getY(), rleft.getWidth(), rleft.getHeight());
  Arc2D right = new Arc2D.Double(rright, 270, -180, Arc2D.OPEN);
  Arc2D left = new Arc2D.Double(rleft, 90, 180, Arc2D.OPEN);
  cylinder = new GeneralPath();
  Point2D p1 = Utilities.getArcPoint(right, 0);
  cylinder.moveTo ((float)p1.getX(), (float)p1.getY());
  cylinder.append(right, true);
  Point2D p2 = Utilities.getArcPoint(right, -180);
  cylinder.lineTo((float)(x+w*wpart), (float)(p2.getY()));
  cylinder.append(left, true);
  cylinder.lineTo ((float)p1.getX(), (float)p1.getY());
  cylinder.closePath();
  p2 = Utilities.getArcPoint(right, -90);
  Point2D p3 = Utilities.getArcPoint(left, 90);
  double rh = h * vFraction;
  double cylinderW = p2.getX()-p3.getX()-2;
  double rw = cylinderW * hFraction;
  componentBounds = new Rectangle2D.Double(p3.getX()+0.5*(cylinderW-rw), p3.getY()-0.5*rh, rw, rh);
 }

 /**
  * Paint the receiver.
  */
 protected void paintComponent (Graphics g)
 {
  Graphics2D g2d = (Graphics2D)g;
  Composite save = g2d.getComposite();
  g2d.setColor(getForeground().brighter());
  g2d.fill(ellipseRight);
  g2d.setColor(getForeground().darker().darker());
  g2d.fill(ellipseLeft);
  g2d.setColor(getForeground());
  g2d.setComposite(alpha);
  g2d.fill(cylinder);
  g2d.setColor(getForeground().darker());
  g2d.setComposite(save);
  g2d.draw(ellipseRight);
  g2d.draw(ellipseLeft);
  g2d.draw(cylinder);
 }
}
