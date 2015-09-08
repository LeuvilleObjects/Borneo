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
 * An abstraction of a graphic command.
 *
 * @version 1.2
 */
public interface GraphicsCommand
{
  /**
   * This method is used to execute the command, ie draw the associated object.
   */
  public void exec (Graphics2D g, Object o);
  /**
   * Returns the depth used to draw a 3D object.
   * <IMG SRC="doc-files/SpaceObject.gif">
   */
  public java.awt.Dimension getDepth ();
}

