/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.publication;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

/**
 * <p>
 * An abstract class used to define default values and settings to serve as a base for other
 * implementations
 * </p>
 * 
 * @version 2.0.0 9-17-2015
 * @author Salvadore Jefferson
 */

public abstract class BasePeriodical extends AbstractPublication implements Periodical {

  private static final long serialVersionUID = 111L;
  private final String publisher;
  private final String type;

  public BasePeriodical(@Nonnull final String title, @Nonnull final String publisher,
      @Nonnull final String type, final Range year, @Nonnull final PubFreq frequency)
          throws NullPointerException, IllegalArgumentException {

    super(title, year, frequency);
    this.publisher = checkNotNull(publisher, "null not supported for publisher");
    this.type = checkNotNull(type, "null not supported for publisher");

  }

  /**
   * @return The type of periodical
   */
  @Override
  public String getType() {
    return type;
  }

  /**
   * @return The name of the publisher
   */
  @Override
  public String getPublisher() {
    return publisher;
  }

  @Override
  public String toString() {
    String debugMessage = "\n" + super.toString()
        + String.format(
            "%nPeriodical Type: %s | Periodical Title: %s | Publisher: %s | Year Published: %s",
            this.getType(), this.getTitle(), this.getPublisher(), this.getYearsPublished());
    return debugMessage;

  }
}
