/*
 * Copyright © 2015 Salvadore Jefferson
 */

package com.jefferson.salvadore.booker.repo;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.jefferson.salvadore.booker.publication.Publication;

/**
 * A persistent storage repository for {@link Publication}s
 * 
 * @author Salvadore Jefferson
 * @version 2.0.0 12-02-2015
 * 
 */
public interface PublicationRepository {

  /**
   * Adds a {@link Publication} to the repository
   * 
   * @param publication the type of <code>Publication</code> to be added to the repository
   * @throws IOException if there is an error accessing the repository.
   */
  void addPub(@Nonnull final Publication publication) throws IOException;

  /**
   * Removes the specified {@link Publication} from the repository
   * 
   * @param publication The <code>Publication</code> to be removed from the repository.
   * @throws IOException if there is an error accessing the repository.
   */
  void removePubByTitle(@Nonnull final String title) throws IOException;

  /**
   * Retrieves all available {@link Publication}s of a given type.
   * 
   * @param publicationType
   * 
   * @return Collection A new Collection of type <code>P</code>
   * @throws IOException if there is an error accessing the repository.
   */
  <P extends Publication> List<P> getByType(@Nonnull final Class<P> publicationType)
      throws IOException;

  /**
   * Finds a given title in the repository. Throws <code>NoSuchElementException</code> if no title
   * matches query.
   * 
   * @param title The title of the <code>Publication</code> to find
   * @return Publication The specified title if present in the repository.
   * @throws IOException If there is an error accessing the repository.
   * @throws NoSuchelEmentException if no title matches the query.
   */
  @Nullable
  Publication findByTitle(@Nonnull final String title) throws IOException, NoSuchElementException;

  /**
   * Print all <code>Publication</code>s to the standard output window
   * 
   */
  public void printAllPubs();

  /**
   * Set the inventory count for the specified {@link Publication}.
   * 
   * @param String The title of the <code>Publication</code> inventory count to modify.
   * @param inventoryCount The inventory count to apply to this <code>Publication</code>.
   */
  public void setInventoryCount(@Nonnull final String title, final int count) throws IOException;

  /**
   * Returns the current inventory count for the specified {@link Publication} .
   * 
   * @param String The title of the <code>Publication</code>'s inventory count to get.
   * @return int The current inventory count for this <code>Publication</code> .
   */
  public Integer getInventoryCount(@Nonnull final String title) throws IOException;

  /**
   * Print all {@link Publication}s from one repository to another
   * 
   * @param repo The Repository to copy from
   * @throws IOException If an error occurs will accessing the repository
   */
  public default void addAllPublications(@Nonnull final PublicationRepository repo)
      throws IOException {
    for (Publication pub : repo.getByType(Publication.class)) {
      addPub(pub);
    }
  }

  /**
   * Performs the necessary actions to set up the repository
   * 
   * @throws IOException
   */
  public default void initialize() throws IOException {

  }
}
