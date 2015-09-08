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

package com.leuville.borneo.swing;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;

/**
 * This class provides a Panel able to display a background image.
 * This image is automatically scaled to fit the entire available size.
 *
 * @version 1.1
 */
public class ScaledImagePanel extends ImagePanel
{
 /**
  * Construct a new ScaledImagePanel.
  */
 public ScaledImagePanel ()
 {
 }
 /**
  * Construct a new ScaledImagePanel.
  */
 public ScaledImagePanel (Image image)
 {
   super(image);
 }
 /**
  * Draw the background image.
  * The image is scaled to cover the whole background.
  */
 public void drawBackground (Graphics g)
 {
  java.awt.Rectangle ib = getBounds();
  java.awt.Insets in = getInsets();
  g.setColor (getBackground());
  g.fillRect (ib.x+in.left, ib.y+in.top, ib.width-in.right-in.left, ib.height-in.top-in.bottom);
  if (image != null)
    g.drawImage (image, ib.x+in.left, ib.y+in.top, ib.width-in.right-in.left, ib.height-in.top-in.bottom, this);
 }
 /**
  * Returns the preferredSize (ie the minimumSize).
  * @see getMinimumSize
  */
 public Dimension getPreferredSize()
 {
  Dimension d = getSize();
  if ((d.width == 0) || (d.height == 0))
      return getMinimumSize();
  else
      return d;
 }
}


