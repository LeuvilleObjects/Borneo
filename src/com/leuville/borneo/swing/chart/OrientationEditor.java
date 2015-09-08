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

import com.leuville.borneo.swing.chart.Orientation;

/**
 * A PropertyEditor for the orientation property.
 * This editor is a Choice type editor.
 *
 * @version 1.1
 */
public class OrientationEditor extends java.beans.PropertyEditorSupport
{
 public String[] getTags()
 {
   String [] result = {   Orientation.NORTH,
         Orientation.SOUTH,
         Orientation.EAST,
         Orientation.WEST
       };
   return result;
 }
 public boolean isPaintable()
 {
   return false;
 }
 public boolean supportsCustomEditor()
 {
   return false;
 }
 public String getAsText()
 {
   Orientation o = (Orientation)getValue();
   if (o != null)
     return o.toString();
   else
     return "Unknown";
 }
 public void setAsText(String text) throws java.lang.IllegalArgumentException
 {
  if (text.equals (Orientation.NORTH)) {
      setValue (Orientation.up);
  } else if (text.equals (Orientation.SOUTH)) {
      setValue (Orientation.down);
  } else if (text.equals (Orientation.WEST)) {
      setValue (Orientation.left);
  } else if (text.equals (Orientation.EAST)) {
      setValue (Orientation.right);
  } else {
      throw new java.lang.IllegalArgumentException (text);
  }
    }
}
