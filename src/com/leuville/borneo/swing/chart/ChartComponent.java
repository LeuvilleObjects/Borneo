/*
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
import java.awt.event.*;
import java.awt.geom.*;
import java.beans.*;
import java.io.*;
import com.leuville.borneo.swing.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.ListCellRenderer;

/**
  * The abstract base class for chart components.
  *
  * @version 1.2.1.1
  */
abstract public class ChartComponent extends JComponent implements Serializable, ChartInfo
{
 /**
  * This interface describes the protocol needed to draw a list of values
  * in a list of rectangles.
  */
 static public interface ValueDrawer
 {
   /**
    * Draw each value in each rectangle using provided settings.
    */
   public void draw (Graphics2D g, Color stringColor, Color foreground, Color background, String msg, double x, double y);
 }
 /**
  * Basic implementation of ValueDrawer interface.
  * Values are displayed in color rectangles.
  */
 static public class BasicValueDrawer implements ValueDrawer, java.io.Serializable
 {
  private final static Insets in = new Insets (2, 2, 2, 2);
  private final Rectangle2D r1 = new Rectangle2D.Double(0, 0, 0, 0);
  private final Rectangle2D r2 = new Rectangle2D.Double(0, 0, 0, 0);
   public void draw(Graphics2D g, Color stringColor, Color foreground, Color background, String info, double x, double y)
   {
      FontMetrics fm = g.getFontMetrics();
      int h = fm.getHeight();
      int w = fm.stringWidth (info);
      r1.setRect (x-w/2-in.left, y-in.top-h/2, w+in.right+in.left, h+in.bottom+in.top);
      g.setColor (background);
      g.fill (r1);
      g.setColor (stringColor);
      g.draw (r1);
      g.setColor (foreground);
      r2.setRect(r1.getX()+1, r1.getY()+1, r1.getWidth()-2, r1.getHeight()-2);
      g.draw (r2);
      g.setColor (stringColor);
      g.drawString (info, (float)(r2.getX()+in.left), (float)(r2.getY()+h-(r2.getHeight()-h)/2));
   }
 }
 /**
  * The default MessageDrawer object.
  */
 static protected ValueDrawer defaultValueDrawer = new BasicValueDrawer();
 /**
  * The default precision used to display a value (1/1000).
  */
 static protected double defaultValueDisplayPrecision = 0.001;
 /**
  * The MessageDrawer object.
  */
 protected transient ValueDrawer valueDrawer = defaultValueDrawer;
 /**
  * The font used to draw values.
  */
 protected Font valueDrawerFont = new Font ("default", Font.PLAIN, 10);
 /**
  * The MessageDrawer control.
  * This property is true if the user has requested
  * an additionnal display of chart values.
  */
 protected boolean drawValue = false;
 /**
  * Default colors.
  */
 static protected Color [] defaultColors = {
        Color.blue, Color. cyan,
        Color.green , Color.red,
        Color.yellow, Color.magenta,
        Color.orange, Color.pink,
        Color.gray, Color.white };
 /**
  * Rectangle describing drawable component surface (ie bounds() minus borders).
  */
 protected com.leuville.borneo.swing.Rectangle insideBounds;
 /**
  * Rectangle describing the front rectangle of a 3D rectangle.
  */
 protected com.leuville.borneo.swing.Rectangle frontRectangle;
 /**
  * Rectangle describing the back rectangle of a 3D rectangle.
  */
 protected com.leuville.borneo.swing.Rectangle backRectangle;
 /**
  * Rectangle describing drawable surface (ie insideBounds plus spacing).
  */
 protected com.leuville.borneo.swing.Rectangle drawableBounds;
 /**
  * Spacing around the drawable surface.
  */
 protected Insets spacing ;
 /**
  * The default null depth object.
  */
 protected static final Dimension defaultNullDepth = new Dimension (0, 0);
 /**
  * The default font.
  */
 public static final Font defaultFont = new Font ("default", Font.PLAIN, 12);
 /**
  * Default constructor.
  */
 protected ChartComponent ()
 {
  setDoubleBuffered(true);
  setOpaque(false);
  setName (getClass().getName());
  spacing = new Insets (0, 0, 0, 0);
  insideBounds = new com.leuville.borneo.swing.Rectangle (0, 0, 0, 0);
  drawableBounds = new com.leuville.borneo.swing.Rectangle (0, 0, 0, 0);
  frontRectangle = new com.leuville.borneo.swing.Rectangle (0, 0, 0, 0);
  backRectangle = new com.leuville.borneo.swing.Rectangle (0, 0, 0, 0);
  setFont (defaultFont);
}

 /**
  * Set the drawValue property.
  */
 public void setDrawValue (boolean drawValue)
 {
   this.drawValue = drawValue;
   repaint();
 }
 /**
  * Get the drawValue property.
  */
 public boolean isDrawValue ()
 {
   return drawValue;
 }
 /**
  * Return the number of sources of the chart.
  * The default value is 1.
  */
 public int getNbSource()
 {
   return 1;
 }
 /**
  * Return the color of a source.
  * @param i The number of the source.
  */
 public java.awt.Color getColor(int i)
 {
   if (i != 0)
     throw new IllegalArgumentException ();
   return getForeground();
 }
 /**
  * Return the formatted value of a source as a String.
  * @param i The number of the source.
  */
 abstract public String getFormattedValue(int i);
 /**
  * Return the info associated to the source.
  * @param i The number of the source.
  */
 public String getLabel(int i)
 {
   if (i != 0)
     throw new IllegalArgumentException ();
   return getName();
 }
 /**
  * Reshape the receiver.
  * The offscreen Image is destroyed, the component is laid out and repainted.
  * @see java.awt.Component
  * @see layoutComponent
  */
 public void setBounds (int x, int y, int width, int height)
 {
  super.setBounds (x, y, width, height);
  layoutComponent ();
  repaint();
 }
 /**
  * Layout the receiver.
  * This method set insideBounds & insets according to bounds.
  * Insets is borderWidth + spacing.
  * @see updateDrawableBounds
  */
 protected void layoutComponent ()
 {
  Insets in = (Insets) getInsets().clone();
  in.top += spacing.top;
  in.left += spacing.left;
  in.bottom += spacing.bottom;
  in.right += spacing.right;
  Dimension d = getSize();
  insideBounds.setBounds (in.left, in.top, d.width-in.left-in.right, d.height-in.top-in.bottom);
  updateDrawableBounds ();
 }
 /**
  * Update the drawableBounds member according to insideBounds and spacing.
  */
 protected void updateDrawableBounds()
 {
  // here, we would like to avoid Rectangle creation
  drawableBounds.x = insideBounds.x - spacing.left;
  drawableBounds.y = insideBounds.y - spacing.top;
  drawableBounds.width = insideBounds.width + spacing.left + spacing.right;
  drawableBounds.height = insideBounds.height + spacing.top + spacing.bottom;
  Dimension d = getDepth();
  backRectangle.setToBackRectangleOf (insideBounds, d);
  frontRectangle.setToFrontRectangleOf (insideBounds, d);
 }
 /**
  * Default implementation. Does nothing.
  */
 protected void defaultPaintComponent (Graphics2D g)
 {
 }
 /**
  * Draw a value.
  */
 protected void drawValue(Graphics2D g, Color sourceColor, String msg, double x, double y)
 {
  g.setFont (valueDrawerFont);
  valueDrawer.draw(g, getLegendColor(), getForeground(), getBackground(), msg, x, y);
 }

  /**
  * The legend and scale color.
  */
 protected Color legendColor = Color.black;

 /**
  * Returns the legend color.
  */
 public Color getLegendColor()
 {
   return legendColor;
 }
 /**
  * Sets the legend color.
  * Areas are laid out.
  * @see setAreas
  */
 public void setLegendColor (Color legendColor)
 {
   this.legendColor = legendColor;
   repaint();
 }

 /**
  * If the UI delegate is non-null, call its paint
  * method.  We pass the delegate a copy of the Graphics
  * object to protect the rest of the paint code from
  * irrevocable changes (e.g. Graphics.translate()).
  *
  * @see #paint
  * @see #defaultPaintComponent
  */
 protected void paintComponent(Graphics g)
 {
    super.paintComponent(g);
    defaultPaintComponent((Graphics2D)g);
 }
 /**
  * Returns the inside bounds as a com.leuville.borneo.swing.Rectangle.
  * @return Inside bounds
  */
 public com.leuville.borneo.swing.Rectangle getInsideBounds ()
 {
   return insideBounds;
 }
 /**
  * Returns the draw area (ie insideBounds + spacing).
  */
 public com.leuville.borneo.swing.Rectangle getDrawableBounds ()
 {
  return drawableBounds;
 }
 /**
  * Set the spacing property.
  */
 protected void setSpacing (Insets spacing)
 {
  this.spacing = spacing;
  setBounds (getBounds());
 }
 /**
  * Get the spacing property.
  */
 public Insets getSpacing()
 {
  return spacing;
 }
 /**
  * Returns the inside size as a Dimension.
  * @return Inside size
  */
 public java.awt.Dimension getInsideSize()
 {
  java.awt.Dimension d = getSize ();
  java.awt.Insets insets = getInsets();
  d.width -= insets.left+insets.right;
  d.height -= insets.top+insets.bottom;
  return d;
 }

 /**
  * Returns the depth of the receiver. This depth is used to draw 3D objects.
  * <IMG SRC="doc-files/SpaceObject.gif">
  * This methods returns a (0, 0) depth.
  */
 public java.awt.Dimension getDepth ()
 {
  return defaultNullDepth;
 }

 /**
  * Returns front rectangle of the receiver used to draw 3D object.
  * <IMG SRC="doc-files/SpaceObject.gif">
  */
 public com.leuville.borneo.swing.Rectangle frontRectangle ()
 {
  return frontRectangle;
 }

 /**
  * Returns back rectangle of the receiver used to draw 3D object.
  * If the receiver is a 2D object, front rectangle and back rectangle are equal.
  * <IMG SRC="doc-files/SpaceObject.gif">
  */
 public com.leuville.borneo.swing.Rectangle backRectangle ()
 {
  return backRectangle;
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
  * Return the root container (ie Frame) of this component.
  */
 public Container getRootParent ()
 {
  Container c = getParent(), prev = null;
  while (c != null) {
    prev = c;
    c = c.getParent();
  }
  return prev;
 }

 /**
  * Return true if infoMode or drawValue is true.
  */
 protected boolean needTotalClear ()
 {
   return (drawValue);
 }
 /**
  * Set the valueDrawer font property.
  */
 public void setValueDrawerFont (Font valueDrawerFont)
 {
  this.valueDrawerFont = valueDrawerFont;
  repaint();
 }
 /**
  * Get the valueDrawer font property.
  */
 public Font getValueDrawerFont()
 {
  return valueDrawerFont;
 }
 /**
  * Restores a serialized object.
  */
 private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException
 {
  s.defaultReadObject();
  valueDrawer = defaultValueDrawer;
 }
}




