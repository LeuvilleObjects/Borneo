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

package com.leuville.borneo.swing.chart;

import java.awt.Color;
import java.util.ArrayList;

/**
 * This class provides a way to store chart attribute lists like labels and colors.
 *
 * @version 1.1
 */
public class ChartSettings implements java.io.Serializable
{
  /**
   * The labels array.
   */
  protected ArrayList labels = new ArrayList();

  /**
   * Get a label.
   */
  public String getLabel(int index)
  {
   try {
    String res = (String)labels.get(index);
    if (res == null) {
      return "";
    } else {
      return res;
    }
   } catch (IndexOutOfBoundsException e) {
    return "";
   }
  }

  /**
   * Get the label indexed property.
   */
  public String[] getLabel()
  {
   String[] res = new String[labels.size()];
   return (String[])labels.toArray(res);
  }

  /**
   * Set a label.
   */
  public void setLabel(int index, String label)
  {
   setObject(labels, index, label);
  }

  /**
   * Set the label indexed property.
   */
  public void setLabel(String strings[])
  {
   addArray(labels, strings);
  }

  /**
   * Convenience method.
   * The list capacity is set to the length of the objects array
   * and then the content is added to the list.
   *
   * @param list    The resulting ArrayList.
   * @param objects The object array to add to the list.
   */
  static public void addArray(ArrayList list, Object[] objects)
  {
   list.clear();
   list.ensureCapacity(objects.length);
   for (int i = 0; i < objects.length; i++) {
    list.add(objects[i]);
   }
  }

  /**
   * Convenience method.
   * Inserts an object at a position in a list.
   * The list is expanded if it is too small.
   *
   * @param list    The resulting ArrayList.
   * @param index   The insertion position.
   * @param object  The object to insert.
   */
  static public void setObject(ArrayList list, int index, Object object)
  {
   if (index >= list.size()) {
    list.ensureCapacity(index);
   }
   list.add (index, object);
  }

  /**
   * The color array.
   */
  protected ArrayList colors = new ArrayList();

  /**
   * Get a color.
   */
  public Color getColor(int index)
  {
   return (Color)colors.get (index % colors.size());
  }

  /**
   * Get the color indexed property.
   */
  public Color[] getColor()
  {
   Color[] res = new Color[colors.size()];
   return (Color[])colors.toArray(res);
  }

  /**
   * Set a color.
   */
  public void setColor(int index, Color color)
  {
   setObject(colors, index, color);
  }

  /**
   * Set the color indexed property.
   */
  public void setColor(Color cols[])
  {
   addArray(colors, cols);
  }
}
