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

package com.leuville.borneo.swing.chart;

/**
 * Common definitions and utilities.
 *
 * @version 1.2
 */
public final class Common
{
 public final static String badNbPoints = "The number of points must be greater than 0";
 public final static String badNb = "The number of BarGraphs must be greater than 0";
 public final static String badMinMax = "Min must be less than max";
 public final static String badStep = "size / nbPoints must be greater than 1";
 public final static String badScroll = "Scroll must not be null";
 public final static String badLastIndex = "This index must be in [0, 100] interval";

 private final static int[] coeffs = { 1, 10, 100, 1000, 10000, 100000, 1000000 };

 public static void checkLastIndex (int min, int max, int index)
 {
  if ((index < min) || (index > max))
     throw new IllegalArgumentException (badLastIndex);
 }

 public static void checkMinMax (double min, double max)
 {
   if (min >= max)
     throw new IllegalArgumentException (badMinMax);
 }
 public static void checkNbPoints (int nbPoints)
 {
   if (nbPoints <= 0)
     throw new IllegalArgumentException (badNbPoints);
 }
 public static void checkStep (int step)
 {
   if (step < 1)
     throw new IllegalArgumentException (badStep);
 }
 public static void checkScroll (Object scroll)
 {
   if (scroll == null)
     throw new IllegalArgumentException (badScroll);
 }
 /**
  * Check orientations.
  * @exception IllegalArgumentException If orientation and scroll are in the same way.
  */
 public static void checkOrientations (Orientation o1, Orientation o2)
 {
  if ((o1.isVertical() && o2.isVertical()) || (o1.isHorizontal() && o2.isHorizontal())) {
      throw new IllegalArgumentException ("orientations cannot be in the same way");
  }
 }
 /**
  * Return a rounded double as a String.
  * @param  d  The initial double.
  * @param  nb  The number of decimal digits (between 0 and 6).
  */
 public static String roundString (double d, int nb)
 {
  d *= coeffs[nb];
  return Double.toString(Math.round(d)/coeffs[nb]);
 }
 public static String roundString (Number n, int nb)
 {
   return roundString (n.doubleValue(), nb);
 }
 public static String roundString (double n, double range, double precision)
 {
   double v = range*precision;
   int i = 0;
   while (v < 1) {
     v *= 10;
     i++;
   }
   return roundString (n, i);
 }
 public static String roundString (Number n, double range, double precision)
 {
  return roundString (n.doubleValue(), range, precision);
 }
 /**
  * Return a string width for a given font.
  */
 public static int stringWidth (java.awt.Font font, String string)
 {
   return (int)(0.6*font.getSize()*string.length());
 }
}

