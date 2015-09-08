package com.leuville.borneo.swing.chart;
/*
 * Copyright (c) Leuville Objects All Rights Reserved.
 *
 * Leuville Objects MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Leuville Objects SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

import javax.swing.table.*;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Default implementation of the MultiTrendModel interface.
 * A multitrend model is a kind of table model.
 *
 * @version 1.1
 */
public class DefaultMultiTrendModel extends AbstractTableModel implements MultiTrendModel, Serializable
{
 /**
  * The TrendStore array (one for each curve).
  */
 protected TrendStore[] table;

 /**
  * The column names.
  */
 protected String[] columnNames;

 /**
  * The minimum of each value.
  */
 protected double min = 0.0;

 /**
  * The maximum of each value.
  */
 protected double max = 100.0;

 /**
  * The capacity (ie the number of points).
  */
 protected int capacity;

 /**
  * Returns a readonly TrendModel describing a curve.
  *
  * @param index  The curve number.
  */
 public TrendModel getTrendModel(final int index)
 {
   return new TrendModel() {
    public double getMinimum() { return min; }
    public double getMaximum() { return max; }
    public int getCapacity() { return table[index].getCapacity(); }
    public int getSize() { return table[index].getSize(); }
    public double getValue(int i) { return table[index].getValue(i); }
    public double getLastValue() {return getValue(table[index].getSize()-1); }
    public double[] getValue() { return table[index].getValue(); }
    public int[] getStatus() { return table[index].getStatus(); }
    public int getStatus(int i) { return table[index].getStatus(i); }
    public int getLastStatus() { return getStatus(table[index].getSize()-1); }
    public Object getElementAt(int i) { return new Double(getValue(i)); }

    public void addValue(double value, int status) { throw new UnsupportedOperationException(); }
    public void addValue(double value) { throw new UnsupportedOperationException(); }
    public void setCapacity(int capacity) { throw new UnsupportedOperationException(); }
    public void setMinimum(double min) { throw new UnsupportedOperationException(); }
    public void setMaximum(double max) { throw new UnsupportedOperationException(); }
    public void setValue(int i, double val) { throw new UnsupportedOperationException(); }
    public void setStatus(int i, int status) { throw new UnsupportedOperationException(); }
    public void setValue(double[] values, int[] status) { throw new UnsupportedOperationException(); }
    public void setValue(double[] values) { throw new UnsupportedOperationException(); }
    public void addListDataListener(ListDataListener l) { throw new UnsupportedOperationException(); }
    public void removeListDataListener(ListDataListener l) { throw new UnsupportedOperationException(); }
   };
 }

 /**
  * Creates a new DefaultMultiTrendModel.
  *
  * @param curveNumber    The number of curves.
  * @param curveCapacity  The capacity of each curve.
  */
 public DefaultMultiTrendModel (int curveNumber, int curveCapacity)
 {
  table = new TrendStore[curveNumber];
  columnNames = new String[curveNumber];
  capacity = curveCapacity;
  for (int i = 0; i < curveNumber; i++) {
    table[i] = new TrendStore(curveCapacity);
  }
 }

 /**
  * Returns the size property, which is the size of the biggest curve.
  */
 public int getSize()
 {
  int n = -1;
  for (int i = 0; i < table.length; i++) {
    n = Math.max(n, table[i].getSize());
  }
  return n;
 }

 /**
  * Returns the curve number.
  */
 public int getCurveNumber()
 {
  return getRowCount();
 }

 /**
  * Get the capacity.
  */
 public int getCapacity()
 {
  return getColumnCount();
 }

 /**
  * Set the capacity and fires an event.
  * @see javax.swing.table.AbstractTableModel#fireTableStructureChanged()
  */
 public void setCapacity(int capacity)
 {
  this.capacity = capacity;
  for (int i = 0; i < table.length; i++) {
    table[i].setCapacity(capacity);
  }
  fireTableStructureChanged();
 }

 /**
  * Get the column count (capacity).
  */
 public int getColumnCount()
 {
  return capacity;
 }

 /**
  * Get the row count (table length).
  */
 public int getRowCount()
 {
  return table.length;
 }

 /**
  * Get the name of a column.
  * @param index  The column number.
  */
 public String getColumnName(int index)
 {
  String res = columnNames[index];
  return (res == null ? super.getColumnName(index) : res);
 }

 /**
  * Set the name of a column.
  * @param index  The column number.
  * @param name   The name.
  */
 public void setColumnName(int index, String name)
 {
  columnNames[index] = name;
  fireTableStructureChanged();
 }

 /**
  *  Returns Double.class by default
  */
 public Class getColumnClass(int columnIndex)
 {
  return Double.class;
 }

 /**
  * Returns always true.
  */
 public boolean isCellEditable(int rowIndex, int columnIndex)
 {
  return true;
 }

 /**
  * Set the value of a table cell.
  * @see #setValue(double, int, int)
  *
  * @param aValue       The value, whose type must be Number.
  * @param rowIndex     The row index.
  * @param columnIndex  The column index.
  */
 public void setValueAt(Object aValue, int rowIndex, int columnIndex)
 {
  double val = ((Number)aValue).doubleValue();
  setValue(val, rowIndex, columnIndex);
 }

 /**
  * Get a cell value.
  */
 public Object getValueAt(int rowIndex, int columnIndex)
 {
  return new Double(getValue(rowIndex, columnIndex));
 }

 /**
  * Get the minimum.
  */
 public double getMinimum()
 {
  return min;
 }

 /**
  * Set the minimum and fires an event.
  * @see javax.swing.table.AbstractTableModel#fireTableStructureChanged()
  */
 public void setMinimum(double min)
 {
  this.min = min;
  fireTableStructureChanged();
 }

 /**
  * Get the maximum.
  */
 public double getMaximum()
 {
  return max;
 }

 /**
  * Set the maximum and fires an event.
  * @see javax.swing.table.AbstractTableModel#fireTableStructureChanged()
  */
 public void setMaximum(double max)
 {
  this.max = max;
  fireTableStructureChanged();
 }

 /**
  * Get a value.
  */
 public double getValue(int curveNumber, int dataIndex)
 {
  return table[curveNumber].getValue(dataIndex);
 }

 /**
  * Get a list of values.
  * @param curveNumber  The curve index.
  */
 public double[] getValue(int curveNumber)
 {
  return table[curveNumber].getValue();
 }

 /**
  * Get the status of a value.
  */
 public int getStatus(int curveNumber, int dataIndex)
 {
  return table[curveNumber].getStatus(dataIndex);
 }

 /**
  * Set a value and fires an event.
  * @see javax.swing.table.AbstractTableModel#fireTableCellUpdated(int, int)
  */
 public void setValue(double value, int curveNumber, int dataIndex)
 {
  table[curveNumber].setValue(dataIndex, value);
  fireTableCellUpdated(curveNumber, dataIndex);
 }

 /**
  * Set the value of a curve.
  * @see javax.swing.table.AbstractTableModel#fireTableRowsUpdated(int, int)
  */
 public void setValue(double[] value, int curveNumber)
 {
  for (int i = 0; i < value.length; i++) {
    table[curveNumber].setValue(i, value[i]);
  }
  fireTableRowsUpdated(curveNumber, curveNumber);
 }

 /**
  * Set a status and fires an event.
  * @see javax.swing.table.AbstractTableModel#fireTableCellUpdated(int, int)
  */
 public void setStatus(int status, int curveNumber, int dataIndex)
 {
  table[curveNumber].setStatus(dataIndex, status);
  fireTableCellUpdated(curveNumber, dataIndex);
 }

 /**
  * Append a value to a curve and fires an event.
  * @see javax.swing.table.AbstractTableModel#fireTableCellUpdated(int, int)
  */
 public void addValue(int curveIndex, double value)
 {
  table[curveIndex].addValue(value);
  fireTableCellUpdated(curveIndex, table[curveIndex].getSize());
 }
}
