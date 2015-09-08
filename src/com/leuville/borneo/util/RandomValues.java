package com.leuville.borneo.util;

import java.beans.*;
import java.util.*;
import java.io.*;

public class RandomValues extends java.awt.Label implements Runnable
{
 Number [] values;
 int delay = 1000;
 double min = 0.0;
 double max = 100.0;
 int number = 3;
 PropertyChangeSupport support = new PropertyChangeSupport (this);
 public RandomValues ()
 {
  super ("RandomValues");
   new Thread(this).start();
 }
 public void addPropertyChangeListener (PropertyChangeListener l)
 {
   support.addPropertyChangeListener (l);
 }
 public void removePropertyChangeListener (PropertyChangeListener l)
 {
   support.removePropertyChangeListener (l);
 }
 public Number[] getValues ()
 {
   return values;
 }
 public Number getValues (int i)
 {
   return values[i];
 }
 public void run ()
 {
   while (true) {
     Number [] old;
     synchronized (this) {
      old = new Number [number];
       values = new Number [number];
       for (int i = 0; i < number; i++) {
         old[i] = values[i];
         double n = min + Math.random () * max;
         values[i] = new Double (n);
       }
     }
     support.firePropertyChange ("values", old, values);
     try {
       Thread.sleep (delay);
     }
     catch (Exception e) {
     }
   }
 }
 public synchronized void setNumber (int number)
 {
   this.number = number;
 }
 public int getNumber ()
 {
   return number;
 }
 public void setDelay (int delay)
 {
   this.delay = delay;
 }
 public int getDelay ()
 {
   return delay;
 }
 public void setMinimum (double min)
 {
   this.min = min;
 }
 public double getMinimum()
 {
   return min;
 }
 public void setMaximum (double max)
 {
   this.max = max;
 }
 public double getMaximum()
 {
   return max;
 }
 protected void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException
 {
  s.defaultReadObject();
  new Thread(this).start();
 }
}
class RandomValuesBeanInfo extends SimpleBeanInfo
{
 protected Vector properties = new Vector ();
 public void defineProperties (Class c) throws IntrospectionException
 {
   PropertyDescriptor p1 = new PropertyDescriptor ("min", c);
  properties.addElement (p1);
   PropertyDescriptor p2 = new PropertyDescriptor ("max", c);
  properties.addElement (p2);
   PropertyDescriptor p3 = new PropertyDescriptor ("delay", c);
  properties.addElement (p3);
   PropertyDescriptor p4 = new PropertyDescriptor ("number", c);
  properties.addElement (p4);
  IndexedPropertyDescriptor d = new IndexedPropertyDescriptor ("value", c,
              "getValues", null,
              "getValues", null);
  d.setBound (true);
  properties.addElement (d);
 }
 public void initProperties () throws IntrospectionException
 {
  defineProperties (new com.leuville.borneo.util.RandomValues().getClass());
 }
 public PropertyDescriptor[] getPropertyDescriptors() {
  try {
    initProperties ();
    int n = properties.size ();
        PropertyDescriptor result[] = new PropertyDescriptor[n];
        properties.copyInto (result);
        return result;
  } catch (IntrospectionException ex) {
        ex.printStackTrace ();
        return null;
  }
 }
 public int getDefaultPropertyIndex()
 {
  return 0;
 }
 /*
  * A MethodDescriptor describes an externally visible method supported
  * by a bean.  This method should return null if the information should
  * be obtained by automatic analysis.
  */
 public MethodDescriptor[] getMethodDescriptors()
 {
        return null;
 }
}
