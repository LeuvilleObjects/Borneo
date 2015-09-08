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

import java.util.*;

/**
 * A FIFO is a First In First Out list of objects.
 * @version 1.1     1.1
 * @author   Laurent Nel
 */
public class FIFO extends AbstractList implements java.lang.Cloneable, java.io.Serializable

{
  /**
   * Iterator for a FIFO.
   */
  public class Iterator implements java.util.Iterator
  {
    int index;
    /**
     * Return true if the index is not at the end of the list.
     */
    public boolean hasNext()
    {
      return (index < FIFO.this.size);
    }
    /**
     * Return the current object and increment the index.
     */
    public Object next()
    {
      return FIFO.this.list[index++];
    }
    /**
     * Remove the current object.
     */
    public void remove ()
    {
      FIFO.this.remove(index);
    }
  }
  /**
   * The list of objects contained in the FIFO.
   */
  protected java.lang.Object list[];
  /**
   * The size of the list.
   */
  protected int size;
  /**
   * Constructs a new FIFO with a capacity of 10 objects.
   * @param source The source to spy.
   */
  public FIFO ()
  {
    this (10);
  }
  /**
   * Constructs a new FIFO.
   * @param capacity The capacity.
   */
  public FIFO (int capacity)
  {
    list = new java.lang.Object [capacity];
    size = 0;
  }

  /**
   * Modify the capacity and keep stored objects if possible.
   */
  public void setCapacity (int newCapacity)
  {
    Object[] tmp = new Object[newCapacity];
    if (newCapacity > list.length) {
      System.arraycopy(list, 0, tmp, 0, list.length);
    } else {
      if (newCapacity > size) {
        System.arraycopy (list, 0, tmp, 0, size);
      } else {
        System.arraycopy (list, size-newCapacity, tmp, 0, newCapacity);
      }
    }
  }

  /**
   * Removes all objects from the list.
   */
  public void clear()
  {
    list = new java.lang.Object [list.length];
    size = 0;
  }
  /**
   * Adds an object in the FIFO.
   * The first entered object is removed if necessary.
   */
  public boolean add (Object anObject)
  {
    if ((size > 0) && (size == list.length)) {
      // remove the first element
      System.arraycopy (list, 1, list, 0, size-1);
      size--;
    }
    list[size++] = anObject;
    return true;
  }

  /**
   * Return true if the parameter is equal to the receiver.
   */
  public boolean equals(Object o)
  {
    if (o instanceof FIFO) {
      return super.equals(o);
    } else
      return false;
  }
  /**
   * Returns the size (ie number of non-null objects).
   */
  public int size ()
  {
    return size;
  }
  /**
   * Returns the size (ie number of non-null objects).
   */
  public int getSize ()
  {
    return size;
  }
  /**
   * Returns the capacity.
   */
  public int capacity ()
  {
    return list.length;
  }
  /**
   * Returns the capacity.
   */
  public int getCapacity ()
  {
    return list.length;
  }
  /**
   * Clones the receiver.
   */
  public synchronized Object clone ()
  {
    try {
      FIFO copy = (FIFO) super.clone ();
      copy.list = new java.lang.Object[size];
      System.arraycopy (list, 0, copy.list, 0, size);
      copy.size = size;
      return copy;
    } catch (CloneNotSupportedException e) {
          throw new InternalError();
    }
  }
  /**
   * Returns the object at the given index.
   */
  public Object get (int i)
  {
    return list[i];
  }
  /**
   * Removes an object.
   */
  public Object remove (int index)
  {
    Object r = list[index];
    System.arraycopy (list, index+1, list, index, size-index);
    size--;
    return r;
  }
  /**
   * Replaces an element at an index.
   */
  public Object set (int i, Object element)
  {
    Object prev = list[i];
    list[i] = element;
    return prev;
  }

  /**
   * Removes elements whose index are between from and to.
   */
  protected void removeRange(int from, int to)
  {
    System.arraycopy (list, to, list, from, to-from);
    size -= to-from;
  }
}


