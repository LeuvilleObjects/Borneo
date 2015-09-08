package com.leuville.borneo.demo;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.beans.*;

import javax.swing.*;
import javax.swing.event.*;

import com.leuville.borneo.util.*;
import com.leuville.borneo.dataflow.*;
import com.leuville.borneo.swing.*;
import com.leuville.borneo.swing.chart.*;

/**
 * Demonstration applet.
 */
public class Trend2 extends DemoFrame implements Runnable, ItemListener
{
  JComboBox curveType;
  Trend trend;
  JList jlist;
  int ms = 1000;

 public static void main(String[] args)
  {
    Trend2 b = new Trend2();
    b.pack();
    b.setVisible(true);
  }

 private class BigLine extends Curve.Line
 {
  private Stroke stroke = new BasicStroke(8.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);

  public void draw (Graphics2D g, TrendModel model, DoubleDataListPaint paint, Orientation o1, Orientation o2,
          com.leuville.borneo.swing.Rectangle limits, int step, int valueIndex, int cellNumber)
  {
      Stroke save = g.getStroke();
      g.setStroke(stroke);
      super.draw(g, model, paint, o1, o2, limits, step, valueIndex, cellNumber);
      g.setStroke(save);
  }
 }

 public void postInit()
  {
    trend = new Trend (10, 0, 100, true);
    trend.setForeground (Color.green);
    trend.setSize (240, 180);
    trend.setBorder (BorderFactory.createLineBorder(Color.black, 3));
    trend.setCurve (Curve.HISTO3D);
    trend.setValueScale (true);
    trend.setPaint( new DoubleDataListPaint() {
        public Paint getPaint(DoubleDataListModel model, double value, int index)
        {
          double min = model.getMinimum();
          double max = model.getMaximum();
          double range = max - min;
          if (value < min + range*0.25)
            return Color.green;
          else if (value < min + range*0.75)
            return Color.yellow;
          else
            return Color.red;
        }
      });
    final Knob knob = new Knob (trend.getNbPoints(), trend.getCapacity());
    knob.setSize (100, 100);
    knob.setDrawValue(true);
    knob.setValueScale(false);
    knob.setForeground (Color.yellow);
    knob.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    knob.setToolTipText("Number of points");
    knob.addStateChangeListener(new StateChangeListener() {
      public void stateChanged(StateChangeEvent e)
      {
        trend.setNbPoints((int)Math.round(knob.getValue()));
      }
    });
    final JTextField min = new JTextField (3);
    min.setText (new Double(trend.getMinimum()).toString());
    min.addActionListener (
      new ActionListener() {
        public void actionPerformed (ActionEvent e) {
          double n = new Double (min.getText()).doubleValue();
          Trend2.this.trend.setMinimum (n);
        }
      }
    );
    final JTextField max = new JTextField (3);
    max.setText (new Double(trend.getMaximum()).toString());
    max.addActionListener (
      new ActionListener() {
        public void actionPerformed (ActionEvent e) {
          double n = new Double (max.getText()).doubleValue();
          Trend2.this.trend.setMaximum (n);
        }
      }
    );

    curveType = new JComboBox ();
    curveType.addItem ("dots");
    curveType.addItem ("curve");
    curveType.addItem ("bars");
    curveType.addItem ("polygons");
    curveType.addItem ("plain");
    curveType.addItem ("histo");
    curveType.addItem ("histo (2)");
    curveType.addItem ("histo (4)");
    curveType.addItem ("histo 3D");
    curveType.addItem ("histo 3D (2)");
    curveType.addItem ("histo 3D (4)");
    curveType.addItem ("line + stoke");
    curveType.addItem ("line + plots");
    curveType.addItem ("plots");
    curveType.setSelectedItem ("histo 3D");
    curveType.addItemListener (this);

    JPanel control2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    control2.add (createLabeledTextField("min:", min));
    control2.add (createLabeledTextField("max:", max));

    JScrollPane scrollpane = new JScrollPane();
    jlist = new JList(trend.getModel());
    scrollpane.setViewportView(jlist);
    scrollpane.setPreferredSize(new Dimension(130, 130));

    JPanel p = new JPanel (new BorderLayout());
    buttonPanel.add (curveType);
    p.add (trend, BorderLayout.CENTER);
    p.add(knob, BorderLayout.WEST);
    p.add(scrollpane, BorderLayout.EAST);
    mainPanel.add(p);

    buttonPanel.add (control2);

    final JSlider delay = new JSlider (500, 5000, 1000);
    delay.setToolTipText("Adjust delay");
    delay.setPaintTicks(true);
    delay.setPaintLabels(true);
    delay.setPaintTrack(true);
    delay.setLabelTable(delay.createStandardLabels(5));
    delay.addChangeListener (
      new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          ms = delay.getValue();
        }
      }
    );
//    buttonPanel.add(delay);
    super.postInit ();
  }

  public void run ()
  {
    float v2;
    while (true) {
      v2 = (float)(100*Math.random());
      trend.setValue (v2);
      jlist.repaint();
      try {
        Thread.sleep (ms);
      }
      catch (Exception e) {}
    }
      }
  public void itemStateChanged (ItemEvent e)
  {
    JComboBox c = (JComboBox)e.getItemSelectable();
    switch (c.getSelectedIndex()) {
      case 0 :
        trend.setCurve (Curve.DOT); break;
      case 1 :
        trend.setCurve (Curve.LINE); break;
      case 2 :
        trend.setCurve (Curve.THINBAR); break;
      case 3 :
        trend.setCurve (Curve.POLYGON); break;
      case 4 :
        trend.setCurve (Curve.PLAIN); break;
      case 5 :
        trend.setCurve (Curve.HISTO); break;
      case 6 :
        trend.setCurve (new Curve.Histo(2)); break;
      case 7 :
        trend.setCurve (new Curve.Histo(4)); break;
      case 8 :
        trend.setCurve (Curve.HISTO3D); break;
      case 9 :
        trend.setCurve (new Curve.Histo3D (2)); break;
      case 10:
        trend.setCurve (new Curve.Histo3D (4)); break;
      case 11:
        trend.setCurve (new BigLine ()); break;
      case 12:
        trend.setCurve (new Curve.PlottedLine ()); break;
      case 13:
        trend.setCurve (new Curve.Plot ()); break;
    }
  }
}
