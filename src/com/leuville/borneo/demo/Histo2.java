package com.leuville.borneo.demo;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import com.leuville.borneo.util.*;
import com.leuville.borneo.swing.*;
import com.leuville.borneo.swing.chart.*;

/**
 * Applet de démonstration.
 * Cette applet illustre l'utilisation des objets de type :
 * - HistoGraph
 */
public class Histo2 extends DemoFrame implements Runnable
{
  HistoGraph histo2;

    public static void main(String[] args)
  {
    Histo2 b = new Histo2();
    b.pack();
    b.setVisible(true);
  }

      public void postInit()
  {
    histo2 = new HistoGraph ();
    histo2.setName ("Histogramme 3D");
    histo2.setScale (true);
    histo2.setBorder (null);
    histo2.setBackground (Color.lightGray);
    histo2.setColor (new Color[] { Color.red, Color.yellow, Color.magenta});
    histo2.setLabel(new String[] {"A", "B", "C"});
    histo2.setStringLegend(true);
    histo2.setSize (120, 120);
    histo2.setValueScale (true);
    mainPanel.add (histo2);

    ChartLegend lp = new ChartLegend (histo2, histo2.getOrientation());
    lp.setBorder (null);
    lp.setSize (60, 120);
    mainPanel.add (lp);

    histo2.setSize (200, 200);

    super.postInit();
   }
   public void run ()
   {
    float v1, v2, v3;
    while (true) {
      v1 = (float)(100*Math.random());
      v2 = (float)(100*Math.random());
      v3 = (float)(100*Math.random());
      histo2.getModel().setValue (new double[] { v1, v2, v3 });
      try {
        Thread.sleep (850);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
      }
}
