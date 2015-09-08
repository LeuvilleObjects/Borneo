package com.leuville.borneo.util;

import java.beans.*;
import java.util.*;

public class VariableThresholdBeanInfo extends SimpleBeanInfo
{
  public BeanInfo[] getAdditionalBeanInfo()
  {
    return new BeanInfo[] {
      new SimpleBeanInfo() {
        public PropertyDescriptor[] getPropertyDescriptors()
        {
          try {
            PropertyDescriptor com = new PropertyDescriptor ("rule", new com.leuville.borneo.util.VariableThreshold().getClass());
            com.setPropertyEditorClass (new com.leuville.borneo.util.VariableThresholdRuleEditor().getClass());
            return new PropertyDescriptor[] {
              com
            };
          } catch (IntrospectionException e) {
            return null;
          }
        }
      }
    };
  }
 public java.awt.Image getIcon (int iconKind)
 {
  if (iconKind == BeanInfo.ICON_MONO_16x16 || iconKind == BeanInfo.ICON_COLOR_16x16) {
    java.awt.Image i = loadImage ("beaninfo/VariableThreshold_16.gif");
    return i;
  }
  if (iconKind == BeanInfo.ICON_MONO_32x32 || iconKind == BeanInfo.ICON_COLOR_32x32) {
    java.awt.Image i = loadImage ("beaninfo/VariableThreshold_16.gif");
    return i;
  }
  return null;
 }
}
