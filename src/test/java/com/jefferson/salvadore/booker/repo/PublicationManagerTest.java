/*
 * Copyright © 2015 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker.repo;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.jefferson.salvadore.booker.publication.Book;

public class PublicationManagerTest {
  static PublicationManager manager;
  static PublicationRepository fakeRepo;
  static Book testBook;

  @Before
  public void setUpTest() throws IOException {
    fakeRepo = new FakePublicationRepository();
    manager = new PublicationManagerImpl(fakeRepo);
    testBook = mock(Book.class);
    fakeRepo.addPub(testBook);
    manager.addStockToInventory(testBook.getTitle(), 10);
  }

  @Test
  public void testAddStockToInventory() throws IOException {
    assertThat(manager.getInventoryCount(testBook.getTitle()), is(10));
  }

  @Test
  public void removeStockFromInventory() throws IOException {
    manager.removeStockFromInventory(testBook.getTitle(), 1);
    assertThat(manager.getInventoryCount(testBook.getTitle()), is(9));
  }

  @AfterClass
  public static void cleanUp() {
    manager = null;
    testBook = null;
    fakeRepo = null;
  }
}
