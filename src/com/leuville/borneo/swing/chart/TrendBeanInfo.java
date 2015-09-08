/*
 * @(#) TrendBeanInfo.java version 1.10 date 98/06/16  Laurent Nel
 *
 * Copyright (c) 1997 Leuville Objects All Rights Reserved.
 *
 * Leuville Objects MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Leuville Objects SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

package com.leuville.borneo.swing.chart;

import java.beans.*;

/**
 * BeanInfo for a Trend.
 *
 * @version 1.1
 */
public class TrendBeanInfo extends SimpleBeanInfo
{
  public TrendBeanInfo()
  {
  }
  public java.awt.Image getIcon (int iconKind)
  {
    java.awt.Image i = null;
    switch (iconKind) {
      case BeanInfo.ICON_MONO_16x16:
      case BeanInfo.ICON_COLOR_16x16:
      case BeanInfo.ICON_MONO_32x32:
      case BeanInfo.ICON_COLOR_32x32:
            i = loadImage ("beaninfo/Trend_16.gif");
    }
    return i;
  }
}

