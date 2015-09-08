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
import java.util.*;
import com.leuville.borneo.util.FIFO;
import java.beans.*;
import com.leuville.borneo.swing.*;

/**
 * A Trend which is automatically feeded by a thead.
 *
 * @version 1.1
 */
public class ThreadedTrend extends Trend
{
  /**
   * The thread activity.
   */
  protected boolean active;

  /**
   * Time unit in milliseconds.
   */
  protected int timeUnit;

  /**
   * Constructs a new Trend able to display 10 points between 0.0 and 100.0.
   */
  public ThreadedTrend ()
  {
    this (10, 0.0, 100.0, 1000);
  }

  /**
   * Constructs a new Trend without scale.
   * @param nbPoints The number of displayed points.
   * @param min The minimum value.
   * @param max The maximum value.
   */
  public ThreadedTrend (int nbPoints, double min, double max, int timeUnit)
  {
    this (nbPoints, min, max, false, timeUnit);
  }

  /**
   * Constructs a new Trend.
   * @param nbPoints The number of displayed points.
   * @param min The minimum value.
   * @param max The maximum value.
   * @param hasScale The scale control.
   */
  public ThreadedTrend (int nbPoints, double min, double max, boolean hasScale, int timeUnit)
  {
    super (nbPoints, min, max, hasScale);
    this.timeUnit = timeUnit;
    this.active = true;
    autoFeed.start();
  }

  /**
   * Adds a new point.
   */
  public void addPoint (double aNumber)
  {
    synchronized (model) {
      model.addValue (aNumber);
    }
  }

  /**
   * The automatic data feed.
   */
  protected Thread autoFeed = new Thread (new Runnable() {
      public void run ()
      {
        while (true) {
          int n = ThreadedTrend.this.model.getSize();
          if (ThreadedTrend.this.active && (n > 0)) {
            synchronized (ThreadedTrend.this.model) {
              double lastValue = ThreadedTrend.this.model.getValue(n-1);
              int lastStatus = ThreadedTrend.this.model.getStatus(n-1);
              ThreadedTrend.this.model.addValue (lastValue, lastStatus);
            }
            repaint ();
          }
          try {
            Thread.sleep (timeUnit);
          }
          catch (InterruptedException e) {
          }
        }
      }
  });

  /**
   * Set the time unit (ie the thread sleep delay).
   */
  public void setTimeUnit (int timeUnit)
  {
    this.timeUnit = timeUnit;
  }

  /**
   * Get the time unit (ie the thread sleep delay).
   */
  public int getTimeUnit ()
  {
    return timeUnit;
  }

  /**
   * Set the activity.
   */
  public void setActive (boolean active)
  {
    this.active = active;
  }

  /**
   * Get the activity.
   */
  public boolean isActive ()
  {
    return active;
  }
}

