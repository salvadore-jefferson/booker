/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.publication;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.jefferson.salvadore.booker.publication.PubFreq.NA;
import static com.jefferson.salvadore.booker.publication.PubFreq.pubFreqOf;
import static java.util.Objects.hash;

import javax.annotation.Nonnull;

/**
 * <p>
 * The Book class is used for creating book objects for use in the Booker Application
 * </p>
 * 
 * @version 4.0.0 9-17-2015
 * @author Salvadore Jefferson
 */
public class Book extends AbstractPublication {

  private static final long serialVersionUID = 111L;
  static final int MIN_SOLD = 100_000_000;
  static final int CURRENT_YEAR = 2015;
  private final int totalSold;
  private final String authorName;
  private final String printLanguage;
  private final PubFreq frequency;

  // TODO Add javadoc for the main Book constructor
  /**
   * @param authorName The name of the book's author. Cannot be <code>null</code>
   * @param printLanguage The language the book was originally printed in. Cannot be
   *        <code>null</code>
   * @throws IllegalArgumentException A value entered for totalSold or publicationDate is not valid
   * @throws NullPointerException A value entered for title, authorName or printLanguage is not
   *         valid
   */
  public Book(@Nonnull final String title, @Nonnull final String authorName,
      @Nonnull final String printLanguage, final Range year, final int totalSold,
      @Nonnull final PubFreq frequency) throws NullPointerException, IllegalArgumentException {

    super(title, year, frequency);
    this.authorName = checkNotNull(authorName, "null not supported for authorName");
    this.printLanguage = checkNotNull(printLanguage, "null not supported for printLanguage");
    checkArgument(totalSold >= MIN_SOLD,
        "totalSold must be greater than or equal to 100,000,000 for books in this list");
    this.totalSold = totalSold;
    this.frequency = checkNotNull(frequency);
  }

  /**
   * <p>
   * A chained constructor of the Book class. This constructor sets the printLanguage parameter to
   * "English", and calls the main constructor of the Book class for all other parameters
   * </p>
   * 
   * @param title The title of the book
   * @param authorName The name of the book's author
   * @param year The date the book was originally published
   * @param totalSold The total number of copies sold as of the writing of this class
   */
  public Book(@Nonnull final String title, @Nonnull final String authorName, final Range year,
      final int totalSold, @Nonnull final PubFreq frequency) {

    this(title, authorName, "English", year, totalSold, pubFreqOf(NA));

  }

  /**
   * <p>
   * A chained constructor of the Book class. This constructor sets the printLanguage parameter to
   * "English", the totalSold parameter to 100,000,000 and calls the main constructor of the Book
   * class for all other parameters
   * </p>
   * 
   * @param title The title of the book
   * @param authorName The name of the book's author
   * @param publicationDate The date the book was originally published
   */
  public Book(@Nonnull final String title, @Nonnull final String authorName, final Range year,
      @Nonnull final PubFreq frequency) {

    this(title, authorName, "English", year, MIN_SOLD, pubFreqOf(NA));

  }

  /**
   * @return String The name of the author
   */
  public String getAuthor() {
    return authorName;
  }

  /**
   * @return String The language the original printing was written in
   */
  public String getLanguage() {
    return printLanguage;
  }

  /**
   * return int The total number of copies sold as of the writing of this class
   */
  public int getTotalSold() {
    return totalSold;
  }

  /**
   * Prints the list of Books
   * 
   * @param Publication A variable length of parameters, passed to the method as arguments
   * @see <a href = "http://docs.oracle.com/javase/7/docs/technotes/guides/language/varargs.html">
   *      varargs</a>
   */
  public static void printAll(final Publication... publications) {

    for (Publication theBook : publications) {
      if (theBook instanceof Book) {
        final Book book = (Book) theBook;
        System.out.println(String.format(
            "Book Title: %s | Author: %s | Print Language: %s | Publish Date: %s |  Total in Circulation:%s",
            book.getTitle(), book.getAuthor(), book.getLanguage(), book.getYearsPublished(),
            book.getTotalSold()));
      }
    }
  }

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof Book)) {
      return false;
    }
    final Book book = (Book) otherObject;
    return this.getTitle().equals(book.getTitle()) && this.getAuthor().equals(book.getAuthor());
  }

  @Override
  public int hashCode() {
    return hash(getTitle(), getAuthor());
  }

  @Override
  public String toString() {
    String debugMessage = "\n" + super.toString()
        + String.format("%nBook Title: %s | Author: %s | Year Published: %s", this.getTitle(),
            this.getAuthor(), this.getYearsPublished());
    return debugMessage;

  }
}
