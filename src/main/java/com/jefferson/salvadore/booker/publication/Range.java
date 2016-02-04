/*
 * Copyright © 2015 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker.publication;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.hash;

import java.io.Serializable;

import javax.annotation.Nonnull;

/**
 * <p>
 * Creates values of type {@link Integer}, to represent a single date or a range of dates to define
 * the year range a publication was first published. This class is <em>final</em> and cannot be
 * subclasses.
 * 
 * A set of <code>constant</code> date ranges have been defined for this class
 * </p>
 * 
 * @version 1.0.0 9-17-2015
 * @author Salvadore Jefferson
 * 
 * @see #DEFAULT_RANGE
 * @see #CURRENT_YEAR
 */
public final class Range implements Serializable {

  private static final long serialVersionUID = 111L;
  /**
   * The default range. Primarily used when a publication does not specify the publish date.
   * <code>rangeStart</code> and <code>rangeEnd</code> set to 0, 0
   */
  public static final Range DEFAULT_RANGE = new Range(0, 0);

  /**
   * Used to represent a range of dates that correspond to the current year. <code>rangeStart</code>
   * and <code>rangeEnd</code> set to 2015, 2015
   */
  public static final Range CURRENT_YEAR = new Range(2015, 2015);

  /**
   * Used to set the start date of the range
   */
  private final Integer rangeStart;

  /**
   * Used to set the end date of the range
   */
  private final Integer rangeEnd;

  /**
   * <p>
   * Constructor for the <code>Range</code> class. Takes 2 Integer arguments as parameters. Set to
   * <code>private</code> so it can not be called to create new <code>Range</code> objects.
   * </p>
   * 
   * @param rangeStart
   * @param rangeEnd
   * @throws NullPointerException
   */
  private Range(@Nonnull final Integer rangeStart, @Nonnull final Integer rangeEnd)
      throws NullPointerException {
    this.rangeStart = checkNotNull(rangeStart);
    this.rangeEnd = checkNotNull(rangeEnd);
  }

  /**
   * <p>
   * Constructor for the Range class. Takes a single Integer argument. <code>rangeEnd</code> is set
   * to whatever is passed for <code>rangeStart</code>. This constructor is set to
   * <code>private</code> so it can not be called to create new <code>Range</code> objects.
   * </p>
   * 
   * @param rangeStart
   * @throws NullPointerException
   */
  private Range(@Nonnull final Integer rangeStart) throws NullPointerException {
    this.rangeStart = checkNotNull(rangeStart);
    this.rangeEnd = rangeStart;
  }

  /**
   * <p>
   * A static factory method for the <code>Range</code> class when a single argument is passed to
   * the constructor. Use this <code>of</code> method to create new <code>Range</code> objects. If
   * <code>zero</code> is passed for <code>rangeStart</code>, then {@link #DEFAULT_RANGE} will be
   * returned.
   * </p>
   * 
   * @param rangeStart
   * @return Range
   * @throws NullPointerException
   */
  public static @Nonnull Range of(@Nonnull final Integer rangeStart) throws NullPointerException {
    checkNotNull(rangeStart);
    if (rangeStart == 0) {
      return DEFAULT_RANGE;
    }
    return new Range(rangeStart, rangeStart);
  }

  /**
   * <p>
   * A static factory method for the <code>Range</code> class when two arguments are passed to the
   * constructor. Use this <code>of</code> method to create new <code>Range</code> objects. If
   * <code>zero</code> is passed for <code>rangeStart</code>, then {@link #DEFAULT_RANGE} will be
   * returned. If <code>zero</code> is passed for <code>rangeEnd</code>, then <code>rangeEnd</code>
   * will be set to <code>rangeStart</code>.
   * </p>
   * 
   * @param rangeStart
   * @param rangeEnd
   * @return Range
   * @throws NullPointerException
   */
  public static @Nonnull Range of(@Nonnull final Integer rangeStart,
      @Nonnull final Integer rangeEnd) throws NullPointerException {
    checkNotNull(rangeStart);
    checkNotNull(rangeEnd);
    if (rangeStart == 0 && rangeEnd == 0) {
      return DEFAULT_RANGE;
    }
    return new Range(rangeStart, rangeEnd);
  }

  // This method is not called, and commenting it out has no affect on the application
  /*- 
   * public Integer getYearPublished() { 
   * return rangeStart; 
   * }
   */

  /**
   * Get the start of the range of dates
   * 
   * @return Integer
   */
  public Integer getRangeStart() {
    return rangeStart;
  }

  /**
   * Get the end of the range of dates
   * 
   * @return Integer
   */
  public Integer getRangeEnd() {
    return rangeEnd;
  }

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (!(otherObject instanceof Range)) {
      return false;
    }
    final Range year = (Range) otherObject;
    return this.getRangeStart().equals(year.getRangeStart())
        && this.getRangeEnd().equals(year.getRangeEnd());
  }

  @Override
  public int hashCode() {
    return hash(getRangeStart(), getRangeEnd());
  }

  @Override
  public String toString() {
    return String.format("From: %s | To: %s", this.getRangeStart(), this.getRangeEnd());
  }

}

