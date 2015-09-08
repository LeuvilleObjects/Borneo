package com.leuville.borneo.demo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.leuville.borneo.swing.chart.Curve;
import com.leuville.borneo.swing.chart.MultiTrend;
import com.leuville.borneo.swing.chart.Orientation;

/**
 * Demonstration applet.
 * This applet is a sample with :
 * MultiTrend
 */
public class MultiTrend1 extends DemoFrame implements Runnable
{
  MultiTrend mt;
  JTable table;

    public static void main(String[] args)
  {
    MultiTrend1 b = new MultiTrend1();
    b.pack();
    b.setVisible(true);
  }

  public void postInit()
  {
    mt = new MultiTrend (Orientation.up, Orientation.right, true);
    mt.setColor(new Color[] {Color.blue, Color.green, Color.yellow});
    mt.setLabel(new String[] {"Blue", "Green", "Yellow"});
    mt.setCurve(new Curve[] {Curve.PLAIN, Curve.LINE, Curve.DOT});
    mt.setForeground (Color.black);
    mt.setBackground (Color.lightGray);
    mt.setBorder (BorderFactory.createLoweredBevelBorder());
    mt.setSize (250, 200);
    mt.setCapacity(50);
    mt.setNbPoints (8);
    mt.setScrollBar(true);
    mt.setValueScale(true);
    final JSlider nbPoints = new JSlider (2, mt.getCapacity(), mt.getNbPoints());
    nbPoints.setToolTipText("Adjust visible points");
    nbPoints.setPaintTicks(true);
    nbPoints.setPaintLabels(true);
    nbPoints.setPaintTrack(true);
    nbPoints.setLabelTable(nbPoints.createStandardLabels(5));
    nbPoints.addChangeListener (
      new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          mt.setNbPoints (nbPoints.getValue());
        }
      }
    );
//    nbPoints.setBackground (Color.lightGray);
    JPanel pp = new JPanel(new BorderLayout());
    pp.add (nbPoints, BorderLayout.NORTH);
    pp.add(mt, BorderLayout.CENTER);
    mainPanel.add(pp);

    table = new JTable(mt.getTableModel());
    table.setRowHeight(15);
    table.setIntercellSpacing(new Dimension(3, 3));
    JScrollPane scrollpane = new JScrollPane(table);
    JTableHeader th = table.getTableHeader();
    th.setResizingAllowed(true);
    TableColumnModel colModel = table.getColumnModel();
//    DefaultTableCellRenderer renderer = new  DefaultTableCellRenderer();
//    renderer.setPreferredSize(new Dimension(100, 15));
    for (int i = 0; i < colModel.getColumnCount(); i++) {
      TableColumn tc = colModel.getColumn(i);
//      tc.setCellRenderer(renderer);
      tc.setResizable(true);
    }
    scrollpane.setPreferredSize(new Dimension(280, 200));
    scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    pp.add(scrollpane, BorderLayout.EAST);

    super.postInit();
  }
  public void run ()
  {
    float v1, v2, v3;
    while (true) {
      v1 = (float)(100*Math.random());
      v2 = (float)(100*Math.random());
      v3 = (float)(100*Math.random());
      mt.getModel().addValue(0, v1);
      mt.getModel().addValue(1, v2);
      mt.getModel().addValue(2, v3);
      table.repaint();
      try {
        Thread.sleep (700);
      }
      catch (Exception e) {}
    }
      }
}
