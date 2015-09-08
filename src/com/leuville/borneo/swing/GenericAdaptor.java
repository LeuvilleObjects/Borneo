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

package com.leuville.borneo.swing;

import java.beans.*;
import java.lang.reflect.*;
import java.awt.event.*;
import java.util.EventObject;

/**
 * GenericAdaptor defines a generic event adaptor mechanism.
 * This is an abstract class which must be subclassed.
 * These interfaces are implemented : ActionListener, AdjustmentListener, ItemListener,
 * PropertyChangeListener, TextListener, VetoableChangeListener.
 *
 * @version 1.1
 */
abstract public class GenericAdaptor implements
            ActionListener,
            AdjustmentListener,
            ItemListener,
            PropertyChangeListener,
            TextListener,
            VetoableChangeListener
{
 /**
  * Target for action.
  */
 protected Object target;
 /**
  * Constructs a new GenericAdaptor.
  * @param target The target object.
  */
 protected GenericAdaptor (Object target)
 {
   this.target = target;
 }
 /**
  * ActionListener interface.
  * @see triggerAction
  */
 public void actionPerformed (ActionEvent event) 
 {
   triggerAction (event);
 }
 /**
  * ActionListener interface.
  * @see triggerAction
  */
 public void adjustmentValueChanged (AdjustmentEvent event) 
 {
   triggerAction (event);
 }
 /**
  * ActionListener interface.
  * @see triggerAction
  */
 public void itemStateChanged (ItemEvent event) 
 {
   triggerAction (event);
 }
 /**
  * ActionListener interface.
  * @see triggerAction
  */
 public void propertyChange (PropertyChangeEvent event) 
 {
   triggerAction (event);
 }
 /**
  * ActionListener interface.
  * @see triggerAction
  */
 public void textValueChanged (TextEvent event) 
 {
   triggerAction (event);
 }
 /**
  * ActionListener interface.
  * @see triggerAction
  */
 public void vetoableChange (PropertyChangeEvent event) 
 {
   triggerAction (event);
 }
 /**
  * This method is called when an event occurs.
  * An implementation must be provided by concrete subclasses.
  */
 abstract protected void triggerAction (EventObject event); 
}


