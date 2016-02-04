/*
 * Copyright © 2015 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker.util;

import static com.jefferson.salvadore.booker.Booker.CONFIG_DIR;

import java.io.IOException;

import com.google.inject.AbstractModule;
import com.jefferson.salvadore.booker.Booker;
import com.jefferson.salvadore.booker.repo.PublicationManager;
import com.jefferson.salvadore.booker.repo.PublicationManagerImpl;
import com.jefferson.salvadore.booker.repo.PublicationRepository;
import com.jefferson.salvadore.booker.repo.file.FilePublicationRepository;

/**
 * A utility class to inject dependencies needed to execute the {@link Booker} application
 * 
 * @author Salvadore Jefferson
 * @version 1.0.0 12-05-15
 *
 */
public class PublicationModule extends AbstractModule {

  @Override
  protected void configure() {
    try {
      bind(PublicationRepository.class)
          .toInstance(new FilePublicationRepository(CONFIG_DIR.resolve("publication-files")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    bind(PublicationManager.class).to(PublicationManagerImpl.class);
  }

}
