/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.publication;

import static com.jefferson.salvadore.booker.publication.PubFreq.*;
import static com.jefferson.salvadore.booker.publication.Range.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

/**
 * This class tests the methods of the {@link Magazine} class
 * 
 * @version 1.0.0 9-9-2015
 * @author Salvadore Jefferson
 * @see Magazine
 */
public class MagazineTest {

  /**
   * <p>
   * Testing that the {@link Magazine} constructor fails fast with a {@link NullPointerException},
   * when a <code>null</code> value is passed to <code>title</code>
   * </p>
   * 
   * @throws NullPointerException
   * @see Magazine#getTitle()
   */
  @Test(expected = NullPointerException.class)
  public void testMagazineConstructorForNullTitle() throws NullPointerException {

    new Magazine(null, "AARP", of(2015), 22_274_096, pubFreqOf(MONTHLY));
  }

  /**
   * <p>
   * Testing that the {@link Magazine} constructor fails fast with a {@link NullPointerException},
   * when a <code>null</code> value is passed to <code>publisher</code>
   * </p>
   * 
   * @throws NullPointerException
   * @see Magazine#getTitle()
   */
  @Test(expected = NullPointerException.class)
  public void testMagazineConstructorForNullPublisher() throws NullPointerException {

    new Magazine("Title", null, of(2015), 22_274_096, pubFreqOf(MONTHLY));
  }

  /**
   * <p>
   * Testing that the {@link Magazine#getTitle} method of the {@link Magazine} class returns the
   * expected value
   * </p>
   * 
   * @see Magazine#getTitle()
   * @see Magazine
   */
  @Test
  public void testGetTitle() {

    Magazine mag = new Magazine("Title", "Publisher", of(2015), 22_274_096, pubFreqOf(MONTHLY));
    assertThat(mag.getTitle(), is(equalTo("Title")));
  }

  /**
   * <p>
   * Testing that the {@link Magazine#getPublisher} method of the {@link Magazine} class returns the
   * expected value
   * </p>
   * 
   * @see Magazine#getPublisher()
   * @see Magazine
   */
  @Test
  public void testGetPublisher() {

    Magazine mag = new Magazine("Title", "Publisher", of(2015), 22_274_096, pubFreqOf(MONTHLY));
    assertThat(mag.getPublisher(), is(equalTo("Publisher")));
  }

  /**
   * <p>
   * Testing that the {@link Magazine#equals} method of the {@link Magazine} class works correctly
   * </p>
   * 
   * @see Magazine#equals()
   * @see Magazine
   */
  @Test
  public void testEquals() {
    Magazine mag1 = new Magazine("Title", "Publisher", of(2015), 22_274_096, pubFreqOf(MONTHLY));
    Magazine mag2 = new Magazine("Title", "Publisher", of(2015), 22_274_096, pubFreqOf(MONTHLY));
    Magazine mag3 = new Magazine("Title", "Publisher", of(2015), 22_274_096, pubFreqOf(MONTHLY));
    assertThat(mag1, is(equalTo(mag2)));
    assertThat(mag2, is(equalTo(mag3)));
    assertThat(mag1, is(equalTo(mag3)));
  }

  /**
   * <p>
   * Testing that the {@link Magazine#hashCode} method of the {@link Magazine} class works correctly
   * </p>
   * 
   * @see Magazine#hashCode()
   * @see Magazine
   */
  @Test
  public void testHash() {
    Magazine mag1 = new Magazine("Title", "Publisher", of(2015), 22_274_096, pubFreqOf(MONTHLY));
    Magazine mag2 = new Magazine("Title", "Publisher", of(2015), 22_274_096, pubFreqOf(MONTHLY));
    assertThat(mag1.hashCode(), is(equalTo(mag2.hashCode())));
  }
}
