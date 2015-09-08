package com.leuville.borneo.demo;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

import com.leuville.borneo.swing.chart.Orientation;

/**
 * Demonstration applet.
 * This applet is a sample with :
 * com.leuville.borneo.swing.chart.BarGraph3D
 */
public class BarGraph3D extends DemoFrame implements Runnable
{
  com.leuville.borneo.swing.chart.BarGraph3D b3, b4;
  JLabel l3;


 public static void main(String[] args)
  {
    BarGraph3D b = new BarGraph3D();
    b.pack();
    b.setVisible(true);
  }

 public void postInit()
  {
    b3 = new com.leuville.borneo.swing.chart.BarGraph3D(0, 100);
    b3.setName ("Test");
    b3.setBorder (null);
    b3.setScale (true);
    b3.setValueScale (true);
    b3.setScaleFraction (5);
    b3.setStringLegend (true);
    b3.setForeground (Color.yellow);
    b3.setBackground (Color.lightGray);
    b3.setSize (180, 60);
    b3.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

    l3 = new JLabel ("               ");
    l3.setBackground (Color.lightGray);
    JLabel l4 = new JLabel ("valeur:");
    l4.setBackground (Color.lightGray);

    b4 = new com.leuville.borneo.swing.chart.BarGraph3D(0, 100, Orientation.up);
    b4.setName ("Test");
    b4.setBorder (null);
    b4.setScale (true);
    b4.setValueScale (true);
    b4.setScaleFraction (2);
    b4.setStringLegend (true);
    b4.setForeground (Color.red);
    b4.setBackground (Color.lightGray);
    b4.setSize (60, 80);

    mainPanel.add(b3);
    mainPanel.add(l4);
    mainPanel.add(l3);
    mainPanel.add(b4);

    super.postInit ();
      }
     public void run ()
  {
    float v1;

    while (true) {
      v1 = (float)(100*Math.random());
      b3.setValue(v1);
      b4.setValue(v1);
      l3.setText(new Float(v1).toString());
      try {
        Thread.sleep (850);
      }
      catch (Exception e) {}
    }
      }
}
