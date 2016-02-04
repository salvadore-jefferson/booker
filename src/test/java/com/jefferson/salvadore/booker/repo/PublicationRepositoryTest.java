/*
 * Copyright © 2015 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker.repo;

import static com.jefferson.salvadore.booker.publication.PubFreq.*;
import static com.jefferson.salvadore.booker.publication.Range.of;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.jefferson.salvadore.booker.publication.Book;
import com.jefferson.salvadore.booker.publication.Journal;
import com.jefferson.salvadore.booker.repo.PublicationRepository;
import com.jefferson.salvadore.booker.repo.snapshot.SnapshotTopPublicationRepository;

public class PublicationRepositoryTest {

  @Test
  public void testFindPubByTitleMethod() {
    PublicationRepository repo = new SnapshotTopPublicationRepository();
    try {
      assertThat(repo.findByTitle("PLOS ONE").getTitle(), is("PLOS ONE"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void testFindByTypeMethod() {
    PublicationRepository repo = new SnapshotTopPublicationRepository();
    try {
      List<Journal> journalList = repo.getByType(Journal.class);
      assertThat(journalList.size(), is(5));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test
  public void testAddNewPublicationMethod() {
    PublicationRepository repo = new SnapshotTopPublicationRepository();
    try {
      repo.addPub(new Book("Salvadore", "Jefferson", of(1887), pubFreqOf(NA)));
      assertThat(repo.findByTitle("Salvadore").getTitle(), is("Salvadore"));
    } catch (NullPointerException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Test(expected = NoSuchElementException.class)
  public void testRemovePublicationMethod() {
    PublicationRepository repo = new SnapshotTopPublicationRepository();
    try {
      repo.addPub(new Book("Salvadore", "Jefferson", of(1887), pubFreqOf(NA)));
      assertThat(repo.findByTitle("Salvadore").getTitle(), is("Salvadore"));
      repo.removePubByTitle("Salvadore");
      repo.findByTitle("Salvadore");
    } catch (NullPointerException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
