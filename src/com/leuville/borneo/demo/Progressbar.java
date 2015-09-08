package com.leuville.borneo.demo;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import com.leuville.borneo.swing.FillRectCommand;
import com.leuville.borneo.swing.chart.ProgressBar;

/**
 * Demonstration applet.
 * This applet is a sample with :
 * ProgressBar
 * com.leuville.borneo.swing.*Command classes
 */
public class Progressbar extends DemoFrame implements Runnable
{
  ProgressBar b2;
  JLabel l2;

 public static void main(String[] args)
  {
    Progressbar b = new Progressbar();
    b.pack();
    b.setVisible(true);
  }

      public void postInit()
  {
    b2 = new ProgressBar ();
    b2.setForeground (Color.green);
    b2.setBackground (Color.lightGray);
    b2.setBorder (BorderFactory.createLineBorder(Color.black, 3));
    b2.setCommand (new FillRectCommand ());
    b2.setSize (140, 30);
    l2 = new JLabel ("                  ");
    l2.setBackground (Color.lightGray);
    mainPanel.add(b2);
    mainPanel.add(l2);
    JLabel l3 = new JLabel (" %  ");
    l3.setBackground (Color.lightGray);
    mainPanel.add(l3);

    super.postInit ();
      }
     public void run ()
  {
    float v2;

    while (true) {
      v2 = (float)(100*Math.random());
      l2.setText (new Float(v2).toString());
      b2.setValue(v2);
      try {
        Thread.sleep (850);
      }
      catch (Exception e) {}
    }
      }
}
