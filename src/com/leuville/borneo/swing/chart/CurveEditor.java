/*
 * Leuville Objects MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Leuville Objects SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

package com.leuville.borneo.swing.chart;

import com.leuville.borneo.swing.chart.Curve;

/**
 * A PropertyEditor for the orientation property.
 * This editor is a Choice type editor.
 *
 * @version 1.1
 */
public class CurveEditor extends java.beans.PropertyEditorSupport
{
 public CurveEditor()
 {
 }
 public String[] getTags()
 {
   String [] result = {
         "Dot",
         "Line",
         "Plotted line",
         "Thin bar",
         "Polygon",
         "Plain",
         "Histo",
         "3-D histo",
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
 private final static Class DotClass = new Curve.Dot().getClass();
 private final static Class LineClass = new Curve.Line().getClass();
 private final static Class ThinBarClass = new Curve.ThinBar().getClass();
 private final static Class PolygonClass = new Curve.Polygon().getClass();
 private final static Class PlainClass = new Curve.Plain().getClass();
 private final static Class HistoClass = new Curve.Histo().getClass();
 private final static Class Histo3DClass = new Curve.Histo3D().getClass();
 private final static Class PlottedLineClass = new Curve.PlottedLine().getClass();
 public String getAsText()
 {
/*
   Curve.Type t = (Curve.Type)getValue();
   Class o = t.getClass();
   if (o == DotClass)
     return "Dot";
   else if (o == LineClass)
     return "Line";
   else if (o == PlottedLineClass)
     return "Plotted line";
   else if (o == ThinBarClass)
     return "Thin bar";
   else if (o == PolygonClass)
     return "Polygon";
   else if (o == PlainClass)
     return "Plain";
   else if (o == HistoClass)
     return "Histo";
   else if (o == Histo3DClass)
     return "Histo3D";
   else
*/
     return "Unknown";
 }
 public void setAsText(String text) throws java.lang.IllegalArgumentException
 {
/*
  if (text.equals ("Dot")) {
      setValue (Curve.DOT);
  } else if (text.equals ("Line")) {
      setValue (Curve.LINE);
  } else if (text.equals ("Plotted line")) {
      setValue (Curve.PLOTTEDLINE);
  } else if (text.equals ("Thin bar")) {
      setValue (Curve.THINBAR);
  } else if (text.equals ("Polygon")) {
      setValue (Curve.POLYGON);
  } else if (text.equals ("Plain")) {
      setValue (Curve.PLAIN);
  } else if (text.equals ("Histo")) {
      setValue (Curve.HISTO);
  } else if (text.equals ("3-D histo")) {
      setValue (Curve.HISTO3D);
  } else {
      throw new java.lang.IllegalArgumentException (text);
  }
*/
    }
}
