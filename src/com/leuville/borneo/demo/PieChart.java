package com.leuville.borneo.demo;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.leuville.borneo.swing.chart.Orientation;
import com.leuville.borneo.swing.chart.SevenSegment;
import com.leuville.borneo.swing.chart.StackedBar;

/**
 * Demonstration applet.
 * This applet is a sample with :
 * PieChart
 * BarGraph
 * StackedBar
 */
public class PieChart extends DemoFrame implements Runnable
{
  com.leuville.borneo.swing.chart.PieChart pc;
  StackedBar st;
  SevenSegment a, b, c;

  public static void main(String[] args)
  {
    PieChart b = new PieChart();
    b.pack();
    b.setVisible(true);
  }

  public void postInit()
  {
    pc = new com.leuville.borneo.swing.chart.PieChart();
    pc.setName ("Pie");
    pc.setColor(new Color[] {Color.green, Color.yellow, Color.blue});
    pc.setSize (120, 120);
    pc.setBackground (Color.lightGray);
    pc.setDrawValue(true);

    a = new com.leuville.borneo.swing.chart.SevenSegment ();
    a.setDigits(3);
    a.setPrecision(1);
    a.setOpaque (true);
    a.setBackground (java.awt.Color.black);
    a.setBorder (new javax.swing.border.BevelBorder (0));
    a.setThickness (5);
    a.setForeground (java.awt.Color.green);
    a.setOffColor (java.awt.Color.darkGray);

    b = new com.leuville.borneo.swing.chart.SevenSegment ();
    b.setDigits(3);
    b.setPrecision(1);
    b.setOpaque (true);
    b.setBackground (java.awt.Color.black);
    b.setBorder (new javax.swing.border.BevelBorder (0));
    b.setThickness (5);
    b.setForeground (java.awt.Color.yellow);
    b.setOffColor (java.awt.Color.darkGray);

    c = new com.leuville.borneo.swing.chart.SevenSegment ();
    c.setDigits(3);
    c.setPrecision(1);
    c.setOpaque (true);
    c.setBackground (java.awt.Color.black);
    c.setBorder (new javax.swing.border.BevelBorder (0));
    c.setThickness (5);
    c.setForeground (java.awt.Color.blue);
    c.setOffColor (java.awt.Color.darkGray);

    a.setSize(140, 50);
    b.setSize(140, 50);
    c.setSize(140, 50);

    mainPanel.add(pc);
    JPanel g = new JPanel(new GridLayout(3, 1, 5, 5));
    g.add(a);
    g.add(b);
    g.add(c);
    mainPanel.add(g);

    st = new StackedBar (Orientation.up, 3);
    st.setBorder (null);
    st.setColor (pc.getColor());
    st.setSize (80, 140);
    st.setCommand (new com.leuville.borneo.swing.Fill3DRectCommand());
//    st.setValueScale (true);
    //st.setDecorationString(new String[] {"Source 1", "Source 2", "Source 3"});
    //st.setStringLegend (true);
    mainPanel.add (st);

    super.postInit ();
  }

  public void run ()
  {
    float v1, v2, v3;
    while (true) {
      v1 = (float)(100*Math.random());
      v2 = (float)(100*Math.random());
      v3 = (float)(100*Math.random());
      st.getModel().setValue (new double[] {v1, v2, v3});
      pc.getModel().setValue (new double[] {v1, v2, v3});
      a.setValue(v1);
      b.setValue(v2);
      c.setValue(v3);
      try {
        Thread.sleep (850);
      }
      catch (Exception e) {}
    }
      }
}
