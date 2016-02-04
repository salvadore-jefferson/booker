/*
 * Copyright © 2015 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker.repo;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jefferson.salvadore.booker.publication.Publication;


/**
 * This is a <code>Fake</code> implementation of {@link PublicationRepository}. For testing purposes
 * only.
 * 
 * @author Salvadore Jefferson
 *
 */
public class FakePublicationRepository implements PublicationRepository {
  final Logger logger = LoggerFactory.getLogger(PublicationManagerTest.class);
  private final Set<Publication> repo = new HashSet<>();
  private final Map<String, Integer> publicationMap = new HashMap<>();
  private Integer inventoryCount = 0;

  @Override
  public void addPub(Publication publication) throws IOException {
    final String title = publication.getTitle();
    repo.add(publication);
    publicationMap.put(title, inventoryCount);

  }

  @Override
  public void removePubByTitle(String title) throws IOException {
    for (Publication pub : repo) {
      if (pub.getTitle().equals(title)) {
        repo.remove(pub);
      }
    }

  }

  @Override
  public <P extends Publication> List<P> getByType(Class<P> publicationType) throws IOException {
    logger.info("This method is not implemented for the current test usage");
    return null;
  }

  @Override
  public Publication findByTitle(String title) throws IOException, NoSuchElementException {
    logger.info("This method is not implemented for the current test usage");
    return null;
  }

  @Override
  public void printAllPubs() {
    logger.info("This method is not implemented for the current test usage");

  }

  @Override
  public void setInventoryCount(String title, int count) throws IOException {
    publicationMap.replace(title, count);

  }

  @Override
  public Integer getInventoryCount(String title) throws IOException {
    return publicationMap.getOrDefault(title, inventoryCount);
  }

  /**
   * Returns the size of the repository
   * 
   * @return int The repository's size
   */
  public int getRepoSize() {
    return repo.size();
  }

}
