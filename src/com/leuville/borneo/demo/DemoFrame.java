package com.leuville.borneo.demo;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import com.leuville.borneo.util.*;
import com.leuville.borneo.swing.*;
import com.leuville.borneo.swing.chart.*;

/**
 * Abstract base class for demo applets.
 */
public abstract class DemoFrame extends JInternalFrame implements Runnable
{
  Thread thread;
  JButton start, stop;
  boolean started;
  JPanel mainPanel = new JPanel();
  JPanel buttonPanel;

 public DemoFrame()
 {
  init();
 }
 public void init()
 {
    getContentPane().setLayout(new GridLayout(1, 1));
    JScrollPane sp = new JScrollPane(mainPanel);
    getContentPane().add (sp);
    mainPanel.setDoubleBuffered(true);

    start = new JButton ("Start");
    ActionListener sl = new ActionListener () {
          public void actionPerformed (ActionEvent e) {
            if (!started) {
              thread = new Thread (DemoFrame.this);
              thread.start();
              started = true;
            }
          }
        };
    start.addActionListener (sl);
    stop = new JButton ("Stop");
    ActionListener stl = new ActionListener () {
          public void actionPerformed (ActionEvent e) {
            if (started) {
              started = false;
              thread.stop();
            }
          }
        };
    stop.addActionListener (stl);

    buttonPanel = placeButtons();
    // call postInit()
    postInit();
  }
  public void postInit()
  {
  }
  public JPanel placeButtons ()
  {
    JPanel p = new JPanel (new ColumnLayout());
    p.add (start);
    p.add (stop);
    mainPanel.add (p);
    return p;
  }
  public void stop()
  {
    if (started) {
      thread.stop();
      thread = null;
      started = false;
    }
  }
  protected JPanel createLabeledTextField (String label, JTextField tf)
  {
      JPanel p = new JPanel(new BorderLayout());
      JLabel l = new JLabel(label, Label.RIGHT);
      p.add(l, BorderLayout.WEST);
      p.add(tf, BorderLayout.CENTER);
      return p;
  }
  protected static void demo(JInternalFrame intFrame)
  {
    JFrame f = new JFrame();
    JDesktopPane desktop = new JDesktopPane();
    f.getContentPane().add(desktop, BorderLayout.CENTER);
    desktop.add(intFrame, JLayeredPane.DRAG_LAYER, 0);
    intFrame.setIconifiable(true);
    intFrame.setResizable(true);
    intFrame.setMaximizable(true);
    f.setVisible(true);
  }
 public static class ColumnLayout implements LayoutManager {

  int xInset = 5;
  int yInset = 5;
  int yGap = 2;

  public void addLayoutComponent(String s, Component c) {}

  public void layoutContainer(Container c) {
      Insets insets = c.getInsets();
      int height = yInset + insets.top;

      Component[] children = c.getComponents();
      Dimension compSize = null;
      for (int i = 0; i < children.length; i++) {
        compSize = children[i].getPreferredSize();
        children[i].setSize(compSize.width, compSize.height);
        children[i].setLocation( xInset + insets.left, height);
        height += compSize.height + yGap;
      }

  }

  public Dimension minimumLayoutSize(Container c) {
      Insets insets = c.getInsets();
      int height = yInset + insets.top;
      int width = 0 + insets.left + insets.right;

      Component[] children = c.getComponents();
      Dimension compSize = null;
      for (int i = 0; i < children.length; i++) {
        compSize = children[i].getPreferredSize();
        height += compSize.height + yGap;
        width = Math.max(width, compSize.width + insets.left + insets.right + xInset*2);
      }
      height += insets.bottom;
      return new Dimension( width, height);
  }

  public Dimension preferredLayoutSize(Container c) {
      return minimumLayoutSize(c);
  }

  public void removeLayoutComponent(Component c) {}

}
}

