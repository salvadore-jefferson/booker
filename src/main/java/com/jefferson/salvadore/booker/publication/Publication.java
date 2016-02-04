/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.publication;

import java.io.Serializable;

/**
 * <p>
 * An interface used to define common behavior of multiple publication types
 * </p>
 * 
 * @version 2.0.0 11-18-2015
 * @author Salvadore Jefferson
 */
public interface Publication extends Serializable {

  /**
   * Get the title of the publication.
   * 
   * @return String
   */
  String getTitle();

  /**
   * Get the range; in years, of the publication's publish date
   * 
   * @return Range
   */
  Range getYearsPublished();

  /**
   * returns the <code>int</code> value represented by this {@link PubFreq}
   * 
   * @return int The value represented by this {@Publication}s {@link PubFreq}
   */
  int getPubFreq();
}

