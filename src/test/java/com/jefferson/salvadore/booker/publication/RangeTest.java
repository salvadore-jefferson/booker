/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.publication;

import static com.jefferson.salvadore.booker.publication.Range.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

/**
 * This class tests the methods of the {@link Range} class
 * 
 * @version 1.0.0 9-17-2015
 * @author Salvadore Jefferson
 */
public class RangeTest {

  /**
   * <p>
   * Testing that the {@link Range} constructor fails fast with a {@link NullPointerException}, when
   * a <code>null</code> value is passed to <code>rangeStart</code>
   * </p>
   * 
   * @throws NullPointerException
   * @see Range#getRangeStart()
   */
  @Test(expected = NullPointerException.class)
  public void testRangeConstructorForNullRangeStart() throws NullPointerException {

    of(null, 2015);
  }

  /**
   * <p>
   * Testing that the {@link Range} constructor fails fast with a {@link NullPointerException}, when
   * a <code>null</code> value is passed to <code>rangeEnd</code>
   * </p>
   * 
   * @throws NullPointerException
   * @see Range#getRangeEnd()
   */
  @Test(expected = NullPointerException.class)
  public void testRangeConstructorForNullRangeEnd() throws NullPointerException {

    of(2015, null);
  }

  /**
   * Testing that the constant {@link Range#DEFAULT_RANGE} is used when zero is passed for
   * <code>rangeStart</code>
   */
  @Test
  public void testRangeInstansiation() {

    Range range1 = of(0);
    assertThat(range1, is(equalTo(DEFAULT_RANGE)));
  }

  /**
   * <p>
   * Testing that the {@link Range#equals} method of the {@link Range} class works correctly
   * </p>
   * 
   * @see Range#equals()
   * @see Range
   */
  @Test
  public void testEquals() {
    Range range1 = of(2001, 2010);
    Range range2 = of(2001, 2010);
    Range range3 = of(2001, 2010);
    assertThat(range1, is(equalTo(range2)));
    assertThat(range2, is(equalTo(range3)));
    assertThat(range1, is(equalTo(range3)));
  }

  /**
   * <p>
   * Testing that the {@link Range#hashCode} method of the {@link Range} class works correctly
   * </p>
   * 
   * @see Range#hashCode()
   * @see Range
   */
  @Test
  public void testHash() {
    Range range1 = Range.of(2001, 2010);
    Range range2 = Range.of(2001, 2010);
    assertThat(range1.hashCode(), is(equalTo(range2.hashCode())));
  }
}
