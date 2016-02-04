/*
 * Copyright © 2015 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker.repo;

import java.io.IOException;

import javax.annotation.Nonnull;

import com.jefferson.salvadore.booker.publication.Publication;
import com.jefferson.salvadore.booker.util.PublicationModule;

/**
 * An Interface for interacting with the Data Level to manipulate the inventory count for a single
 * {@link Publication} at a time, in a {@link PublicationRepository}.
 * 
 * @author Salvadore Jefferson
 * @version 1.0.0 12-02-15
 */
public interface PublicationManager {

  /**
   * Increases the inventory count of the repository by the specified amount.
   * 
   * @param title The title of the {@link Publication} who's stock to increase.
   * @param amountToAdd The amount to add to the stock level.
   * @throws IOException If an error occurs while accessing the Repository
   */
  void addStockToInventory(@Nonnull final String title, @Nonnull final Integer amountToAdd)
      throws IOException;

  /**
   * Decreases the inventory count of the repository by the specified amount.
   * 
   * @param title The title of the {@link Publication} who's stock to decrease.
   * @param amountToRemove The amount to remove from the stock level.
   * @throws IOException If an error occurs while accessing the Repository
   */
  void removeStockFromInventory(@Nonnull final String title, @Nonnull final Integer amountToRemove)
      throws IOException;

  /**
   * Gets the inventory count for the specified {@link Publication}
   * 
   * @param title The title of the <code>Publication</code> to query
   * @return The inventory count for the specified <code>Publication</code>
   * @throws IOException If an error occurs while accessing the Repository
   */
  Integer getInventoryCount(@Nonnull final String title) throws IOException;

  /**
   * Sets the {@link PublicationRepository} via Dependency Injection through the
   * {@link PublicationModule} class. This will override the default
   * {@link SnapshoptTopPublicationReposiroty} implementation.
   * 
   * @param repo The desired PublicationRepository to manage.
   */
  public void setRepo(@Nonnull final PublicationRepository repo);
}
