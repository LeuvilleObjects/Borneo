package com.leuville.borneo.demo;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author
 * @version
 */
public class BorneoSet extends javax.swing.JFrame {

  /** Initializes the Form */
  public BorneoSet() {
    super("Borneo Set");
    initComponents ();
    JInternalFrame[] frames = new JInternalFrame[] {
      new BarGraph(),
      new Histo1(),
      new Meter1(),
      new MultiTrend1(),
      new Trend2(),
      new PieChart()
    };
    Rectangle[] dim = new Rectangle[] {
      new Rectangle (10, 10, 200, 200),
      new Rectangle (250, 30, 565, 300),
      new Rectangle (30, 450, 550, 300),
      new Rectangle (350, 200, 300, 300),
      new Rectangle (400, 25, 450, 200),
      new Rectangle (400, 400, 500, 200)
    };
    String[] titles = new String[] {
      "BarGraphs",
      "HistoGraphs & JList",
      "Meters",
      "MultiTrend & JTable",
      "Trend & Jlist",
      "PieChart & StackedBar"
    };
    for (int i = 0; i < frames.length; i++) {
      JInternalFrame f = frames[i];
      f.setIconifiable(true); f.setResizable(true); f.setMaximizable(true);
      f.setPreferredSize(new Dimension(dim[i].width, dim[i].height));
      f.setBounds(dim[i].x, dim[i].y, dim[i].width, dim[i].height);
      f.setTitle(titles[i]);
      desktop.add(f, JLayeredPane.DEFAULT_LAYER, -i);
      f.setVisible(true);
    }
    setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the FormEditor.
   */
  private void initComponents () {//GEN-BEGIN:initComponents
    addWindowListener (new java.awt.event.WindowAdapter () {
        public void windowClosing (java.awt.event.WindowEvent evt) {
          exitForm (evt);
        }
      }
    );
    getContentPane ().setLayout (new java.awt.GridLayout (1, 1));

    desktop = new javax.swing.JDesktopPane ();

    getContentPane ().add (desktop);

  }//GEN-END:initComponents

  /** Exit the Application */
  private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
    System.exit (0);
  }//GEN-LAST:event_exitForm


// Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JDesktopPane desktop;
// End of variables declaration//GEN-END:variables


  public static void main(java.lang.String[] args)
  {
    try {
      BorneoSet b = new BorneoSet();
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
      SwingUtilities.updateComponentTreeUI(SwingUtilities.getRoot(b));
      b.setVisible (true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
