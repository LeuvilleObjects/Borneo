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

import com.leuville.borneo.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * <IMG SRC="doc-files/SimpleMeter.gif">
 * <p>A meter.
 *
 * @version 1.2
 */
public class SimpleMeter extends AbstractMeter
{
    /**
     * The path used to describe the colored space between external and internal arcs.
     */
    transient protected GeneralPath innerSpace;

    /**
     * Constructs a new SimpleMeter.
     * min = 0.0, max = 100.0
     */
    public SimpleMeter()
    {
        this(0.0, 100.0);
    }

    /**
     * Constructs a new SimpleMeter.
     * start = 120, extent = -60
     */
    public SimpleMeter(double d1, double d2)
    {
      this(120, -60, d1, d2);
    }

    /**
     * Constructs a new SimpleMeter.
     * needleDrawer = new BasicNeedle()
     */
    public SimpleMeter(double start, double extent, double d1, double d2)
    {
      super(start, extent, d1, d2);
      setNeedleDrawer(new BasicNeedle());
    }

    /**
     * Layout the component.
     */
    protected void layoutComponent ()
    {
     super.layoutComponent ();
     innerSpace = new GeneralPath ();
     innerSpace.append (intermediate, true);
     Arc2D.Double temp = new Arc2D.Double(internal.getBounds(), internal.getAngleStart()+internal.getAngleExtent(), -internal.getAngleExtent(), Arc2D.Double.OPEN);
     innerSpace.append (temp, true);
     innerSpace.closePath();
    }

    private transient Point2D p1 = new Point2D.Double(0, 0), p2 = new Point2D.Double(0, 0);
    private transient Line2D line = new Line2D.Double(0, 0, 0, 0);
    /**
     * Fill the colored space and draws the value scale.
     */
     protected void drawBackground(Graphics2D g)
      {
        double min = getMinimum();
        double max = getMaximum();

        g.setColor(getForeground());
        g.fill(innerSpace);
        g.setColor (legendColor);
        g.draw(innerSpace);

        String s = null;
        g.setFont(getValueDrawerFont());
        double step = angleExtent / legendSteps;
        int sw, fh = g.getFont().getSize();
        for (int k = 0; k <= legendSteps; k++) {
          Utilities.getArcPoint (p1, external, k*step);
          Utilities.getArcPoint (p2, internal, k*step);
          line.setLine(p1, p2);
          g.draw(line);
          if ((valueScale) && ((k % 2) == 0)) {
            s = Common.roundString (min+k*(max-min)/legendSteps, max-min, defaultValueDisplayPrecision);
            sw = Common.stringWidth (g.getFont(), s);
            double a = (360 + angleStart + k*step) % 360;
            double coeff = 0;
            if ((Math.abs(angleExtent) >= 350) && (k == legendSteps)) {
                coeff = 1;
            }
            double ar = Math.toRadians(a);
// SUN bug
//          if (a > 180)
            if (a <= 180)
              g.drawString (s, (float)(p1.getX()-(sw/2)+(sw+2)*Math.cos(ar)/2), (float)(p1.getY()-3-coeff*fh));
            else
              g.drawString (s, (float)(p1.getX()-(sw/2)+(sw+2)*Math.cos(ar)/2), (float)(p1.getY()+3*fh/4+coeff*fh));
          }
        }
      }

     /**
       * Returns the minimum size.
       */
      public Dimension getMinimumSize()
      {
        return new Dimension(30, 30);
      }
}

