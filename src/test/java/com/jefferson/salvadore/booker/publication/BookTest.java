/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.publication;

import static com.jefferson.salvadore.booker.publication.Book.MIN_SOLD;
import static com.jefferson.salvadore.booker.publication.PubFreq.*;
import static com.jefferson.salvadore.booker.publication.Range.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

/**
 * This class tests the methods of the {@link Book} class
 * 
 * @version 1.0.1 9-9-2015
 * @author Salvadore Jefferson
 * @see Book
 */

public class BookTest {

  /**
   * <p>
   * Testing that the {@link Book} constructor fails fast with a {@link NullPointerException}, when
   * a <code>null</code> value is passed to <code>bookTitle</code>
   * </p>
   * 
   * @throws NullPointerException
   * @see Book#getTitle()
   */
  @Test(expected = NullPointerException.class)
  public void testBookConstructorForNullBookTitle() throws NullPointerException {

    new Book(null, "Author", "English", of(2015), MIN_SOLD, pubFreqOf(NA));
  }

  /**
   * <p>
   * Testing that the {@link Book} constructor fails fast with a {@link NullPointerException}, when
   * a <code>null</code> values is passed to <code>authorName</code>
   * </p>
   * 
   * @throws NullPointerException
   * @see Book#getAuthor()
   */
  @Test(expected = NullPointerException.class)
  public void testBookConstructorForNullAuthorName() throws NullPointerException {

    new Book("Title", null, "English", of(2015), MIN_SOLD, pubFreqOf(NA));
  }

  /**
   * <p>
   * Testing that the {@link Book} constructor fails fast with a {@link NullPointerException}, when
   * <code>null</code> is passed to <code>printLanguage</code>
   * </p>
   * 
   * @throws NullPointerException
   * @see Book#getLang()
   */
  @Test(expected = NullPointerException.class)
  public void testBookConstructorForNullLanguage() throws NullPointerException {

    new Book("Title", "Author", null, of(2015), MIN_SOLD, pubFreqOf(NA));
  }

  /**
   * <p>
   * Testing that the {@link Book} constructor fails fast with an {@link IllegalArgumentException},
   * when a value less then <code>zero</code> is passed for <code>publicationDate</code>
   * </p>
   * 
   * @throws IllegalArgumentException
   * @see Book#getYearsPublished()
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBookConstructorForPublicationDateIllegalArgumentException()
      throws IllegalArgumentException {

    new Book("Title", "Author", "Lang", of(-2015), MIN_SOLD, pubFreqOf(NA));
  }

  /**
   * <p>
   * Testing that the {@link Book} constructor fails fast with an {@link IllegalArgumentException},
   * when a value less then <code>100,000,000</code> is passed for <code>totalSold</code>
   * </p>
   * 
   * @throws IllegalArgumentException
   * @see Book#gettotalSold()
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBookConstructorFortotalSoldIllegalArgumentException()
      throws IllegalStateException {

    new Book("Title", "Author", "Lang", of(2017), MIN_SOLD - 1, pubFreqOf(NA));
  }


  /**
   * <p>
   * Testing that the {@link Book#getTitle} method of the {@link Book} class returns the expected
   * value
   * </p>
   * 
   * @see Book#getTitle()
   * @see Book
   */
  @Test
  public void testGetTitle() {

    Book book = new Book("Title", "Author", "Lang", of(2015), MIN_SOLD, pubFreqOf(NA));
    assertThat(book.getTitle(), is(equalTo("Title")));
  }

  /**
   * <p>
   * Testing that the {@link Book#getAuthor} method of the {@link Book} class returns the expected
   * value
   * </p>
   * 
   * @see Book#getAuthor()
   * @see Book
   */
  @Test
  public void testGetAuthor() {

    Book book = new Book("Title", "Author", "Lang", of(2015), MIN_SOLD, pubFreqOf(NA));
    assertThat(book.getAuthor(), is(equalTo("Author")));
  }

  /**
   * <p>
   * Testing that the {@link Book#getLang} method of the {@link Book} class returns the expected
   * value
   * </p>
   * 
   * @see Book#getLang()
   * @see Book
   */
  @Test
  public void testGetLang() {

    Book book = new Book("Title", "Author", "Lang", of(2015), MIN_SOLD, pubFreqOf(NA));
    assertThat(book.getLanguage(), is(equalTo("Lang")));
  }

  /**
   * <p>
   * Testing that the {@link Book#getPublicationDate} method of the {@link Book} class returns the
   * expected value
   * </p>
   * 
   * @see Book#getYearsPublished()
   * @see Book
   */
  @Test
  public void testGetPublicationDate() {

    Book book = new Book("Title", "Author", "Lang", of(2015, 2016), MIN_SOLD, pubFreqOf(NA));
    assertThat(book.getYearsPublished(), is(equalTo(of(2015, 2016))));
  }

  /**
   * <p>
   * Testing that the {@link Book#gettotalSold} method of the {@link Book} class returns the
   * expected value
   * </p>
   * 
   * @see Book#gettotalSold()
   * @see Book
   */
  @Test
  public void testGetTotalSold() {

    Book book = new Book("Title", "Author", "Lang", of(2015), MIN_SOLD, pubFreqOf(NA));
    assertThat(book.getTotalSold(), is(equalTo(MIN_SOLD)));
  }

  /**
   * <p>
   * Testing that the {@link Book#equals} method of the {@link Book} class works correctly
   * </p>
   * 
   * @see Book#equals()
   * @see Book
   */
  @Test
  public void testEquals() {
    Book book1 = new Book("Title", "Author", "Lang", of(2015), MIN_SOLD, pubFreqOf(NA));
    Book book2 = new Book("Title", "Author", "Lang", of(2015), MIN_SOLD, pubFreqOf(NA));
    Book book3 = new Book("Title", "Author", "Lang", of(2015), MIN_SOLD, pubFreqOf(NA));
    assertThat(book1, is(equalTo(book2)));
    assertThat(book2, is(equalTo(book3)));
    assertThat(book1, is(equalTo(book3)));
  }

  /**
   * <p>
   * Testing that the {@link Book#hashCode} method of the {@link Book} class works correctly
   * </p>
   * 
   * @see Book#hashCode()
   * @see Book
   */
  @Test
  public void testHash() {
    Book book1 = new Book("Title", "Author", "Lang", of(2015), MIN_SOLD, pubFreqOf(NA));
    Book book2 = new Book("Title", "Author", "Lang", of(2015), MIN_SOLD, pubFreqOf(NA));
    assertThat(book1.hashCode(), is(equalTo(book2.hashCode())));
  }
}
