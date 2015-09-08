package com.leuville.borneo.util;

import java.beans.*;
import java.util.*;

public class MultiInput
{
 Number [] values;
 PropertyChangeSupport support = new PropertyChangeSupport (this);
 public MultiInput ()
 {
   values = new Number [3];
 }
 public void addPropertyChangeListener (PropertyChangeListener l)
 {
   support.addPropertyChangeListener (l);
 }
 public void removePropertyChangeListener (PropertyChangeListener l)
 {
   support.removePropertyChangeListener (l);
 }
 public void setValue0 (Number v)
 {
   setValue (0, v);
 }
 public void setValue1 (Number v)
 {
   setValue (1, v);
 }
 public void setValue2 (Number v)
 {
   setValue (2, v);
 }
 public void setValue (int i, Number v)
 {
   Number [] bidon = new Number [values.length];
   for (int j = 0; j < bidon.length; j++)
     bidon[j] = values[j];
   System.out.println (i+" "+v);
   values[i] = v;
   support.firePropertyChange ("value", bidon, values);
 }
 public void setValue (Number [] v)
 {
   for (int j = 0; j < v.length; j++)
     System.out.println (j+ " "+ v[j]);
  values = v;
 }
 public Number[] getValue ()
 {
   return values;
 }
 public Number getValue (int i)
 {
   return values[i];
 }
 public Number getValue0 ()
 {
   return getValue (0);

 }
 public Number getValue1 ()
 {
   return getValue (1);

 }
 public Number getValue2 ()
 {
   return getValue (2);

 }
 public void propertyChange (PropertyChangeEvent e)
 {
   System.out.println (e.getPropertyName());
   values = (Number[])e.getNewValue();
   for (int j = 0; j < values.length; j++)
     System.out.println (j+ "="+ values[j]);
 }
 public void entree (Object e)
 {
   System.out.println (e);
 }
}
class MultiInputBeanInfo extends SimpleBeanInfo
{
 protected Vector properties = new Vector ();
 public void defineProperties (Class c) throws IntrospectionException
 {
  properties.addElement (new PropertyDescriptor ("value0", c));
  properties.addElement (new PropertyDescriptor ("value1", c));
  properties.addElement (new PropertyDescriptor ("value2", c));
  IndexedPropertyDescriptor d = new IndexedPropertyDescriptor ("value", c);
  d.setBound (true);
  properties.addElement (d);
 }
 public void initProperties () throws IntrospectionException
 {
  defineProperties (new com.leuville.borneo.util.MultiInput().getClass());
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
/*
 public BeanDescriptor getBeanDescriptor ()
 {
   return new BeanDescriptor (borneo.util.MultiInput.class);
 }
*/
}
