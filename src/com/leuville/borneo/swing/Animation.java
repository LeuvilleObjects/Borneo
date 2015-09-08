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

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.*;
import javax.swing.*;

/**
 * A Canvas with an Animation support (implementing the Animatable interface).
 * This object uses the Animator thread to perform animation loop.
 * A Mediatracker is used to load images.
 *
 * @version 1.1
 */

public class Animation extends JComponent implements Animatable
{
  /**
   * The current displayed image.
   */
  private transient Image curImage;
  /**
   * The Image names.
   */
  private String[] images;
  /**
   * The delay.
   */
  private int delay;
  /**
   * The animator thread.
   */
  private transient Animator animator;
  /**
   * The mediatracker.
   */
  private MediaTracker tracker;
  /**
   * Creates an new Animation.
   * The receiver is double buffered.
   */
  public Animation ()
  {
    tracker = new MediaTracker (this);
    setDoubleBuffered(true);
  }
  /**
   * Creates an new Animation.
   * Please note that if you use this constructor, the resulting object 
   * will not be serializable.
   * @param images The image array.
   * @param delay The delay between two images (in ms).
   */
  public Animation (Image[] images, int delay)
  {
    curImage = images[0];
    initAnimator (images, delay);
  }
  /**
   * Creates an new Animation.
   * @param names Array of images file names.
   * @param delay The delay between two images (in ms).
   */
  public Animation (String[] images, int delay)
  {
    this.images = images;
    initAnimator (images, delay);
  }
  /**
   * Creates an new Animation using images file names from T1 to T10 for instance.
   * @param path A pathname to an image directory.
   * @param root The root of images file names.
   * @param from The first suffix.
   * @param from The last suffix.
   * @param delay The delay between two images (in ms).
   */
  public Animation (String path, String root, int from, int to, int delay)
  {
    int n = to - from + 1;
    images = new String [n];
    for (int i = 0; i < n; i++) {
      images[i] = path + File.separator + root + 
           File.separator + (i+from) + 
           File.pathSeparator + ".gif";
    }
    initAnimator (images, delay);
  }
  /**
   * Load images.
   */
  private Image[] loadImages (String[] paths)
  {
    Toolkit t = Toolkit.getDefaultToolkit();
    Image[] im = new Image [paths.length];
    for (int i = 0; i < paths.length; i++) {
      im[i] = t.getImage (paths[i]);
      tracker.addImage (im[i], i);
    }
    return im;
  }
  /**
   * Initializes the animator.
   */
  private void initAnimator (String[] images, int delay)
  {
    Image[] im = loadImages (images);
    initAnimator (im, delay);
  }
  /**
   * Initializes the animator.
   * This is a blocking call.
   */
  private void initAnimator (Image[] im, int delay)
  {
    this.delay = delay;
    animator = new Animator (this, im, delay);
    try {
      tracker.waitForAll ();
    }
    catch (Exception e) {
    }
    animator.start ();
    animator.suspend ();
  }
  /**
   * Resumes the animator, if suspended.
   */
  public void resume () 
  {
    animator.resume();
  }
  /**
   * Suspend the animator.
   */
  public void suspend () 
  {
    animator.suspend();
  }
  /**
   * Updates the double buffer with the current image.
   */
  public void paintComponent (Graphics g)
  {
    java.awt.Rectangle ib = getBounds();
    java.awt.Insets in = getInsets();
    g.setColor (getBackground());
    g.fillRect (ib.x+in.left, ib.y+in.top, 
        ib.width-in.right, ib.height-in.bottom);
    if (curImage != null)
      g.drawImage (curImage, ib.x+in.left, ib.y+in.top, this);
  }
  /**
   * Animatable protocol.
   * A repaint request is generated.
   */
  public void displayImage (Image curImage)
  {
    this.curImage = curImage;
    repaint();
  }
  /**
   * Get images.
   */
  public String[] getImages ()
  {
      return images;
  }
  /**
   * Get an image.
   */
  public String getImage(int i)
  {
      return images[i];
  }
  /**
   * Set images.
   */
  public void setImages (String[] images)
  {
      this.images = images;
      initAnimator (images, delay);
  }
  /**
   * Replaces an image.
   */
  public void setImage (int i, String image)
  {
      images[i] = image;
      initAnimator (images, delay);
  }
  /**
   * Get the delay.
   */
  public int getDelay ()
  {
    if (animator != null)
      return animator.getDelay ();
    else
      return 0;
  }
  /**
   * Set the delay.
   */
  public void setDelay (int delay)
  {
    if (animator != null)
      animator.setDelay (delay);
  }
  /**
   * Load images when the component is unserialized (ie read from disk).
   */
  protected void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException 
  {
      s.defaultReadObject();
      initAnimator(images, delay);
  }
}

