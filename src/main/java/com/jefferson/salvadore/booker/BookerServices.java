/*
 * Copyright © 2015 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.annotation.Nonnull;

import com.jefferson.salvadore.booker.publication.Publication;
/**
 * A <code>Interface</code> for encapsulating the application logic of the {@link Booker}
 * application
 * 
 * @author Salvadore Jefferson
 * @version 1.0.0 12-02-15
 *
 */
import com.jefferson.salvadore.booker.repo.PublicationRepository;


public interface BookerServices {

  /**
   * Purchase a variable quantity of the specified title from the {@PublicationRepository}
   * 
   * @param title The title of the {@link Publication} to purchase.
   * @param quantity The number of copies to purchase.
   * @throws IOException If an error occurs while accessing the repository. Or if
   *         <code>quantity</code> is greater than the inventory for this title.
   */
  void purchasePublication(@Nonnull String title, @Nonnull Integer quantity) throws IOException;

  /**
   * Checks the inventory count of the specified title.
   * 
   * @param title The title to query
   * @return Integer The current inventory count for this title.
   * @throws IOException If an error occurs while accessing the repository.
   */
  Integer checkInventory(@Nonnull String title) throws IOException;

  /**
   * Walks through the argument list and prints the name of specified <code>Publication</code>s
   * based on search criteria
   * 
   * @throws IOException Thrown if there is a problem accessing the repository
   */
  void printPubByType(@Nonnull PublicationRepository repo, @Nonnull String typeList)
      throws IOException;

  /**
   * Delegates operations to
   * {@link SubscriptionService#subscribe(PublicationRepository, String, int)}
   * 
   * @param repo The repository to access the data from.
   * @param title The title of the Periodical subscribed to.
   * @param issueCount The number of issues to receive
   * @throws IOException If an issue occurs while accessing the repository.
   */
  void subscribeToPeriodical(@Nonnull final PublicationRepository repo, @Nonnull final String title,
      final int issueCount) throws IOException;

  /**
   * Opens an HTTP connection to the Google Books API and prints the resultant information to the
   * console window
   * 
   * @throws MalformedURLException
   * @throws IOException
   */
  void openConnectionToGoogleBooksAPI(@Nonnull final String isbn)
      throws MalformedURLException, IOException;
}
