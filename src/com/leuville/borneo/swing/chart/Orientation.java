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

import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.geom.*;
import com.leuville.borneo.swing.*;

/**
 * Implements utilities used by oriented canvases.
 *
 * @version 1.1
 */
final public class Orientation implements java.io.Serializable {
  /**
   * North constant.
   */
  public final static String NORTH = "North";
  /**
   * South constant.
   */
  public final static String SOUTH = "South";
  /**
   * West constant.
   */
  public final static String WEST = "West";
  /**
   * East constant.
   */
  public final static String EAST = "East";
  /**
   * RIGHT constant.
   */
  public final static int RIGHT = 0;
  /**
   * LEFT constant.
   */
  public final static int LEFT = 1;
  /**
   * UP constant.
   */
  public final static int UP = 2;
  /**
   * DOWN constant.
   */
  public final static int DOWN = 3;
  /**
   * The left orientation object.
   */
  public final static Orientation left = new Orientation (LEFT);
  /**
   * The right orientation object.
   */
  public final static Orientation right = new Orientation (RIGHT);
  /**
   * The up orientation object.
   */
  public final static Orientation up = new Orientation (UP);
  /**
   * The down orientation object.
   */
  public final static Orientation down = new Orientation (DOWN);
  /**
   * The orientation.
   */
  public final int way;
  /**
   * Constructs a new Orientation.
   * Default is RIGHT.
   */
  public Orientation ()
  {
    this(RIGHT);
  }
  /**
   * Constructs a new Orientation.
   */
  public Orientation (int way)
  {
    this.way = way;
  }
  /**
   * Returns true if RIGHT or LEFT.
   */
  public boolean isHorizontal()
  {
    return ((way == LEFT) || (way == RIGHT));
  }
  /**
   * Returns true if UP or DOWN.
   */
  public boolean isVertical()
  {
    return ((way == UP) || (way == DOWN));
  }
  /**
   * Returns the way attribute.
   */
  public final int getWay ()
  {
    return way;
  }
  /**
   * Returns the name of this instance.
   */
  public String toString()
  {
    switch (way) {
      case LEFT:
        return WEST ;
      case RIGHT:
        return EAST;
      case UP:
        return NORTH;
      case DOWN:
        return SOUTH;
    }
    return null;
  }
  /**
   * Returns the opposite orientation of the receiver.
   */
  public Orientation opposite ()
  {
    switch (way) {
      case LEFT:
        return right ;
      case RIGHT:
        return left;
      case UP:
        return down;
      case DOWN:
        return up;
    }
    return null;
  }
  /**
  * Updates a Rectangle representing the interval between two values.
  * @param r The rectangle.
  * @param origin The first value.
  * @param end The second value.
  */
  public void update (Rectangle r, int origin, int end)
  {
    switch (way) {
      case RIGHT:
      case LEFT:
        r.x = Math.min (origin, end);
        r.width = Math.max (origin, end) -r.x + 1;
        break;
      case UP:
      case DOWN:
        r.y = Math.min (origin, end);
        r.height = Math.max (origin, end) - r.y + 1;
        break;
    }
  }
  /**
   * Computes a Line object representing a value in an interval in a 2D space.
   * @param b      A Rectangle representing an interval.
   * @param value      The pixel value.
   * @param origin    The resulting origin point.
   * @param end      The resulting end point.
   */
  public void newLine (Rectangle b, int value, Point origin, Point end)
  {
    switch (way) {
      case LEFT:
      case RIGHT:
        origin.x = value;
        origin.y = b.y;
        end.x = value;
        end.y = b.y + b.height - 1;
        break;
      case UP:
      case DOWN:
        origin.x = b.x + b.width - 1;
        origin.y = value;
        end.x = b.x;
        end.y = value;
    }
      }
  /**
   * Returns a Line object representing a value in an interval in a 2D space.
   * @param b A Rectangle representing an interval.
   * @param value The pixel value.
   */
  public Line2D newLine (Rectangle b, int value)
  {
    return newLine (b, value, new Dimension (0, 0));
  }
  /**
   * Returns a Line object representing a value in an interval.
   * @param b A Rectangle representing an interval.
   * @param value The pixel value.
   * @param depth The depth.
   */
  public Line2D newLine (Rectangle b, int value, Dimension depth)
  {
    java.awt.Point p1 = null, p2 = null;
    switch (way) {
      case LEFT:
      case RIGHT:
        p1 = new java.awt.Point (value + depth.width, b.y);
        p2 = new java.awt.Point (value, b.y + b.height - 1);
        break;
      case UP:
      case DOWN:
        p1 = new java.awt.Point (b.x + b.width - 1, value - depth.height);
        p2 = new java.awt.Point (b.x, value);
    }
    return new Line2D.Double (p1, p2);
  }
  /**
   * Returns a coordinate corresponding to a value.
   * @param min The minimum.
   * @param max The maximum.
   * @param v The value.
   * @param b The rectangle that is the coordinates interval.
   */
  public int scaleValue (double min, double max, double v, java.awt.Rectangle b)
  {
    int res = -1;
    switch (way) {
      case RIGHT:
        res = (int)(b.x+Math.round((v-min) * (b.width-1) / (max - min )));
        break;
      case LEFT:
        res = (int)(b.x+b.width-1-Math.round((v-min) * (b.width-1) / (max - min)));
        break;
      case UP:
        res = (int)(b.y+b.height-1-Math.round((v-min) * (b.height-1) / (max - min)));
        break;
      case DOWN:
        res = (int)(b.y+Math.round((v-min) * (b.height-1) / (max - min)));
        break;
    }
    return res;
  }
  /**
   * Retuns a value between min and max corresponding to point (x, y).
   * This is the reverse method of scaleValue
   */
  public double getValue (double min, double max, int x, int y, java.awt.Rectangle b)
  {
    switch (way) {
      case RIGHT:
        return (max-min)*(x-b.x) / b.width;
      case LEFT:
        return (max-min)*(b.x+b.width+1-x) / b.width;
      case UP:
        return (max-min)*(b.y+b.height+1-y) / b.height;
      case DOWN:
        return (max-min)*(y-b.y) / b.height;
    }
    return 0.0;
  }
  /**
   * Scrolls a Rectangle.
   * @param r The rectangle to scroll.
   * @param offScreen The double buffer.
   * @param eraseColor The color used to clear the background.
   * @param step The scroll step.
   * @return The erased Rectangle.
   */
  public com.leuville.borneo.swing.Rectangle scroll (Rectangle r, Graphics offScreen, Color eraseColor, int step)
  {
    com.leuville.borneo.swing.Rectangle erased = new com.leuville.borneo.swing.Rectangle ();
    scroll (r, offScreen, eraseColor, step, erased);
    return erased;
  }
  /**
   * Scrolls a Rectangle.
   * @param r The rectangle to scroll.
   * @param offScreen The double buffer.
   * @param eraseColor The color used to clear the background.
   * @param step The scroll step.
   * @param erased The erased Rectangle (modified by this method).
   */
  public void scroll (Rectangle r, Graphics offScreen, Color eraseColor, int step, Rectangle erased)
  {
    offScreen.setColor (eraseColor);
    switch (way) {
      case RIGHT:
        offScreen.copyArea (r.x, r.y, r.width, r.height, -step, 0);
        erased.x = r.x + r.width-step;
        erased.y = r.y;
        erased.width = step;
        erased.height = r.height;
        break;
      case LEFT:
        offScreen.copyArea (r.x, r.y, r.width, r.height, step, 0);
        erased.x = r.x;
        erased.y = r.y;
        erased.width = step;
        erased.height = r.height;
        break;
      case UP:
        offScreen.copyArea (r.x, r.y, r.width, r.height, 0, step);
        erased.x = r.x;
        erased.y = r.y;
        erased.width = r.width;
        erased.height = step;
        break;
      case DOWN:
        offScreen.copyArea (r.x, r.y, r.width, r.height, 0, -step);
        erased.x = r.x;
        erased.y = r.y + r.height - step;
        erased.width = r.width;
        erased.height = step;
        break;
    }
    offScreen.fillRect (erased.x, erased.y, erased.width, erased.height);
  }
  /**
   * Returns the origin of a Rectangle.
   */
  public Point origin (Rectangle r)
  {
    switch (way) {
      case RIGHT:
        return new Point (r.x, r.y);
      case LEFT:
        return new Point (r.x + r.width -1, r.y);
      case UP:
        return new Point (r.x, r.y + r.height - 1);
      case DOWN:
        return new Point (r.x, r.y);
    }
    return null;
  }
  /**
   * Returns the origin of a Rectangle.
   */
  public Point origin (Rectangle r, int offset)
  {
    switch (way) {
      case RIGHT:
        return new Point (r.x, r.y + offset);
      case LEFT:
        return new Point (r.x + r.width -1, r.y + offset);
      case UP:
        return new Point (r.x + offset, r.y + r.height - 1);
      case DOWN:
        return new Point (r.x + offset, r.y);
    }
    return null;
  }
  /**
   * Returns the end of a Rectangle.
   */
  public Point end (Rectangle r)
  {
    switch (way) {
      case RIGHT:
        return new Point (r.x + r.width-1, r.y);
      case LEFT:
        return new Point (r.x, r.y);
      case UP:
        return new Point (r.x, r.y);
      case DOWN:
        return new Point (r.x, r.y + r.height-1);
    }
    return new Point (r.x, r.y);
  }

  private int endY (Rectangle r)
  {
    switch (way) {
       case DOWN:
        return r.y + r.height-1;
      default:
        return r.y;
    }
  }

  private int endX (Rectangle r)
  {
    switch (way) {
      case RIGHT:
        return r.x + r.width-1;
      default:
        return r.x;
    }
  }
  /**
   * Gets a polygon using two points and two orientations.
   * @param limits Bounds as a Rectangle.
   * @param offScreen The double buffer.
   * @param second The scroll direction.
   * @param v1 The first point.
   * @param v2 The second point.
   * @param min The minimum point value.
   * @param max The maximum point value.
   * @param step The distance between the two points.
   */
  public void getPolygon (Polygon res, Rectangle limits, Orientation second, double v1, double v2,
          double min, double max, int step)
  {
    getPolygon (res, limits, second, v1, v2, min, max, step, 0);
  }
  /**
   * Gets a polygon using two points and two orientations.
   * This polygon is located n*step pixels before the 'end' of limits
   * according to this and second orientations.
   * @param limits Bounds as a Rectangle.
   * @param second The scroll direction.
   * @param v1 The first point.
   * @param v2 The second point.
   * @param min The minimum point value.
   * @param max The maximum point value.
   * @param step The distance between the two points.
   * @param n The reverse index of the point (ie 0 = the last point).
   */
  public void getPolygon (Polygon res, Rectangle limits, Orientation second, double v1, double v2,
          double min, double max, int step, int n)
  {
    int end;
    int ax = 0, ay = 0, bx = 0, by = 0;
    int cx = 0, cy = 0, dx = 0, dy = 0;
    switch (way) {
      case RIGHT:
          end = endX(limits);
          ax = end - (n+1)*step;
          cx = ax;
          ay = second.scaleValue (min, max, v1, limits);
          cy = second.scaleValue (min, max, Math.max(min, 0), limits);
          bx = end - n*step;
          dx = bx;
          by = second.scaleValue (min, max, v2, limits);
          dy = second.scaleValue (min, max, Math.max(min, 0), limits);
          break;
      case LEFT:
          end = endX(limits);
          ax = end + n*step;
          cx = ax;
          ay = second.scaleValue (min, max, v2, limits);
          cy = second.scaleValue (min, max, Math.max(min, 0), limits);
          bx = end + (n+1)*step;
          dx = bx;
          by = second.scaleValue (min, max, v1, limits);
          dy = second.scaleValue (min, max, Math.max(min, 0), limits);
          break;
      case UP:
          end = endY(limits);
          ay = end+n*step;
          cy = ay;
          ax = second.scaleValue (min, max, v2, limits);
          cx = second.scaleValue (min, max, Math.max(min, 0), limits);
          by = end + (n+1)*step;
          dy = by;
          bx = second.scaleValue (min, max, v1, limits);
          dx = second.scaleValue (min, max, Math.max(min, 0), limits);
          break;
      case DOWN:
          end = endY(limits);
          ay = end - (n+1)*step;
          cy = ay;
          ax = second.scaleValue (min, max, v1, limits);
          cx = second.scaleValue (min, max, Math.max(min, 0), limits);
          by = end-n*step;
          dy = by;
          bx = second.scaleValue (min, max, v2, limits);
          dx = second.scaleValue (min, max, Math.max(min, 0), limits);
          break;
    }
    res.addPoint (ax, ay);
    res.addPoint (bx, by);
    res.addPoint (dx, dy);
    res.addPoint (cx, cy);
  }
  /**
   * Gets a point indicating value v at the end of limits rectangle.
   * The value depends on the scroll direction.
   * @param limits Bounds as a Rectangle.
   * @param second The scroll direction.
   * @param v The value.
   * @param min The minimum point value.
   * @param max The maximum point value.
   */
  public void getPoint (Point res, Rectangle limits, Orientation second, double v,
        double min, double max, int step)
  {
    getPoint (res, limits, second, v, min, max, 0);
  }
  /**
   * Gets a point indicating value v at the end of limits rectangle.
   * The value depends on the scroll direction.
   * @param limits Bounds as a Rectangle.
   * @param second The scroll direction.
   * @param v The value.
   * @param min The minimum point value.
   * @param max The maximum point value.
   * @param n The reverse index of the point (ie 0 = the last point).
   */
  public void getPoint (Point res, Rectangle limits, Orientation second, double v,
        double min, double max, int step, int n)
  {
    int ax = 0, ay = 0;
    int m = 2*n+1;
    switch (way) {
      case RIGHT:
          ax = endX(limits) - (int)(Math.round(m*step/2));
          ay = second.scaleValue (min, max, v, limits);
          break;
      case LEFT:
          ax = endX(limits) + (int)(Math.round(m*step/2));
          ay = second.scaleValue (min, max, v, limits);
          break;
      case UP:
          ay = endY(limits) + (int)(Math.round(m*step/2));
          ax = second.scaleValue (min, max, v, limits);
          break;
      case DOWN:
          ay = endY(limits) - (int)(Math.round(m*step/2));
          ax = second.scaleValue (min, max, v, limits);
          break;
    }
    res.x = ax;
    res.y = ay;
  }
  /**
   * Gets bar indicating value v at the end of limits rectangle.
   * This depends on the scroll direction.
   * @param limits Bounds as a Rectangle.
   * @param second The scroll direction.
   * @param v The value.
   * @param min The minimum point value.
   * @param max The maximum point value.
   */
  public void getThinBar (Line2D res, Rectangle limits, Orientation second, double v,
        double min, double max, int step)
  {
    getThinBar (res, limits, second, v, min, max, step, 0);
  }
  /**
   * Gets bar indicating value v at the end of limits rectangle.
   * This depends on the scroll direction.
   * @param limits Bounds as a Rectangle.
   * @param second The scroll direction.
   * @param v The value.
   * @param min The minimum point value.
   * @param max The maximum point value.
   * @param n The reverse index of the point (ie 0 = the last point).
   */
  public void getThinBar (Line2D res, Rectangle limits, Orientation second, double v,
        double min, double max, int step, int n)
  {
    int ax = 0, ay = 0, bx = 0, by = 0;
    int m = 2*n+1;
    switch (way) {
      case RIGHT:
          ax = endX(limits) - (int)(Math.round(m*step/2));
          bx = ax;
          ay = second.scaleValue (min, max, v, limits);
          by = second.scaleValue (min, max, Math.max(min, 0), limits);
          break;
      case LEFT:
          ax = endX(limits) + (int)(Math.round(m*step/2));
          bx = ax;
          ay = second.scaleValue (min, max, v, limits);
          by = second.scaleValue (min, max, Math.max(min, 0), limits);
          break;
      case UP:
          ay = endY(limits) + (int)(Math.round(m*step/2));
          by = ay;
          ax = second.scaleValue (min, max, v, limits);
          bx = second.scaleValue (min, max, Math.max(min, 0), limits);
          break;
      case DOWN:
          ay = endY(limits) - (int)(Math.round(m*step/2));
          by = ay;
          ax = second.scaleValue (min, max, v, limits);
          bx = second.scaleValue (min, max, Math.max(min, 0), limits);
          break;
    }
    res.setLine (ax, ay, bx, by);
  }
  /**
   * Returns a line between two values given by v1 and v2 parameters.
   * @param limits Bounds as a Rectangle.
   * @param second The scroll direction.
   * @param v1 The first point.
   * @param v2 The second point.
   * @param min The minimum point value.
   * @param max The maximum point value.
   * @param step The distance between the two points.
   */
  public void getLine (Line2D res, Rectangle limits, Orientation second, double v1, double v2,
        double min, double max, int step)
  {
    getLine (res, limits, second, v1, v2, min, max, step, 0);
  }
  /**
   * Returns a line between two values given by v1 and v2 parameters.
   * @param limits Bounds as a Rectangle.
   * @param second The scroll direction.
   * @param v1 The first point.
   * @param v2 The second point.
   * @param min The minimum point value.
   * @param max The maximum point value.
   * @param step The distance between the two points.
   * @param n The reverse index of the point (ie 0 = the last point).
   */
  public void getLine (Line2D res, Rectangle limits, Orientation second, double v1, double v2,
        double min, double max, int step, int n)
  {
    int end;
    int ax = 0, ay = 0, bx = 0, by = 0;
    switch (way) {
      case RIGHT:
          end = endX(limits);
          ax = end - (n+1)*step;
          ay = second.scaleValue (min, max, v1, limits);
          bx = end-n*step;
          by = second.scaleValue (min, max, v2, limits);
          break;
      case LEFT:
          end = endX(limits);
          ax = end+n*step;
          ay = second.scaleValue (min, max, v2, limits);
          bx = end + (n+1)*step;
          by = second.scaleValue (min, max, v1, limits);
          break;
      case UP:
          end = endY(limits);
          ay = end+n*step;
          ax = second.scaleValue (min, max, v2, limits);
          by = end + (n+1)*step;
          bx = second.scaleValue (min, max, v1, limits);
          break;
      case DOWN:
          end = endY(limits);
          ay = end - (n+1)*step;
          ax = second.scaleValue (min, max, v1, limits);
          by = end-n*step;
          bx = second.scaleValue (min, max, v2, limits);
          break;
    }
    res.setLine (ax, ay, bx, by);
  }
  /**
   * Gets a rectangle using one point and two orientations.
   * @param limits Bounds as a Rectangle.
   * @param offScreen The double buffer.
   * @param second The scroll direction.
   * @param v The point.
   * @param min The minimum point value.
   * @param max The maximum point value.
   * @param step The distance between the two points.
   */
  public void getRectangle (Rectangle res, Rectangle limits, Orientation second, double v,
          double min, double max, int step)
  {
    getRectangle (res, limits, second, v, min, max, step, 0);
  }
  /**
   * Gets a rectangle using one point and two orientations.
   * This rectangle is located n*step pixels before the 'end' of limits
   * according to this and second orientations.
   * @param limits Bounds as a Rectangle.
   * @param second The scroll direction.
   * @param v The point.
   * @param min The minimum point value.
   * @param max The maximum point value.
   * @param step The distance between the two points.
   * @param n The reverse index of the point (ie 0 = the last point).
   */
  public void getRectangle (Rectangle res, Rectangle limits, Orientation second, double v,
          double min, double max, int step, int n)
  {
    switch (way) {
      case RIGHT:
          res.x = endX(limits) - (n+1)*step;
          res.y = second.scaleValue (min, max, v, limits);
          res.width = step;
          res.height = second.scaleValue (min, max, Math.max(min, 0), limits) - res.y + 1;
          break;
      case LEFT:
          res.x = endX(limits) + n*step;
          res.y = second.scaleValue (min, max, v, limits);
          res.width = step;
          res.height = second.scaleValue (min, max, Math.max(min, 0), limits) - res.y + 1;
          break;
      case UP:
          res.x = endY(limits)+n*step;
          res.y = second.scaleValue (min, max, v, limits);
          res.width = second.scaleValue (min, max, Math.max(min, 0), limits) - res.x + 1;
          res.height = step;
          break;
      case DOWN:
          res.x = endY(limits) - (n+1)*step;
          res.y = second.scaleValue (min, max, v, limits);
          res.width = second.scaleValue (min, max, Math.max(min, 0), limits) - res.x + 1;
          res.height = step;
          break;
    }
  }

  public Line2D getTopLine (Rectangle rectangle, Line2D result)
  {
    switch (way) {
      case RIGHT:
        result.setLine(
          rectangle.x+rectangle.width, rectangle.y,
          rectangle.x+rectangle.width, rectangle.y+rectangle.height);
        break;
      case LEFT:
        result.setLine(
          rectangle.x, rectangle.y,
          rectangle.x, rectangle.y+rectangle.height);
        break;
      case UP:
        result.setLine(
          rectangle.x, rectangle.y,
          rectangle.x+rectangle.width, rectangle.y);
        break;
      case DOWN:
        result.setLine(
          rectangle.x, rectangle.y+rectangle.height,
          rectangle.x+rectangle.width, rectangle.y+rectangle.height);
        break;
    }
    return result;
  }

}


