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
import java.awt.event.*;
import java.io.*;
import com.leuville.borneo.swing.*;
import com.leuville.borneo.dataflow.*;

/**
 * <IMG SRC="doc-files/Knob.gif">
 * <p>A button which edit the value of a model.
 *
 * @version 1.1
 */
public class Knob extends RangeMeter
{
  /**
   * A specialized Needle class, used to draw the button.
   * <IMG SRC="doc-files/Knob.gif">
   */
  final private class KnobNeedle implements NeedleDrawer, Serializable
  {
    transient private Arc2D value = new Arc2D.Double(0, 0, 1, 1, 0, 360, Arc2D.PIE);
    transient private GeneralPath knobPath = new GeneralPath();
    /**
     * Creates a new KnobNeedle.
     */
    public KnobNeedle()
    {
      value = new Arc2D.Double(0, 0, 1, 1, 0, 360, Arc2D.PIE);
      knobPath = new GeneralPath();
    }

    transient private Point2D p0 = new Point2D.Double(0.0, 0.0);
    transient private Point2D dp = new Point2D.Double(0.0, 0.0);
    transient private Point2D uP = new Point2D.Double(0.0, 0.0);
    transient private Arc2D up = new Arc2D.Double(0, 0, 1, 1, 0, 360, Arc2D.PIE);
    transient private Arc2D down = new Arc2D.Double(0, 0, 1, 1, 0, 360, Arc2D.PIE);

    /**
     * Draws the knob.
     * @see AbstractMeter.NeedleDrawer
     */
    final public void drawNeedle (Graphics2D g, AbstractMeter meter, Arc2D needle)
    {
      knobPath.reset();
      double delta = 0.05 * knob.getAngleExtent();
      double angle = needle.getAngleStart() + needle.getAngleExtent() - 0.5 * delta;
      for (int i = 0; i < 10; i++) {
        Utilities.getArcPoint(p0, knob, angle);
        knobPath.moveTo((float)p0.getX(), (float)p0.getY());
        up.setArc (knob.getBounds2D(), angle, delta, Arc2D.PIE);
        knobPath.append(up, false);
        Utilities.getArcPoint(p0, up, up.getAngleExtent());
        knobPath.moveTo((float)p0.getX(), (float)p0.getY());
        down.setArc(knobInternalLimit.getBounds2D(), angle+up.getAngleExtent(), up.getAngleExtent(), Arc2D.PIE);
        Utilities.getArcPoint(dp, down, 0);
        knobPath.lineTo((float)dp.getX(), (float)dp.getY());
        knobPath.append(down, false);
        Utilities.getArcPoint(p0, down, down.getAngleExtent());
        knobPath.moveTo((float)p0.getX(), (float)p0.getY());
        Utilities.getArcPoint(uP, up, up.getAngleExtent()+down.getAngleExtent());
        knobPath.lineTo((float)uP.getX(), (float)uP.getY());
        angle += up.getAngleExtent()+down.getAngleExtent();
        }
      knobPath.closePath();
      g.setColor(meter.getForeground().darker());
      g.fill(knobPath);
      g.setColor(meter.getForeground());
      Utilities.getArcPoint(p0, buttonLimit, needle.getAngleExtent());
      value.setArcByCenter(p0.getX(), p0.getY(), 0.5*(knobInternalLimit.getWidth()-buttonLimit.getWidth()), 0, 360, Arc2D.PIE);
      g.fill(value);
    }
/*
    final public void drawNeedle (Graphics2D g, AbstractMeter meter, Arc2D needle)
    {
      knobPath.reset();
      double delta = 0.05 * knob.getAngleExtent();
      double angle = needle.getAngleStart() + needle.getAngleExtent() - 0.5 * delta;
      for (int i = 0; i < 10; i++) {
        Point2D p0 = Utilities.getArcPoint(knob, angle);
        knobPath.moveTo((float)p0.getX(), (float)p0.getY());
        Arc2D up = new Arc2D.Double(knob.getBounds2D(), angle, delta, Arc2D.PIE);
        knobPath.append(up, false);
        p0 = Utilities.getArcPoint(up, up.getAngleExtent());
        knobPath.moveTo((float)p0.getX(), (float)p0.getY());
        Arc2D down = new Arc2D.Double(knobInternalLimit.getBounds2D(), angle+up.getAngleExtent(), up.getAngleExtent(), Arc2D.PIE);
        Point2D dp = Utilities.getArcPoint(down, 0);
        knobPath.lineTo((float)dp.getX(), (float)dp.getY());
        knobPath.append(down, false);
        p0 = Utilities.getArcPoint(down, down.getAngleExtent());
        knobPath.moveTo((float)p0.getX(), (float)p0.getY());
        Point2D uP = Utilities.getArcPoint(up, up.getAngleExtent()+down.getAngleExtent());
        knobPath.lineTo((float)uP.getX(), (float)uP.getY());
        angle += up.getAngleExtent()+down.getAngleExtent();
        }
      knobPath.closePath();
      g.setColor(meter.getForeground().darker());
      g.fill(knobPath);
      g.setColor(meter.getForeground());
      Point2D p2 = Utilities.getArcPoint(buttonLimit, needle.getAngleExtent());
      value.setArcByCenter(p2.getX(), p2.getY(), 0.5*(knobInternalLimit.getWidth()-buttonLimit.getWidth()), 0, 360, Arc2D.PIE);
      g.fill(value);
    }  */
  }

  /**
   * The knob limit.
   */
  transient protected Arc2D knob;

  /**
   * The knob internal limit.
   */
  transient protected Arc2D knobInternalLimit;

  /**
   * The button limit.
   */
  transient protected Arc2D buttonLimit;

  /**
   * Construct a new Knob.
   */
  public Knob()
  {
    this (0, 100);
  }

  /**
   * Construct a new Knob.
   * A MouseMotionListener tracks mouseDrag events.
   */
  public Knob (double min, double max)
  {
    super(195, -210, min, max);
    setNeedleDrawer(new KnobNeedle());
    MouseMotionListener ml = new MouseMotionListener() {
      public void mouseMoved(MouseEvent e)
      {
      }
      public void mouseDragged(MouseEvent e)
      {
        int x = e.getX();
        int y = e.getY();
        if (!(Knob.this.knob.contains(x, y)))
          return;
        double a = Utilities.getAngleForPoint(internal, x, y) - internal.getAngleStart();
        double mini = Knob.this.getMinimum();
        double maxi = Knob.this.getMaximum();
        double val = mini + a*(maxi-mini)/internal.getAngleExtent();
        if (val < mini )
          val = mini;
        else if (val > maxi)
          val = maxi;
        Knob.this.setValue(val);
      }
    };
    addMouseMotionListener(ml);
    setDrawValue(true);
  }

  /**
   * Returns the initial centered frame.
   * @see AbstractMeter#getCenteredFrame(java.awt.geom.Rectangle2D)
   */
  protected Rectangle2D getCenteredFrame(Rectangle2D initRect)
  {
   Arc2D ext = new Arc2D.Double (initRect, angleStart, angleExtent, Arc2D.Double.OPEN);
   Rectangle2D r = getRealBounds(ext);
   Rectangle2D needleRect = getNeedleRect(initRect);
   Rectangle2D res = new Rectangle2D.Double(0, 0, 0, 0);
   Rectangle2D.union (r, needleRect, res);
   return res;
  }

  /**
   * Layout the receiver.
   */
  protected void layoutComponent()
  {
    super.layoutComponent();
    Rectangle2D r = needle.getFrame();
    double d = needle.getWidth()*0.5*0.10;
    r = new Rectangle2D.Double (r.getX()+d, r.getY()+d, r.getWidth()-2*d, r.getHeight()-2*d);
    knob = new Arc2D.Double(r.getX(), r.getY(), r.getWidth(), r.getHeight(), needle.getAngleStart(), (needle.getAngleExtent() < 0 ? -360 : 360), Arc2D.PIE);
    r.setRect (r.getX()+d, r.getY()+d, r.getWidth()-2*d, r.getHeight()-2*d);
    knobInternalLimit = new Arc2D.Double(r.getX(), r.getY(), r.getWidth(), r.getHeight(), needle.getAngleStart(), (needle.getAngleExtent() < 0 ? -360 : 360), Arc2D.PIE);
    r.setRect (r.getX()+d, r.getY()+d, r.getWidth()-2*d, r.getHeight()-2*d);
    buttonLimit = new Arc2D.Double(r.getX(), r.getY(), r.getWidth(), r.getHeight(), needle.getAngleStart(), (needle.getAngleExtent() < 0 ? -360 : 360), Arc2D.PIE);
  }

  /**
   * Add a StateChangeListener.
   */
  public void addStateChangeListener(StateChangeListener l)
  {
    listenerList.add(StateChangeListener.class, l);
  }

  /**
   * Remove a StateChangeListener.
   */
  public void removeStateChangeListener(StateChangeListener l)
  {
    listenerList.remove(StateChangeListener.class, l);
  }

  /**
   * Send a StateChangeEvent to each registered listener.
   */
  protected void fireStateChanged(StateChangeEvent event)
  {
    Object[] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
          if (listeners[i]==StateChangeListener.class) {
              ((StateChangeListener)listeners[i+1]).stateChanged(event);
          }
    }
  }

  /**
   * This method is called when a change occurs in the model.
   * @see AbstractMeter#stateChanged(StateChangeEvent)
   */
  protected void stateChanged(StateChangeEvent event)
  {
    super.stateChanged(event);
    fireStateChanged(event);
  }
}
