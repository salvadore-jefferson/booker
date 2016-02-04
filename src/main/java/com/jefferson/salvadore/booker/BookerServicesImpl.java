/*
 * Copyright © 2015 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.net.HttpURLConnection.HTTP_OK;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jefferson.salvadore.booker.publication.Book;
import com.jefferson.salvadore.booker.publication.Journal;
import com.jefferson.salvadore.booker.publication.Magazine;
import com.jefferson.salvadore.booker.repo.PublicationManager;
import com.jefferson.salvadore.booker.repo.PublicationRepository;
import com.jefferson.salvadore.booker.util.IO;

/**
 * An implementation of the {@link BookerServices} interface.
 * 
 * @author Salvadore Jefferson
 * @version 1.0.0 12-02-15
 *
 */
@Singleton
public class BookerServicesImpl implements BookerServices {
  static final Logger logger = LoggerFactory.getLogger(BookerServicesImpl.class);
  public static final URI GOOGLE_BOOKS_API = URI.create("https://www.googleapis.com/books/v1/");

  private final PublicationManager manager;

  @Inject
  public BookerServicesImpl(@Nonnull final PublicationManager manager) {
    this.manager = checkNotNull(manager);
  }

  @Override
  public void purchasePublication(@Nonnull final String title, @Nonnull final Integer quantity)
      throws IOException {

    manager.removeStockFromInventory(title, quantity);
  }

  @Override
  public Integer checkInventory(@Nonnull final String title) throws IOException {
    return manager.getInventoryCount(title);
  }

  @Override
  public void printPubByType(@Nonnull final PublicationRepository repo,
      @Nonnull final String typeList) throws IOException {
    for (String splitString : typeList.split(",")) {
      if (splitString.equalsIgnoreCase("book")) {
        List<Book> bookList = repo.getByType(Book.class);
        for (Book book : bookList) {
          System.out.println(book);
        }
      }
      if (splitString.equalsIgnoreCase("magazine")) {
        List<Magazine> magList = repo.getByType(Magazine.class);
        for (Magazine mag : magList) {
          System.out.println(mag);
        }
      }
      if (splitString.equalsIgnoreCase("journal")) {
        List<Journal> journalList = repo.getByType(Journal.class);
        for (Journal journal : journalList) {
          System.out.println(journal);
        }
      }
    }
  }

  @Override
  public void subscribeToPeriodical(@Nonnull final PublicationRepository repo,
      @Nonnull final String title, final int issueCount) throws IOException {
    final SubscriptionService subscriber = new SubscriptionService(repo, title, issueCount);
    new Thread(subscriber).start();
  }

  @Override
  public void openConnectionToGoogleBooksAPI(@Nonnull final String isbn)
      throws MalformedURLException, IOException {
    String urlResolver = "volumes?q=isbn:" + isbn;
    URI resolvedURI = GOOGLE_BOOKS_API.resolve(urlResolver);
    final HttpURLConnection connection = (HttpURLConnection) resolvedURI.toURL().openConnection();
    connection.setRequestMethod("GET");
    if (connection.getResponseCode() != HTTP_OK) {
      logger.info("an error occured while establishing this connection");
      return;
    }
    try (final InputStream inputStream = new BufferedInputStream(connection.getInputStream())) {
      final Book book = JsonParser.parseJson(inputStream);
      System.out.println(book.toString());
    }
  }
}
