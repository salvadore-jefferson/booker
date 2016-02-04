/*
 * Copyright © 2015 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker.repo;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.jefferson.salvadore.booker.publication.Publication;
import com.jefferson.salvadore.booker.repo.file.FilePublicationRepository;

/**
 * An implementation of {@link PublicationManager}, for manipulating the inventory level of the
 * {@link Publication}s in a {@link FilePublicationRepository}
 * 
 * @author Salvadore Jefferson
 * @version 1.0.0 12-02-15
 *
 */
@Singleton
public class PublicationManagerImpl implements PublicationManager {

  private PublicationRepository repo;


  @Inject
  @Override
  public void setRepo(@Nonnull final PublicationRepository repo) {
    this.repo = checkNotNull(repo);
  }

  /**
   * Creates a new {@link PublicationManager} implementation with a
   * {@link SnapshoptTopPublicationReposiroty} as the default repository. Use the
   * {@link #setRepo(PublicationRepository)} to override the repository.
   */
  public PublicationManagerImpl(@Nonnull final PublicationRepository repo) {
    this.repo = repo;
  }

  @Override
  public void addStockToInventory(String title, Integer amountToAdd) throws IOException {
    final Integer newCount = repo.getInventoryCount(title) + amountToAdd;
    repo.setInventoryCount(title, newCount);

  }

  @Override
  public void removeStockFromInventory(String title, Integer amountToRemove) throws IOException {
    final Integer newCount = repo.getInventoryCount(title) - amountToRemove;
    repo.setInventoryCount(title, newCount);

  }

  @Override
  public Integer getInventoryCount(String title) throws IOException {
    return repo.getInventoryCount(title);
  }

}
