/*
 * Copyright © 2015 Salvadore Jefferson
 */
package com.jefferson.salvadore.booker;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jefferson.salvadore.booker.publication.Periodical;
import com.jefferson.salvadore.booker.repo.PublicationRepository;

/**
 * A simple service for subscribing to {@link Periodical}s through the {@link Booker} application,
 * via command line arguments.
 * 
 * @author Salvadore Jefferson
 * @version 1.0.0 12-13-15
 *
 */
@Singleton
public class SubscriptionService extends Thread {
  private long delay;
  private int issueCount;
  final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);
  private final PublicationRepository repo;
  private final String title;

  public SubscriptionService(@Nonnull final PublicationRepository repo, @Nonnull final String title,
      final int issueCount) {
    this.repo = checkNotNull(repo);
    this.title = checkNotNull(title);
    this.issueCount = issueCount;
  }

  private volatile static boolean endProcess = false;

  private void endProcess() {
    endProcess = true;
    interrupt();
  }

  /**
   * Prints out a message to the console about the type of {@link Periodical} a user subscribed to
   * as well as the title, issue number and remaining issues on the subscription before renewal is
   * required.
   * 
   * @param repo The repository to access the data from.
   * @param title The title of the Periodical subscribed to.
   * @param issueCount The number of issues to receive
   * @throws IOException If an issue occurs while accessing the repository.
   */
  public void subscribe() throws IOException {

    int counter = 1;
    final List<Periodical> periodicalList = repo.getByType(Periodical.class);
    Periodical matchedPeriodical = null;
    for (Periodical periodical : periodicalList) {
      if (periodical.getTitle().equals(title)) {
        matchedPeriodical = periodical;
        delay = getDelay(matchedPeriodical);
        logger.info("delay = {}", delay);
      }
    }
    if (matchedPeriodical.getPubFreq() == 0) {
      issueCount = 1;
      logger.info("There is no way to determine when the next issue of {} will ship",
          matchedPeriodical.getTitle());
    }
    while (!endProcess) {
      if (counter == issueCount) {
        endProcess();
      }
      // If delay is -1 or zero, a default value of 3 seconds is used
      if (delay == -1 || delay == 0) {
        delay = 3;
      }
      try {
        TimeUnit.SECONDS.sleep(delay);
      } catch (InterruptedException e) {
        logger.info("Interupted from sleep", e);
      }
      System.out
          .println(String.format("%s issue #%s of %s has arrived! You have %s issues remaining",
              matchedPeriodical.getType(), counter, matchedPeriodical.getTitle(),
              (issueCount - counter)));
      counter++;
    }
  }

  @Override
  public void run() {
    while (!endProcess) {
      try {
        subscribe();
      } catch (IOException e) {
        logger.warn("Something went wrong", e);
      }
    }
  }

  /**
   * Determines the amount of time the thread should sleep based on how often the {@link Periodical}
   * is distributed. The thread will sleep for one second per week between issue shipments.
   * 
   * @param periodical
   * @return
   */
  private long getDelay(@Nonnull final Periodical periodical) {
    int pubFreq = periodical.getPubFreq();
    switch (pubFreq) {
      case 52:
        delay = 1;
        break;
      case 12:
        delay = 4;
        break;
      default:
        delay = 1;
        break;
    }
    return delay;
  }
}
