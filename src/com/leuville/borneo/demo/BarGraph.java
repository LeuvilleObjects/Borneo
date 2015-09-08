package com.leuville.borneo.demo;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.leuville.borneo.swing.FillRectCommand;
import com.leuville.borneo.swing.chart.EllipseGraph;
import com.leuville.borneo.swing.chart.HPump1;
import com.leuville.borneo.swing.chart.HTank1;
import com.leuville.borneo.swing.chart.Orientation;
import com.leuville.borneo.swing.chart.Thermometer;
import com.leuville.borneo.swing.chart.VTank1;

/**
 * Demonstration applet.
 * This applet is a sample with :
 * BarGraph
 * com.leuville.borneo.swing.*Command classes
 */
public class BarGraph extends DemoFrame implements Runnable
{
  EllipseGraph b1;
  com.leuville.borneo.swing.chart.BarGraph b2, b3, b4;
  HPump1 pump;
  HTank1 tank;
  VTank1 tank2;
  Thermometer thermo;

  public static void main(String[] args)
  {
    BarGraph b = new BarGraph();
    b.setBounds(0, 0, 500, 200);
    demo(b);
  }
 public BarGraph()
 {
 }
 public void postInit()
  {
    b1 = new EllipseGraph (0, 100, Orientation.right);
    b1.setFilled(true);
    b1.setForeground (Color.cyan);
    b1.setBackground (Color.lightGray);
    b1.setBorder (null);
    b1.setSize (130, 30);
    b1.setDrawValue(true);
    pump = new HPump1();
    pump.setSize(120, 80);
    pump.setForeground (Color.blue);
    pump.setBackground (Color.lightGray);
    pump.setComponent(b1);

    b2 = new com.leuville.borneo.swing.chart.BarGraph(0, 100, Orientation.left);
    b2.setForeground (Color.magenta);
    b2.setBackground (Color.lightGray);
    b2.setBorder (new javax.swing.border.LineBorder(Color.red, 2));
    b2.setCommand (new FillRectCommand ());
    b2.setSize (180, 60);
    b2.setValueScale (true);

    b3 = new com.leuville.borneo.swing.chart.BarGraph(0, 100, Orientation.right);
    b3.setForeground (Color.red);
    b3.setFilled(true);
    b3.setBackground (Color.lightGray);
    b3.setCommand (new FillRectCommand ());
    tank = new HTank1();
    tank.setSize(200, 140);
    tank.setForeground (Color.magenta);
    tank.setBackground (Color.lightGray);
    tank.setComponent(b3);

    tank2 = new VTank1();
    tank2.setSize(140, 200);
    tank2.setForeground (Color.green);
    tank2.setBackground (Color.lightGray);
    b4 = new com.leuville.borneo.swing.chart.BarGraph(0, 100, Orientation.up);
    b4.setForeground (tank2.getForeground());
    b4.setFilled(true);
    b4.setBackground (Color.lightGray);
    b4.setBorder (BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    b4.setCommand (new FillRectCommand ());
    tank2.setComponent(b4);

    thermo = new Thermometer (0, 100);
    thermo.setForeground (Color.magenta);
    thermo.setBackground (Color.lightGray);
    thermo.setSize(80, 130);

    com.leuville.borneo.swing.chart.BarGraph b5 = new com.leuville.borneo.swing.chart.BarGraph3D(0, 100);
    b5.setName ("3D");
    b5.setBorder (null);
    b5.setScale (true);
    b5.setValueScale (true);
    b5.setScaleFraction (5);
    b5.setStringLegend (true);
    b5.setForeground (Color.yellow);
    b5.setBackground (Color.lightGray);
    b5.setSize (180, 60);
    b5.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

    b5.setModel(b2.getModel());
    JPanel pp = new JPanel(new ColumnLayout());
    pp.add(b2);
    pp.add(b5);

    mainPanel.add (pp);
    mainPanel.add (thermo);
    mainPanel.add (pump);
    mainPanel.add (tank);
    mainPanel.add (tank2);

    super.postInit();
  }

  public void run ()
  {
    float v1, v2;

    while (true) {
      v1 = (float)(100*Math.random());
      v2 = (float)(100*Math.random());
      b1.setValue (v1);
      thermo.setValue (v1);
      b2.setValue (v2);
      b3.setValue (v2);
      b4.setValue (v2);
      try {
        Thread.sleep (850);
      }
      catch (Exception e) {}
    }
      }
}
