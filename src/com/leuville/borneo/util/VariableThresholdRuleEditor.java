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

package com.leuville.borneo.util;

import com.leuville.borneo.util.*;

/**
 * A PropertyEditor for the orientation property.
 * This editor is a Choice type editor.
 *
 * @version 1.1
 */
public class VariableThresholdRuleEditor extends java.beans.PropertyEditorSupport
{
 public String[] getTags()
 {
   String [] result = {
         "Less",
         "Less or equal",
         "Equal",
         "Not equal",
         "Greater or equal",
         "Greater"
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
   VariableThreshold.Rule o = (VariableThreshold.Rule)getValue();
  if (o instanceof VariableThreshold.Less)
      return "Less";
  else if (o instanceof VariableThreshold.LessEqual)
      return "Less or equal";
  else if (o instanceof VariableThreshold.Equal)
      return "Equal";
  else if (o instanceof VariableThreshold.NotEqual)
      return "Not equal";
  else if (o instanceof VariableThreshold.MoreEqual)
      return "Greater or equal";
  else if (o instanceof VariableThreshold.More)
      return "Greater";
   return "";
 }
 public void setAsText(String text) throws java.lang.IllegalArgumentException
 {
  if (text.equals ("Less")) {
      setValue (new VariableThreshold.Less());
  } else if (text.equals ("Less or equal")) {
      setValue (new VariableThreshold.LessEqual());
  } else if (text.equals ("Equal")) {
      setValue (new VariableThreshold.Equal());
  } else if (text.equals ("Not equal")) {
      setValue (new VariableThreshold.Less());
  } else if (text.equals ("Greater or equal")) {
      setValue (new VariableThreshold.MoreEqual());
  } else if (text.equals ("Greater")) {
      setValue (new VariableThreshold.More());
  } else {
      throw new java.lang.IllegalArgumentException (text);
  }
    }
}
