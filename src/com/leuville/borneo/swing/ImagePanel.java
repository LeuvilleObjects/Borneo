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
import javax.swing.*;

/**
 * An ImagePanel is a double buffer panel able to display a background image.
 * This class implements the Animatable interface, so it may be used also as
 * animation support.
 *
 * @version 1.1
 */
public class ImagePanel extends JPanel implements Animatable
{
 /**
  * The background image.
  */
 protected transient Image image;
 /**
  * Construct a new ImagePanel.
  */
 public ImagePanel ()
 {
   setDoubleBuffered(true);
 }
 /**
  * Construct a new ImagePanel.
  */
 public ImagePanel (Image image)
 {
   this.image = image;
 }
 /**
  * Draw the background image.
  * 1. Clear surface not covered by the image.
  * 2. Draw the image in the middle of the panel.
  * The image is not scaled.
  */
 public void paintComponent (Graphics g)
 {
   // clear surface not covered by the image
  //super.drawBackground(g);
   //g.setColor (getBackground());
  java.awt.Rectangle ib = getBounds();
  //g.fillRect (ib.x, ib.y, ib.width, ib.height);
  // the image is centered
  if (image != null) {
    int w = image.getWidth(this);
    int h = image.getHeight(this);
    g.drawImage (image, ib.x + (ib.width-w)/2, ib.y + (ib.height-h)/2, this);
   }
 }
 /**
  * Updates the background image.
  * Animatable implementation.
  */
 public void displayImage (Image image)
 {
  this.image = image;
  repaint();
 }
 /**
  * Set the background image.
  */
 public void setImage (Image image)
 {
   displayImage (image);
 }
 /**
  * Get the background image.
  */
 public Image getImage ()
 {
   return image;
 }
 /**
  * Returns the preferredSize (ie the minimumSize).
  * @see getMinimumSize
  */
 public Dimension getPreferredSize()
 {
  return getMinimumSize();
 }
 /**
  * Return the size needed to display the entire image.
  */
 public Dimension getMinimumSize()
 {
  if (image != null) {
    int w = image.getWidth(this);
    int h = image.getHeight(this);
    return new Dimension (w, h);
   } else
    return new Dimension(20, 20);
 }
}




