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
 * This class tests the methods of the {@link Journal} class
 * 
 * @version 1.0.0 9-13-2015
 * @author Salvadore Jefferson
 * @see Journal
 */
public class JournalTest {

  /**
   * <p>
   * Testing that the {@link Journal} constructor fails fast with a {@link NullPointerException},
   * when a <code>null</code> value is passed to <code>title</code>
   * </p>
   * 
   * @throws NullPointerException
   */
  @Test(expected = NullPointerException.class)
  public void testJournalConstructorForNullTitle() throws NullPointerException {

    new Journal(null, "Nature Publishing Group", of(2015), pubFreqOf(UNDISCLOSED), "Earth Sciences",
        11.47);
  }

  /**
   * <p>
   * Testing that the {@link Journal} constructor fails fast with a {@link NullPointerException},
   * when a <code>null</code> value is passed to <code>publisher</code>
   * </p>
   * 
   * @throws NullPointerException
   */
  @Test(expected = NullPointerException.class)
  public void testJournalConstructorForNullPublisher() throws NullPointerException {

    new Journal("Nature Communications", null, of(2015), pubFreqOf(UNDISCLOSED), "Earth Sciences",
        11.47);
  }

  /**
   * <p>
   * Testing that the {@link Journal} constructor fails fast with a {@link NullPointerException},
   * when a <code>null</code> value is passed to <code>frequency</code>
   * </p>
   * 
   * @throws NullPointerException
   */
  @Test(expected = NullPointerException.class)
  public void testJournalConstructorForNullFrequency() throws NullPointerException {

    new Journal("Nature Communications", "Nature Publishing Group", of(2015), null,
        "Earth Sciences", 11.47);
  }

  /**
   * <p>
   * Testing that the {@link Journal} constructor fails fast with a {@link NullPointerException},
   * when a <code>null</code> value is passed to <code>discipline</code>
   * </p>
   * 
   * @throws NullPointerException
   */
  @Test(expected = NullPointerException.class)
  public void testJournalConstructorForNullDiscipline() throws NullPointerException {

    new Journal("Nature Communications", "Nature Publishing Group", of(2015),
        pubFreqOf(UNDISCLOSED), null, 11.47);
  }

  /**
   * <p>
   * Testing that the {@link Journal} constructor fails fast with a {@link IllegalArgumentException}
   * , when a value less then <code>zero</code> is passed for <code>impactFactor</code>
   * </p>
   * 
   * @throws IllegalArgumentException
   */
  @Test(expected = IllegalArgumentException.class)
  public void testJournalConstructorForIllegalArgumentExceptionImpactFactor()
      throws IllegalArgumentException {

    new Journal("Nature Communications", "Nature Publishing Group", of(2015),
        pubFreqOf(UNDISCLOSED), "Earth Sciences", -11.47);
  }

  /**
   * <p>
   * Testing that the {@link Journal#getTitle} method of the {@link Journal} class returns the
   * expected value
   * </p>
   * 
   * @see Journal#getTitle()
   * @see Journal
   */
  @Test
  public void testGetTitle() {

    Journal journal = new Journal("Title", "Nature Publishing Group", of(2015),
        pubFreqOf(UNDISCLOSED), "Earth Sciences", 11.47);
    assertThat(journal.getTitle(), is(equalTo("Title")));
  }

  /**
   * <p>
   * Testing that the {@link Journal#getPublisher} method of the {@link Journal} class returns the
   * expected value
   * </p>
   * 
   * @see Journal#getPublisher()
   * @see Journal
   */
  @Test
  public void testGetPublisher() {

    Journal journal = new Journal("Nature Communications", "Publisher", of(2015),
        pubFreqOf(UNDISCLOSED), "Earth Sciences", 11.47);
    assertThat(journal.getPublisher(), is(equalTo("Publisher")));
  }

  /**
   * <p>
   * Testing that the {@link Journal#getType} method of the {@link Journal} class returns the
   * expected value
   * </p>
   * 
   * @see Journal#getType()
   * @see Journal
   */
  @Test
  public void testGetType() {

    Journal journal = new Journal("Title", "Nature Publishing Group", of(2015),
        pubFreqOf(UNDISCLOSED), "Earth Sciences", 11.47);
    assertThat(journal.getType(), is(equalTo("Journal")));
  }

  /**
   * <p>
   * Testing that the {@link Journal#getImpactFactor} method of the {@link Journal} class returns
   * the expected value
   * </p>
   * 
   * @see Journal#getImpactFactor()
   * @see Journal
   */
  @Test
  public void testGetImpactFactor() {

    Journal journal = new Journal("Title", "Nature Publishing Group", of(2015),
        pubFreqOf(UNDISCLOSED), "Earth Sciences", 11.47);
    assertThat(journal.getImpactFactor(), is(equalTo(11.47)));
  }

  /**
   * <p>
   * Testing that the {@link Journal#equals} method of the {@link Journal} class works correctly
   * </p>
   * 
   * @see Journal#equals()
   * @see Journal
   */
  @Test
  public void testEquals() {
    Journal journal1 = new Journal("Title", "Nature Publishing Group", of(2015),
        pubFreqOf(UNDISCLOSED), "Earth Sciences", 11.47);
    Journal journal2 = new Journal("Title", "Nature Publishing Group", of(2015),
        pubFreqOf(UNDISCLOSED), "Earth Sciences", 11.47);
    Journal journal3 = new Journal("Title", "Nature Publishing Group", of(2015),
        pubFreqOf(UNDISCLOSED), "Earth Sciences", 11.47);
    assertThat(journal1, is(equalTo(journal2)));
    assertThat(journal2, is(equalTo(journal3)));
    assertThat(journal1, is(equalTo(journal3)));
  }

  /**
   * <p>
   * Testing that the {@link Journal#hashCode} method of the {@link Journal} class works correctly
   * </p>
   * 
   * @see Journal#hashCode()
   * @see Journal
   */
  @Test
  public void testHash() {
    Journal journal1 = new Journal("Title", "Nature Publishing Group", of(2015),
        pubFreqOf(UNDISCLOSED), "Earth Sciences", 11.47);
    Journal journal2 = new Journal("Title", "Nature Publishing Group", of(2015),
        pubFreqOf(UNDISCLOSED), "Earth Sciences", 11.47);
    assertThat(journal1.hashCode(), is(equalTo(journal2.hashCode())));
  }
}
