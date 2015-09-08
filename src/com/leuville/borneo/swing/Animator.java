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

import java.awt.*;

/**
 * An Animator is a thread able to perform an animation
 * on an "Animatable" component.
 *
 * @version 1.1
 */
public class Animator extends Thread
{
  /**
   * The Animatable component.
   */
  private Animatable component;
  /**
   * The images array.
   */
  private transient Image[] images;
  /**
   * The animation delay in ms.
   */
  private int delay;
  /**
   * Constructs a new Animator.
   * @param component The animatable component.
   * @param images An array of images.
   * @param delay The animation delay in ms.
   */
  public Animator (Animatable component, Image[] images, int delay)
  {
    this.component = component;
    this.images = images;
    this.delay = delay;
  }
  /**
   * The run loop.
   */
  public void run ()
  {
    int i =0;
    while (true) {
      component.displayImage (images[i]);
      i++;
      i = i % images.length;
      try {
        sleep (delay);
      }
      catch (Exception e) {
      }
    }
  }
  /**
   * Set images array.
   */
  public void setImages (Image[] images)
  {
    this.images = images;
  }
  /**
   * Get an images array.
   */
  public Image[] getImages ()
  {
    return images;
  }
  /**
   * Set an Image using an index.
   */
  public void setImages (int index, Image im)
  {
    if (index >= images.length) {
      Image[] t = new Image[index+1];
      System.arraycopy (images, 0, t, 0, images.length);
      images = t;
    }
    images[index] = im;
  }
  /**
   * Get an Image using an index.
   */
  public Image getImages (int index)
  {
    return images[index];
  }
  /**
   * Set the delay.
   */
  public void setDelay (int delay)
  {
    this.delay = delay;
  }
  /**
   * Get the delay.
   */
  public int getDelay ()
  {
    return delay;
  }
}

