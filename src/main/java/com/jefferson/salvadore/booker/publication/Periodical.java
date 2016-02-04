/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.publication;

/**
 * <p>
 * An interface used to define common behavior of multiple periodical types
 * </p>
 * 
 * @version 1.0.0 9-11-2015
 * @author Salvadore Jefferson
 */

public interface Periodical extends Publication {

  /**
   * Get the Type of the periodical. For example, Journal or Magazine
   * 
   * @return String
   */
  String getType();

  /**
   * Get the name of the publisher
   * 
   * @return String
   */
  String getPublisher();

}
