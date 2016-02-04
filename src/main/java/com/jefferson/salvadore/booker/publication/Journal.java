/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.publication;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.hash;

import javax.annotation.Nonnull;

/**
 * <p>
 * The Journal class is used for creating journal objects for use in the Booker Application
 * </p>
 * 
 * @version 3.0.0 10-25-2015
 * @author Salvadore Jefferson
 * @extends BasePeriodical
 */
public class Journal extends BasePeriodical {

  private static final long serialVersionUID = 111L;
  private final String discipline;
  private final double impactFactor;

  /**
   * <p>
   * Constructor for creating Journal objects
   * </p>
   * 
   * @param title The title of the Journal
   * @param publisher Who published the Journal
   * @param year The year first published
   * @param frequency How often the Journal is distributed
   * @param discipline The field of study for this Journal
   * @param impactFactor Used as a proxy for the relative importance of a Journal within its field
   * @throws NullPointerException
   * @throws IllegalArgumentException
   */
  public Journal(@Nonnull final String title, @Nonnull final String publisher, final Range year,
      @Nonnull final PubFreq frequency, @Nonnull final String discipline, final double impactFactor)
          throws NullPointerException, IllegalArgumentException {

    super(title, publisher, "Journal", year, frequency);

    checkArgument(impactFactor >= 0, "impactFactor must be greater than or equal to zero");
    this.impactFactor = impactFactor;

    this.discipline = checkNotNull(discipline, "null not supported for discipline");
  }

  /**
   * @return double Used as a proxy for the relative importance of a Journal within its field
   */
  public double getImpactFactor() {
    return impactFactor;
  }

  /**
   * @return String The area of study the Journal covers
   */
  public String getDiscipline() {
    return discipline;
  }

  /**
   * Prints the list of Journals
   * 
   * @param Publication A variable length of parameters, passed to the method as arguments
   * @see <a href = "http://docs.oracle.com/javase/7/docs/technotes/guides/language/varargs.html">
   *      varargs</a>
   */
  public static void printAll(final Publication... publications) {

    for (Publication periodical : publications) {
      if (periodical instanceof Journal) {
        final Journal journal = (Journal) periodical;
        System.out.println(String.format(
            "Periodical Type: %s | Title: %s | Publish Date: %s | Impact Factor: %s | Publisher: %s | Discipline: %s",
            journal.getType(), journal.getTitle(), journal.getYearsPublished(),
            journal.getImpactFactor(), journal.getPublisher(), journal.getDiscipline()));
      }
    }
  }

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof Journal)) {
      return false;
    }
    final Journal journal = (Journal) otherObject;
    return this.getTitle().equals(journal.getTitle())
        && this.getPublisher().equals(journal.getPublisher());
  }

  @Override
  public int hashCode() {
    return hash(getTitle(), getPublisher());
  }
}
