/*
 *
 * Leuville Objects MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Leuville Objects SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

package com.leuville.borneo.swing;

import com.leuville.borneo.swing.*;

/**
 * A PropertyEditor forGraphicsCommand properties.
 * This editor is a Choice type editor.
 *
 * @version 1.1
 */
public class RectCommandEditor extends java.beans.PropertyEditorSupport
{
 private RectCommand[] commands = {
    Draw3DRectCommand.defaultCommand,
    DrawRectCommand.defaultCommand,
    DrawRoundRectCommand.defaultCommand,
    Fill3DRectCommand.defaultCommand,
    FillRectCommand.defaultCommand,
    FillRoundRectCommand.defaultCommand
 };
 public String[] getTags()
 {
  String[] tags = new String[commands.length];
  for (int i = 0; i < tags.length; i++) {
    tags[i] = commands[i].toString();
  }
  return tags;
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
   RectCommand c = (RectCommand)getValue();
   return c.toString();
 }
 public void setAsText(String text) throws java.lang.IllegalArgumentException
 {
  for (int i = 0; i < commands.length; i++) {
    if (text.equals (commands[i].toString())) {
      setValue (commands[i]);
      return;
    }
  }
  throw new java.lang.IllegalArgumentException (text);
 }
}
