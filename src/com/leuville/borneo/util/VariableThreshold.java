/*
 *
 * Leuville Objects MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Leuville Objects SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

package com.leuville.borneo.util;

import java.beans.*;
import java.util.*;

/**
 * A threshold object with an Observable data source and an Observable threshold.
 * The Rule inner interface defines the way to know if the threshold is raised or not.
 *
 * @version 1.1
 */

public class VariableThreshold implements java.io.Serializable, Observer
{
/**
 * The Rule interface.
 */
public interface Rule extends java.io.Serializable
{
  /**
    * The Rule protocol.
    */
  public boolean raise (Number value, Number threshold);
}
/**
 * "less than" rule useful to know if value < threshold.
 */
public static class Less implements Rule {
  public boolean raise (Number value, Number threshold) {
    return (value.doubleValue() < threshold.doubleValue());
  }
}
/**
 * "less than or equal" rule useful to know if value <= threshold.
 */
public static class LessEqual implements Rule {
  public boolean raise (Number value, Number threshold) {
    return (value.doubleValue() <= threshold.doubleValue());
  }
}
/**
 * "equal" rule useful to know if value = threshold.
 */
public static class Equal implements Rule {
  public boolean raise (Number value, Number threshold) {
    return (value.doubleValue() == threshold.doubleValue());
  }
}
/**
 * "not equal" rule useful to know if value <> threshold.
 */
public static class NotEqual implements Rule {
  public boolean raise (Number value, Number threshold) {
    return (value.doubleValue() != threshold.doubleValue());
  }
}
/**
 * "more than" rule useful to know if value > threshold.
 */
public static class More implements Rule {
  public boolean raise (Number value, Number threshold) {
    return (value.doubleValue() > threshold.doubleValue());
  }
}
/**
 * "more than or equal" rule useful to know if value >= threshold.
 */
public static class MoreEqual implements Rule {
  public boolean raise (Number value, Number threshold) {
    return (value.doubleValue() >= threshold.doubleValue());
  }
}
/**
 * "in interval [a, b]" rule.
 */
public static class In implements Rule {
  protected double a, b;
  public In (double a, double b) {
    this.a = a;
    this.b = b;
  }
  public boolean raise (Number value, Number threshold) {
    double v = value.doubleValue();
    return ((v >= a) && (v <= b));
  }
}
/**
 * "not in interval [a, b]" rule.
 */
public static class NotIn implements Rule {
  protected double a, b;
  public NotIn (double a, double b) {
    this.a = a;
    this.b = b;
  }
  public boolean raise (Number value, Number threshold) {
    double v = value.doubleValue();
    return ((v < a) || (v > b));
  }
}
/**
 * Convenience default Less rule.
 */
public static final Rule LESS = new Less();
/**
 * Convenience default LessEqual rule.
 */
public static final Rule LESS_EQUAL = new LessEqual();
/**
 * Convenience default Equal rule.
 */
public static final Rule EQUAL = new Equal();
/**
 * Convenience default NotEqual rule.
 */
public static final Rule NOT_EQUAL = new NotEqual();
/**
 * Convenience default MoreEqual rule.
 */
public static final Rule MORE_EQUAL = new MoreEqual();
/**
 * Convenience default More rule.
 */
public static final Rule MORE = new More();
/**
 * The current state.
 */
protected boolean raised;
/**
 * The current rule.
 */
private Rule rule;
/**
 * The current data source value.
 */
protected Number dataValue = new Integer(0);
/**
 * The current threshold value.
 */
protected Number thresholdValue = new Integer(0);
/**
 * Instance of the PropertyChangeSupport utility class for 
 * bound properties.
 */
protected PropertyChangeSupport support = new PropertyChangeSupport(this);
/**
 * Constructs a default VariableThreshold.
 * The rule is set to Equal.
 */
public VariableThreshold ()
{
  this (EQUAL);
}
/**
 * Constructs a default VariableThreshold.
 * @param rule The rule.
 */
public VariableThreshold (Rule rule)
{
  this.rule = rule;
}
/**
 * Set the rule object.
 * @param rule The Rule object (may not be null).
 */
public void setRule (Rule rule)
{
  this.rule = rule;
}
/**
 * Get the rule object.
 */
public Rule getRule ()
{
  return rule;
}
/**
 * This method is called when the data source is modified.
 * Call checkState.
 * @see checkState
 */
public void update (Observable source, Object arg)
{
    this.dataValue = (Number)arg;
    checkState ();
}
/**
 * Fires a PropertyChangeEvent if the threshold is raised.
 * This object is synchronized during operation.
 */
protected void checkState() 
{
    synchronized (this) {
  boolean oldState = raised;
  raised = rule.raise (dataValue, thresholdValue);
  if (raised) {
      support.firePropertyChange("value", new Boolean(oldState),new Boolean(raised));
  }
    }
}
/**
 * Get the current state.
 */
public boolean isRaised ()
{
  return raised;
}
/**
 * Adds a new PropertyChangeListener object.
 */
public void addPropertyChangeListener (PropertyChangeListener l)
{
  support.addPropertyChangeListener (l);
}
/**
 * Removes a PropertyChangeListener object.
 */
public void removePropertyChangeListener (PropertyChangeListener l)
{
  support.removePropertyChangeListener (l);
}
/**
 * Set the data to test.
 */
public void setDataValue (Number dataValue)
{
    this.dataValue = dataValue;
    checkState ();
}
/**
 * Get the latest tested data.
 */
public Number getDataValue()
{
    return dataValue;
}
/**
 * Set the data to test.
 */
public void setThresholdValue (Number thresholdValue)
{
    this.thresholdValue = thresholdValue;
    checkState ();
}
/**
 * Get the latest tested data.
 */
public Number getThresholdValue()
{
    return thresholdValue;
}
}
