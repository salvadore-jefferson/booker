/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.publication;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Comparator;

import javax.annotation.Nonnull;

/**
 * <p>
 * AbstractPublication is the superclass of {@link Book}, {@link Magazine} and {@link Journal}
 * classes. This is an abstract class to prohibit others from instantiating the class as well as
 * abstracting out common data and behavior shared by <code>Book</code> and <code>Magazine</code>
 * <code>Journal</code> objects.
 * </p>
 * 
 * @version 3.0.0 9-17-2015
 * @author Salvadore Jefferson
 */
public abstract class AbstractPublication implements Publication {

  private static final long serialVersionUID = 111L;
  private final String title;
  private final Range yearsPublished;
  private final PubFreq frequency;

  /**
   * <p>
   * The construct for creating {@link Book}, {@link Magazine} and {@link Journal} publications
   * </p>
   * 
   * @param title The title of the publication. Cannot be <code>null</code>
   * @param years The date the publication was originally published. Must be a positive number
   *        greater than or equal to zero
   * @throws IllegalArgumentException A value entered for totalInCirculation or publicationDate is
   *         not valid
   * @throws NullPointerException A value entered for title, authorName or printLanguage is
   *         <code>null</code>
   */
  public AbstractPublication(@Nonnull final String title, @Nonnull final Range years,
      @Nonnull final PubFreq frequency) throws NullPointerException, IllegalArgumentException {

    this.title = checkNotNull(title, "null not supported for title");
    this.frequency = checkNotNull(frequency);

    checkArgument(years.getRangeStart() >= 0,
        "publicationDate must be greater than or equal to zero");
    yearsPublished = years;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public Range getYearsPublished() {
    return yearsPublished;
  }

  @Override
  public int getPubFreq() {
    return frequency.getFreqValue();
  }

  public static EndDateComparatorImpl publicationEndDateComparator = new EndDateComparatorImpl();

  public static class EndDateComparatorImpl implements Comparator<Publication> {

    @Override
    public int compare(Publication o1, Publication o2) {
      return Integer.compare(((AbstractPublication) o1).getYearsPublished().getRangeEnd(),
          ((AbstractPublication) o2).getYearsPublished().getRangeEnd());
    }
  }
}
