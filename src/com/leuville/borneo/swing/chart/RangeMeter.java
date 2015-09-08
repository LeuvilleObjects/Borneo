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
 * <IMG SRC="doc-files/RangeMeter.gif">
 * <p>A meter with color ticks.
 *
 * @version 1.2
 */
public class RangeMeter extends AbstractMeter
{
    /**
     * Constructs a new RangeMeter.
     * min = 0.0, max = 100.0
     */
    public RangeMeter()
    {
        this(0.0, 100.0);
    }

    /**
     * Constructs a new RangeMeter.
     * start = 120, range = -60
     */
    public RangeMeter(double d1, double d2)
    {
      this(120, -60, d1, d2);
    }

    /**
     * Constructs a new RangeMeter.
     * The needleDrawer is initialized with an AbstractMeter.BasicNeedle instance.
     */
    public RangeMeter(double start, double extent, double d1, double d2)
    {
      super(start, extent, d1, d2);
      setNeedleDrawer(new AbstractMeter.BasicNeedle());
    }

    /**
     * The color ranges array.
     * Initialized by default to { Color.green, Color.yellow, Color.red }.
     */
    protected Color[] colors = new Color[] {Color.green, Color.yellow, Color.red};

    /**
     * Set the color ranges property.
     */
    public void setRangeColors(Color[] rangeColors)
    {
      colors = rangeColors;
      layoutComponent();
      repaint();
    }

    /**
     * Get the color ranges property.
     */
    public Color[] getRangeColors()
    {
      return colors;
    }

    /**
     * The ranges property.
     * Initialized by default to { 0.25, 0.50, 0.25 }.
     */
    protected double[] ranges = new double[] {0.25, 0.50, 0.25};

    /**
     * Set the ranges property.
     *
     * @exception IllegalArgumentException  If the sum of ranges is not 1.0.
     */
    public void setRanges(double[] ranges)
    {
      double sum = 0.0;
      for (int i = 0; i < ranges.length; i++) {
        if ((ranges[i] <= 0.0) || (ranges[i] >= 1.0)) {
          throw new IllegalArgumentException("the range is not between 0.0 and 1.0");
        }
        sum += ranges[i];
      }
      if (sum != 1.0) {
        throw new IllegalArgumentException("the sum is not 1.0");
      }
      this.ranges = ranges;
      layoutComponent();
      repaint();
    }

    /**
     * Get the range property.
     */
    public double[] getRanges()
    {
      return ranges;
    }

    /**
     * The path used to fill color ticks.
     */
    transient protected GeneralPath[] innerSpace;

    /**
     * Layout the component.
     */
    protected void layoutComponent ()
    {
     super.layoutComponent ();
     innerSpace = new GeneralPath[ranges.length];
     double angle = 0;
     double min = getMinimum();
     for (int i = 0; i < ranges.length; i++) {
      innerSpace[i] = new GeneralPath();
      innerSpace[i].append(new Arc2D.Double(
                              intermediate.getBounds(),
                              angleStart + angle, ranges[i]*angleExtent, Arc2D.Double.OPEN),
                           true);
      innerSpace[i].append(new Arc2D.Double(
                              internal.getBounds(),
                              angleStart + angle + ranges[i]*angleExtent, -ranges[i]*angleExtent, Arc2D.Double.OPEN),
                           true);
      innerSpace[i].closePath();
      angle += ranges[i]*angleExtent;
     }
    }

    private transient Point2D p1 = new Point2D.Double(0, 0), p2 = new Point2D.Double(0, 0);
    private transient Line2D line = new Line2D.Double(0, 0, 0, 0);
    /**
     * Draws the color marks and the value scale.
     */
    protected void drawBackground(Graphics2D g)
    {
      double min = getMinimum();
      double max = getMaximum();

      for (int i = 0; i < ranges.length; i++) {
        g.setColor(colors[i]);
        g.fill(innerSpace[i]);
        g.setColor (legendColor);
        g.draw(innerSpace[i]);
      }

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
// SUN BUG
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


