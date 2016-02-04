/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.publication;

import static java.util.Objects.hash;

import javax.annotation.Nonnull;

/**
 * <p>
 * The Magazine class is used for creating magazine objects for use in the Booker Application
 * </p>
 * 
 * @version 3.0.0 10-25-2015
 * @author Salvadore Jefferson
 */
public class Magazine extends BasePeriodical {

  private static final long serialVersionUID = 111L;
  private final int totalInCirculation;

  /**
   * <p>
   * The construct for creating Magazine publications
   * </p>
   * 
   * @param title The title of the publication. Cannot be <code>null</code>
   * @param publisher The name of the publisher. Cannot be <code>null</code>
   * @param year The date the publication was originally published. Must be a positive number
   *        greater than or equal to zero
   * @param totalInCirculation The total number of copies sold as of the writing of this class.
   * @throws IllegalArgumentException A value entered for totalInCirculation or publicationDate is
   *         not valid
   * @throws NullPointerException A value entered for title, authorName or printLanguage is
   *         <code>null</code>
   */
  public Magazine(@Nonnull final String title, @Nonnull final String publisher, final Range year,
      final int totalInCirculation, @Nonnull final PubFreq frequency)
          throws NullPointerException, IllegalArgumentException {

    super(title, publisher, "Magazine", year, frequency);
    this.totalInCirculation = totalInCirculation;

  }

  /**
   * return int The total number of periodicals in circulation
   */
  public int getTotalInCirculation() {
    return totalInCirculation;
  }

  /**
   * Prints the list of Magazines
   * 
   * @param Publication A variable length of parameters, passed to the method as arguments
   * @see <a href = "http://docs.oracle.com/javase/7/docs/technotes/guides/language/varargs.html">
   *      varargs</a>
   */
  public static void printAll(final Publication... publications) {

    for (Publication periodical : publications) {
      if (periodical instanceof Magazine) {
        final Magazine magazine = (Magazine) periodical;
        System.out.println(String.format(
            "Periodical Type: %s | Title: %s | Publish Date: %s | Total in Circulation: %s | Publisher: %s",
            magazine.getType(), magazine.getTitle(), magazine.getYearsPublished(),
            magazine.getTotalInCirculation(), magazine.getPublisher()));
      }
    }
  }

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof Magazine)) {
      return false;
    }
    final Magazine mag = (Magazine) otherObject;
    return this.getTitle().equals(mag.getTitle()) && this.getPublisher().equals(mag.getPublisher());
  }

  @Override
  public int hashCode() {
    return hash(getTitle(), getPublisher());
  }
}
