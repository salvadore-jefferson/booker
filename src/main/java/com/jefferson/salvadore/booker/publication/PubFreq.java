/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.publication;

import javax.annotation.Nonnull;

import com.jefferson.salvadore.booker.deprecated.SourceCodeReference;

/**
 * A set of type <code>Enum</code> used to represent the publication frequency of
 * {@link Periodicals} in the {@link SourceCodeReference} application
 * 
 * @author Salvadore Jefferson
 * @version 1.0.0 10-25-2015
 *
 */
public enum PubFreq {
  /**
   * A weekly publication frequency
   */
  WEEKLY(52),

  /**
   * A Monthly publication frequency
   */
  MONTHLY(12),

  /**
   * An unknown publication frequency
   */
  UNDISCLOSED(0),

  /**
   * An <code>Enum</code> for non-applicable publication types. For example {@link Book}
   */
  NA(-1);

  private final int freqValue;

  private PubFreq(final int freqValue) {
    this.freqValue = freqValue;
  }

  /**
   * A static factory method for assigning a <code>PubFreq</code> to a {@link Publication}
   * 
   * @param pubFreq
   * @return PubFreq
   */
  public static PubFreq pubFreqOf(@Nonnull final PubFreq pubFreq) {
    return pubFreq;
  }

  /**
   * Returns an <code>int</code> representing the publication frequency. If <code>PubFreq</code> is
   * set to <code>UNDISCLOSED</code>, zero is returned to indicate an unknown value
   */
  public int getFreqValue() {
    return freqValue;
  }
}
