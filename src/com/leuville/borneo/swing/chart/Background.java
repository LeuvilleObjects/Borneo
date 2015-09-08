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

/**
 * Background is the abstract base class for specialized backgrounds like tank, pump ...
 *
 * @version 1.1.1.1
 */
abstract public class Background extends JPanel
{
 /**
  * The active component displayed on top of the background.
  * This component may be a BarGraph, EllipseGraph, Knob , ...
  */
 protected ChartComponent component;
 /**
  * The component bounds.
  */
 transient protected Rectangle2D componentBounds;

 /**
  * Set the component.
  * @see #addImpl(Component, Object, int)
  */
 public void setComponent(ChartComponent component)
 {
  if (this.component != null) {
    remove(this.component);
  }
  this.component = component;
  if (this.component != null) {
    add(this.component);
  }
  repaint();
 }

 /**
  * Add a component to the receiver.
  * If the component is a ChartComponent, then it is retained as the active component.
  */
 protected void addImpl(Component component, Object constraints, int index)
 {
  if (component instanceof ChartComponent) {
    if (this.component != null) {
      remove(this.component);
    }
    this.component = (ChartComponent)component;
    if (this.component != null) {
      super.addImpl(this.component, constraints, index);
    }
  } else {
    super.addImpl(component, constraints, index);
  }
 }

 /**
  * @see #remove(Component)
  */
 public void remove(int index)
 {
  remove(getComponent(index));
 }
 /**
  * Removes a component.
  */
 public void remove(Component component)
 {
  if (component == this.component) {
    this.component = null;
  }
  super.remove(component);
 }

 /**
  * Get the active component.
  */
 final public ChartComponent getComponent()
 {
  return component;
 }

 /**
  * Constructs a new Background.
  * The LayoutManager is set to NullLayout.
  *
  * @see com.leuville.borneo.swing.NullLayout
  */
 protected Background()
 {
  super(new com.leuville.borneo.swing.NullLayout());
 }

 /**
  * Set the bounds of the receiver.
  * Calls layoutComponent to compute the bounds of the active component.
  * @see #layoutComponent()
  */
 public void setBounds (int x, int y, int width, int height)
 {
  super.setBounds (x, y, width, height);
  layoutComponent ();
  if (component != null)
    component.setBounds(componentBounds.getBounds());
  repaint();
 }

 /**
  * Layout the active component.
  */
 abstract protected void layoutComponent();

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
  * Get the preferred size.
  * It returns the actual size if there is one, the minimum size otherwise.
  */
 public Dimension getPreferredSize()
 {
  Dimension d = getSize();
  if ((d.width == 0) || (d.height == 0)) {
    return getMinimumSize();
  } else {
    return d;
  }
 }

 /**
  * Returns the minimum size.
  */
 public Dimension getMinimumSize()
 {
  return new Dimension(50, 50);
 }
}
