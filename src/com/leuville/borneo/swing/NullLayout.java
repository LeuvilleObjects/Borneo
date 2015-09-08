/*
 * Copyright (c) Leuville Objects All Rights Reserved.
 *
 * Leuville Objects MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Leuville Objects SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

package com.leuville.borneo.swing;

import java.awt.*;

/**
 * NullLayout allows placement of components in absolute positions.
 *
 * @version 1.1
 */
public class NullLayout implements LayoutManager2, java.io.Serializable
{
  /** generated Serialized Version UID */
//  static final long serialVersionUID = -1919857869177070440L;

  /** Adds the specified component with the specified name to
  * the layout.
  * @param name the component name
  * @param comp the component to be added
  */
  public void addLayoutComponent(String name, Component comp) {
    throw new IllegalArgumentException();
  }

  /** Removes the specified component from the layout.
  * @param comp the component to be removed
  */
  public void removeLayoutComponent(Component comp) {
  }

  /** Calculates the preferred dimension for the specified
  * panel given the components in the specified parent container.
  * @param parent the component to be laid out
  *
  * @see #minimumLayoutSize
  */
  public Dimension preferredLayoutSize(Container parent) {
    return parent.getPreferredSize();
  }

  /** Calculates the minimum dimension for the specified
  * panel given the components in the specified parent container.
  * @param parent the component to be laid out
  * @see #preferredLayoutSize
  */
  public Dimension minimumLayoutSize(Container parent) {
    return parent.getMinimumSize();
  }

  /** Lays out the container in the specified panel.
  * @param parent the component which needs to be laid out
  */
  public void layoutContainer(Container parent) {
  }

  /** Adds the specified component to the layout, using the specified
  * constraint object.
  * @param comp the component to be added
  * @param constr  where/how the component is added to the layout.
  */
  public void addLayoutComponent(Component comp, Object constr) {
    if (constr != null)
      throw new IllegalArgumentException();
  }

  /** Returns the maximum size of this component.
  * @see java.awt.Component#getMinimumSize()
  * @see java.awt.Component#getPreferredSize()
  * @see LayoutManager
  */
  public Dimension maximumLayoutSize(Container target) {
    return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
  }

  /** Returns the alignment along the x axis.  This specifies how
  * the component would like to be aligned relative to other
  * components.  The value should be a number between 0 and 1
  * where 0 represents alignment along the origin, 1 is aligned
  * the furthest away from the origin, 0.5 is centered, etc.
  */
  public float getLayoutAlignmentX(Container target) {
    return 0;
  }

  /** Returns the alignment along the y axis.  This specifies how
  * the component would like to be aligned relative to other
  * components.  The value should be a number between 0 and 1
  * where 0 represents alignment along the origin, 1 is aligned
  * the furthest away from the origin, 0.5 is centered, etc.
  */
  public float getLayoutAlignmentY(Container target) {
    return 0;
  }

  /** Invalidates the layout, indicating that if the layout manager
  * has cached information it should be discarded.
  */
  public void invalidateLayout(Container target) {
  }
}
