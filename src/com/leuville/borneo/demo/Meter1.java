package com.leuville.borneo.demo;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

import com.leuville.borneo.util.*;
import com.leuville.borneo.swing.*;
import com.leuville.borneo.swing.chart.*;

/**
 * Demonstration applet.
 * This applet is a sample with :
 * Meter
 */
public class Meter1 extends DemoFrame implements Runnable
{
  SimpleMeter meter, meter2, meter3, meter4;
  RangeMeter meter5, meter6;
  Knob knob;

  public static void main(String[] args)
  {
    Meter1 b = new Meter1();
    b.pack();
    b.setVisible(true);
  }

      public void postInit()
  {
    meter = new SimpleMeter (210, -240, 0, 100);
    meter.setSize (160, 200);
    meter.setForeground (Color.red);
    meter.setBackground (Color.lightGray);
    meter.setBorder(BorderFactory.createLineBorder(Color.black, 1));
/*
    meter2 = new SimpleMeter (270, -225, 0, 100);
    meter2.setSize (180, 260);
    meter2.setForeground (Color.green);
    meter2.setBackground (Color.lightGray);
    meter2.setBorder(BorderFactory.createLineBorder(Color.black, 1));
*/
    meter3 = new SimpleMeter (-90, -360, 0, 100);
    meter3.setNeedleDrawer(new AbstractMeter.SectorNeedle());
    meter3.setSize (180, 180);
    meter3.setForeground (Color.yellow);
    meter3.setBackground (Color.lightGray);
    meter3.setBorder(BorderFactory.createLineBorder(Color.black, 1));

    meter4 = new SimpleMeter (-80, -200, 0, 100);
    meter4.setNeedleDrawer(new AbstractMeter.ValueNeedle());
    meter4.setSize (180, 180);
    meter4.setForeground (Color.pink);
    meter4.setBackground (Color.lightGray);
    meter4.setBorder(BorderFactory.createLineBorder(Color.black, 1));

    meter5 = new RangeMeter (-90, -225, 0, 100);
    meter5.setSize (180, 180);
    meter5.setForeground (Color.white);
    meter5.setBackground (Color.lightGray);
    meter5.setBorder(BorderFactory.createLineBorder(Color.black, 1));

    meter6 = new RangeMeter (-200,- 140, 0, 100);
    meter6.setSize (180, 180);
    meter6.setForeground (Color.white);
    meter6.setBackground (Color.lightGray);
    meter6.setBorder(BorderFactory.createLineBorder(Color.black, 1));

    knob = new Knob (0, 100);
    knob.setSize (180, 180);
    knob.setForeground (Color.yellow);
    knob.setBackground (Color.lightGray);
    knob.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    knob.setModel(meter6.getModel());
    knob.setToolTipText("Input & Output");

    JPanel pp = new JPanel(new GridLayout(2, 3));
    pp.add (meter);
//    pp.add (meter2);
    pp.add (meter3);
    pp.add (meter4);
    pp.add (meter5);
    pp.add (meter6);
    pp.add (knob);
    mainPanel.add (pp);

    super.postInit ();
  }
  public void run ()
  {
    double v1;

    while (true) {
      v1 = 100*Math.random();
      meter.setValue (v1);
//      meter2.setValue (v1);
      meter3.setValue (v1);
      meter4.setValue (v1);
      meter5.setValue (v1);
      meter6.setValue (v1);
      try {
        Thread.sleep (850);
      }
      catch (Exception e) {}
    }
      }
}
