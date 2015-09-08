package com.leuville.borneo.demo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;

import com.leuville.borneo.swing.FillRectCommand;
import com.leuville.borneo.swing.chart.DoubleDataModel;
import com.leuville.borneo.swing.chart.DoubleDataPaint;
import com.leuville.borneo.swing.chart.HistoGraph;
import com.leuville.borneo.swing.chart.Orientation;

/**
 * Applet de démonstration.
 * Cette applet illustre l'utilisation des objets de type :
 * - HistoGraph
 */
public class Histo1 extends DemoFrame implements Runnable
{
  HistoGraph histo;

  public static void main(String[] args)
  {
    Histo1 b = new Histo1();
    b.pack();
    b.setVisible(true);
  }

 public void postInit()
  {
    histo = new HistoGraph ();
    histo.setOrientation(Orientation.up);
    histo.setScale (true);
    histo.setCommand (new FillRectCommand ());
    histo.setBarInsets (new Insets (2, 5, 2, 5));
    histo.setBackground (Color.lightGray);
    histo.setValueScale (true);
    histo.setLabel(new String[] {"Source 1", "Source 2", "Source 3"});
    histo.setStringLegend (true);
    histo.setSize(300, 200);
    histo.getModel().setValue(new double[] {0, 0, 0});

    JPanel hp = new JPanel(new BorderLayout());
    final JComboBox combo = new JComboBox();
    combo.addItem("Up");
    combo.addItem("Left");
    combo.addItem("Right");
    combo.addItem("Down");
    combo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switch (combo.getSelectedIndex()) {
          case 0:
            histo.setOrientation(Orientation.up); break;
          case 1:
            histo.setOrientation(Orientation.left); break;
          case 2:
            histo.setOrientation(Orientation.right); break;
          case 3:
            histo.setOrientation(Orientation.down); break;
        }
      }
    });
    hp.add(combo, BorderLayout.NORTH);
    hp.add(histo, BorderLayout.CENTER);
    mainPanel.add (hp);

    JScrollPane scrollpane = new JScrollPane();
    JList jlist = new JList(histo.getModel());
    scrollpane.setViewportView(jlist);
    scrollpane.setPreferredSize(new Dimension(140, 95));
    scrollpane.setBorder (new javax.swing.border.TitledBorder (
            new javax.swing.border.EtchedBorder (), "Jlist on same Model", 2, 2));

    JScrollPane scrollpane2 = new JScrollPane();
    JList jlist2 = new JList(histo.getModel());
    jlist2.setFixedCellHeight(20);

    class BGRenderer extends com.leuville.borneo.swing.chart.BarGraph implements ListCellRenderer
    {
     public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean focus)
     {
      if (value instanceof Number) {
        setValue(((Number)value).doubleValue());
      } else
        setValue(0);
      if (selected) {
        setDrawValue(true);
      } else {
        setDrawValue(false);
      }
      return this;
     }
    };
    BGRenderer renderer = new BGRenderer();
    renderer.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    renderer.setOrientation(Orientation.right);
    renderer.setForeground(Color.yellow);
    renderer.setPaint(new DoubleDataPaint() {
      public Paint getPaint(DoubleDataModel model, double value) {
        if (value > 0.6*model.getMaximum())
          return Color.orange;
        else
          return Color.yellow;
      }
    });
    jlist2.setCellRenderer(renderer);
    scrollpane2.setViewportView(jlist2);
    scrollpane2.setPreferredSize(new Dimension(140, 95));
    scrollpane2.setBorder (new javax.swing.border.TitledBorder (
            new javax.swing.border.EtchedBorder (), "BarGraph as renderer", 2, 2));

    JPanel pp = new JPanel(new ColumnLayout());
    pp.add(scrollpane);
    pp.add(scrollpane2);
    mainPanel.add(pp);

    super.postInit();
  }
  public void run ()
  {
    float v1, v2, v3;
    while (true) {
      v1 = (float)(100*Math.random());
      v2 = (float)(100*Math.random());
      v3 = (float)(100*Math.random());
      histo.getModel().setValue (new double[] {v1, v2, v3});
      try {
        Thread.sleep (850);
      }
      catch (Exception e) {}
    }
      }
}
