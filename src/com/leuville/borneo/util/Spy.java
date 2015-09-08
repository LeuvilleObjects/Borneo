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

import java.io.*;

/**
 * A Spy is an object able to poll an information owned by another object
 * using a given period.
 * The behaviour of the Spy is based upon the Source interface.
 * This Source interface may be implemented easily by an anonymous class.
 * A Spy is an Observable object that notifies it's observers.
 * Example:
 * <pre>
 *  Spy.Source source = new Spy.Source () {
 *          public double getValue()
 *          {
 *        return readDouble();  // provided by outer class
 *          }
 *      };
 *  Spy mySpy = new Spy (source, 820);
 *  mySpy.addObserver (something);
 *  mySpy.resume();
 * </pre>
 *
 * @version 1.1
 */
public class Spy extends    java.util.Observable
    implements  Runnable, java.io.Serializable
{
  /**
   * The minimum behaviour of the source.
   */
  public static interface Source extends java.io.Serializable
  {
      public double getValue();
  }
  /**
   * The source to spy.
   */
  private Source source;
  /**
   * The period.
   */
  private int period = 1000;
  /**
   * The spy thread.
   */
  private transient Thread thread;
  /**
   * True if the thread is suspended.
   */
  private boolean suspended;
  /**
   * Constructs a new Spy.
   */
  public Spy ()
  {
    this (null, 1000);
  }
  /**
   * Constructs a new Spy.
   * @param source The source to spy.
   * @param period The polling period.
   */
  public Spy (Source source, int period)
  {
    this.source = source;
    this.period = period;
    thread = new Thread(this);
    suspended = true;
    thread.start ();
  }
  /**
   * Resumes the spy polling.
   */
  public void resume ()
  {
    suspended = false;
    thread.resume();
  }
  /**
   * Suspends the spy polling.
   */
  public void suspend ()
  {
    suspended = true;
    thread.suspend ();
  }
  /**
   * The spy infinite loop.
   * If the spy is not suspended, the source value is notified.
   */
  public void run ()
  {
    while (true) {
      if (!suspended) {
        setChanged();
        try {
          notifyObservers (new Double(source.getValue()));
        }
        catch (Exception e) {
        }
      }
      try {
        thread.sleep (period);
      }
      catch (Exception e) {}
    }
  }
  /**
   * Set the source.
   */
  public void setSource (Source source)
  {
    this.source = source;
  }
  /**
   * Get the source.
   */
  public Source getSource()
  {
    return source;
  }
  /**
   * Set the period.
   */
  public void setPeriod (int period)
  {
    this.period = period;
  }
  /**
   * Get the period.
   */
  public int getPeriod()
  {
    return period;
  }
  /**
   * Create the thread when the Spy is unserialized (ie read from disk).
   */
  protected void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException 
  {
      s.defaultReadObject();
      thread = new Thread(this);
      thread.start ();
  }
}

