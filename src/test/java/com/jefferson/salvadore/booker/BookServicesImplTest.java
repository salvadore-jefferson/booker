/*
 * Copyright © 2015 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jefferson.salvadore.booker.publication.Book;
import com.jefferson.salvadore.booker.repo.PublicationManager;

public class BookServicesImplTest {
  static BookerServices service;
  static PublicationManager manager;
  static Book testBook;

  @BeforeClass
  public static void setUpTest() {
    manager = mock(PublicationManager.class);
    service = new BookerServicesImpl(manager);
    testBook = mock(Book.class);
  }

  @Test
  public void testBookerServicesImpl() throws IOException {
    service.purchasePublication(testBook.getTitle(), 1);
    verify(manager).removeStockFromInventory(testBook.getTitle(), 1);

    service.checkInventory(testBook.getTitle());
    verify(manager).getInventoryCount(testBook.getTitle());

  }

  @AfterClass
  public static void cleanUp() {
    manager = null;
    testBook = null;
    service = null;
  }
}
