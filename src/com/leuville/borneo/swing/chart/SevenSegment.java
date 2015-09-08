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
import javax.swing.event.*;
import java.io.*;
import com.leuville.borneo.dataflow.*;

/**
 * <IMG SRC="doc-files/SevenSegment-2.gif">
 * <p>A 'seven segment' numerical display.
 *
 * @version 1.2
 */
public class SevenSegment extends JComponent
{
 /**
  * The number of digits.
  */
 protected int n1;

 /**
  * The precision (number of digits after the point).
  */
 protected int n2;

 /**
  * The color used to draw digits which are in the 'off' state.
  */
 protected Color offColor;

 /**
  * The class which defines each digit.
  */
 private class NumberDisplay implements Serializable
 {
  private Rectangle bounds;
  private transient RoundRectangle2D[] segments;
  private transient RoundRectangle2D point;
  private boolean showPoint;
  private int thickness;

  public NumberDisplay()
  {
    this(false, 2);
  }
  public NumberDisplay(boolean showPoint, int thickness)
  {
    this.showPoint = showPoint;
    this.thickness = thickness;
  }
  public final void draw(Graphics2D g, char car)
  {
    //
    //    0
    //  1   2
    //    3
    //  4   5
    //    6
    switch (car) {
        case ' ':
          off(g, 0); off(g, 1); off(g, 2); off(g, 3); off(g, 4); off(g, 5); off(g, 6);
          break;
        case '-':
          off(g, 0); off(g, 1); off(g, 2); on(g, 3); off(g, 4); off(g, 5); off(g, 6);
          break;
        case '1':
          off(g, 0); off(g, 1); on(g, 2); off(g, 3); off(g, 4); on(g, 5); off(g, 6);
          break;
        case '2':
          on(g, 0); off(g, 1); on(g, 2); on(g, 3); on(g, 4); off(g, 5); on(g, 6);
          break;
        case '3':
          on(g, 0); off(g, 1); on(g, 2); on(g, 3); off(g, 4); on(g, 5); on(g, 6);
          break;
        case '4':
          off(g, 0); on(g, 1); on(g, 2); on(g, 3); off(g, 4); on(g, 5); off(g, 6);
          break;
        case '5':
          on(g, 0); on(g, 1); off(g, 2); on(g, 3); off(g, 4); on(g, 5); on(g, 6);
          break;
        case '6':
          on(g, 0); on(g, 1); off(g, 2); on(g, 3); on(g, 4); on(g, 5); on(g, 6);
          break;
        case '7':
          on(g, 0); off(g, 1); on(g, 2); off(g, 3); off(g, 4); on(g, 5); off(g, 6);
          break;
        case '8':
          on(g, 0); on(g, 1); on(g, 2); on(g, 3); on(g, 4); on(g, 5); on(g, 6);
          break;
        case '9':
          on(g, 0); on(g, 1); on(g, 2); on(g, 3); off(g, 4); on(g, 5); off(g, 6);
          break;
        case '0':
          on(g, 0); on(g, 1); on(g, 2); off(g, 3); on(g, 4); on(g, 5); on(g, 6);
          break;
    }
    if (showPoint) {
      g.setColor(SevenSegment.this.getForeground());
      g.fill(point);
    }
  }

  private final void on(Graphics2D g, int i)
  {
    g.setColor(SevenSegment.this.getForeground());
    g.fill(segments[i]);
  }
  private final void off(Graphics2D g, int i)
  {
    Color c = SevenSegment.this.offColor;
    if (c == null)
      c = SevenSegment.this.getBackground().darker();
    g.setColor(c);
    g.fill(segments[i]);
  }

  public void setBounds (int x, int y, int width, int height)
  {
    double r = 0.5*thickness;
    bounds = new Rectangle (x, y, width, height);
    // keep space for decimal point
    double w = width - thickness - 2;
    segments = new RoundRectangle2D.Double[7];
    // upper segment
    segments[0] = new RoundRectangle2D.Double(x+thickness, y, w-2*(thickness), thickness, r, r);
    // up left
    segments[1] = new RoundRectangle2D.Double(x, segments[0].getY()+segments[0].getHeight(), thickness, 0.5*height-(1.5*thickness), r, r);
    // up right
    segments[2] = new RoundRectangle2D.Double(segments[0].getX()+segments[0].getWidth(), segments[1].getY(), segments[1].getWidth(), segments[1].getHeight(), r, r);
    // middle
    segments[3] = new RoundRectangle2D.Double(segments[0].getX(), segments[1].getY()+segments[1].getHeight(), segments[0].getWidth(), segments[0].getHeight(), r, r);
    // bottom left
    segments[4] = new RoundRectangle2D.Double(x, segments[3].getY()+segments[3].getHeight(), segments[1].getWidth(), segments[1].getHeight(), r, r);
    // bottom right
    segments[5] = new RoundRectangle2D.Double(segments[3].getX()+segments[3].getWidth(), segments[4].getY(), segments[4].getWidth(), segments[4].getHeight(), r, r);
    // bottom
    segments[6] = new RoundRectangle2D.Double(segments[0].getX(), segments[4].getY()+segments[4].getHeight(), segments[0].getWidth(), segments[0].getHeight(), r, r);
    // decimal point
    if (showPoint)
      point = new RoundRectangle2D.Double(segments[5].getX()+segments[5].getWidth()+1, segments[6].getY(), thickness, thickness, r, r);
  }
 }

 /**
  * The array of digits.
  */
 protected NumberDisplay[] segment;

 /**
  * The display string.
  */
 protected char[] string;

 /**
  * The digit thickness.
  */
 protected int thickness;

 /**
  * Constructs a one digit display.
  * thickness = 2.
  */
 public SevenSegment()
 {
  this(1, 0, 2);
 }

 /**
  * Creates a new SevenSegment.
  * model = an instance of DefaultDoubleDataModel.
  * @see DoubleDataModel
  */
 public SevenSegment (int n1, int n2, int thickness)
 {
  setOpaque(false);
  setDoubleBuffered(true);
  this.n1 = n1;
  this.n2 = n2;
  segment = new NumberDisplay[n1+n2];
  this.thickness = thickness;
  model = new DefaultDoubleDataModel(0.0, Status.HIGH_CONFIDENCE, 0, 100);
  model.addStateChangeListener(stateChangeListener);
 }

 /**
  * Set the thickness property.
  */
 public void setThickness(int thickness)
 {
  this.thickness = thickness;
  layoutComponent();
  repaint();
 }

 /**
  * Get the thickness property.
  */
 final public int getThickness()
 {
  return thickness;
 }

 /**
  * Set the precision property.
  */
 public void setPrecision(int precision)
 {
  this.n2 = precision;
  layoutComponent();
  repaint();
 }

 /**
  * Get the precision property.
  */
 final public int getPrecision()
 {
  return n2;
 }

 /**
  * Set the digits property.
  */
 public void setDigits(int digits)
 {
  this.n1 = digits;
  layoutComponent();
  repaint();
 }

 /**
  * Get the digits property.
  */
 final public int getDigits()
 {
  return n1;
 }

 /**
  * Set the offColor property.
  */
 public void setOffColor(Color offColor)
 {
  this.offColor = offColor;
  repaint();
 }

 /**
  * Get the offColor property.
  */
 final public Color getOffColor()
 {
  return offColor;
 }

 /**
  * Set the bounds and layout the component.
  * @see #layoutComponent()
  */
 public void setBounds (int x, int y, int width, int height)
 {
  super.setBounds (x, y, width, height);
  layoutComponent ();
  repaint();
 }

 /**
  * Layout the component.
  */
 protected void layoutComponent()
 {
  java.awt.Insets in = getInsets();
  int w = getWidth() - in.left - in.right;
  int siz = n1 + n2;
  int segWidth = (int)Math.round(w / siz);
  string = new char[siz];
  segment = new NumberDisplay[siz];
  for (int i = 0; i < siz; i++) {
    segment[i] = new NumberDisplay((i==(n1-1)) && (n2 != 0), thickness);
    segment[i].setBounds(in.left+i*segWidth, in.top, segWidth, getHeight()-in.top-in.bottom);
    string[i] = '-';
  }
 }

 /**
  * The data model.
  */
 protected DoubleDataModel model;

 /**
  * The model state change listener.
  */
 protected StateChangeListener stateChangeListener = createStateChangeListener();

  private class ModelListener implements StateChangeListener, Serializable
  {
    final public void stateChanged(StateChangeEvent event)
    {
      switch (event.getType()) {
        case StateChangeEvent.RANGE:
          SevenSegment.this.setBounds(getBounds());
          break;
        case StateChangeEvent.VALUE:
        case StateChangeEvent.STATUS:
          SevenSegment.this.repaint();
          SevenSegment.this.stateChanged(event);
          break;
      }
    }
  }

  private Convert convert = new Convert();

  private void clearString()
  {
    for (int i = 0; i < string.length; i++) {
      string[i] = ' ';
    }
  }

  /**
   * Called when a model state change occurs.
   * This method set the display string and repaint the receiver.
   * @see #fireStateChanged(StateChangeEvent)
   */
  protected void stateChanged(StateChangeEvent event)
  {
    int val = (int)Math.round(model.getValue() * Math.pow(10, n2));
    int len;

    if (val == 0) {
      clearString();

	  // Patch to align on right
	  len = n2 + 1;
	  int diff = segment.length - len;
	  for (int i=0; i < diff; i++)
		  string[i] = ' ';
	  for (int i=diff; i < segment.length; i++)
		  string[i] = '0';
	} else {
      len = convert.intToChars(val);
      if (len > segment.length) {
        clearString();
        for (int i = 0; i < segment.length; i++) {
          string[i] = '-';
        }
      } else {
        for (int i = 0; i < len; i++) {
          string[string.length-1-i] = convert.buf[convert.buf.length-1-i];
        }
        int diff = segment.length - len;
        for (int i = 0; i < diff; i++) {
          string[i] = ' ';
        }
      }
    }
    repaint();
    fireStateChanged(event);
  }

  private static class Convert implements Serializable
  {
    private final char[] buf = new char[12];
    /**
     * All possible chars for representing a number as a String
     */
    final static char[] digits = {
      '0' , '1' , '2' , '3' , '4' , '5' ,
      '6' , '7' , '8' , '9' , 'a' , 'b' ,
      'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
      'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
      'o' , 'p' , 'q' , 'r' , 's' , 't' ,
      'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };

    /**
     * Array of chars to lookup the char for the digit in the tenth's
     * place for a two digit, base ten number.  The char can be got by
     * using the number as the index.
     */
    private final static char[] radixTenTenths = {
      '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
      '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
      '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
      '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
      '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
      '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
      '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
      '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
      '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
      '9', '9', '9', '9', '9', '9', '9', '9', '9', '9'
    };
    /**
     * Array of chars to lookup the char for the digit in the unit's
     * place for a two digit, base ten number.  The char can be got by
     * using the number as the index.
     */
    private final static char[] radixTenUnits = {
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    public int intToChars(int i)
    {
      boolean negative = (i < 0);
      int charPos = 12;
      if (i == Integer.MIN_VALUE) {
          // " -2147483648";
          buf[0] = ' ';
          buf[1] = '-';
          buf[2] = '2';
          buf[3] = '1';
          buf[4] = '4';
          buf[5] = '7';
          buf[6] = '4';
          buf[7] = '8';
          buf[8] = '3';
          buf[9] = '6';
          buf[10] = '4';
          buf[11] = '8';
          return 11;
      }
      if (negative) {
          i = -i;
      }
      do {
          int digit = i%100;
          buf[--charPos] = radixTenUnits[digit];
          buf[--charPos] = radixTenTenths[digit];
          i = i / 100;
      } while(i != 0);
      if (buf[charPos] == '0') {
          charPos++;
      }
      if (negative) {
          buf[--charPos] = '-';
      }
      for (int j = 0; j < charPos; j++) {
        buf[j] = ' ';
      }
      return (12-charPos);
    }
  }

  /**
   * Paint the receiver.
   */
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    if (isOpaque()) {
        g.setColor(getBackground());
        Insets in = getInsets();
        g.fillRect(in.left, in.top, getWidth()-in.left-in.right, getHeight()-in.top-in.bottom);
    }
    for (int i = 0; i < segment.length; i++) {
      segment[i].draw((Graphics2D)g, string[i]);
    }
  }

  /**
   * Creates the model state change listener.
   */
  protected StateChangeListener createStateChangeListener()
  {
    return new ModelListener();
  }

  /**
   * Adds a StateChangeListener to the bargraph.
   *
   * @param l the StateChangeListener to add
   * @see #fireStateChanged
   * @see #removeStateChangeListener
   */
  public void addStateChangeListener(StateChangeListener l)
  {
    listenerList.add(StateChangeListener.class, l);
  }


  /**
   * Removes a StateChangeListener from the bargraph.
   *
   * @param l the StateChangeListener to remove
   * @see #fireStateChanged
   * @see #addStateChangeListener
   */
  public void removeStateChangeListener(StateChangeListener l)
  {
    listenerList.remove(StateChangeListener.class, l);
  }

  /**
   * Send a ChangeEvent, whose source is this bargrap, to
   * each listener.  This method method is called each time
   * a ChangeEvent is received from the model.
   *
   * @see #addStateChangeListener
   * @see javax.swing.event.EventListenerList
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
   * Returns data model that handles the sliders three
   * fundamental properties: minimum, maximum, value.
   *
   * @see #setModel
   */
  public final DoubleDataModel getModel()
  {
    return model;
  }

  /**
   * Set the data model.
   * @see DoubleDataModel#removeStateChangeListener(StateChangeListener)
   * @see DoubleDataModel#addStateChangeListener(StateChangeListener)
   * @see javax.swing.JComponent#firePropertyChange(String, Object, Object)
   */
  public void setModel(DoubleDataModel model)
  {
    DoubleDataModel oldModel = this.model;
    if (oldModel != null) {
      oldModel.removeStateChangeListener(stateChangeListener);
    }
    this.model = model;
    if (this.model != null) {
      this.model.addStateChangeListener(stateChangeListener);
    }
    firePropertyChange("model", oldModel, this.model);
  }

  /**
   * Set the model value.
   */
  public void setValue (double aValue)
  {
    double old = model.getValue();
    if (aValue == old)
      return;
    model.setValue(aValue);
    firePropertyChange("value", new Double(old), new Double(aValue));
  }

  /**
   * Returns the current value.
   */
  public final double getValue ()
  {
    return model.getValue();
  }

  /**
   * Get the minimum.
   */
  public final double getMinimum()
  {
    return model.getMinimum();
  }

  /**
   * Set the minimum and modifies the scale if needed.
   * A repaint request is generated.
   */
  public void setMinimum (double min)
  {
    double oldMin = model.getMinimum();
    model.setMinimum(min);
    firePropertyChange("minimum", new Double(oldMin), new Double(min));
  }

  /**
   * Returns the highest value represented by this bargraph
   */
  public final double getMaximum()
  {
    return model.getMaximum();
  }

  /**
   * Set the maximum.
   * A repaint request is generated.
   */
  public void setMaximum (double max)
  {
    double oldMax = model.getMaximum();
    model.setMaximum(max);
    firePropertyChange("maximum", new Double(oldMax), new Double(max));
  }

  /**
  * Get the preferred size.
  * It returns the actual size if there is one, the minimum size otherwise.
  */
 public Dimension getPreferredSize()
 {
  java.awt.LayoutManager layout = getLayout();
  if (layout != null) {
    return layout.preferredLayoutSize(this);
  } else {
    Dimension d = getSize();
    if ((d.width == 0) || (d.height == 0)) {
      return getMinimumSize();
    } else {
      return d;
    }
  }
 }

 /**
  * Returns the minimum size.
  */
 public Dimension getMinimumSize()
 {
  return new Dimension(10, 10);
 }


}
