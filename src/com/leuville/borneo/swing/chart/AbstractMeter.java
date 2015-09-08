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
import java.io.*;
import com.leuville.borneo.swing.*;
import com.leuville.borneo.dataflow.*;

/**
 * The abstract base class for meter objects.
 * @version 1.1
 */
public abstract class AbstractMeter extends ChartComponent
{
  /**
   * The object used to determine the current drawing pattern (color, paint, ...)
   */
  protected DoubleDataPaint paint = createDoubleDataPaint();

  /**
   * The default paint class.
   */
  final protected class DefaultDoubleDataPaint implements DoubleDataPaint, Serializable
  {
    /**
     * The default paint is the foreground color.
     */
    final public Paint getPaint(DoubleDataModel model, double value)
    {
      return AbstractMeter.this.getForeground();
    }
  }

  /**
   * Returns an instance of DoubleDataPaint.
   */
  protected DoubleDataPaint createDoubleDataPaint()
  {
    return new DefaultDoubleDataPaint();
  }

  /**
   * Set the paint property.
   */
  public void setPaint(DoubleDataPaint paint)
  {
    this.paint = paint;
    repaint();
  }

  /**
   * Get the paint property.
   */
  final public DoubleDataPaint getPaint()
  {
    return paint;
  }

  /**
   * The interface of the needle drawer.
   */
  public static interface NeedleDrawer
  {
    public void drawNeedle (Graphics2D g, AbstractMeter meter, Arc2D needle);
  }

  /**
   * A classical needle.
   * <IMG SRC="doc-files/BasicNeedle.gif">
   */
  public static class BasicNeedle implements NeedleDrawer, Serializable
    {
      transient private GeneralPath needlePath = new GeneralPath();
      transient private Arc2D center = new Arc2D.Double(0, 0, 0, 0, 0, 0, Arc2D.PIE);

      public BasicNeedle()
      {
        needlePath = new GeneralPath();
        center = new Arc2D.Double(0, 0, 0, 0, 0, 0, Arc2D.PIE);
      }
      // perf.
      transient private Point2D p0 = new Point2D.Double(0.0, 0.0);

      public void drawNeedle(Graphics2D g, AbstractMeter meter, Arc2D needle)
      {
        needlePath.reset();
        double cw = 10;
        center.setArc(needle.getCenterX()-0.5*cw, needle.getCenterY()-0.5*cw, cw, cw, meter.getAngleStart(), 360, Arc2D.PIE);
        g.fill(center);
        double extent = needle.getAngleExtent();
        Utilities.getArcPoint(p0, center, extent-90);
        needlePath.moveTo((float)p0.getX(), (float)p0.getY());
        Utilities.getArcPoint(p0, needle, extent);
        needlePath.lineTo((float)p0.getX(), (float)p0.getY());
        Utilities.getArcPoint(p0, center,  extent+90);
        needlePath.lineTo((float)p0.getX(), (float)p0.getY());
        needlePath.closePath();
        g.setColor(meter.getLegendColor());
        g.fill (needlePath);
    }
    /**
     * Restores a serialized object.
     */
    private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException
    {
      s.defaultReadObject();
      needlePath = new GeneralPath();
      center = new Arc2D.Double(0, 0, 0, 0, 0, 0, Arc2D.PIE);
    }
   }

   /**
    * A needle drawn as a filled arc.
    * <IMG SRC="doc-files/SectorNeedle.gif">
    */
   public static class SectorNeedle implements NeedleDrawer, Serializable
   {
    static private AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);

    final public void drawNeedle (Graphics2D g, AbstractMeter meter, Arc2D needle)
    {
     Composite save = g.getComposite();
     g.setComposite(alpha);
     g.fill(needle);
     g.setComposite(save);
    }
  }

  /**
   * A needle with the current value.
   * <IMG SRC="doc-files/ValueNeedle.gif">
   */
  public static class ValueNeedle extends BasicNeedle
  {
    static private AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
    static private Insets in = new Insets(2, 2, 2, 2);

    private transient Point2D p = new Point2D.Double(0.0, 0.0);
    private transient RoundRectangle2D.Double r = new  RoundRectangle2D.Double (0, 0, 0, 0, 0, 0);

    final public void drawNeedle (Graphics2D g, AbstractMeter meter, Arc2D needle)
    {
      super.drawNeedle(g, meter, needle);
      Composite save = g.getComposite();
      g.setComposite(alpha);
      Utilities.getArcPoint(p, needle, needle.getAngleExtent());
      Font savefont = g.getFont();
      g.setFont (meter.getValueDrawerFont());
      String info = meter.getFormattedValue (0);
      FontMetrics fm = g.getFontMetrics();
      int h = fm.getHeight();
      int w = fm.stringWidth (info);
      r.setRoundRect (p.getX()-w/2-in.left, p.getY()-in.top-h/2,
                  w+in.right, h+in.bottom, (w+in.right)/2, (h+in.bottom)/2);
      g.setColor (meter.getForeground());
      g.fill(r);
      g.setColor (meter.getLegendColor());
      g.setComposite(save);
      g.draw(r);
      g.drawString (info, (float)(r.getX()+in.left), (float)(r.getY()+h-(r.getHeight()-h)/2));
      g.setFont(savefont);
    }
  }

  /**
   * The needle drawer object.
   */
  protected NeedleDrawer needleDrawer;

  /**
   * Set the needle drawer property.
   * The component is laid out.
   */
  public void setNeedleDrawer (NeedleDrawer needleDrawer)
  {
    this.needleDrawer = needleDrawer;
    setBounds(getBounds());
  }

  /**
   * Get the needle drawer.
   */
  final public NeedleDrawer getNeedleDrawer()
  {
    return needleDrawer;
  }
  /**
   * The model.
   */
  protected DoubleDataModel model;

  /**
   * The object which listens model state changes.
   * @see #createStateChangeListener()
   */
  protected StateChangeListener stateChangeListener = createStateChangeListener();

  private class ModelListener implements StateChangeListener, Serializable
  {
    public void stateChanged(StateChangeEvent event)
    {
      AbstractMeter.this.stateChanged(event);
    }
  }

  /**
   * This method is called when a change occurs in the model.
   * Ths receiver is updated according to the StateChange Event type.
   */
  protected void stateChanged(StateChangeEvent event)
  {
    switch (event.getType()) {
        case StateChangeEvent.RANGE:
          setBounds(getBounds());
          break;
        case StateChangeEvent.VALUE:
          AbstractMeter.this.needle.setAngleExtent(getAngle(AbstractMeter.this.model.getValue()));
        case StateChangeEvent.STATUS:
          repaint();
          break;
      }
  }
  /**
   * Creates the default model state change listener.
   * This listener calls stateChanged when a change occurs.
   * @see #stateChanged(StateChangeEvent)
   */
  protected StateChangeListener createStateChangeListener()
  {
    return new ModelListener();
  }

  /**
   * Returns data model.
   *
   * @see #setModel
   */
  final public DoubleDataModel getModel()
  {
    return model;
  }

  /**
   * Set the data model property.
   * The receiver is registered as a StateChangeListener of the model.
   *
   * @see #getModel
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
   * Start position in degrees.
   */
  protected double angleStart = 180;
  /**
   * Relative angle in degrees.
   */
  protected double angleExtent = -180;
  /**
   * Legend steps (ie the number of graphical marks drawn).
   */
  protected int legendSteps = 10;
  /**
   * Color used to draw legend.
   */
  protected Color legendColor = Color.black;

  /**
   * The external meter limit.
   */
  transient protected Arc2D.Double external;

  /**
   * The intermediate meter limit.
   */
  transient protected Arc2D.Double intermediate;

  /**
   * The internal meter limit.
   */
  transient protected Arc2D.Double internal;

  /**
   * The needle bounds.
   */
  transient protected Arc2D.Double needle;

  /**
   * Constructs a new Meter.
   */
  protected AbstractMeter ()
  {
    this (0.0, 100.0);
  }
  /**
   * Constructs a new Meter.
   */
  protected AbstractMeter (double min, double max)
  {
    this (135, -90, min, max);
  }
  /**
   * Constructs a new Meter.
   * This method instantiates a DefaultDoubleDataModel.
   */
  protected AbstractMeter (double angleStart, double angleExtent, double min, double max)
  {
    Common.checkMinMax (min, max);
    this.angleStart = angleStart;
    this.angleExtent = angleExtent;
    model = new DefaultDoubleDataModel(min, Status.HIGH_CONFIDENCE, min, max);
    model.addStateChangeListener(stateChangeListener);
  }

 /**
  * The valueScale control.
  */
  protected boolean valueScale = true;

  /**
   * Set the valueScale property.
   */
  public void setValueScale (boolean valueScale)
  {
   this.valueScale = valueScale;
   setBounds(getBounds());
  }
  /**
   * Return the valueScale property.
   */
  public boolean isValueScale()
  {
     return valueScale;
  }

  /**
   * Returns real bounds of an arc.
   *
   * @see com.leuville.borneo.swing.Utilities#getBounds2D
   */
  protected Rectangle2D getRealBounds (Arc2D arc)
  {
   int save = arc.getArcType();
   arc.setArcType(Arc2D.PIE);
   Rectangle2D res = Utilities.getBounds2D(arc);
   arc.setArcType(save);
   return res;
  }

  /**
   * Returns the intermediate rectangle used to create the intermediate arc.
   */
  protected Rectangle2D getIntermediateRect(Rectangle2D r)
  {
    return new Rectangle2D.Double(r.getX()+2, r.getY()+2, r.getWidth()-4, r.getHeight()-4);
  }

  /**
   * Returns the rectangle used to create the needle arc.
   */
  protected Rectangle2D getNeedleRect(Rectangle2D r)
  {
    Rectangle2D tmp = getIntermediateRect(r);
    growRect(tmp, - r.getWidth()*0.05, - r.getHeight()*0.05);
    return tmp;
  }

  /**
   * Returns the internal rectangle used to create the internal arc.
   */
  protected Rectangle2D getInternalRect(Rectangle2D r)
  {
    Rectangle2D tmp = getNeedleRect(r);
    growRect(tmp, -2, -2);
    return tmp;
  }

  /**
   * Returns the initial centered frame.
   */
  protected Rectangle2D getCenteredFrame(Rectangle2D initRect)
  {
   Arc2D ext = new Arc2D.Double (initRect, angleStart, angleExtent, Arc2D.Double.OPEN);
   return getRealBounds(ext);
  }

  /**
   * Layout the component.
   */
  protected void layoutComponent ()
  {
   super.layoutComponent ();
   double min = getMinimum();
   double max = getMaximum();
   double radius;
   com.leuville.borneo.swing.Rectangle ib = new com.leuville.borneo.swing.Rectangle(getInsideBounds ());
   if (valueScale) {
    Font f = getValueDrawerFont();
    String minString = Common.roundString (min, max-min, defaultValueDisplayPrecision);
    String maxString = Common.roundString (max, max-min, defaultValueDisplayPrecision);
    int w1 = Common.stringWidth (f, minString);
    int w2 = Common.stringWidth (f, maxString);
    int fh = f.getSize();
    radius = Math.min(ib.height-2*fh, ib.width-2*Math.max(w1, w2));
   } else {
    radius = Math.min(ib.height, ib.width);
   }
   Rectangle2D rb = getCenteredFrame(new Rectangle2D.Double(ib.x, ib.y, radius, radius));
   Rectangle2D r = new Rectangle2D.Double(ib.x-rb.getX()+(ib.width-rb.getWidth())/2, ib.y-rb.getY()+(ib.height-rb.getHeight())/2, radius, radius);
   external = new Arc2D.Double (r, angleStart, angleExtent, Arc2D.Double.OPEN);

   growRect(r, -2, -2);
   intermediate = new Arc2D.Double (r, angleStart, angleExtent, Arc2D.Double.OPEN);
   growRect(r, - radius*0.05, - radius*0.05);
   needle = new Arc2D.Double (r, angleStart, getAngle(getValue()), Arc2D.Double.PIE);
   growRect(r, - 2, - 2);
   internal = new Arc2D.Double (r, angleStart, angleExtent, Arc2D.Double.OPEN);

  }

  private final void growRect(Rectangle2D r, double rx, double ry)
  {
    r.setRect(r.getX()-rx, r.getY()-ry, r.getWidth()+2*rx, r.getHeight()+2*ry);
  }
  /**
   * Set the model value and updates the represented value.
   * A repaint request is generated.
   * Fires a PropertyChangeEvent.
   */
  public void setValue (double value)
  {
    double previous = model.getValue();
    model.setValue(value);
    firePropertyChange("value", new Double(previous), new Double(value));
  }
  /**
   * Returns the model current value.
   */
  final public double getValue ()
  {
    return model.getValue();
  }
   /**
     * Return the value of a source.
     * @param i The number of the source.
     */
   public Number getValue(int i)
   {
     if (i != 0)
       throw new IllegalArgumentException();
     else
       return new Double (getValue());
   }
   /**
     * Return the formatted value of a source as a String.
     * @param i The number of the source.
     */
   public String getFormattedValue(int i)
   {
     if (i != 0)
       throw new IllegalArgumentException();
     else
      return Common.roundString (getValue(), getMaximum()-getMinimum(), defaultValueDisplayPrecision);
   }

  /**
   * Paint the receiver.
   */
  protected void defaultPaintComponent (Graphics2D g)
  {
    drawBackground (g);
    g.setPaint(paint.getPaint(model, model.getValue()));
    needleDrawer.drawNeedle(g, this, needle);
    if (drawValue)
      drawValue(g, getColor(0), getFormattedValue(0), needle.getCenterX(), needle.getCenterY());
    super.defaultPaintComponent(g);
  }
  /**
   * Draws the background.
   */
  abstract protected void drawBackground (Graphics2D g);
  /**
   * Returns the angle corresponding to the value.
   */
  protected final double getAngle (double v)
  {
    double min = getMinimum();
    return angleExtent * (v - min) / (getMaximum()-min);
  }
  /**
   * Returns the lowest value represented by this bargraph
   */
  final public double getMinimum()
  {
    return model.getMinimum();
  }
  /**
   * Set the minimum.
   */
  public void setMinimum (double min)
  {
    model.setMinimum(min);
  }
  /**
   * Returns the highest value represented by this bargraph
   */
  final public double getMaximum()
  {
    return model.getMaximum();
  }
  /**
   * Set the maximum.
   */
  public void setMaximum (double max)
  {
    model.setMaximum(max);
  }
 /**
  * Returns the legendSteps.
  */
  final public int getLegendSteps()
  {
    return legendSteps;
  }
  /**
   * Set the legendSteps.
   */
  public void setLegendSteps (int legendSteps)
  {
    this.legendSteps = legendSteps;
    setBounds (getBounds());
  }
  /**
   * Returns the legendColor.
   */
  final public Color getLegendColor()
  {
    return legendColor;
  }
  /**
   * Set the legendColor.
   */
  public void setLegendColor (Color legendColor)
  {
    this.legendColor = legendColor;
    setBounds (getBounds());
  }
 /**
  * Returns the start position.
  */
  final public double getAngleStart()
  {
    return angleStart;
  }
  /**
   * Set the start position.
   */
  public void setAngleStart (double angleStart)
  {
    this.angleStart = angleStart;
    setBounds (getBounds());
  }
  /**
   * Returns the angle.
   */
  final public double getAngleExtent()
  {
    return angleExtent;
  }
  /**
   * Set the angle.
   */
  public void setAngleExtent (double angleExtent)
  {
    this.angleExtent = angleExtent;
    setBounds (getBounds());
  }

  /**
   * The draw value property.
   */
  protected boolean drawValue = false;

  /**
   * Set the draw value property.
   */
  public void setDrawValue(boolean drawValue)
  {
    this.drawValue = drawValue;
    repaint();
  }

  /**
   * Get the draw value property.
   */
  final public boolean isDrawValue()
  {
    return drawValue;
  }

  /**
   * Return the minimumSize.
   */
   public Dimension getMinimumSize ()
   {
     return new Dimension(50, 50);
   }
}

