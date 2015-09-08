/*
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
 * BeanInfo for a MultiTrend.
 *
 * @version 1.1
 */
public class MultiTrendBeanInfo extends SimpleBeanInfo
{
  public MultiTrendBeanInfo()
  {
  }

  public java.awt.Image getIcon (int iconKind)
  {
    if (iconKind == BeanInfo.ICON_MONO_16x16 || iconKind == BeanInfo.ICON_COLOR_16x16) {
      java.awt.Image i = loadImage ("beaninfo/MultiTrend_16.gif");
      return i;
    }
    if (iconKind == BeanInfo.ICON_MONO_32x32 || iconKind == BeanInfo.ICON_COLOR_32x32) {
      java.awt.Image i = loadImage ("beaninfo/MultiTrend_16.gif");
      return i;
    }
    return null;
  }
}

